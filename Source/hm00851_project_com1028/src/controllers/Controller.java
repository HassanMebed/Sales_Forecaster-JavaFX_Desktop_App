/**
 * Controller.java
 */
package controllers;

import javafx.fxml.FXML;

/**
 * @author Hassan Mebed
 *
 * Every controller should inherit from this class in order to be identified as a controller in this
 * application and utilize main controller variables and methods if any.
 */
public abstract class Controller
{
	public Controller()
	{
	
	}
	
	@FXML
	/**
	 * FXML's initialize method; is called automatically directly after constructor of controller class is
	 * instantiated. Intended to initialize all necessary GUI elements appropriately.
	 */
	public void initialize()
	{
		
	}
	
}
