/**
 * WorkTab2ModelTests.java
 */
package unit_tests;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;


/**
 * @author Hassan Mebed
 * 
 * Tests the three main methods in WorkTab2Model class, which are essentially used all together to 
 * generate a forecast. In the process, tests the boundaries and accuracy levels of the arima model,
 * and the imported time series analysis library used in this software.
 * 
 * Like all the model classes of this application, WorkTab2Model requires a connection to the application's
 * database, so creating an instance of WorkTab2Model for the purposes of testing will not work, because the
 * AppManager class needs to be running in order for any model class to acquire the connection to the application's
 * database. In addition, this application does not support a test database environment, so using the actual
 * application's database for the purposes of testing is very wrong. In any case, tests that involve retrieving
 * and manipulating data from a database are usually integration tests, not unit tests.
 * To remain within the scope of simple unit tests, this class will have mock methods of the three main methods
 * in WorkTab2Model that mimic their behavior and output.
 * 
 */
public class WorkTab2ModelMockTests
{	
	/**
	 * Mock of 'checkIntegrityOfSalesData(Product product)' method.
	 * Iterates over a LinkedHashMap using iterator to mimic iteration over a ResultSet object.
	 * 
	 * @param sales
	 * 			represents a result set of all sales records for a product in the application's database;
	 * 			hash map unique key is year, and corresponding value is double array containing 4 quarterly
	 * 			sales values.
	 * @return true if given sales history holds for the two properties mentioned in the original method's 
	 * 		   description, false if not.
	 */
	private boolean checkIntegrityOfSalesDataMock(LinkedHashMap<Integer, double[]> sales)
	{	
		boolean check = true;
		boolean salesDataSufficient = true;
		int completeYears = 0;
			
		if(sales.size() > 0)
		{	
			if(sales.entrySet().iterator().next().getValue()[3] > 0.0)
			{
				completeYears = completeYears + 1;
			}
			
			Iterator<Map.Entry<Integer, double[]>> records = sales.entrySet().iterator();
			records.next();
			
			while(records.hasNext())
			{				
				if(records.next().getValue()[0] > 0.0 || records.next().getValue()[1] > 0.0 || records.next().getValue()[2] > 0.0 || records.next().getValue()[3] > 0.0)
				{
					completeYears = completeYears + 1;
				}
			}
		}
		else
		{
			salesDataSufficient = false;
		}
			
		if(completeYears < 3)
		{
			salesDataSufficient = false;
		}
			
		if(salesDataSufficient == true)
		{
			Iterator<Map.Entry<Integer, double[]>> records = sales.entrySet().iterator();
				
			int year = records.next().getKey();
				
			while(records.hasNext())
			{
				year = year - 1;
					
				if(records.next().getKey() != year)
				{
					check = false;
					break;
				}					
			}
		}
		else
		{
			check = false;
		}
		
		return check;
	}
	
	/**
	 * Mock of 'checkIfCurrentYearComplete(Product product)' method.
	 * Iterates over a LinkedHashMap using iterator to mimic iteration over a ResultSet object.
	 * 
	 * @param sales
	 * 			represents a result set of all sales records for a product in the application's database;
	 * 			hash map unique key is year, and corresponding value is double array containing 4 quarterly
	 * 			sales values.	
	 * @return 1 if latest year i.e first year in LinkedHashMap has completed quarterly sales, -1 if not.
	 */
	private byte checkIfCurrentYearCompleteMock(LinkedHashMap<Integer, double[]> sales)
	{
		byte check = 0;
		Iterator<Map.Entry<Integer, double[]>> records = sales.entrySet().iterator();
		
		if(records.next().getValue()[3] > 0.0)
		{
			check = 1;
		}
		else
		{
			check = -1;
		}
		
		return check;
	}
	
