/**
 * WorkTab3Model.java
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.Product;

/**
 * @author Hassan Mebed
 *
 * A model class for WorkTabsController, specifically for all methods dealing with the comparison tab.
 * Retrieves all forecasts records for a product/service that match a certain year of sales from the 
 * application's database.
 * Retrieves a product's/service's specific forecast record matching a year of sales from the application's 
 * database using its stored date.  
 */
public class WorkTab3Model extends Model
{
	public WorkTab3Model()
	{
		super();
	}
	
	/**
	 * Retrieves all forecasts records matching a product's/service's given year of sales from the application's
	 * database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the product's/service's year of sales.
	 * @return set of all matching forecasts records for the given product's/service's year of sales. 
	 */
	public ResultSet getMatchingForecasts(Product product, int year)
	{
		ResultSet result = null;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Forecasts_" + Integer.toString(product.getID()) + " where Year=?");
			query.setInt(1, year);
			
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
	 * Retrieves one specific forecast record that matches the given product's/service's year of sales from the
	 * application's database using that forecast record's stored date.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the product's/service's year of sales.
	 * @param date
	 * 			the matching forecasts record's exact stored date.
	 * @return the entire record of the matching forecast.
	 */
	public ResultSet getSpecificForecast(Product product, int year, String date)
	{
		ResultSet result = null;	    
		
		try
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		    Date parsedDate = dateFormat.parse(date);
		    Timestamp selectedDate = new java.sql.Timestamp(parsedDate.getTime());
		    
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Forecasts_" + Integer.toString(product.getID()) + " where Year=? and DateStored=?");
			query.setInt(1, year);
			query.setTimestamp(2, selectedDate);
			
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
}
