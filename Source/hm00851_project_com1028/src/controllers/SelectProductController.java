/**
 * SelectProductController.java
 */
package controllers;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import main.AppManager;
import main.ChildStages;
import models.SelectProductModel;

/**
 * @author Hassan Mebed
 *
 * Controller class for SelectProductView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the SelectProductView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the SelectProductView.fxml file, such as a button.
 */
public class SelectProductController extends Controller
{
	@FXML
	/** injected selection box with items of type String that represent user's stored products/services in the application's database */
	private ComboBox<String> selection;
	
	@FXML
	/** injected label containing user greeting */
	private Label welcome;
	
	@FXML
	/** injected button for user to click when he/she want to access data of selected product/service from the application's database and continue to next stage. */
	private Button submit;
	
	/** controller's corresponding model */
	private SelectProductModel model = null;
	
	/**
	 * Default constructor that calls the Controller super class constructor and initializes the 
	 * controller's model.
	 */
	public SelectProductController()
	{
		super();
		this.model = new SelectProductModel();
	}
	
	@Override
	@FXML
	/**
	 * Initializes welcome label with greeting to user's first name.
	 * Initializes the selection box with all the names of the user's stored products/services.
	 * Notifies AppManager if any model errors need to be displayed.
	 */
	public void initialize()
	{
		List<String> productNames = this.model.getProductNames(AppManager.getInstance().getUser(this));
		
		this.welcome.setText("Welcome, " + AppManager.getInstance().getUser(this).getFirstName() + ".");
		this.selection.getItems().clear();
		
		if(this.selection.getSelectionModel().isEmpty())
		{
			this.submit.setDisable(true);
		}
		
		if(productNames == null)
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
		else
		{
			if(!productNames.contains(""))
			{
				ObservableList<String> productNamesList = FXCollections.observableList(productNames);
				this.selection.setItems(productNamesList);
			}
		}
	}
	
	@FXML
	/**
	 * Called when user clicks the 'About' option in the top menu; notifies AppManager to display the 
	 * 'About' child window.
	 */
	private void onAbout()
	{
		AppManager.getInstance().notifyShowAbout(this);
	}
	
	@FXML
	/**
	 * Called when user clicks on add product/service button.
	 * Notifies AppManager to open ADD_PRODUCT child window.
	 */
	private void onAddProduct()
	{
		AppManager.getInstance().notifyOpenNewWindow(this, ChildStages.ADD_PRODUCT);
	}
	
	@FXML
	/**
	 * Called when user clicks the 'Exit' option in the top menu; notifies AppManager to initiate the
	 * application closure procedure.
	 */
	private void onExit()
	{
		AppManager.getInstance().exit(this);
	}
	
	@FXML
	/**
	 * Called when user clicks the 'Guide' option in the top menu; notifies AppManager to display a child 
	 * window with helpful info related to this window.
	 */
	private void onGuide()
	{
		String text = "If you have added/setup any of your products/services to the system," + "\n" + "they should appear as a list of choices under 'Select Product/Service'" + "\n\n" +
					"If you still haven't added/setup any of your products/services," + "\n" + "you can do so by clicking on 'Add a Product/Service' and following the" + "\n" + 
					"steps afterwards. Note that you do not have to enter any sales at this stage," + "\n" + "but if you do, you can edit them at any time by double clicking on them." + "\n\n" + 
					"If you wish to remove any of the products/services you previously added to the system," + "\n" + "click on 'Remove a Product/Service' then choose it from the list" + "\n" + 
					"of your products/services and click 'Remove'.";
		
		AppManager.getInstance().notifyTextPopup(this, "Product/Service Management Guide", text);
	}
	
	@FXML
	/**
	 * Called when user selects one of their available products/services; enables submit/continue button to be clicked.
	 */
	private void onItemSelected()
	{
		this.submit.setDisable(false);
	}
	
	@FXML
	/**
	 * Called when user clicks the 'Logout' option in the top menu; notifies AppManager to initiate 
	 * logout procedure.
	 */
	private void onLogout()
	{		
		AppManager.getInstance().notifyLogout(this);		
	}
	
	@FXML
	/**
	 * Called when user clicks on remove product/service button; notifies AppManager to open 
	 * REMOVE_PRODUCT child window.
	 */
	private void onRemoveProduct()
	{
		AppManager.getInstance().notifyOpenNewWindow(this, ChildStages.REMOVE_PRODUCT);
	}
	
	@FXML
	/**
	 * Called when user selects product/service to access from selection box and clicks continue button.
	 * Checks if user actually selected a product/service before continuing.
	 * Gets product id of selected product/service by passing its name and current user's id to the model.
	 * If product id is retrieved successfully, notifies AppManager that product/service has been selected
	 * by passing the selected product/service name and retrieved product id to it for initialization and
	 * appropriate stage transition.
	 * If any model errors occur, AppManager will be notified to display them instead.
	 */
	private void onSelect()
	{
		int productID = this.model.getProductID(AppManager.getInstance().getUser(this), this.selection.getValue());
			
		if(productID == 0)
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
		else
		{
			AppManager.getInstance().notifyProductSelection(this, productID, this.selection.getValue());
			AppManager.getInstance().notifyTransitionToWorkTabs(this);
		}		
	}
	
}