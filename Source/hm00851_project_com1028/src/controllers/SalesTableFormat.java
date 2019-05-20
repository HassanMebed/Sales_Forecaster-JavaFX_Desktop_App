/**
 * salesTableFormat.java
 */
package controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Hassan Mebed
 *
 * Class required for ObservableList data model used to initialize sales TableView row values and retrieve 
 * rows and row values from it.
 * Each object of this class represents a row in the TableView
 */
public class SalesTableFormat
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
	
	/**
	 * Parameterized constructor that initializes above global variables with passed parameters.
	 * 
	 * @param year
	 * 			year record.
	 * @param q1
	 * 			Q1 sales record.
	 * @param q2
	 * 			Q2 sales record.
	 * @param q3
	 * 			Q3 sales record.
	 * @param q4
	 * 			Q4 sales record.
	 */
	public SalesTableFormat(int year, double q1, double q2, double q3, double q4)
	{
		this.year = new SimpleIntegerProperty(year);
		this.q1 = new SimpleDoubleProperty(q1);
		this.q2 = new SimpleDoubleProperty(q2);
		this.q3 = new SimpleDoubleProperty(q3);
		this.q4 = new SimpleDoubleProperty(q4);
	}
	
	/**
	 * @return This row's Q1 sales record.
	 */
	public double getQ1()
	{
		return this.q1.get();
	}
	
	/**
	 * @return This row's Q2 sales record.
	 */
	public double getQ2()
	{
		return this.q2.get();
	}
	
	/**
	 * @return This row's Q3 sales record.
	 */
	public double getQ3()
	{
		return this.q3.get();
	}
	
	/**
	 * @return This row's Q4 sales record.
	 */
	public double getQ4()
	{
		return this.q4.get();
	}
	
	/**
	 * @return This row's year record
	 */
	public int getYear()
	{
		return this.year.get();
	}
}
