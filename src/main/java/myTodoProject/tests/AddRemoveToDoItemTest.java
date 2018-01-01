package myTodoProject.tests;

import org.testng.annotations.Test;

import myTodoProject.infra.pages.Pages;
import myTodoProject.infra.pages.ToDoPage;
import myTodoProject.infra.report.ExtentManager;
import myTodoProject.infra.testManager.TestBasic;

public class AddRemoveToDoItemTest extends TestBasic{
	
	private final static String FIRST_ITEM = "First item";
	private final static String ONE_ITEMS_COUNT_TEXT = "1 item left";
	
	@Test(testName = "Add items to list Test", description = "Verifies text typed in textbox is added to the todo list and marked as not done", groups =
		{ "sanity" })
	public void addItemToList(){
		ToDoPage toDoPage = Pages.todoPage;
		
		ExtentManager.step("Sets a new item", "Sets a new todo item");
		toDoPage.wait4Visibility();
		toDoPage.addNewItem(FIRST_ITEM);
		
		ExtentManager.step("Verifies the item was added", "Verifies the item was added to the list");
		toDoPage.verifyItemAdded(FIRST_ITEM);
		
		ExtentManager.step("Verifies the item is not checked", "Verifies the item is not checked");
		toDoPage.verifyItemNotChecked(FIRST_ITEM);
		
		ExtentManager.step("Verifies items count is 1", "Verifies items count was increased to 1");
		toDoPage.todoCountLabel.expectText(ONE_ITEMS_COUNT_TEXT);
	}
	
	@Test(testName = "Remove items from list", description = "Verifies todo item is removed when clicking the remove item icon", groups =
		{ "sanity" })
	public void removeItemFromList(){
		ToDoPage toDoPage = Pages.todoPage;
		
		ExtentManager.step("Sets a new item", "Sets a new todo item");
		toDoPage.wait4Visibility();
		toDoPage.addNewItem(FIRST_ITEM);
		
		ExtentManager.step("Verifies the item was added", "Verifies the item was added to the list");
		toDoPage.verifyItemAdded(FIRST_ITEM);
		
		ExtentManager.step("Hover and click the 'Remove item' icon", "Hover and click the 'Remove item' icon");
		toDoPage.removeItem(FIRST_ITEM);
		
		ExtentManager.step("Verifies the item was rmoved", "Verifies the item was removed");
		toDoPage.verifyItemRemoved(FIRST_ITEM);
		
		ExtentManager.step("Verifies the item was rmoved", "Verifies the item was removed");
		toDoPage.verifyItemRemoved(FIRST_ITEM);
		
		ExtentManager.step("Verifies count label was removed", "Verifies count label was removed");
		toDoPage.todoCountLabel.expectNotVisible();
		
		
	}


}
