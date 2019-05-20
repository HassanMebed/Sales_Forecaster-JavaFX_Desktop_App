/**
 * ObjectConstructionTests.java
 */
package unit_tests;

import main.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;

import org.junit.Test;

import controllers.*;
import models.*;

/**
 * @author Hassan Mebed
 *
 * Tests each java class in this project on successful object construction.
 * Tests will run successfully, but there will be an exception upon all individual model object creations
 * since they all require a database connection from a running AppManager class upon creation, but this
 * exception is caught and handled in their constructors. The handling of it is to print the exception onto
 * the stack so that you can see that the AppManager dependency is indeed the cause of the exception.
 */
public class ObjectConstructionTests
{
	// classes in 'main' package
	private AppManager appManager = null;
	private User user = null;
	private Product product = null;
	private MainStages mainStage = null;
	private ChildStages childStage = null;
	
	// classes in 'controllers' package
	private AddProductController addProductController = null;
	private EditForecastsController editForecastsController = null;
	private ForecastsTableFormat forecastsTableFormat = null;
	private GeneratedForecastController generatedForecastController = null;
	private LoginController loginController = null;
	private ManageSalesController manageSalesController = null;
	private RegisterController registerController = null;
	private RemoveProductController removeProductController = null;
	private SalesTableFormat salesTableFormat = null;
	private SelectProductController selectProductController = null;
	private TextPopupController textPopupController = null;
	private WorkTabsController workTabsController = null;
	
	// classes in 'models' package
	private AddProductModel addProductModel = null;
	private EditForecastsModel editForecastsModel = null;
	private GeneratedForecastModel generatedForecastModel = null;
	private LoginModel loginModel = null;
	private ManageSalesModel manageSalesModel = null;
	private RegisterModel registerModel = null;
	private RemoveProductModel removeProductModel = null;
	private SelectProductModel selectProductModel = null;
	private WorkTab1Model workTab1Model = null;
	private WorkTab2Model workTab2Model = null;
	private WorkTab3Model workTab3Model = null;
	
	// tests for classes in 'main' package
	
	@Test
	public void testAppManagerConstruction()
	{
		this.appManager = new AppManager();
		
		assertNotNull(this.appManager);		
	}
	
	@Test
	public void testUserConstruction()
	{
		this.user = new User();
		
		assertNotNull(this.user);
		assertEquals(null, this.user.getFirstName());
	}
	
	@Test
	public void testProductConstruction()
	{
		this.product = new Product();
		
		assertNotNull(this.product);
		assertEquals(null, this.product.getName());
	}
	
	@Test
	public void testMainStagesConstruction()
	{
		this.mainStage = MainStages.LOGIN;
		assertEquals("/views/LoginView.fxml", this.mainStage.getUrl());
		
		this.mainStage = MainStages.REGISTER;
		assertEquals("/views/RegisterView.fxml", this.mainStage.getUrl());
		
		this.mainStage = MainStages.SELECT_PRODUCT;
		assertEquals("/views/SelectProductView.fxml", this.mainStage.getUrl());
		
		this.mainStage = MainStages.WORK_TABS;
		assertEquals("/views/WorkTabsView.fxml", this.mainStage.getUrl());
	}
	
	@Test
	public void testChildStagesConstruction()
	{
		this.childStage = ChildStages.ADD_PRODUCT;		
		assertEquals("Add Product/Service", this.childStage.getName());
		
		this.childStage = ChildStages.EDIT_FORECASTS;
		assertEquals("Edit Forecasts", this.childStage.getName());
		
		this.childStage = ChildStages.MANAGE_SALES;
		assertEquals("Add/Remove/Edit Sales", this.childStage.getName());
		
		this.childStage = ChildStages.REMOVE_PRODUCT;
		assertEquals("Remove Product/Service", this.childStage.getName());
		
		this.childStage = ChildStages.TEXT_POPUP;
		assertEquals(null, this.childStage.getName());
	}
	
	// tests for classes in 'controllers' package
	
	@Test
	public void testAddProductControllerConstruction()
	{
		this.addProductController = new AddProductController();
		
		assertNotNull(this.addProductController);
	}
	
	@Test
	public void testEditForecastsControllerConstruction()
	{
		this.editForecastsController = new EditForecastsController();
		
		assertNotNull(this.editForecastsController);
	}
	
