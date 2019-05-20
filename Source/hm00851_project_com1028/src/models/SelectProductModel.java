/**
 * SelectProductModel.java
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
 * Model class for SelectProductController class.
 * Retrieves all of a user's product/service names from the application's database.
 * Retrieves the product id of a user's selected product/service. 
 */
public class SelectProductModel extends Model
{
	public SelectProductModel()
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
			
			result.close();
			query.close();
			
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
	 * Retrieves the product id of a user's selected product's/service's name from the application's database.
	 * 
	 * @param user
	 * 			the current user.
	 * @param name
	 * 			the user's selected product's/service's name.
	 * @return the product id retrieved from the database query.
	 */
	public int getProductID(User user, String name)
	{
		int productID = 0;
		
		try
		{
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select ProductID from Products where UserID=? and ProductName=?");
			query.setString(1, user.getID());
			query.setString(2, name);
			
			ResultSet result = query.executeQuery();
			result.next();
			productID = result.getInt("ProductID");
			
			result.close();
			query.close();
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();		
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();
		}
		
		return productID;
	}
		
}
 