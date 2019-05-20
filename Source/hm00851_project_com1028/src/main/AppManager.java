/**
 * AppManager.java
 */
package main;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedHashMap;
import java.util.Optional;

import controllers.AddProductController;
import controllers.Controller;
import controllers.GeneratedForecastController;
import controllers.LoginController;
import controllers.SelectProductController;
import controllers.TextPopupController;
import controllers.WorkTabsController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Hassan Mebed
 *
 * AppManager is:
 * 1. the main starting point/entry point of this JavaFX application i.e the main class that sets up
 *    the primary stage.
 * 2. a singleton class that manages the transition of scenes of the primary stage, creation of child 
 * 	  stages and displaying of error/info messages.
 * 3. the only class that creates and provides the connection to the application's MySQL database. 
 * 4. the class that stores and provides the current user's info and his/her selected product's/service's
 *    info.
 */
public class AppManager extends Application 
{
	/** stores the one and only running instance of this class */
	private static AppManager appManagerInstance = null;
	
	/** stores the application's MySQL database connection */
	private Connection databaseConnection = null;
	
	/** stores the application's User object which contains the current user's info */
	private User user = null;
	
	/** stores the application's Product object which contains the current user's selected product's/service's info */
	private Product product = null;
	
	/** stores the current fxml layout resource i.e the current view of the main application window */
	private FXMLLoader viewLoaderMainWindow = null;
	
	/** stores the current fxml layout resource i.e the current view of a child window */
	private FXMLLoader viewLoaderChildWindow = null;
	
	/** stores the passed layout of the main window loaded by the FXMLLoader */
	private Parent currentWindow = null;
	
	/** stores the passed layout of a child window loaded by the FXMLLoader */
	private Parent newWindow = null;
	
	/** stores the primary stage i.e the actual window of this JavaFX application and its component attributes */
	private Stage appStage = null;
	
	/** stores the current child stage i.e a new opened window and its component attributes */
	private Stage childStage = null;
	
	/**
	 * Default constructor that initializes the AppManager singleton object with the static instance 
	 * of this class and instantiates the application's User and Product objects. 
	 */
	public AppManager()
	{
		appManagerInstance = this;
		this.user = new User();
		this.product = new Product();
	}
	
	/**
	 * Creates a custom alert object that contains an expandable text area containing a system 
	 * exception stack trace.
	 * 
	 * @param error
	 * 			the string that contains an exception's printed stack trace.
	 * @return the custom alert object containing the formatted text area.
	 */
	public Alert createExceptionAlert(String error)
	{
		Alert alert = new Alert(Alert.AlertType.ERROR);
		TextArea errorText = new TextArea(error);
		GridPane gridContainer = new GridPane();
		
		errorText.setEditable(false);
		errorText.setWrapText(true);
		errorText.setMaxWidth(Double.MAX_VALUE);
		errorText.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(errorText, Priority.ALWAYS);
		GridPane.setHgrow(errorText, Priority.ALWAYS);
		gridContainer.setMaxWidth(Double.MAX_VALUE);
		gridContainer.add(errorText, 0, 0);
		alert.getDialogPane().setExpandableContent(gridContainer);
		
		return alert;
	}
	
	/**
	 * Notifies AppManager to initiate the closure procedure of the application which involves closing 
	 * the database connection then closing the main JavaFx primary stage.
	 * 
	 * @param controller
	 * 			the controller that a close/exit command came from; parameter is unused and is only there 
	 * 			to ensure that a controller instance called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void exit(Controller controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
			
		try
		{
			databaseConnection.close();  
		}
		catch (Exception e)
		{
			e.printStackTrace(); 
		}
			
		this.appStage.close();	
			
	}
	
	/**
	 * @return the application's database connection.
	 */
	public Connection getDatabaseConnection()
	{
		return this.databaseConnection;
	}
	
	/**
	 * @return this instance of AppManager.
	 * Synchronized key word used to ensure that only one object/instance is requesting the instance of
	 * this class at a time.
	 */
	public static synchronized AppManager getInstance()
	{	
		return appManagerInstance;
	}
	