	@Test
	public void testForecastsTableFormatConstruction()
	{
		this.forecastsTableFormat = new ForecastsTableFormat(1999, 1.0, 2.0, 3.0, 4.0, "test");
		
		assertEquals("test", this.forecastsTableFormat.getDateStored());
	}
	
	@Test
	public void testGeneratedForecastControllerConstruction()
	{
		double[] testData = {1.0, 2.0, 3.0, 4.0};
		LinkedHashMap<Integer, double[]> testForecast = new LinkedHashMap<Integer, double[]>();
		testForecast.put(1999, testData);
		
		this.generatedForecastController = new GeneratedForecastController(testForecast);
		
		assertNotNull(this.generatedForecastController);
	}
	
	@Test
	public void testLoginControllerConstruction()
	{
		this.loginController = new LoginController();
		
		assertNotNull(this.loginController);
	}
	
	@Test
	public void testManageSalesControllerConstruction()
	{
		this.manageSalesController = new ManageSalesController();
		
		assertNotNull(this.manageSalesController);
	}
	
	@Test
	public void testRegisterControllerConstruction()
	{
		this.registerController = new RegisterController();
		
		assertNotNull(this.registerController);
	}
	
	@Test
	public void testRemoveProductControllerConstruction()
	{
		this.removeProductController = new RemoveProductController();
		
		assertNotNull(this.removeProductController);
	}
	
	@Test
	public void testSalesTableFormatConstruction()
	{
		this.salesTableFormat = new SalesTableFormat(1999, 1.0, 2.0, 3.0, 4.0);
		
		assertEquals(1999, this.salesTableFormat.getYear());
	}
	
	@Test
	public void testSelectProductControllerConstruction()
	{
		this.selectProductController = new SelectProductController();
		
		assertNotNull(this.selectProductController);
	}
	
	@Test
	public void testTextPopupControllerConstruction()
	{
		this.textPopupController = new TextPopupController("test");
		
		assertNotNull(this.textPopupController);
	}
	
	@Test
	public void testWorkTabsControllerConstruction()
	{
		this.workTabsController = new WorkTabsController();
		
		assertNotNull(this.workTabsController);
	}
	
	// tests for classes in 'models' package
	
	@Test
	public void testAddProductModelContruction()
	{
		this.addProductModel = new AddProductModel();
		
		assertEquals("", this.addProductModel.getModelErrors());
	}
	
	@Test
	public void testEditForecastsModelContruction()
	{
		this.editForecastsModel = new EditForecastsModel();
		
		assertEquals("", this.editForecastsModel.getModelErrors());
	}
	
	@Test
	public void testGeneratedForecastModelContruction()
	{
		this.generatedForecastModel = new GeneratedForecastModel();
		
		assertEquals("", this.generatedForecastModel.getModelErrors());
	}
	
	@Test
	public void testLoginModelContruction()
	{
		this.loginModel = new LoginModel();
		
		assertEquals("", this.loginModel.getModelErrors());
	}
	
	@Test
	public void testManageSalesModelContruction()
	{
		this.manageSalesModel = new ManageSalesModel();
		
		assertEquals("", this.manageSalesModel.getModelErrors());
	}
	
	@Test
	public void testRegisterModelContruction()
	{
		this.registerModel = new RegisterModel();
		
		assertEquals("", this.registerModel.getModelErrors());
	}
	
	@Test
	public void testRemoveProductModelContruction()
	{
		this.removeProductModel = new RemoveProductModel();
		
		assertEquals("", this.removeProductModel.getModelErrors());
	}
	
	@Test
	public void testSelectProductModelContruction()
	{
		this.selectProductModel = new SelectProductModel();
		
		assertEquals("", this.selectProductModel.getModelErrors());
	}
	
	@Test
	public void testWorkTab1ModelContruction()
	{
		this.workTab1Model = new WorkTab1Model();
		
		assertEquals("", this.workTab1Model.getModelErrors());
	}
	
	@Test
	public void testWorkTab2ModelContruction()
	{
		this.workTab2Model = new WorkTab2Model();
		
		assertEquals("", this.workTab2Model.getModelErrors());
	}
	
	@Test
	public void testWorkTab3ModelContruction()
	{
		this.workTab3Model = new WorkTab3Model();
		
		assertEquals("", this.workTab3Model.getModelErrors());
	}
	
}
