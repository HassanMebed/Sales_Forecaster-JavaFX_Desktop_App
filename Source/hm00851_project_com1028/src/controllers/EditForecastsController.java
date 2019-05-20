/**
 * EditForecastsController.java
 */
package controllers;

import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import main.AppManager;
import models.EditForecastsModel;

/**
 * @author Hassan Mebed
 *
 * Controller class for EditForecastsView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the EditForecastsView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the EditForecastsView.fxml file, such as a button.
 */
public class EditForecastsController extends Controller
{
	@FXML
	/** injected label containing forecasts table's title */
	private Label tableTitle;
	
	@FXML
	/** injected table using the format and guidelines of the ForecastsTableFormat class */
	private TableView<ForecastsTableFormat> forecastsTable;
	
	@FXML
	/** injected table column 'Year' corresponding to the year integer blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Integer> year;
	
	@FXML
	/** injected table column 'Q1' corresponding to the q1 double blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Double> q1;
	
	@FXML
	/** injected table column 'Q2' corresponding to the q2 double blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Double> q2;
	
	@FXML
	/** injected table column 'Q3' corresponding to the q3 double blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Double> q3;
	
	@FXML
	/** injected table column 'Q4' corresponding to the q4 double blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Double> q4;
	
	@FXML
	/** injected table column 'Date Stored' corresponding to the dateStored String blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, String> dateStored;
	
	@FXML
	/** injected button for user to click when he/she wish to remove selected forecast record */
	private Button remove;
	
	/** controller's corresponding model */
	private EditForecastsModel model = null;
	
	/**
	 * Default constructor that calls the Controller super class constructor and initializes the 
	 * controller's model.
	 */
	public EditForecastsController()
	{
		super();
		this.model = new EditForecastsModel();
	}
	
