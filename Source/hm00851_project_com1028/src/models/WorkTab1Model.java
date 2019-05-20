/**
 * WorkTab1Model.java
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
 * A model class for WorkTabsController, specifically for all methods dealing with the sales tab or the retrieval of
 * sales from the application's database.
 */
public class WorkTab1Model extends Model
{
	public WorkTab1Model()
	{
		super();
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
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Sales_" + Integer.toString(product.getID()) + " order by Year ASC");
			
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
