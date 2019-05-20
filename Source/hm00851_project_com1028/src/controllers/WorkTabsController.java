/**
 * WorkTabsController.java
 */
package controllers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.AppManager;
import main.ChildStages;
import models.WorkTab1Model;
import models.WorkTab2Model;
import models.WorkTab3Model;

/**
 * @author Hassan Mebed
 *
 * Controller class for WorkTabsView.fxml
 * All fields after an '@FXML' tag indicate a GUI element created in the WorkTabsView.fxml file, being
 * injected in this class.
 * All methods after an '@FXML' tag are linked to the 'onAction' attribute or similar attribute of a GUI 
 * element created in the WorkTabsView.fxml file, such as a button.
 */
public class WorkTabsController extends Controller
{
	@FXML
	/** injected label containing the sales tab's title */
	private Label tab1Title;
	
	@FXML
	/** injected label containing the forecasts tab's title */
	private Label tab2Title;
	
	@FXML
	/** injected label containing the resulted mean average % error of a sales vs forecasts comparison */
	private Label mape;
	
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
	/** injected table column 'Q4' corresponding to the q1 double blueprint in the SalesTableFormat class */
	private TableColumn<SalesTableFormat, Double> q4;
	
	@FXML
	/** injected area chart with x axis values of type string and y axis values of type double; for plotting the recorded sales */
	private AreaChart<String, Number> salesChart;
	
	@FXML 
	/** injected line chart with x axis values of type string and y axis values of type double; for plotting the sales vs forecasts comparison */
	private LineChart<String, Number> comparisonChart;
	
	@FXML
	/** injected table using the format and guidelines of the ForecastsTableFormat class */
	private TableView<ForecastsTableFormat> forecastsTable;
	
	@FXML
	/** injected table column 'Year' corresponding to the year integer blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Integer> yearForecast;
	
	@FXML
	/** injected table column 'Q1' corresponding to the q1 double blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Double> q1Forecast;
	
	@FXML
	/** injected table column 'Q2' corresponding to the q2 double blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Double> q2Forecast;
	
	@FXML
	/** injected table column 'Q3' corresponding to the q3 double blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Double> q3Forecast;
	
	@FXML
	/** injected table column 'Q4' corresponding to the q4 double blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, Double> q4Forecast;
	
	@FXML
	/** injected table column 'Date Stored' corresponding to the dateStored String blueprint in the ForecastsTableFormat class */
	private TableColumn<ForecastsTableFormat, String> dateStored;
	
	@FXML
	/** injected check box for indicating if current year option is chosen or not for forecast generation */
	private CheckBox currentYear;
	
	@FXML
	/** injected check box for indicating if next year option is chosen or not for forecast generation */
	private CheckBox nextYear;
	
	@FXML
	/** injected button for user to click when he/she wish to edit any of their stored forecasts */
	private Button editForecasts;
	
	@FXML
	/** injected button for user to click when he/she wish to generate a new sales vs forecasts comparison chart */
	private Button generateChart;
	
	@FXML
	/** injected scroll pane that contains the sales vs forecasts comparison chart */
	private ScrollPane chartContainer;
	
	/** controller's corresponding model for sales tab */
	private WorkTab1Model tab1Model = null;
	
	/** controller's corresponding model for forecasts tab */
	private WorkTab2Model tab2Model = null;
	
	/** controller's corresponding model for comparison tab */
	private WorkTab3Model tab3Model = null;
	
	/**
	 * Default constructor that calls the Controller super class constructor and initializes the 
	 * controller's three separate models.
	 */
	public WorkTabsController()
	{
		super();
		this.tab1Model = new WorkTab1Model();
		this.tab2Model = new WorkTab2Model();
		this.tab3Model = new WorkTab3Model();
	}
	
	/**
	 * Retrieves the current generated forecast from the forecasts tab model.
	 * 
	 * @return
	 * 		a linked hash map containing the forecasted data, which should contain maximum of 2 elements
	 * 		i.e current year forecasts, or next year's forecasts, or both.
	 */
	public Map<Integer, double[]> getGeneratedForecast()
	{
		return this.tab2Model.getGeneratedForecast();
	}
	