	/**
	 * @param controller
	 * 			the controller that is requesting access to the application's Product object; parameter is 
	 * 			unused and is only there to ensure that a controller instance called this method.
	 * @return the application's Product object.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public Product getProduct(Controller controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		return this.product;
	}
	
	/**
	 * @param controller
	 * 			the controller that is requesting access to the application's User object; parameter is 
	 * 			unused and is only there to ensure that a controller instance called this method.
	 * @return the application's User object.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public User getUser(Controller controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		return this.user;
	}
	
	/**
	 * Constructs and instance of the application in the JavaFX Application Thread.
	 * 
	 * @param args
	 * 			command-line arguments passed for runtime.
	 */
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	/**
	 * Notifies AppManager to call a method to launch a specific child window for showing information 
	 * related to this application.
	 * 
	 * @param controller
	 * 			the controller that was running when the about menu option was clicked; parameter is
	 * 			unused and is only there to ensure that a controller instance called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyShowAbout(Controller controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		String text = "Sales Forecaster v0.1 (May 2019)." + "\n\n" + "Developed by Hassan Mebed." + "\n\n" +
					"Sales Forecaster is a software that assists you" + "\n" + "in forecasting your product's/service's quarterly sales." + "\n" +
					"It does so by using the product's/service's past" + "\n" + "quarterly sales history to project its next year's and/or" + "\n" + 
					"current year's unknown sales.";
		
		this.notifyTextPopup(controller, "About Sales Forecaster", text);
	}
	
	/**
	 * Notifies AppManager to close the currently open child stage and 
	 * then as a result, set the childStage variable to null.
	 *  
	 * @param controller
	 * 			the controller belonging to a child stage that the close/exit/done/cancel command came 
	 * 			from; parameter is unused and is only there to ensure that a controller instance 
	 * 			called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyCloseChildWindow(Controller controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.childStage.close();
		this.childStage = null;
	}
	
	/**
	 * Notifies AppManager to create and show a child stage showing a generated forecast.
	 * @param controller
	 * 			the work tabs controller that handled the generation of the forecast; parameter is
	 * 			unused and is only there to ensure that a work tabs controller instance called this method.	
	 * @param forecastData
	 * 			the linked hash map generated from work tabs model which contains one or two sets of 
	 * 			forecasts with the year as the key and a double array as the corresponding forecast 
	 * 			values. 
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyForecastGenerated(WorkTabsController controller, LinkedHashMap<Integer, double[]> forecastData) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		GeneratedForecastController controllerForecast = new GeneratedForecastController(forecastData);	
		this.childStage = new Stage();
		
		this.childStage.setResizable(false);
		
		try
		{
			this.viewLoaderChildWindow = new FXMLLoader(getClass().getResource("/views/GeneratedForecastView.fxml"));
			
			this.viewLoaderChildWindow.setController(controllerForecast);
			this.viewLoaderChildWindow.load();	
			this.newWindow = this.viewLoaderChildWindow.getRoot();
			this.childStage.setTitle("Generated Forecast");
			this.childStage.getIcons().add(new Image("file:resources/forecastGeneratedIcon.png"));
			this.childStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
			{
		          public void handle(WindowEvent event) 
		          {
		        	  Controller mainWindowController = viewLoaderMainWindow.<Controller>getController();
		        	  mainWindowController.initialize(); 
		          }
		    });        
			this.childStage.setScene(new Scene(this.newWindow));
			this.childStage.initModality(Modality.WINDOW_MODAL);
			this.childStage.initOwner(this.appStage);
			this.childStage.setX(this.appStage.getX() + 200);
			this.childStage.setY(this.appStage.getY() + 100);
			this.childStage.show();			
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
		}				
	}
	
	/**
	 * Notifies AppManager that a user has successfully logged in, so as a result, initialize the User 
	 * object with the logged in user's info and transition to the SELECT_PRODUCT stage.
	 * 
	 * @param controller
	 * 			the login controller that handled the login details of the user; parameter is unused and 
	 * 			is only there to ensure that a login controller instance called this method.	
	 * @param userID
	 * 			the user id entered by the user.
	 * @param firstName
	 * 			the first name that was returned as a result of querying the application's database for 
	 * 			user's first name using his/her id.
	 * @param lastName
	 * 			the last name that was returned as a result of querying the application's database for 
	 * 			user's last name using his/her id.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyLogin(LoginController controller, String userID, String firstName, String lastName) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.setUser(userID, firstName, lastName);
		this.currentWindow = null;
		this.notifyTransitionToSelectProduct(controller);
	}
	
	/**
	 * Notifies AppManager that the current user has requested to logout, so as a result, clear the
	 * application's User and Product objects by setting their attributes to null and transition main
	 * window back to LOGIN stage.
	 * @param controller
	 * 			the controller that handled the logout request made by the user; parameter is unused and 
	 * 			is only there to ensure that a controller instance called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyLogout(Controller controller) throws NullPointerException
	{	
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.setUser(null, null, null);
		this.setProduct(0, null);			
		this.notifyTransitionToLogin(controller);
	}
	
	/**
	 * Notifies AppManager to create and show a new child window from the available child windows in the
	 * ChildStages enum.
	 * @param controller
	 * 			the controller that handled the request of opening the child window; parameter is unused 
	 * 			and is only there to ensure that a controller instance called this method.
	 * @param child
	 * 			the enum that specifies which child window is to be created and shown.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyOpenNewWindow(Controller controller, ChildStages child) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.newWindow = null;
		this.childStage = new Stage();
		this.childStage.setResizable(false);
		
		try
		{
			this.viewLoaderChildWindow = new FXMLLoader(getClass().getResource(child.getUrl()));
			this.viewLoaderChildWindow.load();	
			this.newWindow = this.viewLoaderChildWindow.getRoot();
			this.childStage.setTitle(child.getName());
			
			switch(child)
			{
			case ADD_PRODUCT: 
				this.childStage.getIcons().add(new Image("file:resources/addProductIcon.png"));
				break;
			
			case REMOVE_PRODUCT:
				this.childStage.getIcons().add(new Image("file:resources/removeItemIcon.png"));
				break;
			
			case MANAGE_SALES:
				this.childStage.getIcons().add(new Image("file:resources/manageSalesIcon.png"));
				break;
				
			case EDIT_FORECASTS: 
				this.childStage.getIcons().add(new Image("file:resources/editForecastIcon.png"));
				break;
				
			default:
				break;
			}
			
			this.childStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
			{
		          public void handle(WindowEvent event) 
		          {
		        	  Controller mainWindowController = viewLoaderMainWindow.<Controller>getController();
		        	  mainWindowController.initialize(); 
		          }
		    });        
			this.childStage.setScene(new Scene(this.newWindow));
			this.childStage.initModality(Modality.WINDOW_MODAL);
			this.childStage.initOwner(this.appStage);
			this.childStage.setX(this.appStage.getX() + 200);
			this.childStage.setY(this.appStage.getY() + 100);
			this.childStage.show();			
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
		}	
	}
	
	/**
	 * Notifies AppManager that the current user has selected one of his/her products/services to access, 
	 * so as a result, initialize the Product object with the selected product's/service's info.
	 * 
	 * @param controller
	 * 			the controller that handled the user's product/service selection; parameter is unused and 
	 * 			is only there to ensure that a controller instance called this method.
	 * @param productID
	 * 			the product/service id that was returned as a result of querying the application's database
	 * 			for the product/service id using the product's/service's name and current user's id.
	 * @param name
	 * 			the name of the product/service selected by the user
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyProductSelection(Controller controller, int productID, String name) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.setProduct(productID, name);
	}
	
	/**
	 * Notifies AppManager to refresh the main window by calling its current controller's fxml initialize 
	 * method; used for reloading main window from a child window.
	 * @param controller
	 * 			the controller that requested for the main window to be refreshed; parameter is unused and
	 * 			is only there to ensure that a controller instance called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyReloadMainWindow(Controller controller) throws NullPointerException
	{	
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		Controller mainWindowController = this.viewLoaderMainWindow.<Controller>getController();
		mainWindowController.initialize();
	}
	
	/**
	 * Notifies AppManager to create and show a specific child window for displaying text passed 
	 * through the method's parameters.
	 *  
	 * @param controller
	 * 			the controller that handled the request for opening the aforementioned child window; 
	 * 			parameter is unused and is only there to ensure that a controller instance called this 
	 * 			method. 
	 * @param title
	 * 			the title of the window to be shown.
	 * @param text
	 * 			the text in the body of the window to be shown.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyTextPopup(Controller controller, String title, String text) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		TextPopupController controllerTextPopup = new TextPopupController(text);	
		this.childStage = new Stage();
		
		this.childStage.setResizable(false);
		
		try
		{
			this.viewLoaderChildWindow = new FXMLLoader(getClass().getResource(ChildStages.TEXT_POPUP.getUrl()));
			
			this.viewLoaderChildWindow.setController(controllerTextPopup);
			this.viewLoaderChildWindow.load();	
			this.newWindow = this.viewLoaderChildWindow.getRoot();
			this.childStage.setTitle(title);
			this.childStage.getIcons().add(new Image("file:resources/helpIcon.png"));
			this.childStage.setScene(new Scene(this.newWindow));
			this.childStage.initModality(Modality.WINDOW_MODAL);
			this.childStage.initOwner(this.appStage);
			this.childStage.setX(this.appStage.getX() + 200);
			this.childStage.setY(this.appStage.getY() + 100);
			this.childStage.show();			
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
		}				
	}
	
	/**
	 * Notifies AppManager to transition the main window to the LOGIN stage.
	 * 
	 * @param controller
	 * 			the controller that requested the transition; parameter is unused and is only there to 
	 * 			ensure that a controller instance called this method. 
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyTransitionToLogin(Controller controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.currentWindow = null;
		
		try
		{
			this.viewLoaderMainWindow = new FXMLLoader(getClass().getResource(MainStages.LOGIN.getUrl()));
			this.viewLoaderMainWindow.load();
			this.currentWindow = this.viewLoaderMainWindow.getRoot();
			this.appStage.setScene(new Scene(this.currentWindow));
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
		}
	}
	
	/**
	 * Notifies AppManager to transition the current child window to the MANAGE_SALES stage.
	 * 
	 * @param controller
	 * 			the add product controller that requested the transition; parameter is unused and is only 
	 * 			there to ensure that an add product controller instance called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyTransitionToManageSales(AddProductController controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.newWindow = null;
		
		try
		{
			this.viewLoaderChildWindow = new FXMLLoader(getClass().getResource(ChildStages.MANAGE_SALES.getUrl()));
			this.viewLoaderChildWindow.load();
			this.newWindow = this.viewLoaderChildWindow.getRoot();
			this.childStage.setTitle(ChildStages.MANAGE_SALES.getName());
			this.childStage.getIcons().clear();
			this.childStage.getIcons().add(new Image("file:resources/manageSalesIcon.png"));
			this.childStage.setScene(new Scene(this.newWindow));
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
		}
	}
	
	/**
	 * Notifies AppManager to transition the main window to the REGISTER stage.
	 * 
	 * @param controller
	 * 			the login controller that requested the transition; parameter is unused and is only there
	 * 		 	to ensure that a login controller instance called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyTransitionToRegister(LoginController controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.currentWindow = null;
		
		try
		{
			this.viewLoaderMainWindow = new FXMLLoader(getClass().getResource(MainStages.REGISTER.getUrl()));
			this.viewLoaderMainWindow.load();
			this.currentWindow = this.viewLoaderMainWindow.getRoot();
			this.appStage.setScene(new Scene(this.currentWindow));
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
		}
	}
	
	/**
	 * Notifies AppManager to transition the main window to the SELECT_PRODUCT stage.
	 * 
	 * @param controller
	 * 			the work tabs controller that requested the transition; parameter is unused and is only
	 * 		 	there to ensure that a work tabs controller instance called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyTransitionToSelectProduct(Controller controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.currentWindow = null;
		
		try
		{
			this.viewLoaderMainWindow = new FXMLLoader(getClass().getResource(MainStages.SELECT_PRODUCT.getUrl()));
			this.viewLoaderMainWindow.load();
			this.currentWindow = this.viewLoaderMainWindow.getRoot();
			this.appStage.setScene(new Scene(this.currentWindow));
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
		}
	}
	
	/**
	 * Notifies AppManager to transition the main window to the WORK_TABS stage.
	 * 
	 * @param controller
	 * 			the select product controller that requested the transition; parameter is unused and is 
	 * 			only there to ensure that a select product controller instance called this method.
	 * @throws NullPointerException 
	 * 			if controller parameter is null.
	 */
	public void notifyTransitionToWorkTabs(SelectProductController controller) throws NullPointerException
	{
		if(controller.equals(null))
		{
			throw new NullPointerException("A controller class must call this method.");
		}
		
		this.currentWindow = null;
		
		try
		{
			this.viewLoaderMainWindow = new FXMLLoader(getClass().getResource(MainStages.WORK_TABS.getUrl()));
			this.viewLoaderMainWindow.load();
			this.currentWindow = this.viewLoaderMainWindow.getRoot();
			this.appStage.setScene(new Scene(this.currentWindow));
			this.appStage.setX(this.appStage.getX() - 200);
		}
		catch(Exception e)
		{
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
		}
	}
	
