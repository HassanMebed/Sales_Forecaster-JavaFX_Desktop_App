/**
 * RegisterModel.java
 */
package models;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Hassan Mebed
 *
 * Model class for RegisterController class.
 * Verifies user input for registering into system before adding the user record to the application's database.
 */
public class RegisterModel extends Model
{	
	public RegisterModel()
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
	 * Verifies given user's user id, and then upon successful verification, adds given user record to the
	 * application's database.
	 * 
	 * @param firstName
	 * 			new user's first name.
	 * @param lastName
	 * 			new user's last name.
	 * @param userID
	 * 			new user's username.
	 * @param password
	 * 			new user's password.
	 * @return true if operation was successful, false if not.
	 */
	public boolean register(String firstName, String lastName, String userID, String password)
	{
		boolean success = true;
		
		try
		{				
			PreparedStatement query = super.getDatabaseConnection().prepareStatement("select * from Users where UserID=?");
			query.setString(1, userID);
			
			ResultSet result = query.executeQuery();
			
			if(result.next() == true)
			{
				success = false;
				
				super.modelErrors = super.modelErrors + "*Entered username already exists." + "\n";
			}
			else
			{
				PreparedStatement insert = super.getDatabaseConnection().prepareStatement("insert into Users (UserID, FirstName, LastName, Password) values (?, ?, ?, ?)");
				insert.setString(1, userID);
				insert.setString(2, firstName);
				insert.setString(3, lastName);
				insert.setString(4, password);
				
				insert.executeUpdate();
				insert.close();
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

}
