/**
 * AddSalesController.java
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.AppManager;
import models.ManageSalesModel;

/**
 * @author Hassan Mebed
 *
 * Controller class for ManageSalesView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the ManageSalesView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the ManageSalesView.fxml file, such as a button.
 */
public class ManageSalesController extends Controller
{
	@FXML
	/** injected label containing title of sales table */
	private Label tableTitle;
	
	@FXML
	/** injected table using the format and guidelines of the SalesTableFormat class */
	private TableView<SalesTableFormat> salesTable;
	
	@FXML
	/** injected table column 'Year' corresponding to the year integer blueprint in the SalesTableFormat class */
	private TableColumn<SalesTableFormat, Integer> year;
	
	@FXML
	/** injected table column 'Q1' corresponding to the q1 double blueprint in the SalesTableFormat class */
	private TableColumn<SalesTableFormat, Double> q1;
	
	@FXML
	/** injected table column 'Q2' corresponding to the q2 double blueprint in the SalesTableFormat class */
	private TableColumn<SalesTableFormat, Double> q2;
	
	@FXML
	/** injected table column 'Q3' corresponding to the q3 double blueprint in the SalesTableFormat class */
	private TableColumn<SalesTableFormat, Double> q3;
	
	@FXML
	/** injected table column 'Q4' corresponding to the q4 double blueprint in the SalesTableFormat class */
	private TableColumn<SalesTableFormat, Double> q4;
	
	@FXML
	/** injected text input field for adding a year value */
	private TextField yearInput;
	
	@FXML
	/** injected text input field for adding a Q1 value */
	private TextField q1Input;
	
	@FXML
	/** injected text input field for adding a Q2 value */
	private TextField q2Input;
	
	@FXML
	/** injected text input field for adding a Q3 value */
	private TextField q3Input;
	
	@FXML
	/** injected text input field for adding a Q4 value */
	private TextField q4Input;
	
	@FXML
	/** injected button for user to click when he/she wish to remove selected sales record */
	private Button remove;
	
	/** controller's corresponding model */
	private ManageSalesModel model = null;
	
	/**
	 * Default constructor that calls the Controller super class constructor and initializes the 
	 * controller's model.
	 */
	public ManageSalesController()
	{
		super();
		this.model = new ManageSalesModel();
	}
	
