/**
 * ChildStages.java
 */
package main;

/**
 * @author Hassan Mebed
 *
 * Contains constant values for each available child stage including stage's fxml resource location 
 * and name.
 */
public enum ChildStages
{
	ADD_PRODUCT("Add Product/Service", "/views/AddProductView.fxml"), 
	REMOVE_PRODUCT("Remove Product/Service", "/views/RemoveProductView.fxml"), 
	MANAGE_SALES("Add/Remove/Edit Sales", "/views/ManageSalesView.fxml"),
	EDIT_FORECASTS("Edit Forecasts", "/views/EditForecastsView.fxml"),
	TEXT_POPUP(null, "/views/TextPopupView.fxml");
	
	/** child stage's name */
	private String name = null;
	
	/** child stage's url */
	private String url = null;
	
	/**
	 * Parameterized constructor that initializes above global variables with the passed parameters.
	 * 
	 * @param name
	 * 			child stage's given name.
	 * @param url
	 * 			child stage's url.
	 */
	private ChildStages(final String name, final String url)
	{
		this.name = name;
		this.url = url;
	}
	
	/**
	 * @return child stage's name.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * @return child stage's url.
	 */
	public String getUrl()
	{
		return this.url;
	}
}
