/**
 * TextPopupController.java
 */
package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import main.AppManager;

/**
 * @author Hassan Mebed
 *
 * Controller class for TextPopupView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the TextPopupView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the TextPopupView.fxml file, such as a button. 
 */
public class TextPopupController extends Controller
{
	@FXML
	/** injected text area for displaying requested text */
	private TextArea text;
	
	/** the actual string of text that will be added to the text area */
	private String textAreaText = null;
	
	/**
	 * Parameterized constructor that calls the Controller super class constructor and initializes the 
	 * the string of text that will be added to the text area with passed parameter.
	 * 
	 * @param textAreaText
	 * 			the text requested to be displayed in the pop-up window.
	 */
	public TextPopupController(String textAreaText)
	{
		super();
		this.textAreaText = textAreaText;
	}
	
	@Override
	@FXML
	/**
	 * Initializes the text area with the string of text passed in the constructor's signature.
	 */
	public void initialize()
	{
		this.text.setText(this.textAreaText);
	}
	
	@FXML
	/**
	 * Called when user clicks on close button; notifies AppManager to close this child window.
	 */
	private void onClose()
	{
		AppManager.getInstance().notifyCloseChildWindow(this);
	}
}