	@Override
	@FXML
	/**
	 * Initializes titles of sales and forecasts tabs to include the product's/service's name.
	 * Initializes the sales table and sales chart in the sales tab with the recorded sales records in
	 * the application's database, using the sales tab model.
	 * Initializes the forecasts table in the forecasts tab with the stored generated forecasts in the
	 * application's database, using the forecasts tab model. Also, initializes the tab's check boxes and
	 * edit forecasts button accordingly.
	 * Initializes the comparison tab's comparison chart accordingly, and its accompanying MAPE label,
	 * and the generate chart button. Chart and MAPE label should only be visible when successful call to 
	 * generate chart is made. 
	 */
	public void initialize()
	{
		//Tab 1 Initialization
		
		if(this.salesChart.getData().size() != 0)
		{
			this.salesChart.getData().get(0).getData().clear();
		}
		else
		{
			this.salesChart.getData().add(new XYChart.Series<>());
			this.salesChart.getData().get(0).setName("Sales");
		}
		
		this.salesChart.setAnimated(false);
		this.tab1Title.setText("Sales Info for " + AppManager.getInstance().getProduct(this).getName());
		
		ResultSet sales = this.tab1Model.getSales(AppManager.getInstance().getProduct(this));
		
		if(sales == null)
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab1Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
		else
		{
			this.year.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Integer>("year"));
			this.q1.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Double>("q1"));
			this.q2.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Double>("q2"));
			this.q3.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Double>("q3"));
			this.q4.setCellValueFactory(new PropertyValueFactory<SalesTableFormat, Double>("q4"));
			
			ObservableList<SalesTableFormat> salesList = FXCollections.observableArrayList();
			
			try
			{
				if(sales.next() == true)
				{
					String spacing = "";
					
					do
					{
						salesList.add(new SalesTableFormat(sales.getInt("Year"), sales.getDouble("Q1"), sales.getDouble("Q2"), sales.getDouble("Q3"), sales.getDouble("Q4")));
						
						if(sales.isLast())
						{
							if(sales.getDouble("Q4") > 0.0)
							{
								spacing = spacing + "" + "\0";
								
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q4" + spacing, sales.getDouble("Q4")));
							}
							else if(sales.getDouble("Q3") > 0.0)
							{
								spacing = spacing + "" + "\0";
								
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
							}
							else if(sales.getDouble("Q2") > 0.0 && sales.getDouble("Q3") == 0)
							{
								spacing = spacing + "" + "\0";
								
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
							}
							else if(sales.getDouble("Q1") > 0.0 && sales.getDouble("Q2") == 0 && sales.getDouble("Q3") == 0)
							{
								spacing = spacing + "" + "\0";
								
								this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + "\n" + sales.getInt("Year"), sales.getDouble("Q1")));
							}
						}
						else
						{
							spacing = spacing + "" + "\0";
							
							this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
							this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
							this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
							this.salesChart.getData().get(0).getData().add(new XYChart.Data<>("Q4" + spacing, sales.getDouble("Q4")));
						}
					}
					while(sales.next());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(!salesList.isEmpty())
			{
				this.salesTable.setItems(salesList);
			}
			else
			{
				this.salesTable.getItems().clear();
			}
		}
		
		//Tab 2 Initialization
		
		this.tab2Title.setText("Forecast Generation and Forecasts Data for " + AppManager.getInstance().getProduct(this).getName());
		this.editForecasts.setDisable(false);
		this.currentYear.setSelected(false);
		this.nextYear.setSelected(false);
		
		ResultSet forecasts = this.tab2Model.getForecasts(AppManager.getInstance().getProduct(this));
		
		if(forecasts == null)
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab2Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
		else
		{
			this.yearForecast.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Integer>("year"));
			this.q1Forecast.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Double>("q1"));
			this.q2Forecast.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Double>("q2"));
			this.q3Forecast.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Double>("q3"));
			this.q4Forecast.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, Double>("q4"));
			this.dateStored.setCellValueFactory(new PropertyValueFactory<ForecastsTableFormat, String>("dateStored"));
			
			ObservableList<ForecastsTableFormat> forecastsList = FXCollections.observableArrayList();
			
			try
			{
				if(forecasts.next() == true)
				{	
					do
					{
						forecastsList.add(new ForecastsTableFormat(forecasts.getInt("Year"), forecasts.getDouble("Q1"), forecasts.getDouble("Q2"), forecasts.getDouble("Q3"), forecasts.getDouble("Q4"), forecasts.getTimestamp("DateStored").toString()));
					}
					while(forecasts.next());
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
				this.editForecasts.setDisable(true);
			}
			
			//Tab 3 Initialization
			
			this.comparisonChart.setAnimated(false);
			
			try
			{
				boolean check = true;
				
				sales.beforeFirst();
				forecasts.beforeFirst();
				
				if(sales.next() == false)
				{
					check = false;
					this.generateChart.setDisable(true);
				}
				
				if(forecasts.next() == false)
				{
					check = false;
					this.generateChart.setDisable(true);
				}
				
				if(check == true)
				{
					this.generateChart.setDisable(false);
				}
				
				sales.close();
				forecasts.close();
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(this.comparisonChart.getData().isEmpty())
			{
				this.chartContainer.setVisible(false); 
				this.mape.setVisible(false);
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
	 * Called when user clicks the 'Back' button in the bottom of the stage; notifies AppManager to transition
	 * back to the select product/service stage.
	 */
	private void onBack()
	{
		AppManager.getInstance().notifyTransitionToSelectProduct(this);
	}
	
	@FXML
	/**
	 * Called when user clicks the 'Edit' button under the forecasts table in the forecasts tab; notifies
	 * AppManager to open the EDIT_FORECASTS child window.
	 */
	private void onEditForecasts()
	{
		AppManager.getInstance().notifyOpenNewWindow(this, ChildStages.EDIT_FORECASTS);
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
	 * Called when user clicks the generate new comparison chart button in the comparison tab.
	 * Clears the currently shown comparison chart and its accompanying MAPE label.
	 * Plots each one of the sales records on the line chart as one series, with each of their corresponding
	 * forecasts as another series, which were queried from the comparison tab model (the sales were retrieved 
	 * from sales tab model). And if a sales record has more than 1 matching forecasts, the method will store 
	 * the 'Date Stored' row value of each of these matching forecasts in an ArrayList that will be passed to a 
	 * choice dialogue that will be displayed to the user, demanding one of the forecast records be chosen for 
	 * the comparison. 
	 * If there are stored forecasts for a future year i.e a year not yet recorded in the sales table, they will
	 * be displayed as well, through getting the remaining forecasts from the forecasts tab model.
	 * Finally, as each sales vs forecast comparison record is being plotted, the method will keep track of number
	 * of observations & summation of each sale quarter's forecast absolute % error. Both of these variables will
	 * be used to calculate the MAPE value and initialize it to the MAPE label under the comparison chart.
	 * Shows the composed comparison chart and MAPE label if no errors occur.
	 * Notifies AppManager to display any model errors, if any, or display error if user fails to choose distinct
	 * forecast when multiple matches are found for a corresponding sales record.
	 */
	private void onGenerateComparisonChart()
	{	
		// reset chart data if it has any
		if(this.comparisonChart.getData().size() != 0)
		{
			this.comparisonChart.getData().get(0).getData().clear();
			this.comparisonChart.getData().get(1).getData().clear();
			this.chartContainer.setVisible(false);
		}
		else
		{
			this.comparisonChart.getData().add(new XYChart.Series<>());
			this.comparisonChart.getData().add(new XYChart.Series<>());
			this.comparisonChart.getData().get(0).setName("Sales");
			this.comparisonChart.getData().get(1).setName("Forecasts");
		}
		
		this.mape.setText(null);
		
		ResultSet sales = this.tab1Model.getSales(AppManager.getInstance().getProduct(this));
		
		if(sales == null)
		{
			AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab1Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
		}
		else
		{
			boolean connectionOK = true;
			boolean success = true;
			String spacing = "";
			int numberOfObservations = 0;
			double absolutePercentageErrors = 0;
			
			try
			{
				// loops through each sales record and queries database for all matching forecasts with the same year as that sales records.
				// Plots the sales record and matching forecasts onto the graph while keeping record of # of observations i.e # of forecasts
				// and the current sum of each of the plotted forecasts' absolute % error.
				while(sales.next())
				{
					ResultSet forecasts = this.tab3Model.getMatchingForecasts(AppManager.getInstance().getProduct(this), sales.getInt("Year"));
					int numberOfMatches = 0;
					
					if(forecasts == null)
					{
						AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab1Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
						this.comparisonChart.getData().clear();
						connectionOK = false;
						break;
					}
					else
					{
						if(forecasts.next() == true)
						{
							forecasts.last();
							
							numberOfMatches = forecasts.getRow();
							
							// if there is more than one matching forecasts then ask user to choose which want they want for comparison.
							if(numberOfMatches > 1)
							{
								ArrayList<String> matches = new ArrayList<String>();
								
								forecasts.beforeFirst();
								
								while(forecasts.next())
								{
									matches.add(forecasts.getTimestamp("DateStored").toString());
								}
								
								ChoiceDialog<String> matchToChoose = new ChoiceDialog<>(matches.get(0), matches);
								matchToChoose.setTitle("CONFIRMATION");
								matchToChoose.setHeaderText("Multiple Forecasts for Year " + sales.getInt("Year") + " Found" );
								matchToChoose.setContentText("Please select one for the comparison:");
								
								((Stage)matchToChoose.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:resources/confirmIcon.png"));
								
								Optional<String> chosenMatch = matchToChoose.showAndWait();
								
								if(chosenMatch.isPresent())
								{
								   ResultSet selectedForecast = this.tab3Model.getSpecificForecast(AppManager.getInstance().getProduct(this), sales.getInt("Year"), chosenMatch.get());
								   
								   if(selectedForecast == null)
								   {
									   AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab3Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
									   this.comparisonChart.getData().clear();
									   
									   break;
								   }
								   else
								   {
									   selectedForecast.next();
									   
									   if(sales.isLast())
										{
											if(sales.getDouble("Q4") > 0.0)
											{
												spacing = spacing + "" + "\0";
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
												
												if(selectedForecast.getDouble("Q1") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, selectedForecast.getDouble("Q1")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - selectedForecast.getDouble("Q1"))) / sales.getDouble("Q1");
												}
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
												
												if(selectedForecast.getDouble("Q2") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), selectedForecast.getDouble("Q2")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q2") - selectedForecast.getDouble("Q2"))) / sales.getDouble("Q2");
												}
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
												
												if(selectedForecast.getDouble("Q3") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, selectedForecast.getDouble("Q3")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q3") - selectedForecast.getDouble("Q3"))) / sales.getDouble("Q3");
												}
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q4" + spacing, sales.getDouble("Q4")));
												
												if(selectedForecast.getDouble("Q4") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q4" + spacing, selectedForecast.getDouble("Q4")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q4") - selectedForecast.getDouble("Q4"))) / sales.getDouble("Q4");
												}
											}
											else if(sales.getDouble("Q3") > 0.0)
											{
												spacing = spacing + "" + "\0";
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
												
												if(selectedForecast.getDouble("Q1") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, selectedForecast.getDouble("Q1")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - selectedForecast.getDouble("Q1"))) / sales.getDouble("Q1");
												}
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
												
												if(selectedForecast.getDouble("Q2") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), selectedForecast.getDouble("Q2")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q2") - selectedForecast.getDouble("Q2"))) / sales.getDouble("Q2");
												}
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
												
												if(selectedForecast.getDouble("Q3") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, selectedForecast.getDouble("Q3")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q3") - selectedForecast.getDouble("Q3"))) / sales.getDouble("Q3");
												}
											}
											else if(sales.getDouble("Q2") > 0.0 && sales.getDouble("Q3") == 0)
											{
												spacing = spacing + "" + "\0";
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
												
												if(selectedForecast.getDouble("Q1") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, selectedForecast.getDouble("Q1")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - selectedForecast.getDouble("Q1"))) / sales.getDouble("Q1");
												}
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
												
												if(selectedForecast.getDouble("Q2") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), selectedForecast.getDouble("Q2")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q2") - selectedForecast.getDouble("Q2"))) / sales.getDouble("Q2");
												}
											}
											else if(sales.getDouble("Q1") > 0.0 && sales.getDouble("Q2") == 0 && sales.getDouble("Q3") == 0)
											{
												spacing = spacing + "" + "\0";
												
												this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + "\n" + sales.getInt("Year"), sales.getDouble("Q1")));
												
												if(selectedForecast.getDouble("Q1") > 0.0)
												{
													this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + "\n" + sales.getInt("Year"), selectedForecast.getDouble("Q1")));
													numberOfObservations = numberOfObservations + 1;
													absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - selectedForecast.getDouble("Q1"))) / sales.getDouble("Q1");
												}
											}							
										}
										else
										{
											spacing = spacing + "" + "\0";
											
											this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
											
											if(selectedForecast.getDouble("Q1") > 0.0)
											{
												this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, selectedForecast.getDouble("Q1")));
												numberOfObservations = numberOfObservations + 1;
												absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - selectedForecast.getDouble("Q1"))) / sales.getDouble("Q1");
											}
											
											this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
											
											if(selectedForecast.getDouble("Q2") > 0.0)
											{
												this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), selectedForecast.getDouble("Q2")));
												numberOfObservations = numberOfObservations + 1;
												absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q2") - selectedForecast.getDouble("Q2"))) / sales.getDouble("Q2");
											}
											
											this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
											
											if(selectedForecast.getDouble("Q3") > 0.0)
											{
												this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, selectedForecast.getDouble("Q3")));
												numberOfObservations = numberOfObservations + 1;
												absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q3") - selectedForecast.getDouble("Q3"))) / sales.getDouble("Q3");
											}
											
											this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q4" + spacing, sales.getDouble("Q4")));
											
											if(selectedForecast.getDouble("Q4") > 0.0)
											{
												this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q4" + spacing, selectedForecast.getDouble("Q4")));
												numberOfObservations = numberOfObservations + 1;
												absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q4") - selectedForecast.getDouble("Q4"))) / sales.getDouble("Q4");
											}
										}
								   }
								   
								   selectedForecast.close();
								}
								else
								{
									success = false;
									this.comparisonChart.getData().clear();
									break;
								}
							}
							else
							{	
								if(sales.isLast())
								{
									if(sales.getDouble("Q4") > 0.0)
									{
										spacing = spacing + "" + "\0";
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
										
										if(forecasts.getDouble("Q1") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, forecasts.getDouble("Q1")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - forecasts.getDouble("Q1"))) / sales.getDouble("Q1");
										}
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
										
										if(forecasts.getDouble("Q2") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), forecasts.getDouble("Q2")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q2") - forecasts.getDouble("Q2"))) / sales.getDouble("Q2");
										}
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
										
										if(forecasts.getDouble("Q3") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, forecasts.getDouble("Q3")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q3") - forecasts.getDouble("Q3"))) / sales.getDouble("Q3");
										}
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q4" + spacing, sales.getDouble("Q4")));
										
										if(forecasts.getDouble("Q4") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q4" + spacing, forecasts.getDouble("Q4")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q4") - forecasts.getDouble("Q4"))) / sales.getDouble("Q4");
										}
									}
									else if(sales.getDouble("Q3") > 0.0)
									{
										spacing = spacing + "" + "\0";
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
										
										if(forecasts.getDouble("Q1") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, forecasts.getDouble("Q1")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - forecasts.getDouble("Q1"))) / sales.getDouble("Q1");
										}
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
										
										if(forecasts.getDouble("Q2") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), forecasts.getDouble("Q2")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q2") - forecasts.getDouble("Q2"))) / sales.getDouble("Q2");
										}
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
										
										if(forecasts.getDouble("Q3") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, forecasts.getDouble("Q3")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q3") - forecasts.getDouble("Q3"))) / sales.getDouble("Q3");
										}
									}
									else if(sales.getDouble("Q2") > 0.0 && sales.getDouble("Q3") == 0)
									{
										spacing = spacing + "" + "\0";
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
										
										if(forecasts.getDouble("Q1") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, forecasts.getDouble("Q1")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - forecasts.getDouble("Q1"))) / sales.getDouble("Q1");
										}
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
										
										if(forecasts.getDouble("Q2") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), forecasts.getDouble("Q2")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q2") - forecasts.getDouble("Q2"))) / sales.getDouble("Q2");
										}
									}
									else if(sales.getDouble("Q1") > 0.0 && sales.getDouble("Q2") == 0 && sales.getDouble("Q3") == 0)
									{
										spacing = spacing + "" + "\0";
										
										this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + "\n" + sales.getInt("Year"), sales.getDouble("Q1")));
										
										if(forecasts.getDouble("Q1") > 0.0)
										{
											this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + "\n" + sales.getInt("Year"), forecasts.getDouble("Q1")));
											numberOfObservations = numberOfObservations + 1;
											absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - forecasts.getDouble("Q1"))) / sales.getDouble("Q1");
										}
									}							
								}
								else
								{
									spacing = spacing + "" + "\0";
									
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
									
									if(forecasts.getDouble("Q1") > 0.0)
									{
										this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, forecasts.getDouble("Q1")));
										numberOfObservations = numberOfObservations + 1;
										absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q1") - forecasts.getDouble("Q1"))) / sales.getDouble("Q1");
									}
									
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
									
									if(forecasts.getDouble("Q2") > 0.0)
									{
										this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), forecasts.getDouble("Q2")));
										numberOfObservations = numberOfObservations + 1;
										absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q2") - forecasts.getDouble("Q2"))) / sales.getDouble("Q2");
									}
									
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
									
									if(forecasts.getDouble("Q3") > 0.0)
									{
										this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, forecasts.getDouble("Q3")));
										numberOfObservations = numberOfObservations + 1;
										absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q3") - forecasts.getDouble("Q3"))) / sales.getDouble("Q3");
									}
									
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q4" + spacing, sales.getDouble("Q4")));
									
									if(forecasts.getDouble("Q4") > 0.0)
									{
										this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q4" + spacing, forecasts.getDouble("Q4")));
										numberOfObservations = numberOfObservations + 1;
										absolutePercentageErrors = absolutePercentageErrors + (Math.abs(sales.getDouble("Q4") - forecasts.getDouble("Q4"))) / sales.getDouble("Q4");
									}
								}
							}
						}
						else
						{
							if(sales.isLast())
							{
								spacing = spacing + "" + "\0";
								
								if(sales.getDouble("Q4") > 0.0)
								{
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q4" + spacing, sales.getDouble("Q4")));
								}
								else if(sales.getDouble("Q3") > 0.0)
								{
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
								}
								else if(sales.getDouble("Q2") > 0.0 && sales.getDouble("Q3") == 0)
								{
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
								}
								else if(sales.getDouble("Q1") > 0.0 && sales.getDouble("Q2") == 0 && sales.getDouble("Q3") == 0)
								{
									this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + "\n" + sales.getInt("Year"), sales.getDouble("Q1")));
								}
							}
							else
							{
								spacing = spacing + "" + "\0";
								
								this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q1" + spacing, sales.getDouble("Q1")));
								this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q2" + "\n" + sales.getInt("Year"), sales.getDouble("Q2")));
								this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q3" + spacing, sales.getDouble("Q3")));
								this.comparisonChart.getData().get(0).getData().add(new XYChart.Data<>("Q4" + spacing, sales.getDouble("Q4")));
							}
						}
					}
					
					forecasts.close();
				}
				
				// in case there are forecast values for future years of sales, plot them as well too, but do not count them as observations.
				ResultSet remainingForecasts = this.tab2Model.getForecasts(AppManager.getInstance().getProduct(this));
						
				if(remainingForecasts == null)
				{
					AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab1Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
					connectionOK = false;
				}
				else
				{	
					sales.last();
					remainingForecasts.last();
					
					if(remainingForecasts.getInt("Year") > sales.getInt("Year"))
					{
						ArrayList<Integer> selectedForecasts = new ArrayList<Integer>();
						
						remainingForecasts.beforeFirst();
						
						while(remainingForecasts.next())
						{
							boolean skipForecast = false;
								
							for(int i = 0; i < selectedForecasts.size(); i++)
							{
								if(remainingForecasts.getInt("Year") == selectedForecasts.get(i))
								{
									skipForecast = true;
								}
							}
								
							if(skipForecast == false)
							{
								boolean check = true;
									
								sales.beforeFirst();
									
								while(sales.next())
								{	
									if(sales.getInt("Year") == remainingForecasts.getInt("Year"))
									{
										check = true;
										break;
									}
									else
									{
										check = false;
									}
								}
									
								if(check == false)
								{
									ResultSet forecastMatches = this.tab3Model.getMatchingForecasts(AppManager.getInstance().getProduct(this), remainingForecasts.getInt("Year"));
									int numberOfMatches = 0;
									
									if(forecastMatches == null)
									{
										AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab3Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
										this.comparisonChart.getData().clear();
										this.initialize();
									}
									else
									{
										forecastMatches.last();
											
										numberOfMatches = forecastMatches.getRow();
											
										if(numberOfMatches > 1)
										{
											ArrayList<String> matches = new ArrayList<String>();
											
											forecastMatches.beforeFirst();
												
											while(forecastMatches.next())
											{
												matches.add(forecastMatches.getTimestamp("DateStored").toString());
											}
												
											forecastMatches.first();
												
											ChoiceDialog<String> matchToChoose = new ChoiceDialog<>(matches.get(0), matches);
											matchToChoose.setTitle("CONFIRMATION");
											matchToChoose.setHeaderText("Multiple Forecasts for Year " + forecastMatches.getInt("Year") + " Found" );
											matchToChoose.setContentText("Please select one for the comparison:");
												
											((Stage)matchToChoose.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:resources/confirmIcon.png"));
												
											Optional<String> chosenMatch = matchToChoose.showAndWait();
												
											if(chosenMatch.isPresent())
											{
											   ResultSet selectedForecast = this.tab3Model.getSpecificForecast(AppManager.getInstance().getProduct(this), forecastMatches.getInt("Year"), chosenMatch.get());
												   
											   if(selectedForecast == null)
											   {
												   AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab3Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
											   }
											   else
											   {
												   spacing = spacing + "" + "\0";
													
												   selectedForecast.next();
													   
												   selectedForecasts.add(selectedForecast.getInt("Year"));
													   
												   if(selectedForecast.getDouble("Q4") > 0.0)
												   {
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, selectedForecast.getDouble("Q1")));
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + selectedForecast.getInt("Year"), selectedForecast.getDouble("Q2")));
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, selectedForecast.getDouble("Q3")));
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q4" + spacing, selectedForecast.getDouble("Q4")));
												   }														
												   else if(selectedForecast.getDouble("Q3") > 0.0)
												   {
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, selectedForecast.getDouble("Q1")));
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + selectedForecast.getInt("Year"), selectedForecast.getDouble("Q2")));
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, selectedForecast.getDouble("Q3")));
												   }	
												   else if(selectedForecast.getDouble("Q2") > 0.0 && selectedForecast.getDouble("Q3") == 0)
												   {
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, selectedForecast.getDouble("Q1")));
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + selectedForecast.getInt("Year"), selectedForecast.getDouble("Q2")));
												   }
												   else if(selectedForecast.getDouble("Q1") > 0.0 && selectedForecast.getDouble("Q2") == 0 && selectedForecast.getDouble("Q3") == 0)
												   {
													   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + "\n" + selectedForecast.getInt("Year"), selectedForecast.getDouble("Q1")));	  
												   } 
											   }
												   
											   selectedForecast.close();
											}
											else
											{
												success = false;
												this.comparisonChart.getData().clear();
												break;
											}
										}
										else
										{
											spacing = spacing + "" + "\0";
											
											   if(remainingForecasts.getDouble("Q4") > 0.0)
											   {
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, remainingForecasts.getDouble("Q1")));
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + remainingForecasts.getInt("Year"), remainingForecasts.getDouble("Q2")));
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, remainingForecasts.getDouble("Q3")));
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q4" + spacing, remainingForecasts.getDouble("Q4")));
											   }														
											   else if(remainingForecasts.getDouble("Q3") > 0.0)
											   {
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, remainingForecasts.getDouble("Q1")));
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + remainingForecasts.getInt("Year"), remainingForecasts.getDouble("Q2")));
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q3" + spacing, remainingForecasts.getDouble("Q3")));
											   }	
											   else if(remainingForecasts.getDouble("Q2") > 0.0 && remainingForecasts.getDouble("Q3") == 0)
											   {
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + spacing, remainingForecasts.getDouble("Q1")));
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q2" + "\n" + remainingForecasts.getInt("Year"), remainingForecasts.getDouble("Q2")));
											   }
											   else if(remainingForecasts.getDouble("Q1") > 0.0 && remainingForecasts.getDouble("Q2") == 0 && remainingForecasts.getDouble("Q3") == 0)
											   {
												   this.comparisonChart.getData().get(1).getData().add(new XYChart.Data<>("Q1" + "\n" + remainingForecasts.getInt("Year"), remainingForecasts.getDouble("Q1")));	  
											   } 
										}
									}
									
									forecastMatches.close();
								}
							}
						}	
					}
					
					sales.close();
					remainingForecasts.close();
				}
					
				if(success == false && connectionOK == true)
				{
					AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Insufficient Data for Comparison", null);
					this.initialize();
				}
				else if(connectionOK == true)
				{
					double mapeValue = (absolutePercentageErrors * 100) / numberOfObservations; 
					mapeValue = Math.round(mapeValue * 100.0)/100.0;
					this.mape.setText("Mean Absolute % Error: " + mapeValue);
					this.chartContainer.setVisible(true);
					this.mape.setVisible(true);
				}			
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}		
	}
	
	@FXML
	/**
	 * Called when user clicks 'Generate Forecast' button in forecasts tab.
	 * Checks if any forecast generation parameters have been selected, and if yes, checks that the current
	 * data set of sales is valid for forecast generation through the forecasts tab model i.e must have at least
	 * 3 years of consecutive complete sales history, and all recorded years of sales history have to be consecutive
	 * as well (no gaps between years). Once the integrity of the sales data has been validated, the method checks
	 * which forecast generation parameters have been selected. If current year was a selected option, the method
	 * communicates with forecasts tab model to ensure that the current year of sales is not already complete.
	 * Then, the selected parameters are passed to the forecasts tab model to use to generate the desired forecasts.
	 * If the forecasts were generated successfully by the model, AppManager is notified that forecasts were generated
	 * and initializes and launches appropriate child window to display them appropriately.
	 * Notifies AppManager to display any model errors, if any. 
	 */
	private void onGenerateForecast()
	{
		boolean check = true;
		
		this.tab2Model.resetForecastData();
		
		if(this.currentYear.isSelected() || this.nextYear.isSelected())
		{
			boolean integrityOfSalesHistory = this.tab2Model.checkIntegrityOfSalesData(AppManager.getInstance().getProduct(this));
			
			if(integrityOfSalesHistory == true)
			{
				if(this.currentYear.isSelected())
				{
					byte isCurrentYearAlreadyComplete = this.tab2Model.checkIfCurrentYearComplete(AppManager.getInstance().getProduct(this));
					
					if(isCurrentYearAlreadyComplete == 1)
					{
						check = false;
						AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Current Sales History Cannot Be Proccessed", this.tab2Model.getModelErrors());
						this.tab2Model.clearErrors();
					}
					else if(isCurrentYearAlreadyComplete == 0)
					{
						check = false;
						AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab2Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
						this.tab2Model.clearErrors();
					}
				}
			}
			else
			{
				check = false;
				
				if(!this.tab2Model.getModelErrors().equals(""))
				{
					AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Current Sales History Cannot Be Proccessed", this.tab2Model.getModelErrors());
				}
				else if(this.tab2Model.getConnectionError() != null)
				{
					AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab2Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
				}
				
				this.tab2Model.clearErrors();
			}
		}
		else
		{
			check = false;
			AppManager.getInstance().sendAlert(new Alert(Alert.AlertType.ERROR), "ERROR", "Please Choose One or More Parameters to Generate a Forecast", null);
		}
		
		if(check == true)
		{
			byte option = 0;
			
			if(this.currentYear.isSelected())
			{
				option = 1;
			}
		
			if(this.nextYear.isSelected())
			{
				option = 2;
			}
			
			if(this.currentYear.isSelected() && this.nextYear.isSelected())
			{
				option = 3;
			}
			
			boolean forecastSuccess = this.tab2Model.generateForecast(option, AppManager.getInstance().getProduct(this));
			
			if(forecastSuccess == true)
			{
				AppManager.getInstance().notifyForecastGenerated(this, this.tab2Model.getGeneratedForecast());
			}
			else
			{
				AppManager.getInstance().sendAlert(AppManager.getInstance().createExceptionAlert(this.tab2Model.getConnectionError()), "ERROR", "Unable to Communicate with Database", "Error Details:");
			}
		}
	}
	
	@FXML
	/**
	 * Called when user clicks the 'Guide' option in the top menu; notifies AppManager to display a child 
	 * window with helpful info related to this window.
	 */
	private void onGuide()
	{
		String text = "From the 'Sales' tab, you can manage your selected product's/service's" + "\n" + "sales by clicking on the 'Manage' button under the sales table." + "\n" +
					"You can also visualize every quarterly sales for each year in the graph next" + "\n" + "to the sales table. Every 4 quarters represent a year (as layed out in the sales table)." + "\n\n" +
					"From the 'Forecasts' tab, you can generate a quarterly sales forecast for either:" + "\n" + "the latest year's unknown sales i.e current year, or next year's quarterly sales, or both." + "\n" +
					"First, you must ensure that you have added at least 3 years of complete quarterly sales history" + "\n" + "for your product/service, and that there are no gaps between the recorded years" + "\n" +
					"of sales history i.e recorded years are 100% consecutive. Once a forecast is generated," + "\n" + "you will have the option to store it to the system, where it will then appear in the" + "\n" + 
					"forecasts table under the 'Forecasts' tab. Once successfully stored, you can edit the forecasted sales" + "\n" + "if you desire by clicking on the 'Edit' button under the forecasts table." + "\n" + 
					"You can store as many forecasts for the same year as you wish, but keep in mind that for the comparison process" + "\n" + "in the 'Comparison' tab, you will be asked to choose only one." + "\n\n" +
					"From the 'Comparison' tab, you can generate a comparison chart plotting all of the" + "\n" + "sales against their corresponding forecast, if any, and a" + "\n" + "result of the mean absolute percentage error (MAPE) of all the forecats." + "\n" +
					"Note that the comparison chart will only generate if there is at least one forecast" + "\n" + "corresponding with on of the recorded years of sales history." + "\n" + "And, as mentioned previously, if you have multiple stored forecasts for the same year," + "\n" + 
					"you will be required to choose one for the comparison.";
		
		AppManager.getInstance().notifyTextPopup(this, "Sales/Forecasts/Comparison Station Guide", text);
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
	 * Called when user clicks the 'Manage' button under the sales table in the sales tab; notifies
	 * AppManager to open the MANAGE_SALES child window. 
	 */
	private void onManageSales()
	{
		AppManager.getInstance().notifyOpenNewWindow(this, ChildStages.MANAGE_SALES);
	}
}
