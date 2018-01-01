package myTodoProject.infra.testManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.Test;
import myTodoProject.common.GlobalParameters;
import myTodoProject.infra.report.ActionResult;
import myTodoProject.infra.report.ExtentManager;
import myTodoProject.infra.utils.StringUtils;

public class TestManager extends TestListenerAdapter implements ISuiteListener, IInvokedMethodListener{
	
	public static Logger logger = LogManager.getLogger(TestManager.class.getName());
	private static final String GLOBAL_PARAMETERS_PROPERTIES_FILE_KEY = "propertiesFile";

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (isTest(method.getTestMethod()))
		{
			try{
				String testName = testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
				logger.info("***** Test: " + testName + " *****");
				ExtentManager.startTest(testName, method.getTestMethod().getDescription());
			}catch(Exception e){
				
			}
			
		}
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		ExtentManager.closeTest();
		
	}

	@Override
	public void onStart(ISuite suite) {
		ExtentManager.startSuite(suite.getName());
		ExtentManager.createInstance();
		loadGlobalParameters();
		
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method loads the global parameters from a properties file. <br>
	 * Get the path to the file from Java System variable with key:
	 * {@value #GLOBAL_PARAMETERS_PROPERTIES_FILE_KEY} default value is:
	 * "./resources/properties/local.properties"
	 *
	 * In order to set and use the Java system variables need to set it like
	 * this: -DpropertiesFile=./resources/properties/Local.properties
	 *
	 * From Eclipse - this is done from the "Run Configuration...", "Arguments"
	 * tab, "VM Arguments"
	 */
	private static void loadGlobalParameters()
	{
		// Get the properties file name from the Java system properties
		String propertiesFileName = System.getProperty(GLOBAL_PARAMETERS_PROPERTIES_FILE_KEY, "./src/main/resources/properties/GlobalParameters.properties");

		// Load the parameters from the properties file to the GlobalParameters
		StringUtils.loadParametersFromPropertiesFile(GlobalParameters.class, propertiesFileName);
	}
	
	public boolean isTest(ITestNGMethod testMethod)
	{
		return (!testMethod.isBeforeClassConfiguration() && !testMethod.isAfterClassConfiguration() && !testMethod.isBeforeSuiteConfiguration() && !testMethod.isAfterSuiteConfiguration());
	}
	
	public void onTestFailure(ITestResult result)
	{
		String methodName = getFailedMethodName(result);
		ActionResult actionResult = new ActionResult(methodName, false);
		ExtentManager.action(actionResult);
	}
	
	private String getFailedMethodName(ITestResult result)
	{
		int index = 0;
		String methodName = result.getThrowable().getStackTrace()[index].getMethodName();

		while (!methodName.contains("Test"))
		{
			index++;
			methodName = result.getThrowable().getStackTrace()[index].getMethodName();
		}

		index--;
		if (index < 0)
			index = 0;

		return result.getThrowable().getStackTrace()[index].getMethodName();
	}

}
