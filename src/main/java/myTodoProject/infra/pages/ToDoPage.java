package myTodoProject.infra.pages;

import org.openqa.selenium.By;
import myTodoProject.infra.elements.Button;
import myTodoProject.infra.elements.CheckBox;
import myTodoProject.infra.elements.Label;
import myTodoProject.infra.elements.TextBox;
import myTodoProject.infra.elements.WebElementActions;

public class ToDoPage extends WebElementActions{
	
	private String url = "http://todomvc.com/examples/angularjs/#/";
	public TextBox newToDoTextBox;
	public CheckBox toggleAllButton;
	public Button clearCompletedButton;
	public Label todoCountLabel;
	private String itemLocator = "//*[@id = 'todo-list']//label[text() = '%s']";
	private String itemCheckboxLocator = "//div[@class = 'view']//input[@type = 'checkbox' and following-sibling::label[text() = '%s']]";
	private String completedItemLocator = "//*[@id = 'todo-list']//*[contains(@class, 'completed')]//label[text() = '%s']";
	private String removeItemIconLocator = "//*[@id = 'todo-list']//label[text() = '%s']/following-sibling::button[@class = 'destroy']";
	
	public ToDoPage(){
		super(By.id("todoapp"));
		newToDoTextBox = new TextBox(By.id("new-todo"));
		toggleAllButton = new CheckBox(By.id("toggle-all"));
		clearCompletedButton = new Button(By.id("clear-completed"));
		todoCountLabel = new Label(By.id("todo-count"));
	}
	
	public void navigateTo()
	{
		navigateTo(url);
	}
	
	public void verifyItemAdded(String itemText){
		TextBox todoItem = new TextBox(By.xpath(String.format(itemLocator, itemText)));
		todoItem.wait4Visibility();
	}
	
	public void markItemAsCompleted(String itemText){
		CheckBox completeCheckbox = new CheckBox(By.xpath(String.format(itemCheckboxLocator, itemText)));
		completeCheckbox.click();
	}
	
	public void verifyItemRemoved(String itemText){
		TextBox todoItem = new TextBox(By.xpath(String.format(itemLocator, itemText)));
		todoItem.expectNotVisible();
	}
	
	public void verifyItemNotChecked(String itemText){
		CheckBox checkBox = new CheckBox(By.xpath(String.format(itemCheckboxLocator, itemText)));
		checkBox.expectNotChecked();
	}
	
	public void verifyItemChecked(String itemText){
		CheckBox checkBox = new CheckBox(By.xpath(String.format(itemCheckboxLocator, itemText)));
		checkBox.expectChecked();
	}
	
	public void verifyItemMarkedAsCompleted(String itemText){
		Label item = new Label(By.xpath(String.format(completedItemLocator, itemText)));
		item.wait4Visibility();
	}
	
	public void removeItem(String itemText){
		TextBox todoItem = new TextBox(By.xpath(String.format(itemLocator, itemText)));
		Button removeIcon = new Button(By.xpath(String.format(removeItemIconLocator, itemText)));
		todoItem.hoverOver();
		removeIcon.click();
	}
	
	public void addNewItem(String itemText){
		newToDoTextBox.type(itemText);
		newToDoTextBox.pressEnter();
	}
	
}
