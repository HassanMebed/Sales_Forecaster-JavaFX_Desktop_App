/**
 * LoginController.java
 */
package controllers;


import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.AppManager;
import models.LoginModel;

/**
 * @author Hassan Mebed
 *
 * Controller class for LoginView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the LoginView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the LoginView.fxml file, such as a button.
 */
public class LoginController extends Controller
{
	@FXML
	/** injected text input field for user's id */
	private TextField username;
	
	@FXML
	/** injected password input field for user's password */
	private PasswordField password;
	
	@FXML
	/** injected vertical layout container for adding elements to it vertically, has all field titles in it */
	private VBox titles;
	
	@FXML
	/** injected vertical layout container for adding elements to it vertically, has all input fields in it */
	private VBox fields;
	
	/** controller's corresponding model */
	private LoginModel model = null;
	
	/**
	 * Default constructor that calls the Controller super class constructor and initializes the 
	 * controller's model.
	 */
	public LoginController()
	{
		super();
		model = new LoginModel();
	}
	
	/**
	 * Validates entered user id and password inputs, and displays on-screen messages for input errors
	 * accordingly.
	 * 
	 * @param username
	 * 			the entered user id.
	 * @param password
	 * 			the entered password.
	 * @return true if both inputs are valid, false if not.	
	 */
	private boolean checkInput(String username, String password)
	{
		boolean check = true;
		
		if(this.fields.getChildren().size() != 2)
		{
			this.titles.getChildren().clear();
			this.fields.getChildren().clear();
			
			Label usernameTitle = new Label("Username");
			usernameTitle.setMinHeight(27);
			usernameTitle.setFont(Font.font("Arial"));
			usernameTitle.setStyle("-fx-font-weight: bold");
			
			Label passwordTitle = new Label("Password");
			passwordTitle.setMinHeight(27);
			passwordTitle.setFont(Font.font("Arial"));
			passwordTitle.setStyle("-fx-font-weight: bold");
			
			this.titles.getChildren().add(usernameTitle);
			this.titles.getChildren().add(passwordTitle);
			this.fields.getChildren().add(this.username);
			this.fields.getChildren().add(this.password);			
		}
		
		if(username.isEmpty())
		{
			check = false;
			
			Label usernameError = new Label("*Required");
			Label placeholder = new Label();
			
			usernameError.setMinHeight(27);
			usernameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(1, placeholder);
			this.fields.getChildren().add(1, usernameError);
		}		
		else if(this.model.checkForSpaces(username))
		{
			check = false;
			
			Label usernameError = new Label("*Cannot have spaces");
			Label placeholder = new Label();
			
			usernameError.setMinHeight(27);
			usernameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(1, placeholder);
			this.fields.getChildren().add(1, usernameError);
		}
		else if(!username.matches("[a-zA-Z0-9]+"))
		{
			check = false;
			
			Label usernameError = new Label("*Must contain only letters and/or numbers");
			Label placeholder = new Label();
			
			usernameError.setMinHeight(27);
			usernameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(1, placeholder);
			this.fields.getChildren().add(1, usernameError);
		}
		
		if(password.isEmpty())
		{
			check = false;
			
			Label passwordError = new Label("*Required");
			
			passwordError.setMinHeight(27);
			passwordError.setTextFill(Color.RED);
			passwordError.setMinHeight(27);
			this.fields.getChildren().add(passwordError);
		}
		else if(this.model.checkForSpaces(password))
		{
			check = false;
			
			Label passwordError = new Label("*Cannot have spaces");
			
			passwordError.setMinHeight(27);
			passwordError.setTextFill(Color.RED);
			passwordError.setMinHeight(27);		
			this.fields.getChildren().add(passwordError);
		}
		else if(!password.matches("[a-zA-Z0-9]+"))
		{
			check = false;
			
			Label passwordError = new Label("*Must contain only letters and/or numbers");
			
			passwordError.setMinHeight(27);
			passwordError.setTextFill(Color.RED);
			passwordError.setMinHeight(27);
			this.fields.getChildren().add(passwordError);
		}
		
		return check;
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
	 * Submits inputed user info when user presses on enter keyboard button.
	 * 
	 * @param keyEvent
	 * 			any keyboard click event.
	 */
	private void onEnterClicked(KeyEvent keyEvent)
	{
		if(keyEvent.getCode() == KeyCode.ENTER)
		{
			this.onLogin();
		}
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
		String text = "If you have created an account, login to it " + "\n" + "here by entering your unique username and your password." +"\n" +
					"Otherwise, click on 'Register' to create your account.";
		
		AppManager.getInstance().notifyTextPopup(this, "Login Guide", text);
	}
	
	@FXML
	/**
	 * Called when user submits login details. 
	 * validates input, then verifies input, using the model, and if input is both successfully validated 
	 * and verified, notifies AppManager that user has successfully logged in by passing user's details for 
	 * initialization and letting AppManager transition stage accordingly.
	 * Notifies AppManager of any model errors that need to be displayed.
	 */
	private void onLogin()
	{
		String capturedUsername = this.username.getText();
		String capturedPassword = this.password.getText();
		
		if(this.checkInput(capturedUsername, capturedPassword))
		{
			boolean loggedIn = this.model.login(capturedUsername, capturedPassword);
			
			if(loggedIn == true)
			{
				ResultSet userInfo = this.model.getUserInfo(capturedUsername, capturedPassword);
				
				if(userInfo == null)
				{
					AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
				}
				else
				{
					try
					{
						userInfo.next();
						
						String firstName = userInfo.getString("FirstName");
						String lastName = userInfo.getString("LastName");
						
						userInfo.close();
						
						AppManager.getInstance().notifyLogin(this, capturedUsername, firstName, lastName);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				if(!this.model.getModelErrors().equals(""))
				{
					AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Invalid Input", this.model.getModelErrors());
				}			
				else if(this.model.getConnectionError() != null)
				{					
					AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
				}
				
				this.model.clearErrors();
			}	
		}
	}
	
	@FXML
	/**
	 * Called when user clicks on button to register; notifies AppManager to transition stage accordingly.
	 */
	private void onRegister()
	{
		AppManager.getInstance().notifyTransitionToRegister(this);
	}
		
}
