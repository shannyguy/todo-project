package myTodoProject.infra.elements;

import org.openqa.selenium.By;

/**
 * Provides all checkbox specific actions
 *
 */
public class CheckBox extends WebElementActions{
	
	public CheckBox(By by) {
		super(by);
	}
	
	/**
	 * Checks if a checkbox is checked
	 * @return true in case checked and false in case isn't
	 */
	protected Boolean isChecked(){
		Boolean isChecked = null;
		try{
			isChecked = getElement().isSelected();
		}catch(Exception e){
			reportResult("isChecked", "Is checked" + getBy(),"Failed", false, e);
		}
		return isChecked;
	}
	
	/**
	 * Verifies the checkbox is checked
	 */
	public void expectChecked(){
		Boolean isChecked = isChecked();
		if(isChecked != null){
			reportResult("ExpectChecked", getBy() + " Is checked", String.valueOf(isChecked).toString(), isChecked, new Exception("Element is not checked while should be"));
		}
		
		
		
	}
	
	/**
	 * Verifies the checkbox is not checked
	 */
	public void expectNotChecked(){
		Boolean isChecked = isChecked();
		if(isChecked != null){
			reportResult("ExpectNotChecked", getBy() + " should not be checked", String.valueOf(!isChecked).toString(), !isChecked, new Exception("Element is checked while shouldn't be"));
		}
		
		
	}

}
