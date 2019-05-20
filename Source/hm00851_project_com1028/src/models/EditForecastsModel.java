/**
 * EditForecastsModel.java
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
 * Model class for EditForecastsController class.
 * Retrieves all current stored forecasts from the application's database.
 * Executes a given update for any of the stored quarterly forecasts.
 * Removes entire forecast records.
 */
public class EditForecastsModel extends Model
{
	public EditForecastsModel()
	{
		super();
	}
	
	/**
	 * Updates a product's/service's current stored value for a year's forecasted quarter 1 sales with new given 
	 * value, on the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param date
	 * 			the exact date the given forecast was stored.
	 * @param year
	 * 			the forecast record's year of forecasts.
	 * @param q1
	 * 			the new quarter 1 forecast value.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editQ1(Product product, String date, int year, double q1)
	{
		boolean success = true;
		Timestamp insertDate = null;
		
		try 
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		    Date parsedDate = dateFormat.parse(date);
		    insertDate = new java.sql.Timestamp(parsedDate.getTime());	    
		} 
		catch(Exception e) 
		{ 
			e.printStackTrace();
		}
		
		try
		{
			PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Forecasts_" + Integer.toString(product.getID()) + " set Q1=? where DateStored=? and Year=?");
			update.setDouble(1, q1);
			update.setTimestamp(2, insertDate);
			update.setInt(3, year);
			update.executeUpdate();
			update.close();
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
	 * Updates a product's/service's current stored value for a year's forecasted quarter 2 sales with new given 
	 * value, on the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param date
	 * 			the exact date the given forecast was stored.
	 * @param year
	 * 			the forecast record's year of forecasts.
	 * @param q2
	 * 			the new quarter 2 forecast value.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editQ2(Product product, String date, int year, double q2)
	{
		boolean success = true;
		Timestamp insertDate = null;
		
		try 
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		    Date parsedDate = dateFormat.parse(date);
		    insertDate = new java.sql.Timestamp(parsedDate.getTime());	    
		} 
		catch(Exception e) 
		{ 
			e.printStackTrace();
		}
		
		try
		{
			PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Forecasts_" + Integer.toString(product.getID()) + " set Q2=? where DateStored=? and Year=?");
			update.setDouble(1, q2);
			update.setTimestamp(2, insertDate);
			update.setInt(3, year);
			update.executeUpdate();
			update.close();
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
	 * Updates a product's/service's current stored value for a year's forecasted quarter 3 sales with new given 
	 * value, on the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param date
	 * 			the exact date the given forecast was stored.
	 * @param year
	 * 			the forecast record's year of forecasts.
	 * @param q3
	 * 			the new quarter 3 forecast value.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editQ3(Product product, String date, int year, double q3)
	{
		boolean success = true;
		Timestamp insertDate = null;
		
		try 
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		    Date parsedDate = dateFormat.parse(date);
		    insertDate = new java.sql.Timestamp(parsedDate.getTime());	    
		} 
		catch(Exception e) 
		{ 
			e.printStackTrace();
		}
		
		try
		{
			PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Forecasts_" + Integer.toString(product.getID()) + " set Q3=? where DateStored=? and Year=?");
			update.setDouble(1, q3);
			update.setTimestamp(2, insertDate);
			update.setInt(3, year);
			update.executeUpdate();
			update.close();
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
	 * Updates a product's/service's current stored value for a year's forecasted quarter 4 sales with new given 
	 * value, on the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param date
	 * 			the exact date the given forecast was stored.
	 * @param year
	 * 			the forecast record's year of forecasts.
	 * @param q4
	 * 			the new quarter 4 forecast value.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editQ4(Product product, String date, int year, double q4)
	{
		boolean success = true;		
		Timestamp insertDate = null;
		
		try 
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		    Date parsedDate = dateFormat.parse(date);
		    insertDate = new java.sql.Timestamp(parsedDate.getTime());	    
		} 
		catch(Exception e) 
		{ 
			e.printStackTrace();
		}
		
		try
		{
			PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Forecasts_" + Integer.toString(product.getID()) + " set Q4=? where DateStored=? and Year=?");
			update.setDouble(1, q4);
			update.setTimestamp(2, insertDate);
			update.setInt(3, year);
			update.executeUpdate();
			update.close();
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
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Forecasts_" + Integer.toString(product.getID()));
			
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
	 * Removes a product's/service's given forecast record from the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param date
	 * 			the exact date the given forecast was stored.
	 * @param year
	 * 			the forecast record's year of forecasts.
	 * @return true if operation was successful, false if not.
	 */
	public boolean removeForecast(Product product, String date, int year)
	{
		boolean success = true;	
		Timestamp insertDate = null;
		
		try 
		{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		    Date parsedDate = dateFormat.parse(date);
		    insertDate = new java.sql.Timestamp(parsedDate.getTime());	    
		} 
		catch(Exception e) 
		{ 
			e.printStackTrace();
		}
		
		try
		{
			PreparedStatement remove = super.getDatabaseConnection().prepareStatement("delete from Forecasts_" + Integer.toString(product.getID()) + " where DateStored=? and Year=?");
			
			remove.setTimestamp(1, insertDate);
			remove.setInt(2, year);
			remove.executeUpdate();
			remove.close();
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
