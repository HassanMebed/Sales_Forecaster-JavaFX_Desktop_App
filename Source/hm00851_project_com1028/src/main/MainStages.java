/**
 * MainStages.java
 */
package main;

/**
 * @author Hassan Mebed
 *
 * Contains constant fxml url value for each available main window stage.
 */
public enum MainStages
{
	LOGIN("/views/LoginView.fxml"), 
	REGISTER("/views/RegisterView.fxml"), 
	SELECT_PRODUCT("/views/SelectProductView.fxml"),
	WORK_TABS("/views/WorkTabsView.fxml");
	
	/** main stage's url */
	private String url = null;
	
	/**
	 * Parameterized constructor that initializes a main stage's fxml url.
	 * 
	 * @param url
	 * 			main stage's url.
	 */
	private MainStages(final String url)
	{
		this.url = url;
	}
	
	/**
	 * @return main stage's url.
	 */
	public String getUrl()
	{
		return this.url;
	}
}