	/**
	 * Initializes AppManager's databaseConnection variable.
	 * @return true if database connection was successfully established, and false if not
	 */
	private boolean onStart()
	{
		boolean check = true;
		
		try
		{
			this.databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sf_system_database" ,"root","");
		}
		catch(Exception e)
		{
			check = false;
			StringWriter error = new StringWriter();

			e.printStackTrace(new PrintWriter(error));
						
			this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Unable to Establish Database Connection", "Error Details:");
		}
		
		return check;
	}
	
	/**
	 * Notifies AppManager to create and a show an alert dialogue window set with the parameters of this
	 * method.
	 * @param alert
	 * 			the alert object instantiated.
	 * @param title
	 * 			the title of the alert dialogue window.
	 * @param header
	 * 			the header of the alert dialogue window.
	 * @param content
	 * 			the text displayed under the header of the alert dialogue window.
	 * @return the option the user clicked after this alert dialogue window was created and shown.
	 */
	public Optional<ButtonType> sendAlert(Alert alert, String title, String header, String content)
	{		
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		try
		{
			if(alert.getAlertType().equals(Alert.AlertType.ERROR))
			{
				((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:resources/errorIcon.png"));
			}
			else if(alert.getAlertType().equals(Alert.AlertType.CONFIRMATION))
			{
				((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:resources/confirmIcon.png"));
			}
			else if(alert.getAlertType().equals(Alert.AlertType.INFORMATION))
			{
				((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:resources/infoIcon.png"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
			
		return alert.showAndWait();
	}
	
	/**
	 * Initializes the application's Product object.
	 * 
	 * @param productID
	 * 			the current user's selected product's id.
	 * @param name
	 * 			the current user's selected product's name.
	 */
	private void setProduct(int productID, String name)
	{
		this.product.setID(productID);
		this.product.setName(name);
	}
	
	/**
	 * Initializes the application's User object.
	 * 
	 * @param userID
	 * 			the current user's id.
	 * @param firstName
	 * 			the current user's first name.
	 * @param lastName
	 * 			the current user's last name.
	 */
	private void setUser(String userID, String firstName, String lastName)
	{
		this.user.setID(userID);
		this.user.setFirstName(firstName);
		this.user.setLastName(lastName);
	}
	
	@Override	
	/**
	 * Mandatory JavaFX start method in main class, needed to setup the primary stage.
	 * 
	 * @param primaryStage
	 * 			the primary stage of this JavaFX application.
	 */
	public void start(Stage primaryStage) 
	{
		if(this.onStart() == true)
		{
			this.appStage = primaryStage;
			
			try
			{
				this.viewLoaderMainWindow = new FXMLLoader(getClass().getResource(MainStages.LOGIN.getUrl()));
				this.viewLoaderMainWindow.load();
				this.currentWindow = this.viewLoaderMainWindow.getRoot();
				this.appStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
				{
			          public void handle(WindowEvent event) 
			          {
			        	  try
			        	  {
			        		  databaseConnection.close();  
			        	  }
			        	  catch (Exception e)
			        	  {
			        		 e.printStackTrace(); 
			        	  }
			          }
			    });
				this.appStage.setTitle("Sales Forecaster");
				this.appStage.getIcons().add(new Image("file:resources/mainAppIcon.png"));
				this.appStage.setScene(new Scene(this.currentWindow));
				this.appStage.setResizable(false);
				this.appStage.show();
			}
			catch(Exception e)
			{
				StringWriter error = new StringWriter();
	
				e.printStackTrace(new PrintWriter(error));
							
				this.sendAlert(this.createExceptionAlert(error.toString()), "ERROR", "Cannot Acquire Resources", "Error Details:");
			}
		}
	}
	
}