package myTodoProject.infra.testManager;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import myTodoProject.common.GlobalParameters;
import myTodoProject.infra.elements.WebElementActions;
import myTodoProject.infra.pages.Pages;
import myTodoProject.infra.pages.ToDoPage;
import myTodoProject.infra.report.ExtentManager;
import myTodoProject.infra.webDriver.DriverManager;
import myTodoProject.infra.webDriver.DriverManagerFactory;
import myTodoProject.infra.webDriver.DriverType;

public class TestBasic {
	
	public static Logger logger = LogManager.getLogger(TestBasic.class.getName());
	private static WebDriver webDriver;
	private DriverManager driverManager;
	
	@BeforeSuite(alwaysRun = true)
	public void initSuite() throws Exception
	{
		driverManager = DriverManagerFactory.getManager(DriverType.valueOf(GlobalParameters.SELENIUM_BROSWER));
		webDriver = driverManager.getDriver().get();
		ExtentManager.webDriver = webDriver;
		WebElementActions.webDriver = webDriver;
		initPages();
		Pages.todoPage.navigateTo(GlobalParameters.MAIN_PAGE);
	}
	
	/**
	 * Executed before each test class
	 */
	@BeforeClass(alwaysRun = true)
	public void initTest() {
		init();	
	}

	/**
	 * Executed before each test method
	 */
	@BeforeMethod(alwaysRun = true)
	public void initMethod() {
		init();	
	}
	
	
	/**
	 * This method stops the driver
	 */
	@AfterSuite(alwaysRun = true)
	public void afterSuite()
	{
		driverManager.quitDriver();
	}
	private void initPages(){
		Pages.initPages();
	}

	public void startStep(String testCaseName, String testCaseDesc)
	{
		logger.info("##### Step: " + testCaseName + " #####");
		ExtentManager.step(testCaseName, testCaseDesc);
	}
	
	private void init(){
		ToDoPage todoPage = Pages.todoPage;
		if(Pages.todoPage.toggleAllButton.isVisible()){
			if(!todoPage.clearCompletedButton.isVisible()){
				todoPage.toggleAllButton.click();
			}	
			todoPage.clearCompletedButton.click();
		}
	}


}
