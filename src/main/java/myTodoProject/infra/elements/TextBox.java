package myTodoProject.infra.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

/**
 * Provides all Textbox specific actions
 */
public class TextBox extends WebElementActions{

	public TextBox(By by) {
		super(by);
	}
	
	/**
	 * Clears input element and inserts text
	 * @param value the text to type
	 */
	public void type(String value){
		try{
			getElement().clear();
			getElement().sendKeys(value);
			reportResult("TypeText", "Type: " + value + "into: " + getBy().toString(), "Succeed", true, null);
		}catch(Exception e){
			reportResult("TypeText", "Type: " + value + "into: " + getBy().toString(), "Failed", false, e);
		}
	}
	
	/**
	 * Appends text to input textbox value
	 * @param value the text to append
	 */
	public void append(String value){
		try{
			getElement().sendKeys(value);
			reportResult("AppendText", "Append: " + value + "into: " + getBy().toString(), "Succeed", true, null);
		}catch(Exception e){
			reportResult("AppendText", "Append: " + value + "into: " + getBy().toString(), "Failed", false, e);
		}
	}
	
	/**
	 * Clicks Enter
	 */
	public void pressEnter(){
		pressKey(Keys.ENTER);
	}
	
	/**
	 * Verifies text is as expected
	 * @param expectedText the expected text
	 */
	public void expectText(String expectedText){
		String actualText = getElement().getText();
		boolean equals = actualText.equals(expectedText);
		reportResult("verifyTextAsExpected", expectedText, actualText, equals, new Exception("Element's text is not  as expected"));
	}

}
