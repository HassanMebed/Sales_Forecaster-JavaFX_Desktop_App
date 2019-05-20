/**
 * ForecastsTableFormat.java
 */
package controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Hassan Mebed
 *
 * Class required for ObservableList data model used to initialize forecasts TableView row values and retrieve 
 * rows and row values from it.
 * Each object of this class represents a row in the TableView
 */
public class ForecastsTableFormat
{
	/** Year column */
	private SimpleIntegerProperty year = null;
	
	/** Q1 column */
	private SimpleDoubleProperty q1 = null;
	
	/** Q2 column */
	private SimpleDoubleProperty q2 = null;
	
	/** Q3 column */
	private SimpleDoubleProperty q3 = null;
	
	/** Q4 column */
	private SimpleDoubleProperty q4 = null;
	
	/** Date Stored column */
	private SimpleStringProperty dateStored = null;
	
	/**
	 * Parameterized constructor that initializes above global variables with passed parameters. 
	 * 
	 * @param year
	 * 			year being forecasted.
	 * @param q1
	 * 			Q1 forecast.
	 * @param q2
	 * 			Q2 forecast.
	 * @param q3
	 * 			Q3 forecast.
	 * @param q4
	 * 			Q4 forecast.
	 * @param dateStored
	 * 			date when forecasts were stored.
	 */
	public ForecastsTableFormat(int year, double q1, double q2, double q3, double q4, String dateStored)
	{
		this.year = new SimpleIntegerProperty(year);
		this.q1 = new SimpleDoubleProperty(q1);
		this.q2 = new SimpleDoubleProperty(q2);
		this.q3 = new SimpleDoubleProperty(q3);
		this.q4 = new SimpleDoubleProperty(q4);
		this.dateStored = new SimpleStringProperty(dateStored);
	}
	
	/**
	 * @return This row's date when forecasts were stored.
	 */
	public String getDateStored()
	{
		return this.dateStored.get();
	}
		
	/**
	 * @return This row's Q1 forecast.
	 */
	public double getQ1()
	{
		return this.q1.get();
	}
	
	/**
	 * @return This row's Q2 forecast.
	 */
	public double getQ2()
	{
		return this.q2.get();
	}
	
	/**
	 * @return This row's Q3 forecast.
	 */
	public double getQ3()
	{
		return this.q3.get();
	}
	
	/**
	 * @return This row's Q4 forecast.
	 */
	public double getQ4()
	{
		return this.q4.get();
	}
	
	/**
	 * @return This row's year being forecasted.
	 */
	public int getYear()
	{
		return this.year.get();
	}
}
