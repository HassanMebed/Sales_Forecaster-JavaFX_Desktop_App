/**
 * Model.java
 */
package models;

import java.sql.Connection;

import main.AppManager;

/**
 * @author Hassan Mebed
 *
 * Every model should inherit from this class in order to be identified as a model in this application and 
 * utilize the application's database connection and the main model variables and methods.
 */
public abstract class Model
{
	/** stores the last error originating from the application's database */
	protected String connectionError = null;
	
	/** stores any model related errors, which are mainly due to validation or verification errors */
	protected String modelErrors = null;
	
	/** stores the application's database connection */
	private Connection databaseConnection = null;
	
	/**
	 * Default constructor that initializes the database connection variable with AppManager's 
	 * set-up database connection and the model errors string with an empty, starting-point string.
	 */
	public Model()
	{
		try
		{
			this.databaseConnection = AppManager.getInstance().getDatabaseConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.modelErrors = "";
	}
	
	/**
	 * Reverts all model errors to their original, default values.
	 */
	public final void clearErrors()
	{
		this.connectionError = null;
		this.modelErrors = "";
	}
	
	/**
	 * @return Current database related error.
	 */
	public final String getConnectionError()
	{
		return this.connectionError;
	}
	
	/**
	 * @return Application's database connection.
	 */
	protected final Connection getDatabaseConnection()
	{
		return this.databaseConnection;
	}
	
	/**
	 * @return Current model errors.
	 */
	public final String getModelErrors()
	{
		return this.modelErrors;
	}

}