	/**
	 * Mock of 'generateForecast(byte option, Product product)' method.
	 * Generates forecast for only one of the options which is for the next year. The logic of the other two
	 * options is very similar to this one, except when forecasting current year, the unknowns arima parameter
	 * changes according to how many quarters in current year/latest year need forecasting.
	 * 
	 * @param sales
	 * 			represents the sales array that is produced from querying the application's database and
	 * 			adding each sales record of a product to the array. Every 4 consecutive elements represent
	 * 			4 quarter sales of a single year.
	 * @return 4 quarterly sales forecasts for the next year
	 */
	private double[] generateForecastMock(double[] sales)
	{
		byte unknowns = 4;
		byte period = 4;
		
		ForecastResult forecastResult = Arima.forecast_arima(sales, unknowns, new ArimaParams(1, 0, 0, 0, 1, 0, period));							
		double[] forecastsNextYear = forecastResult.getForecast();
		
		return forecastsNextYear;
	}
	
	@Test
	/**
	 * Tests the 'checkIntegrityOfSales' mock method.
	 * Tests output of a valid sales history and two invalid sales histories, each suffering due to
	 * one of the two properties(amount of data or if years are consecutive or not) not holding.
	 *
	 */
	public void testCheckIntegrityOfSalesDataMock()
	{
		LinkedHashMap<Integer, double[]> salesGood = new LinkedHashMap<Integer, double[]>();
		LinkedHashMap<Integer, double[]> salesBadNotEnoughYears = new LinkedHashMap<Integer, double[]>();
		LinkedHashMap<Integer, double[]> salesBadNotConsecutiveYears = new LinkedHashMap<Integer, double[]>();
		double[] salesForSampleYear = {1.0, 2.0, 3.0, 4.0};
		
		salesGood.put(2004, salesForSampleYear);
		salesGood.put(2003, salesForSampleYear);
		salesGood.put(2002, salesForSampleYear);
		salesGood.put(2001, salesForSampleYear);
		
		salesBadNotEnoughYears.put(2004, salesForSampleYear);
		salesBadNotEnoughYears.put(2003, salesForSampleYear);
		
		salesBadNotConsecutiveYears.put(2004, salesForSampleYear);
		salesBadNotConsecutiveYears.put(2003, salesForSampleYear);
		salesBadNotConsecutiveYears.put(2001, salesForSampleYear);
		salesBadNotConsecutiveYears.put(2000, salesForSampleYear);
		
		assertEquals(true, this.checkIntegrityOfSalesDataMock(salesGood));
		assertEquals(false, this.checkIntegrityOfSalesDataMock(salesBadNotEnoughYears));
		assertEquals(false, this.checkIntegrityOfSalesDataMock(salesBadNotConsecutiveYears));	
	}
	
	@Test 
	/**
	 * Tests the 'checkIfCurrentYearComplete' mock method.
	 */
	public void testCheckIfCurrentYearCompleteMock()
	{
		LinkedHashMap<Integer, double[]> salesRecordsWithCurrentYearComplete = new LinkedHashMap<Integer, double[]>();
		LinkedHashMap<Integer, double[]> salesRecordsWithCurrentYearCompleteV2 = new LinkedHashMap<Integer, double[]>();
		LinkedHashMap<Integer, double[]> salesRecordsWithCurrentYearNotComplete = new LinkedHashMap<Integer, double[]>();
		double[] salesForSampleYear = {1.0, 2.0, 3.0, 4.0};
		double[] salesForSampleYearV2 = {1.0, 2.0, 0.0, 4.0};
		double[] salesForSampleYearNotComplete = {1.0, 2.0, 0.0, 0.0};
			
		salesRecordsWithCurrentYearComplete.put(2004, salesForSampleYear);
		salesRecordsWithCurrentYearComplete.put(2003, salesForSampleYear);
		salesRecordsWithCurrentYearComplete.put(2002, salesForSampleYear);
		salesRecordsWithCurrentYearComplete.put(2001, salesForSampleYear);
		
		salesRecordsWithCurrentYearCompleteV2.put(2004, salesForSampleYearV2);
		salesRecordsWithCurrentYearCompleteV2.put(2003, salesForSampleYear);
		salesRecordsWithCurrentYearCompleteV2.put(2002, salesForSampleYear);
		salesRecordsWithCurrentYearCompleteV2.put(2001, salesForSampleYear);
		
		salesRecordsWithCurrentYearNotComplete.put(2004, salesForSampleYearNotComplete);
		salesRecordsWithCurrentYearNotComplete.put(2003, salesForSampleYear);
		salesRecordsWithCurrentYearNotComplete.put(2002, salesForSampleYear);
		salesRecordsWithCurrentYearNotComplete.put(2001, salesForSampleYear);
		
		assertEquals(1, this.checkIfCurrentYearCompleteMock(salesRecordsWithCurrentYearComplete));
		assertEquals(1, this.checkIfCurrentYearCompleteMock(salesRecordsWithCurrentYearCompleteV2));
		assertEquals(-1, this.checkIfCurrentYearCompleteMock(salesRecordsWithCurrentYearNotComplete));
	}
	
