/**
 * GeneratedForecastController.java
 */
package controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import main.AppManager;
import models.GeneratedForecastModel;

/**
 * @author Hassan Mebed
 *
 * Controller class for GeneratedForecastView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the GeneratedForecastView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the GeneratedForecastView.fxml file, such as a button.
 */
public class GeneratedForecastController extends Controller
{
	@FXML
	/** injected text area for displaying the generated forecast values */
	private TextArea forecastView;
	
	@FXML
	/** injected button for user to click when wanting to store the generated forecast values */
	private Button storeForecast;
	
	/** controller's corresponding model */
	private GeneratedForecastModel model = null;
	
	/** generated forecasts. key is year and corresponding value is double array of forecasted values. max is two sets of forecasted year values */
	private Map<Integer, double[]> forecastData = null;
	
	/**
	 * Parameterized constructor that calls the Controller super class constructor, initializes the
	 * controller's model and initializes forecastData with passed parameter.
	 * 
	 * @param forecastData
	 * 			LinkedHashMap containing max 2 sets of years and their forecasted values.
	 */
	public GeneratedForecastController(LinkedHashMap<Integer, double[]> forecastData)
	{
		super();
		this.model = new GeneratedForecastModel();
		this.forecastData = forecastData;
	}
	
	@Override
	@FXML
	/**
	 * Extracts data from forecastData LinkedHashMap and formats it in the forecastView TextArea.
	 */
	public void initialize()
	{
		String data = "";
		
		for(Map.Entry<Integer, double[]> forecast: this.forecastData.entrySet())
		{
			data = data + "Year: " + forecast.getKey() + " | ";
			
			if(forecast.getValue().length == 1)
			{
				data = data + "Q4: " + forecast.getValue()[0];
			}
			else if(forecast.getValue().length == 2)
			{
				data = data + "Q3: " + forecast.getValue()[0] + " | Q4: " + forecast.getValue()[1];
			}
			else if(forecast.getValue().length == 3)
			{
				data = data + "Q2: " + forecast.getValue()[0] + " | Q3: " + forecast.getValue()[1] + " | Q4: " + forecast.getValue()[2];
			}
			else if(forecast.getValue().length == 4)
			{
				data = data + "Q1: " + forecast.getValue()[0] + " | Q2: " + forecast.getValue()[1] + " | Q3: " + forecast.getValue()[2] + " | Q4: " + forecast.getValue()[3];
			}
			
			data = data + "\n";
		}
		
		this.forecastView.setText(data);
	}
	
	@FXML
	/**
	 * Called when user clicks done button.
	 * Notifies AppManager to refresh main window and then close this child window.
	 */
	private void onDone()
	{
		AppManager.getInstance().notifyReloadMainWindow(this);
		AppManager.getInstance().notifyCloseChildWindow(this);
	}
	
	@FXML
	/**
	 * Called when user store button.
	 * Passes generated forecasts data to the model in order for it to be stored in the application's database.
	 * Notifies AppManager to display model errors, if any. 
	 */
	private void onStore()
	{
		boolean check = this.model.storeForecasts(AppManager.getInstance().getProduct(this), this.forecastData);
		
		if(check == true)
		{
			AppManager.getInstance().notifyReloadMainWindow(this);
			AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.INFORMATION), "SUCCESS", "Forecasts Have Been Successfully Stored in your Database" , null);
			this.storeForecast.setDisable(true);
		}
		else
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
	}
}
