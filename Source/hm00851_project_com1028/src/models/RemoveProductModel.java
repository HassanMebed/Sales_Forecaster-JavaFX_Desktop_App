/**
 * RemoveProductModel.java
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import main.User;

/**
 * @author Hassan Mebed
 *
 * Model class for RemoveProductController class.
 * Retrieves all of a user's product/service names from the application's database.
 * Removes all possible records of a user's product/service. 
 */
public class RemoveProductModel extends Model
{
	public RemoveProductModel()
	{
		super();
	}
	
	/**
	 * Retrieves all of a given user's stored product/service names from the application's database.
	 * 
	 * @param user
	 * 			the current user.
	 * @return list of all the names of a user's stored products/services.
	 */
	public List<String> getProductNames(User user)
	{
		List<String> productNames = new ArrayList<String>();
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select ProductName from Products where UserID=?");
			query.setString(1, user.getID());
			
			ResultSet result = query.executeQuery();
			
			while(result.next())
			{
				productNames.add(result.getString("ProductName"));
			}
			
			if(productNames.isEmpty())
			{
				productNames.add("");
			}
			
		}
		catch(Exception e)
		{
			productNames = null;
			StringWriter error = new StringWriter();
			
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();			
		}
		
		return productNames;
	}
	
	/**
	 * Removes a user's given product's/service's record as well as all of its sales and forecasts records from
	 * the application's database.
	 * 
	 * @param user
	 * 			the current user.
	 * @param name
	 * 			the name of the user's selected product/service to be removed.
	 * @return true if operation was successful, false if not.
	 */
	public boolean removeProduct(User user, String name)
	{
		boolean success = true;
		
		try
		{
			int productID = 0;
			
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select ProductID from Products where UserID=? and ProductName=?");
			query.setString(1, user.getID());
			query.setString(2, name);
			
			ResultSet result = query.executeQuery();
			result.next();
			productID = result.getInt("ProductID");
			
			result.close();
			query.close();
			
			PreparedStatement removeProductName = super.getDatabaseConnection().prepareStatement("delete from Products where ProductID=?");
			removeProductName.setInt(1, productID);		
			removeProductName.executeUpdate();			
			removeProductName.close();
			
			PreparedStatement removeProductForecasts = super.getDatabaseConnection().prepareStatement("drop table Forecasts_" + Integer.toString(productID));			
			removeProductForecasts.executeUpdate();
			removeProductForecasts.close();
			
			PreparedStatement removeProductSales = super.getDatabaseConnection().prepareStatement("drop table Sales_" + Integer.toString(productID));			
			removeProductSales.executeUpdate();
			removeProductSales.close();
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
