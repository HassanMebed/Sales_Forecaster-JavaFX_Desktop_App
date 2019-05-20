/**
 * RemoveProductController.java
 */
package controllers;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import main.AppManager;
import models.RemoveProductModel;

/**
 * @author Hassan Mebed
 * 
 * Controller class for RemoveProductView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the RemoveProductView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the RemoveProductView.fxml file, such as a button.
 */
public class RemoveProductController extends Controller
{
	@FXML
	/** injected selection box with items of type String that represent user's stored products/services in the application's database */
	private ComboBox<String> selection;
	
	@FXML
	/** injected button for user to click when he/she want to remove selected product/service from the application's database. */
	private Button remove;
	
	/** controller's corresponding model */
	private RemoveProductModel model = null;
	
	/**
	 * Default constructor that calls the Controller super class constructor and initializes the 
	 * controller's model.
	 */
	public RemoveProductController()
	{
		super();
		this.model = new RemoveProductModel();
	}
	
	@Override
	@FXML
	/**
	 * Initializes the selection box with all the names of the user's stored products/services.
	 * Notifies AppManager if any model errors need to be displayed.
	 */
	public void initialize()
	{
		List<String> productNames = this.model.getProductNames(AppManager.getInstance().getUser(this));
		
		if(this.selection.getSelectionModel().isEmpty())
		{
			this.remove.setDisable(true);
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
	 * Called when user clicks done button.
	 * Notifies AppManager to close this child window.
	 */
	private void onDone()
	{
		AppManager.getInstance().notifyCloseChildWindow(this);
	}
	
	@FXML
	/**
	 * Called when user selects one of their available products/services; enables remove button to be clicked.
	 */
	private void onItemSelected()
	{
		this.remove.setDisable(false);
	}
	
	@FXML
	/**
	 * Called when user clicks remove button to remove selected product/service.
	 * Checks if user actually selected a product/service to remove, and if yes, sends confirmation alert
	 * to make sure user wants to remove this product/service. If user confirms by clicking ok, selected
	 * product/service name will be sent to model to use to remove entire product/service records.
	 * If removal was a success, both main window and this view will be re-initialized to show the changes.
	 * If any model errors occur, AppManager will be notified to display them.
	 */
	private void onRemove()
	{
		Optional<ButtonType> confirmation = AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.CONFIRMATION), "CONFIRMATION", "Are You Sure You Want to Delete this Product/Service?", "On deletion, all of the product's/service's data will be permanently removed from the system.");
			
		if(confirmation.get() == ButtonType.OK)
		{
			boolean success = this.model.removeProduct(AppManager.getInstance().getUser(this), this.selection.getValue());
				
			if(success == false)
			{
				AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
			}
			else
			{
				AppManager.getInstance().notifyReloadMainWindow(this);
				AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.INFORMATION), "SUCCESS", "Selected Product/Service has been Removed" , null);
				this.selection.getItems().clear();
				this.initialize();
			}
		}
	}
		
}
