/**
 * RegisterController.java
 */
package controllers;

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
import models.RegisterModel;

/**
 * @author Hassan Mebed
 *
 * Controller class for RegisterView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the RegisterView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the RegisterView.fxml file, such as a button.
 */
public class RegisterController extends Controller
{
	@FXML
	/** injected text input field for user's first name */
	private TextField firstName;
	
	@FXML
	/** injected text input field for user's last name */
	private TextField lastName;
	
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
	private RegisterModel model = null;
	
	/**
	 * Default constructor that calls the Controller super class constructor and initializes the 
	 * controller's model.
	 */
	public RegisterController()
	{
		super();
		this.model = new RegisterModel();
	}
	
	/**
	 * Validates entered first name, last name, user id and password inputs, and displays on-screen 
	 * messages for input errors accordingly.
	 * 
	 * @param firstName
	 * 			the entered first name.
	 * @param lastName
	 * 			the entered last name.
	 * @param username
	 * 			the entered user id.
	 * @param password
	 * 			the entered user password.
	 * @return true if all four input fields are valid, false if not.
	 */
	private boolean checkInput(String firstName, String lastName, String username, String password)
	{
		boolean check = true;
		
		if(this.fields.getChildren().size() != 4)
		{
			this.titles.getChildren().clear();
			this.fields.getChildren().clear();
			
			Label firstNameTitle = new Label("First Name");
			firstNameTitle.setMinHeight(27);
			firstNameTitle.setFont(Font.font("Arial"));
			firstNameTitle.setStyle("-fx-font-weight: bold");
			
			Label lastNameTitle = new Label("Last Name");
			lastNameTitle.setMinHeight(27);
			lastNameTitle.setFont(Font.font("Arial"));
			lastNameTitle.setStyle("-fx-font-weight: bold");
			
			Label usernameTitle = new Label("Username");
			usernameTitle.setMinHeight(27);
			usernameTitle.setFont(Font.font("Arial"));
			usernameTitle.setStyle("-fx-font-weight: bold");
			
			Label passwordTitle = new Label("Password");
			passwordTitle.setMinHeight(27);
			passwordTitle.setFont(Font.font("Arial"));
			passwordTitle.setStyle("-fx-font-weight: bold");
			
			this.titles.getChildren().add(firstNameTitle);
			this.titles.getChildren().add(lastNameTitle);
			this.titles.getChildren().add(usernameTitle);
			this.titles.getChildren().add(passwordTitle);
			this.fields.getChildren().add(this.firstName);
			this.fields.getChildren().add(this.lastName);
			this.fields.getChildren().add(this.username);
			this.fields.getChildren().add(this.password);	
		}
		
		if(firstName.isEmpty())
		{
			check = false;
			
			Label firstNameError = new Label("*Required");
			Label placeholder = new Label();
			
			firstNameError.setMinHeight(27);
			firstNameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(1, placeholder);
			this.fields.getChildren().add(1, firstNameError);
		}		
		else if(this.model.checkForSpaces(firstName))
		{
			check = false;
			
			Label firstNameError = new Label("*Cannot have spaces");
			Label placeholder = new Label();
			
			firstNameError.setMinHeight(27);
			firstNameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(1, placeholder);
			this.fields.getChildren().add(1, firstNameError);
		}
		else if(!firstName.matches("[a-zA-Z]+"))
		{
			check = false;
			
			Label firstNameError = new Label("*Must contain only letters and/or numbers");
			Label placeholder = new Label();
			
			firstNameError.setMinHeight(27);
			firstNameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(1, placeholder);
			this.fields.getChildren().add(1, firstNameError);
		}
		else if(firstName.length() > 50)
		{
			check = false;
			
			Label firstNameError = new Label("*Too long");
			Label placeholder = new Label();
			
			firstNameError.setMinHeight(27);
			firstNameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(1, placeholder);
			this.fields.getChildren().add(1, firstNameError);
		}
		
		if(lastName.isEmpty())
		{
			check = false;
			
			Label lastNameError = new Label("*Required");
			Label placeholder = new Label();
			
			lastNameError.setMinHeight(27);
			lastNameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(this.fields.getChildren().indexOf(this.lastName) + 1, placeholder);
			this.fields.getChildren().add(this.fields.getChildren().indexOf(this.lastName) + 1, lastNameError);
		}		
		else if(this.model.checkForSpaces(lastName))
		{
			check = false;
			
			Label lastNameError = new Label("*Cannot have spaces");
			Label placeholder = new Label();
			
			lastNameError.setMinHeight(27);
			lastNameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(this.fields.getChildren().indexOf(this.lastName) + 1, placeholder);
			this.fields.getChildren().add(this.fields.getChildren().indexOf(this.lastName) + 1, lastNameError);
		}
		else if(!lastName.matches("[a-zA-Z]+"))
		{
			check = false;
			
			Label lastNameError = new Label("*Must contain only letters and/or numbers");
			Label placeholder = new Label();
			
			lastNameError.setMinHeight(27);
			lastNameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(this.fields.getChildren().indexOf(this.lastName) + 1, placeholder);
			this.fields.getChildren().add(this.fields.getChildren().indexOf(this.lastName) + 1, lastNameError);
		}
		else if(lastName.length() > 50)
		{
			check = false;
			
			Label lastNameError = new Label("*Too long");
			Label placeholder = new Label();
			
			lastNameError.setMinHeight(27);
			lastNameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(this.fields.getChildren().indexOf(this.lastName) + 1, placeholder);
			this.fields.getChildren().add(this.fields.getChildren().indexOf(this.lastName) + 1, lastNameError);
		}
		
		if(username.isEmpty())
		{
			check = false;
			
			Label usernameError = new Label("*Required");
			Label placeholder = new Label();
			
			usernameError.setMinHeight(27);
			usernameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(this.fields.getChildren().indexOf(this.username) + 1, placeholder);
			this.fields.getChildren().add(this.fields.getChildren().indexOf(this.username) + 1, usernameError);
		}		
		else if(this.model.checkForSpaces(username))
		{
			check = false;
			
			Label usernameError = new Label("*Cannot have spaces");
			Label placeholder = new Label();
			
			usernameError.setMinHeight(27);
			usernameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(this.fields.getChildren().indexOf(this.username) + 1, placeholder);
			this.fields.getChildren().add(this.fields.getChildren().indexOf(this.username) + 1, usernameError);
		}
		else if(!username.matches("[a-zA-Z0-9]+"))
		{
			check = false;
			
			Label usernameError = new Label("*Must contain only letters and/or numbers");
			Label placeholder = new Label();
			
			usernameError.setMinHeight(27);
			usernameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(this.fields.getChildren().indexOf(this.username) + 1, placeholder);
			this.fields.getChildren().add(this.fields.getChildren().indexOf(this.username) + 1, usernameError);
		}
		else if(username.length() > 62)
		{
			check = false;
			
			Label usernameError = new Label("*Too long");
			Label placeholder = new Label();
			
			usernameError.setMinHeight(27);
			usernameError.setTextFill(Color.RED);
			placeholder.setMinHeight(27);
			this.titles.getChildren().add(this.fields.getChildren().indexOf(this.username) + 1, placeholder);
			this.fields.getChildren().add(this.fields.getChildren().indexOf(this.username) + 1, usernameError);
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
		else if(password.length() > 32)
		{
			check = false;
			
			Label passwordError = new Label("*Too long");
			
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
	 * Called when user clicks on back button.
	 * Notifies AppManager to transition back to login stage.
	 */
	private void onBack()
	{
		AppManager.getInstance().notifyTransitionToLogin(this);
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
			this.onRegister();
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
		String text = "In order to successfully register, you must correctly" + "\n" + "enter your first and last name followed by a unique username," + "\n" +
					"which can only contain combinations of letters and numbers (no spaces)," + "\n" + "then finally, your account password which has the same" + "\n" +
					"limitations as your username.";
		
		AppManager.getInstance().notifyTextPopup(this, "Registration Guide", text);
	}
	
	@FXML
	/**
	 * Called when user submits registration details. 
	 * validates input, then verifies input, using the model, and if input is both successfully validated 
	 * and verified, notifies AppManager to display alert showing that the user has successfully registered. 
	 * Notifies AppManager of any model errors that need to be displayed.
	 */
	private void onRegister()
	{
		String capturedFirstName = this.firstName.getText();
		String capturedLastName = this.lastName.getText();
		String capturedUsername = this.username.getText();
		String capturedPassword = this.password.getText();
		
		if (this.checkInput(capturedFirstName, capturedLastName, capturedUsername, capturedPassword))
		{
			String firstNameFirstLetter = null;
			String lastNameFirstLetter = null;
			String firstNameFormatted = null;
			String lastNameFormatted = null;
			
			capturedFirstName.toLowerCase();
			firstNameFirstLetter = capturedFirstName.substring(0, 1).toUpperCase();
			firstNameFormatted = firstNameFirstLetter + capturedFirstName.substring(1);
			
			capturedLastName.toLowerCase();
			lastNameFirstLetter = capturedLastName.substring(0, 1).toUpperCase();
			lastNameFormatted = lastNameFirstLetter + capturedLastName.substring(1);
			
			boolean registered = this.model.register(firstNameFormatted, lastNameFormatted, capturedUsername, capturedPassword);
			
			if(registered == true)
			{
				AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.INFORMATION), "SUCCESS", "You Have Successfully Registered", "You can now go back and login to get started.");	
				
				this.firstName.clear();
				this.lastName.clear();
				this.username.clear();
				this.password.clear();
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
}