	@Test
	/**
	 * Tests the 'generateForecast' mock method
	 * Uses actual quarterly sales data of Coca-Cola from https://ycharts.com/companies/KO/revenues
	 * Coca-Cola's quarter 1 always ends on March 31st of each year, and quarter 4 on December 31st of
	 * each year.
	 * Less data points = less accuracy in this case, which is why forecasts for lower boundary sales 
	 * history is allowed to be off by 3 for lower boundary and the more data-points added, the more
	 * this "off" value will decrease.
	 */
	public void testGenerateForecastMock()
	{
		/** sales data from years 2007 - 2009; 3 years of complete data is minimum requirement for forecast generation */
		double[] salesDataJustEnoughQuarterlySales = {6.103, 7.733, 7.69, 7.331, 7.379, 9.046, 8.393, 7.126, 7.169, 8.267, 8.044, 7.51};
		
		/** sales data from years 2007 - 2010 */
		double[] salesDataJustOneYearAboveLowerBoundary = {6.103, 7.733, 7.69, 7.331, 7.379, 9.046, 8.393, 7.126, 7.169, 8.267, 8.044, 7.51, 7.525, 8.674, 8.426, 10.49};
		
		/** sales data from years 2007 - 2011 */
		double[] salesDataWithFairAmountOfDataPoints = {6.103, 7.733, 7.69, 7.331, 7.379, 9.046, 8.393, 7.126, 7.169, 8.267, 8.044, 7.51, 7.525, 8.674, 8.426, 10.49, 10.52, 12.74, 12.25, 11.04};
		
		double[] resultForFairAmountOfDataPoints = this.generateForecastMock(salesDataWithFairAmountOfDataPoints);
		double[] resultForLowerBoundarySales = this.generateForecastMock(salesDataJustEnoughQuarterlySales);
		double[] resultForOneYearAboveLowerBoundary = this.generateForecastMock(salesDataJustOneYearAboveLowerBoundary);
		
		assertEquals(7.525, resultForLowerBoundarySales[0], 3);
		assertEquals(8.674, resultForLowerBoundarySales[1], 3);
		assertEquals(8.426, resultForLowerBoundarySales[2], 3);
		assertEquals(10.49, resultForLowerBoundarySales[3], 3);
		
		assertEquals(10.52, resultForOneYearAboveLowerBoundary[0], 2.6);
		assertEquals(12.74, resultForOneYearAboveLowerBoundary[1], 2.6);
		assertEquals(12.25, resultForOneYearAboveLowerBoundary[2], 2.6);
		assertEquals(11.04, resultForOneYearAboveLowerBoundary[3], 2.6);
		
		assertEquals(11.14, resultForFairAmountOfDataPoints[0], 1);
		assertEquals(13.08, resultForFairAmountOfDataPoints[1], 1);
		assertEquals(12.34, resultForFairAmountOfDataPoints[2], 1);
		assertEquals(11.46, resultForFairAmountOfDataPoints[3], 1);
	}
}
