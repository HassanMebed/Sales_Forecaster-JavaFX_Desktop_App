/**
 * AddSalesModel.java
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.Product;

/**
 * @author Hassan Mebed
 *
 * Model class for ManageSalesController class.
 * Retrieves all current sales records from the application's database.
 * Adds a given sales record to the application's database.
 * Executes a given update for any of the sales record's values.
 * Removes entire sales records.
 */
public class ManageSalesModel extends Model
{	
	public ManageSalesModel()
	{
		super();
	}
	
	/**
	 * Adds a product's/service's given sales record information to the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the year of sales.
	 * @param q1
	 * 			the year's quarter 1 sales.
	 * @param q2
	 * 			the year's quarter 2 sales.
	 * @param q3
	 * 			the year's quarter 3 sales.
	 * @param q4
	 * 			the year's quarter 4 sales.
	 * @return true if operation was successful, false if not.
	 */
	public boolean addSales(Product product, int year, double q1, double q2, double q3, double q4)
	{
		boolean success = true;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Sales_" + Integer.toString(product.getID()) + " where Year=?");
			query.setInt(1, year);
			
			ResultSet result = query.executeQuery();
			
			if(result.next() == true)
			{
				success = false;
				super.modelErrors = super.modelErrors + "*Sales record with year: " + year + " already exists." + "\n";
			}
			else
			{
				PreparedStatement insert = super.getDatabaseConnection().prepareStatement("insert into Sales_" + Integer.toString(product.getID()) + "(Year, Q1, Q2, Q3, Q4) values (?, ?, ?, ?, ?)");
				insert.setInt(1, year);
				insert.setDouble(2, q1);
				insert.setDouble(3, q2);
				insert.setDouble(4, q3);
				insert.setDouble(5, q4);
				insert.executeUpdate();
				insert.close();
			}
			
			result.close();
			query.close();
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
	 * Updates a product's/service's current stored value for a year's quarter 1 sales with new given value, on 
	 * the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the year of sales.
	 * @param q1
	 * 			the new quarter 1 sales value.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editQ1(Product product, int year, double q1)
	{
		boolean success = true;
		
		try
		{
			PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Sales_" + Integer.toString(product.getID()) + " set Q1=? where Year=?");
			update.setDouble(1, q1);
			update.setInt(2, year);
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
	 * Updates a product's/service's current stored value for a year's quarter 2 sales with new given value, on 
	 * the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the year of sales.
	 * @param q2
	 * 			the new quarter 2 sales value.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editQ2(Product product, int year, double q2)
	{
		boolean success = true;
		
		try
		{
			PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Sales_" + Integer.toString(product.getID()) + " set Q2=? where Year=?");
			update.setDouble(1, q2);
			update.setInt(2, year);
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
	 * Updates a product's/service's current stored value for a year's quarter 3 sales with new given value, on 
	 * the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the year of sales.
	 * @param q3
	 * 			the new quarter 3 sales value.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editQ3(Product product, int year, double q3)
	{
		boolean success = true;
		
		try
		{
			PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Sales_" + Integer.toString(product.getID()) + " set Q3=? where Year=?");
			update.setDouble(1, q3);
			update.setInt(2, year);
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
	 * Updates a product's/service's current stored value for a year's quarter 4 sales with new given value, on 
	 * the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the year of sales.
	 * @param q4
	 * 			the new quarter 4 sales value.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editQ4(Product product, int year, double q4)
	{
		boolean success = true;
		
		try
		{
			PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Sales_" + Integer.toString(product.getID()) + " set Q4=? where Year=?");
			update.setDouble(1, q4);
			update.setInt(2, year);
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
	 * Updates a year of sales for a product/service on the application's database with a new given one.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the year value that will be updated.
	 * @param newYear
	 * 			the new year value that will replace the old one.
	 * @return true if operation was successful, false if not.
	 */
	public boolean editYear(Product product, int year, int newYear)
	{
		boolean success = true;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select Year from Sales_" + Integer.toString(product.getID()) + " where Year=?");
			query.setInt(1, newYear);
			
			ResultSet result = query.executeQuery();
			
			if(result.next() == false)
			{
				PreparedStatement update = super.getDatabaseConnection().prepareStatement("update Sales_" + Integer.toString(product.getID()) + " set Year=? where Year=?");
				update.setInt(1, newYear);
				update.setInt(2, year);
				update.executeUpdate();
				update.close();
			}
			else
			{
				success = false;
				super.modelErrors = super.modelErrors + "*Entered year already exists." + "\n";
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
	 * Retrieves all stored sales records for a given product/service from the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @return set of all sales records for the product/service.
	 */
	public ResultSet getSales(Product product)
	{
		ResultSet result = null;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Sales_" + Integer.toString(product.getID()));
			
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
	 * Removes a product's/service's given sales record from the application's database.
	 * 
	 * @param product
	 * 			the current user's selected product/service.
	 * @param year
	 * 			the sales record's year of sales.
	 * @return true if operation was successful, false if not.
	 */
	public boolean removeSales(Product product, int year)
	{
		boolean success = true;
		
		try
		{
			PreparedStatement remove = super.getDatabaseConnection().prepareStatement("delete from Sales_" + Integer.toString(product.getID()) + " where Year=?");
			
			remove.setInt(1, year);
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
