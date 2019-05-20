/**
 * AddProductModel.java
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.User;

/**
 * @author Hassan Mebed
 * 
 * Model class for AddProductController class.
 * Creates a new product/service in the application's database using the given user's id and new product's name.
 *
 */
public class AddProductModel extends Model
{
	public AddProductModel()
	{
		super();
	}
	
	/**
	 * Does several operations to fully add a new product/service in the application's database.
	 * First checks if passed product/service name is already recorded for this user in the application's database
	 * i.e if user has already added this product/service before. If not, inserts the new product/service name into
	 * the application's database under current user's id and gets the product id generated for it because it will 
	 * be required for the last two operations which are:
	 * 1. to create sales table for this product/service, so that the entire sales table can be identified using the
	 *    product id.
	 * 2. to create forecasts table for this product/service, so that the entire forecasts table can be identified
	 * 	  using the product id
	 *
	 * @param user
	 * 			the current user
	 * @param name
	 * 			the name of the new product/service
	 * @return product id of the new product/service added to the application's database (will be 0 if operation was unsuccessful)
	 */
	public int addProduct(User user, String name)
	{
		int productIDGenerated = 0;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select ProductName from Products where UserID=? and ProductName=?");
			query.setString(1, user.getID());
			query.setString(2, name);
			
			ResultSet result = query.executeQuery();
			
			if(result.next() == true)
			{
				super.modelErrors = super.modelErrors + "*Product/Service name already exists in your account." + "\n";
			}
			else
			{
				PreparedStatement insert = super.getDatabaseConnection().prepareStatement("insert into Products(UserID, ProductName) values (?, ?)");
				insert.setString(1, user.getID());
				insert.setString(2, name);
				insert.executeUpdate();	
				insert.close();
				
				PreparedStatement query2 = super.getDatabaseConnection().prepareStatement("select ProductID from Products where UserID=? and ProductName=?");
				query2.setString(1, user.getID());
				query2.setString(2, name);
				
				ResultSet result2 = query2.executeQuery();
				result2.next();
				
				productIDGenerated = result2.getInt("ProductID");
				
				result2.close();
				query2.close();
				
				String createSalesTable = "create table if not exists Sales_" + Integer.toString(productIDGenerated) + "\n"
										+ "(" + "\n"
										+ "Year int unique," + "\n"
										+ "Q1 double," + "\n"
										+ "Q2 double," + "\n"
										+ "Q3 double," + "\n"
										+ "Q4 double," + "\n"
										+ "primary key(Year)" + "\n"
										+ ")";
				String createForecastsTable = "create table if not exists Forecasts_" + Integer.toString(productIDGenerated) + "\n"
						+ "(" + "\n"
						+ "DateStored datetime not null," + "\n"
						+ "Year int not null," + "\n"
						+ "Q1 double," + "\n"
						+ "Q2 double," + "\n"
						+ "Q3 double," + "\n"
						+ "Q4 double," + "\n"
						+ "primary key(DateStored, Year)" + "\n"
						+ ")";
				
				PreparedStatement createSalesTableStatement = super.getDatabaseConnection().prepareStatement(createSalesTable);
				createSalesTableStatement.executeUpdate();
				
				PreparedStatement createForecastsTableStatement = super.getDatabaseConnection().prepareStatement(createForecastsTable);
				createForecastsTableStatement.executeUpdate();
			}
		}
		catch(Exception e)
		{
			productIDGenerated = 0;
			
			StringWriter error = new StringWriter();			
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();
		}
		
		return productIDGenerated;
	}
	
}
