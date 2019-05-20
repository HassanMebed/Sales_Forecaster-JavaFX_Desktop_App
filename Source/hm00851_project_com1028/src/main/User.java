/**
 * User.java
 */
package main;

/**
 * @author Hassan Mebed
 *
 * Contains the current user's info with getters and setters.
 */
public class User
{
	/** current user's id */
	private String ID = null;
	
	/** current user's first name */
	private String firstName = null;
	
	/** current user's last name */
	private String lastName = null;
	
	public User()
	{
		
	}
	
	/**
	 * @return current user's first name.
	 */
	public String getFirstName()
	{
		return this.firstName;
	}
	
	/**
	 * @return current user's id.
	 */
	public String getID()
	{
		return this.ID;
	}
	
	/**
	 * @return current user's last name.
	 */
	public String getLastName()
	{
		return this.lastName;
	}
	
	/**
	 * Initializes current user's first name.
	 * @param firstName 
	 * 			current user's first name to be set
	 */
	protected void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	/**
	 * Initializes current user's id.
	 * @param ID
	 * 			current user's id to be set.
	 */
	protected void setID(String ID)
	{
		this.ID = ID;
	}
	
	/**
	 * Initializes current user's last name
	 * @param lastName
	 * 			current user's last name to be set.
	 */
	protected void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

}
