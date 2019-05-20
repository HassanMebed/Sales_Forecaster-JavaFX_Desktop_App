/**
 * GeneratedForecastModel.java
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import main.Product;

/**
 * @author Hassan Mebed
 *
 * Model class for GeneratedForecastController class.
 * Stores the current generated forecasts on the appliaction's database accordingly.
 */
public class GeneratedForecastModel extends Model
{
	public GeneratedForecastModel()
	{
		super();
	}
	
	/**
	 * Extracts forecast values from given linked hash map and stores them on the application's database
	 * accordingly.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param forecasts
	 * 			linked hash map containing maximum of two years with their corresponding quarterly forecast values.
	 * @return true if operation was successful, false if not.
	 */
	public boolean storeForecasts(Product product, Map<Integer, double[]> forecasts)
	{
		boolean success = true;
		
		try
		{	
			for(Map.Entry<Integer, double[]> forecast: forecasts.entrySet())
			{	
				Date date= new Date();
				long time = date.getTime();			
				Timestamp datetime = new Timestamp(time);
				
				if(forecast.getValue().length == 1)
				{
					PreparedStatement insert = super.getDatabaseConnection().prepareStatement("insert into Forecasts_" + Integer.toString(product.getID()) + "(DateStored, Year, Q4) values (?, ?, ?)");
					insert.setTimestamp(1, datetime);
					insert.setInt(2, forecast.getKey());
					insert.setDouble(3, forecast.getValue()[0]);
					insert.executeUpdate();
					insert.close();
				}
				else if(forecast.getValue().length == 2)
				{
					PreparedStatement insert = super.getDatabaseConnection().prepareStatement("insert into Forecasts_" + Integer.toString(product.getID()) + "(DateStored, Year, Q3, Q4) values (?, ?, ?, ?)");
					insert.setTimestamp(1, datetime);
					insert.setInt(2, forecast.getKey());
					insert.setDouble(3, forecast.getValue()[0]);
					insert.setDouble(4, forecast.getValue()[1]);
					insert.executeUpdate();
					insert.close();
				}
				else if(forecast.getValue().length == 3)
				{
					PreparedStatement insert = super.getDatabaseConnection().prepareStatement("insert into Forecasts_" + Integer.toString(product.getID()) + "(DateStored, Year, Q2, Q3, Q4) values (?, ?, ?, ?, ?)");
					insert.setTimestamp(1, datetime);
					insert.setInt(2, forecast.getKey());
					insert.setDouble(3, forecast.getValue()[0]);
					insert.setDouble(4, forecast.getValue()[1]);
					insert.setDouble(5, forecast.getValue()[2]);
					insert.executeUpdate();
					insert.close();
				}
				else if(forecast.getValue().length == 4)
				{
					PreparedStatement insert = super.getDatabaseConnection().prepareStatement("insert into Forecasts_" + Integer.toString(product.getID()) + "(DateStored, Year, Q1, Q2, Q3, Q4) values (?, ?, ?, ?, ?, ?)");
					insert.setTimestamp(1, datetime);
					insert.setInt(2, forecast.getKey());
					insert.setDouble(3, forecast.getValue()[0]);
					insert.setDouble(4, forecast.getValue()[1]);
					insert.setDouble(5, forecast.getValue()[2]);
					insert.setDouble(6, forecast.getValue()[3]);
					insert.executeUpdate();
					insert.close();
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
}