	@Override
	@FXML
	/**
	 * Initializes table's title and column data types.
	 * Allows each table column to be editable by setting a text field to each one.
	 * Adds stored sales data, retrieved from the application's database through the model, to the table.
	 * Notifies AppManager if any model errors need to be displayed.
	 */
	public void initialize()
	{
		ResultSet sales = this.model.getSales(AppManager.getInstance().getProduct(this));
		
		if(sales == null)
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
		else
		{   		
			this.tableTitle.setText(AppManager.getInstance().getProduct(this).getName() + " Sales History");
			this.year.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Integer>("year"));
			this.q1.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Double>("q1"));
			this.q2.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Double>("q2"));
			this.q3.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Double>("q3"));
			this.q4.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Double>("q4"));
			this.year.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() 
			{
		        @Override
		        public Integer fromString(String value) 
		        {    
		        	Integer yearValue = -1;
		        	
		        	if(value.isEmpty())
		        	{
		        		yearValue = 0;
		        	}
		        	else
		        	{
		        		try 
			            {
			                yearValue = super.fromString(value);       
			            } 
			            catch (Exception e) 
			            {
			                
			            }
		        	}
		        	
		            return yearValue;
		        }
		    }));
			
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
			
			ObservableList<SalesTableFormat> salesList = FXCollections.observableArrayList();
			
			try
			{
				if(sales.next() == true)
				{
					do
					{
						salesList.add(new SalesTableFormat(sales.getInt("Year"), sales.getDouble("Q1"), sales.getDouble("Q2"), sales.getDouble("Q3"), sales.getDouble("Q4")));
					}
					while (sales.next());
					
					sales.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(!salesList.isEmpty())
			{
				this.salesTable.setItems(salesList);
				this.remove.setDisable(false);
			}
			else
			{
				this.salesTable.getItems().clear();
				this.remove.setDisable(true);
			}
		}
	}
	
	@FXML
	/**
	 * Called when user submits inputed sales record he/she wish to add to the appliaction's database.
	 * Inputed data of each field is validated then sent to the model to be added to the application's
	 * database appropriately. If any model errors occur, AppManager will be notified to display them.
	 * Lastly, view is re-initialized to display the new added record, if it was added successfully.
	 */
	private void onAdd()
	{
		boolean check = true;
		int capturedYear = 0;
		double capturedQ1 = 0;
		double capturedQ2 = 0;
		double capturedQ3 = 0;
		double capturedQ4 = 0;
		
		if(this.yearInput.getText().isEmpty())
		{
			AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Year Field Cannot Be Empty", "*Year field is the only required field.");
		}
		else
		{
			if(this.yearInput.getText().contains("."))
			{
				check = false;
			}
			else
			{
				String yearFormatted = this.yearInput.getText().replaceAll("[,]", "");
				
				try
				{
					capturedYear = Integer.parseInt(yearFormatted);
					
					if(capturedYear <= 0)
					{
						check = false;
					}
				}
				catch(Exception e)
				{
					check = false;
				}
			}
			
			try
			{	
				String q1Formatted = null;
				String q2Formatted = null;
				String q3Formatted = null;
				String q4Formatted = null;
				
				if(this.q1Input.getText().isEmpty())
				{
					q1Formatted = "0";
				}
				else
				{
					q1Formatted = this.q1Input.getText().replaceAll("[,]", "");
				}
				
				if(this.q2Input.getText().isEmpty())
				{
					q2Formatted = "0";
				}
				else
				{
					q2Formatted = this.q2Input.getText().replaceAll("[,]", "");
				}
				
				if(this.q3Input.getText().isEmpty())
				{
					q3Formatted = "0";
				}
				else
				{
					q3Formatted = this.q3Input.getText().replaceAll("[,]", "");
				}
				
				if(this.q4Input.getText().isEmpty())
				{
					q4Formatted = "0";
				}
				else
				{
					q4Formatted = this.q4Input.getText().replaceAll("[,]", "");
				}
				
				capturedQ1 = Double.parseDouble(q1Formatted);
				capturedQ2 = Double.parseDouble(q2Formatted);
				capturedQ3 = Double.parseDouble(q3Formatted);
				capturedQ4 = Double.parseDouble(q4Formatted);
				
				if(capturedQ1 < 0 || capturedQ2 < 0 || capturedQ3 < 0 || capturedQ4 < 0)
				{
					check = false;
				}
			}
			catch(Exception e)
			{
				check = false;
			}
			
			if(check == true)
			{
				boolean success = this.model.addSales(AppManager.getInstance().getProduct(this), capturedYear, capturedQ1, capturedQ2, capturedQ3, capturedQ4);
				
				if(success == true)
				{
					this.yearInput.clear();
					this.q1Input.clear();
					this.q2Input.clear();
					this.q3Input.clear();
					this.q4Input.clear();
					this.initialize();
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
			else
			{
				AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Invalid Input", "*Make sure you input a valid year and valid sales values.");
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
	private void onEditQ1(TableColumn.CellEditEvent<SalesTableFormat, Double> editEvent)
	{
		SalesTableFormat row = this.salesTable.getSelectionModel().getSelectedItem();
		int year = row.getYear();
		double editedQ1 = editEvent.getNewValue(); 
		
		if(editedQ1 >= 0)
		{
			boolean success = this.model.editQ1(AppManager.getInstance().getProduct(this), year, editedQ1);
				
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
	 * Captured edit is validated and passed to the model accordingly for update, and errors, if any, are 
	 * displayed by notifying AppManager. Lastly, view is re-initialized to display any new changes or
	 * revert to old value if input was invalid.
	 * @param editEvent
	 * 			the captured committed edit.
	 */
	private void onEditQ2(TableColumn.CellEditEvent<SalesTableFormat, Double> editEvent)
	{
		SalesTableFormat row = this.salesTable.getSelectionModel().getSelectedItem();
		int year = row.getYear();
		double editedQ2 = editEvent.getNewValue(); 
		
		if(editedQ2 >= 0)
		{
			boolean success = this.model.editQ2(AppManager.getInstance().getProduct(this), year, editedQ2);
				
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
	 * Captured edit is validated and passed to the model accordingly for update, and errors, if any, are 
	 * displayed by notifying AppManager. Lastly, view is re-initialized to display any new changes or
	 * revert to old value if input was invalid.
	 * @param editEvent
	 * 			the captured committed edit.
	 */
	private void onEditQ3(TableColumn.CellEditEvent<SalesTableFormat, Double> editEvent)
	{
		SalesTableFormat row = this.salesTable.getSelectionModel().getSelectedItem();
		int year = row.getYear();
		double editedQ3 = editEvent.getNewValue(); 
		
		if(editedQ3 >= 0)
		{
			boolean success = this.model.editQ3(AppManager.getInstance().getProduct(this), year, editedQ3);
				
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
	 * Captured edit is validated and passed to the model accordingly for update, and errors, if any, are 
	 * displayed by notifying AppManager. Lastly, view is re-initialized to display any new changes or
	 * revert to old value if input was invalid.
	 * @param editEvent
	 * 			the captured committed edit.
	 */
	private void onEditQ4(TableColumn.CellEditEvent<SalesTableFormat, Double> editEvent)
	{
		SalesTableFormat row = this.salesTable.getSelectionModel().getSelectedItem();
		int year = row.getYear();
		double editedQ4 = editEvent.getNewValue(); 
		
		if(editedQ4 >= 0)
		{
			boolean success = this.model.editQ4(AppManager.getInstance().getProduct(this), year, editedQ4);
				
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
	 * Called when user commits an edit to a field in the Year table column.
	 * Captured edit is validated and passed to the model accordingly for update, and errors, if any, are 
	 * displayed by notifying AppManager. Lastly, view is re-initialized to display any new changes or
	 * revert to old value if input was invalid.
	 * @param editEvent
	 * 			the captured committed edit.
	 */
	private void onEditYear(TableColumn.CellEditEvent<SalesTableFormat, Integer> editEvent)
	{
		int editedYear = editEvent.getNewValue(); 
		int oldYear = editEvent.getOldValue();
		
		if(editedYear > 0)
		{
			boolean success = this.model.editYear(AppManager.getInstance().getProduct(this), oldYear, editedYear);
				
			if(success == false)
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
		else if(editedYear == 0)
		{
			AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Invalid Input", "*Year cannot be empty.");
		}
		else
		{
			AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Invalid Input", "*Make sure you input a valid year value.");
		}
		
		this.initialize();
	}
	
	@FXML
	/**
	 * Submits inputed sales record fields for processing when user presses on enter keyboard button.
	 * 
	 * @param keyEvent
	 * 			any keyboard click event.
	 */
	private void onEnterClicked(KeyEvent keyEvent)
	{
		if(keyEvent.getCode() == KeyCode.ENTER)
		{
			this.onAdd();
		}
	}
	
	@FXML
	/**
	 * Called when user clicks remove button.
	 * Checks for a selected row in the table, extracts its Year column value and passes it to the model to
	 * remove selected record from the application's database.
	 * If removal was a success, re-initializes the view to show the change.
	 */
	private void onRemove()
	{
		SalesTableFormat selectedRow = this.salesTable.getItems().get(this.salesTable.getSelectionModel().getSelectedIndex());
		int year = selectedRow.getYear();
		boolean success = this.model.removeSales(AppManager.getInstance().getProduct(this), year);
		
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