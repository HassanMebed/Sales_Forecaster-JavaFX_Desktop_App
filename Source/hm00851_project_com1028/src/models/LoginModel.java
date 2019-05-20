/**
 * LoginModel.java
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Hassan Mebed
 * 
 * Model class for LoginController class.
 * Verifies user input for logging in to system and retrieves user details from application's database.
 */
public class LoginModel extends Model
{	
	public LoginModel()
	{
		super();
	}
	
	/**
	 * Checks if given string contains any spaces.
	 * 
	 * @param value
	 * 			string of text to be checked.
	 * @return true if string contains a space, false if not.
	 */
	public boolean checkForSpaces(String value)
	{
		boolean check = false;
		
		for(int i = 0; i < value.length(); i++)
		{
			char character = value.charAt(i);
			
			if(character == ' ')
			{
				check = true;
				break;
			}
		}
		
		return check;
	}
	
	/**
	 * Verifies given user id and password for login by querying the application's database for a direct match.
	 * 
	 * @param userID
	 * 			given user id.
	 * @param password
	 * 			given user password.
	 * @return true if user id and password match a record in the application's database, false if not.
	 */
	public boolean login(String userID, String password)
	{
		boolean success = true;
		
		try
		{	
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Users where UserID=? and Password=?");
			query.setString(1, userID);
			query.setString(2, password);
			
			ResultSet result = query.executeQuery();
			
			if(result.next() == false)
			{
				success = false;
				
				super.modelErrors = super.modelErrors + "*Incorrect username or password." + "\n";
			}
			
			result.close();
			query.close();
		}
		catch (Exception e)
		{
			success = false;
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();
		}
		
		return success;
	}
	
	/**
	 * Retrieves logged in user's details from the application's database using their login user id and password.
	 * 
	 * @param userID
	 * 			given user id.
	 * @param password
	 * 			given user password
	 * @return the entire record of user's information on the application's database.
	 */
	public ResultSet getUserInfo(String userID, String password)
	{
		ResultSet result = null;
		
		try
		{	
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select FirstName, LastName from Users where UserID=? and Password=?");
			query.setString(1, userID);
			query.setString(2, password);
			
			result = query.executeQuery();
		}
		catch (Exception e)
		{	
			StringWriter error = new StringWriter();			
			e.printStackTrace(new PrintWriter(error));
			
			super.connectionError = error.toString();
		}
		
		return result;
	}

}