	@Override
	@FXML
	/**
	 * Initializes table's title and column data types.
	 * Allows each quarter forecast column to be editable by setting a text field to each one.
	 * Adds stored forecasts data, retrieved from the application's database through the model, to the table.
	 * Notifies AppManager if any model errors need to be displayed.
	 */
	public void initialize()
	{
		ResultSet forecasts = this.model.getForecasts(AppManager.getInstance().getProduct(this));
		
		if(forecasts == null)
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
		else
		{
			this.tableTitle.setText("Saved Forecasts for " + AppManager.getInstance().getProduct(this).getName());
			this.year.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Integer>("year"));
			this.q1.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Double>("q1"));
			this.q2.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Double>("q2"));
			this.q3.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Double>("q3"));
			this.q4.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Double>("q4"));
			this.dateStored.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, String>("dateStored"));
			this.q1.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() 
			{
		        @Override
		        public Double fromString(String value) 
		        {    
		        	Double q1Value = -1.0;
		        	
		        	if(value.isEmpty())
		        	{
		        		q1Value = 0.0;
		        	}
		        	else
		        	{
		        		try 
			            {
			                q1Value = super.fromString(value);       
			            } 
			            catch (Exception e) 
			            {
			                
			            }
		        	}
		        	
		            return q1Value;
		        }
		    }));
			
			this.q2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() 
			{
		        @Override
		        public Double fromString(String value) 
		        {    
		        	Double q2Value = -1.0;
		        	
		        	if(value.isEmpty())
		        	{
		        		q2Value = 0.0;
		        	}
		        	else
		        	{
		        		try 
			            {
			                q2Value = super.fromString(value);       
			            } 
			            catch (Exception e) 
			            {
			                
			            }
		        	}
		        	
		            return q2Value;
		        }
		    }));
			
			this.q3.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() 
			{
		        @Override
		        public Double fromString(String value) 
		        {    
		        	Double q3Value = -1.0;
		        	
		        	if(value.isEmpty())
		        	{
		        		q3Value = 0.0;
		        	}
		        	else
		        	{
		        		try 
			            {
			                q3Value = super.fromString(value);       
			            } 
			            catch (Exception e) 
			            {
			                
			            }
		        	}
		        	
		            return q3Value;
		        }
		    }));
			
			this.q4.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() 
			{
		        @Override
		        public Double fromString(String value) 
		        {    
		        	Double q4Value = -1.0;
		        	
		        	if(value.isEmpty())
		        	{
		        		q4Value = 0.0;
		        	}
		        	else
		        	{
		        		try 
			            {
			                q4Value = super.fromString(value);       
			            } 
			            catch (Exception e) 
			            {
			                
			            }
		        	}
		        	
		            return q4Value;
		        }
		    }));
			
			ObservableList<ForecastsTableFormat> forecastsList = FXCollections.observableArrayList();
			
			try
			{
				if(forecasts.next() == true)
				{
					do
					{
						forecastsList.add(new ForecastsTableFormat(forecasts.getInt("Year"), forecasts.getDouble("Q1"), forecasts.getDouble("Q2"), forecasts.getDouble("Q3"), forecasts.getDouble("Q4"), forecasts.getTimestamp("DateStored").toString()));
					}
					while (forecasts.next());
					
					forecasts.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(!forecastsList.isEmpty())
			{
				this.forecastsTable.setItems(forecastsList);
			}
			else
			{
				this.forecastsTable.getItems().clear();
				this.remove.setDisable(true);
			}
		}
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
	 * Called when user commits an edit to a field in the Q1 table column.
	 * Captured edit is validated and passed to the model accordingly for update, and errors, if any, are 
	 * displayed by notifying AppManager. Lastly, view is re-initialized to display any new changes or
	 * revert to old value if input was invalid.
	 * @param editEvent
	 * 			the captured committed edit.
	 */
	private void onEditQ1(TableColumn.CellEditEvent<ForecastsTableFormat, Double> editEvent)
	{
		ForecastsTableFormat row = this.forecastsTable.getSelectionModel().getSelectedItem();
		String date = row.getDateStored();
		int year = row.getYear();
		double editedQ1 = editEvent.getNewValue();
		
		if(editedQ1 >= 0)
		{
			boolean success = this.model.editQ1(AppManager.getInstance().getProduct(this), date, year, editedQ1);
				
			if(success == false)
			{
				AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
			}
		}
		else
		{
				AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Invalid Input", "*Make sure you input a valid sales value.");
		}
		
		this.initialize();
	}
	
	@FXML
	/**
	 * Called when user commits an edit to a field in the Q2 table column.
	 * Captured edit is checked and passed to the model accordingly for update, and errors, if any, are 
	 * displayed by notifying AppManager. Lastly, view is re-initialized to display any new changes or
	 * revert to old value if input was invalid.
	 * @param editEvent
	 * 			the captured committed edit.
	 */
	private void onEditQ2(TableColumn.CellEditEvent<ForecastsTableFormat, Double> editEvent)
	{
		ForecastsTableFormat row = this.forecastsTable.getSelectionModel().getSelectedItem();
		String date = row.getDateStored();
		int year = row.getYear();
		double editedQ2 = editEvent.getNewValue();
		
		if(editedQ2 >= 0)
		{
			boolean success = this.model.editQ2(AppManager.getInstance().getProduct(this), date, year, editedQ2);
				
			if(success == false)
			{
				AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
			}
		}
		else
		{
				AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Invalid Input", "*Make sure you input a valid sales value.");
		}
		
		this.initialize();
	}
	
	@FXML
	/**
	 * Called when user commits an edit to a field in the Q3 table column.
	 * Captured edit is checked and passed to the model accordingly for update, and errors, if any, are 
	 * displayed by notifying AppManager. Lastly, view is re-initialized to display any new changes or
	 * revert to old value if input was invalid.
	 * @param editEvent
	 * 			the captured committed edit.
	 */
	private void onEditQ3(TableColumn.CellEditEvent<ForecastsTableFormat, Double> editEvent)
	{
		ForecastsTableFormat row = this.forecastsTable.getSelectionModel().getSelectedItem();
		String date = row.getDateStored();
		int year = row.getYear();
		double editedQ3 = editEvent.getNewValue();
		
		if(editedQ3 >= 0)
		{
			boolean success = this.model.editQ3(AppManager.getInstance().getProduct(this), date, year, editedQ3);
				
			if(success == false)
			{
				AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
			}
		}
		else
		{
				AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Invalid Input", "*Make sure you input a valid sales value.");
		}
		
		this.initialize();
	}
	
	@FXML
	/**
	 * Called when user commits an edit to a field in the Q4 table column.
	 * Captured edit is checked and passed to the model accordingly for update, and errors, if any, are 
	 * displayed by notifying AppManager. Lastly, view is re-initialized to display any new changes or
	 * revert to old value if input was invalid.
	 * @param editEvent
	 * 			the captured committed edit.
	 */
	private void onEditQ4(TableColumn.CellEditEvent<ForecastsTableFormat, Double> editEvent)
	{
		ForecastsTableFormat row = this.forecastsTable.getSelectionModel().getSelectedItem();
		String date = row.getDateStored();
		int year = row.getYear();
		double editedQ4 = editEvent.getNewValue();
		
		if(editedQ4 >= 0)
		{
			boolean success = this.model.editQ4(AppManager.getInstance().getProduct(this), date, year, editedQ4);
				
			if(success == false)
			{
				AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
			}
		}
		else
		{
				AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Invalid Input", "*Make sure you input a valid sales value.");
		}
		
		this.initialize();
	}
	
	@FXML
	/**
	 * Called when user clicks remove button.
	 * Checks for a selected row in the table, extracts its Year and Date Stored column values and passes
	 * them to the model to remove selected record from the application's database.
	 * If removal was a success, re-initializes the view to show the change.
	 */
	private void onRemove()
	{
		ForecastsTableFormat selectedRow = this.forecastsTable.getItems().get(this.forecastsTable.getSelectionModel().getSelectedIndex());
		String date = selectedRow.getDateStored();
		int year = selectedRow.getYear();
		boolean success = this.model.removeForecast(AppManager.getInstance().getProduct(this), date, year);
		
		if(success == true)
		{
			this.initialize();
		}
		else
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
	}
}
