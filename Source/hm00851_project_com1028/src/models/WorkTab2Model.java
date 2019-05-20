/**
 * WorkTab2Model.java
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;

import main.Product;

/**
 * @author Hassan Mebed
 *
 * A model class for WorkTabsController, specifically for all methods dealing with the forecasts tab.
 * Checks if current user's selected product's/service's sales records can be used to generate a proper forecast by 
 * querying the application's database.
 * Checks if the selected product's/service's last inputed year of sales is complete or not by querying the application's
 * database.
 * Retrieves all of the selected product's/service's forecasts records from the application's database and generates 
 * new forecasts for the product/service.
 * Retrieves the current generated forecast data from the application's database.
 */
public class WorkTab2Model extends Model
{
	/** linked hash map for storing generated forecast data; key is year and corresponding value is double array holding max 
	 *  4 quarter forecasts. 
	 *  Should have max of 2 years and their corresponding forecasts.
	 */
	private LinkedHashMap<Integer, double[]> forecastData = null;
	
	public WorkTab2Model()
	{
		super();
		this.forecastData = new LinkedHashMap<Integer, double[]>();
	}
	
	/**
	 * Checks if a given product/service has at least 3 years of complete sales history, and that all recorded 
	 * years of sales history are consecutive i.e no gaps between any of the years.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @return true if the sales history holds for the two aforementioned properties, false if not.
	 */
	public boolean checkIntegrityOfSalesData(Product product)
	{
		boolean check = true;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Sales_" + Integer.toString(product.getID()) + " order by Year DESC");			
			ResultSet result = query.executeQuery();
			boolean salesDataSufficient = true;
			int completeYears = 0;
			
			if(result.next() == true)
			{
				if(result.getDouble("Q4") > 0.0)
				{
					completeYears = completeYears + 1;
				}
				
				while(result.next())
				{				
					if(result.getDouble("Q1") > 0.0 || result.getDouble("Q2") > 0.0 || result.getDouble("Q3") > 0.0 || result.getDouble("Q4") > 0.0)
					{
						completeYears = completeYears + 1;
					}
				}
			}
			else
			{
				salesDataSufficient = false;
				super.modelErrors = super.modelErrors + "*No sales history to work with." + "\n";
			}
			
			if(completeYears < 3)
			{
				salesDataSufficient = false;
				super.modelErrors = super.modelErrors + "*Insufficient amount of sales history. Product/Service must have at least three years of sales history." + "\n";
			}
			
			if(salesDataSufficient == true)
			{
				result.beforeFirst();
				result.next();
				
				int year = result.getInt("Year");
				
				while(result.next())
				{
					year = year - 1;
					
					if(result.getInt("Year") != year)
					{
						check = false;
						super.modelErrors = super.modelErrors + "*Recorded sales history years are not consecutive." + "\n";
						break;
					}					
				}
			}
			else
			{
				check = false;
			}
		}
		catch(Exception e)
		{
			check = false;
			StringWriter error = new StringWriter();
			
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();
		}
		
