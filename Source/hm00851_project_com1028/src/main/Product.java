/**
 * Product.java
 */
package main;

/**
 * @author Hassan Mebed
 *
 * Contains the current user's selected product's/service's info with getters and setters.
 */
public class Product
{
	/** current selected product's/service's id */
	private int ID = 0;
	
	/** current selected product's/service's name */
	private String name = null;
	
	public Product()
	{
		
	}
	
	/**
	 * @return current selected product's/service's id. 
	 */
	public int getID()
	{
		return this.ID;
	}
	
	/**
	 * @return selected product's/service's name.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Initializes current selected product's/service's id.
	 * @param ID
	 * 			current selected product's/service's id to be set.
	 */
	protected void setID(int ID)
	{
		this.ID = ID;
	}
	
	/**
	 * Initializes current selected product's/service's name.
	 * @param name
	 * 			current selected product's/service's name to be set.
	 */
	protected void setName(String name)
	{
		this.name = name;
	}
}
