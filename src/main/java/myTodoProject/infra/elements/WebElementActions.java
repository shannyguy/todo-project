package myTodoProject.infra.elements;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import myTodoProject.common.commonParameters;
import myTodoProject.infra.report.ActionResult;
import myTodoProject.infra.report.ExtentManager;

/**
 * Provides all common webElements actions
 *
 */
public class WebElementActions {
	
	public static Logger logger = LogManager.getLogger(WebElementActions.class.getName());
	
	public static WebDriver webDriver;
	
	private By by;
	
	public WebElementActions(By by){
		this.by = by;
	}
	
	public By getBy(){
		return this.by;
	}

	/**
	 * Navigates to provided url
	 * @param url the url to navigate to
	 */
	public void navigateTo(String url){
		navigateTo(url, commonParameters.PAGE_LOAD_TIMEOUT_SECONDS);
	}
	
	/**
	 * Navigates to provided url
	 * @param url url the url to navigate to
	 * @param timeOut the action timeout
	 */
	private void navigateTo(String url, int timeOut){
		webDriver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
		try
		{		
			webDriver.navigate().to(url);
			reportResult("navigate", url, "succeed", true, null);
		}
		catch (Exception e)
		{
			reportResult("navigate", url, "Failed", false, e);
		}
	}
	
	/**
	 * Gets the WebElement 
	 * @return the WebElement
	 */
	protected WebElement getElement(){
		WebElement webElement = null;
		try{
			webElement = webDriver.findElement(by);
			reportResult("getElement", "Find element: " + by.toString(),"Succeed", true, null);
		}catch(Exception e){
			reportResult("getElement", "Find element: " + by.toString(), "Failed" ,false, e);
		}
		return webElement;
	}
	
	/**
	 * Send keys to input element
	 * @param keys to send
	 */
	public void pressKey(Keys keys){
		getElement().sendKeys(keys);
	}
	
	/**
	 * Clicks an element
	 */
	public void click(){
		try{
		WebElement element = getElement();
		wait4Visibility();
		element.click();
		reportResult("click", by.toString() + " should be clicked", "succeed", true, null);
		}catch(Exception e){
			reportResult("click", by.toString() + " should be clicked", "Failed", false, e);
		}
	}
	
	/**
	 * Hover over element
	 */
	public void hoverOver(){
		Actions action = new Actions(webDriver);
		action.moveToElement(getElement()).build().perform();
	}
		
	/**
	 * Waits for Element's visibility
	 */
	public void wait4Visibility()
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(webDriver, commonParameters.ELEMENT_TIMEOUT_SECONDS);
			wait.until(ExpectedConditions.visibilityOf(getElement()));
			reportResult("wait4VisibilityOfElement", by.toString() + " visible", "true", true, null);
		}
		catch (Exception e)
		{
			reportResult("wait4VisibilityOfElement", by.toString() + " visible", "false", false, e);
		}
	}
	
	/**
	 * Checks if element is visible
	 * @return true in case visible and false isn't
	 */
	public boolean isVisible(){
		return getElement().isDisplayed();
	}
	
	/**
	 * Verifies the element is not visible
	 */
	public void expectNotVisible(){
		boolean found = true;
		try{
			found = webDriver.findElement(by).isDisplayed();		
		}catch(Exception e){
			found = false;
		}
		reportResult("ExpectNotVisible", "NotVisible", String.valueOf(!found), !found, new RuntimeException("Element should't be visible"));	
		
	}
	
	/**
	 * Adds action result to the report
	 * @param actionName the action name
	 * @param expectedResult expected result
	 * @param actualResult actual result
	 * @param success did the action succeed
	 * @param error the error in case of failure
	 */
	public static void reportResult(String actionName, String expectedResult, String actualResult, boolean success, Exception error)
	{
		ActionResult result = new ActionResult(actionName, success);
		result.addParameter("ExpectedValue", expectedResult);
		result.addParameter("ActualValue", actualResult);
		if(!success){
			try{
				result.setErrorMessage(error.getMessage());
				ExtentManager.action(result);
				logger.error(error.getMessage());
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			throw new RuntimeException(error);
		}		
	}


}