		return check;
	}
	
	/**
	 * Checks if a given product's/service's last inputed year of sales is complete i.e has a value > 0 for
	 * quarter 4 sales.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @return 1 if the sales history is complete, -1 if not complete yet and 0 if operation is unsuccessful.
	 */
	public byte checkIfCurrentYearComplete(Product product)
	{
		byte check = 0;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Sales_" + Integer.toString(product.getID()) + " order by Year DESC");			
			ResultSet result = query.executeQuery();
			result.next();
			
			if(result.getDouble("Q4") > 0.0)
			{
				check = 1;
				super.modelErrors = super.modelErrors + "*Current year already has a complete recorded sales history." + "\n";
			}
			else
			{
				check = -1;
			}
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();
			
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();
		}
		
		return check;
	}
	
	/**
	 * Retrieves all stored forecasts records for a given product/service from the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @return set of all forecasts records for the product/service.
	 */
	public ResultSet getForecasts(Product product)
	{
		ResultSet result = null;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Forecasts_" + Integer.toString(product.getID()) + " order by Year ASC");
			
			result = query.executeQuery();
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();
			
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();	
		}
		
		return result;
	}
	
	/**
	 * @return linked hash map with current generated forecasts data.
	 */
	public LinkedHashMap<Integer, double[]> getGeneratedForecast()
	{
		return this.forecastData;
	}
	
	/**
	 * Initializes the linked hash map with generated forecast data that depends on the option parameter given to
	 * this forecast generation method. Available options are:
	 * 1. forecast the unknown sales for the current year(latest recorded year) of sales only.
	 * 2. forecast all of the next year's quarterly sales only.
	 * 3. forecast both the unknown sales for the current year of sales and next year's quarterly sales at the same
	 *    time.
	 * @param option
	 * 			the current user's selected option for the forecast generation procedure.
	 * @param product
	 * 			the current user's selected product/service.
	 * @return true if operation was successful, false if not.
	 */
	public boolean generateForecast(byte option, Product product)
	{
		boolean success = true;
		List<Double> sales = new ArrayList<Double>();
		byte unknowns = 0;
		int year = 0;
		ForecastResult forecastResult = null;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Sales_" + Integer.toString(product.getID()) + " order by Year ASC");			
			ResultSet result = query.executeQuery();
			
			if(option == 1 || option == 3)
			{
				while(result.next())
				{
					if(result.isLast())
					{
						if(result.getDouble("Q3") > 0.0)
						{
							sales.add(result.getDouble("Q1"));
							sales.add(result.getDouble("Q2"));
							sales.add(result.getDouble("Q3"));
							
						}
						else if(result.getDouble("Q2") > 0.0 && result.getDouble("Q3") == 0.0)
						{
							sales.add(result.getDouble("Q1"));
							sales.add(result.getDouble("Q2"));
						}
						else if(result.getDouble("Q1") > 0.0 && result.getDouble("Q2") == 0.0 && result.getDouble("Q3") == 0.0)
						{
							sales.add(result.getDouble("Q1"));
						}
					}
					else
					{
						sales.add(result.getDouble("Q1"));
						sales.add(result.getDouble("Q2"));
						sales.add(result.getDouble("Q3"));
						sales.add(result.getDouble("Q4"));
					}
				}
			}
			else
			{
				while(result.next())
				{
					sales.add(result.getDouble("Q1"));
					sales.add(result.getDouble("Q2"));
					sales.add(result.getDouble("Q3"));
					sales.add(result.getDouble("Q4"));
				}
			}
			
			result.last();
			
			year = result.getInt("Year");
			
			double[] timeSeriesData = new double[sales.size()];
			
			for(int i = 0; i < timeSeriesData.length; i++)
			{
				timeSeriesData[i] = sales.get(i);
			}
			
			switch(option)
			{
				case 1:
				{			
					if(result.getDouble("Q3") > 0.0)
					{
						unknowns = 1;
					}
					else if(result.getDouble("Q2") > 0.0 && result.getDouble("Q3") == 0.0)
					{
						unknowns = 2;
					}
					else if(result.getDouble("Q1") > 0.0 && result.getDouble("Q2") == 0.0 && result.getDouble("Q3") == 0.0)
					{
						unknowns = 3;
					}
					else
					{
						unknowns = 4;
					}
					
					forecastResult = Arima.forecast_arima(timeSeriesData, unknowns, new ArimaParams(1, 0, 0, 0, 1, 0, 4));							
					double[] forecastsThisYear = forecastResult.getForecast();
					
					for(int i = 0; i < forecastsThisYear.length; i++)
					{
						forecastsThisYear[i] = Math.round(forecastsThisYear[i] * 100.0)/100.0;
					}
					
					this.forecastData.put(year, forecastsThisYear);
					
					break;
				}
			
				case 2:
				{
					int nextYear = year + 1;
					unknowns = 4;
					
					forecastResult = Arima.forecast_arima(timeSeriesData, unknowns, new ArimaParams(1, 0, 0, 0, 1, 0, 4));							
					double[] forecastsNextYear = forecastResult.getForecast();
					
					for(int i = 0; i < forecastsNextYear.length; i++)
					{
						forecastsNextYear[i] = Math.round(forecastsNextYear[i] * 100.0)/100.0;
					}
					
					this.forecastData.put(nextYear, forecastsNextYear);
					
					break;
				}
			
				case 3:
				{
					int nextYear = year + 1;
					
					if(result.getDouble("Q3") > 0.0)
					{
						unknowns = 1;
					}
					else if(result.getDouble("Q2") > 0.0 && result.getDouble("Q3") == 0.0)
					{
						unknowns = 2;
					}
					else if(result.getDouble("Q1") > 0.0 && result.getDouble("Q2") == 0.0 && result.getDouble("Q3") == 0.0)
					{
						unknowns = 3;
					}
					else 
					{
						unknowns = 4;
					}
					
					unknowns = (byte) (unknowns + 4);
					
					forecastResult = Arima.forecast_arima(timeSeriesData, unknowns, new ArimaParams(1, 0, 0, 0, 1, 0, 4));							
					double[] forecastsAllYears = forecastResult.getForecast();				
					double[] forecastsThisYear = Arrays.copyOfRange(forecastsAllYears, 0, forecastsAllYears.length - 4);
					double[] forecastsNextYear = Arrays.copyOfRange(forecastsAllYears, forecastsAllYears.length - 4, forecastsAllYears.length);
					
					for(int i = 0; i < forecastsThisYear.length; i++)
					{
						forecastsThisYear[i] = Math.round(forecastsThisYear[i] * 100.0)/100.0;
					}
					
					for(int i = 0; i < forecastsNextYear.length; i++)
					{
						forecastsNextYear[i] = Math.round(forecastsNextYear[i] * 100.0)/100.0;
					}
					
					this.forecastData.put(year, forecastsThisYear);
					this.forecastData.put(nextYear, forecastsNextYear);
					
					break;
				}
			}		
		}
		catch(Exception e)
		{
			success = false;
			StringWriter error = new StringWriter();
			
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();
		}
		
		return success;
	}
	
	/**
	 * Clears the linked hash map's current stored forecast data, so that it can be used for storing another
	 * set of generated forecast data. 
	 */
	public void resetForecastData()
	{
		this.forecastData.clear();
	}
}