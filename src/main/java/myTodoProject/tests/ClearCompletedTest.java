package myTodoProject.tests;

import org.testng.annotations.Test;

import myTodoProject.infra.pages.Pages;
import myTodoProject.infra.pages.ToDoPage;
import myTodoProject.infra.report.ExtentManager;
import myTodoProject.infra.testManager.TestBasic;

public class ClearCompletedTest extends TestBasic{
	
	private final static String FIRST_ITEM = "First item";
	private final static String SECOND_ITEM = "Second item";
	private final static String ONE_ITEMS_COUNT_TEXT = "1 item left";
	private final static String TWO_ITEMS_COUNT_TEXT = "2 items left";
	
	@Test(testName = "Clear completed", description = "Verifies 'Clear completed' link is displayed when there are completed items and it clears them when clicked", groups =
		{ "sanity" })
	public void clearCompletedTest(){
		ToDoPage toDoPage = Pages.todoPage;
		
		ExtentManager.step("Sets two new items", "Sets two new items");
		toDoPage.wait4Visibility();
		toDoPage.addNewItem(FIRST_ITEM);
		toDoPage.addNewItem(SECOND_ITEM);
		
		ExtentManager.step("Verifies items count is 2", "Verifies items count is 2");
		toDoPage.todoCountLabel.expectText(TWO_ITEMS_COUNT_TEXT);
		
		ExtentManager.step("Mark the first added item as complete", "Mark the first added item as complete by clicking its checkbox");
		toDoPage.markItemAsCompleted(FIRST_ITEM);
		
		ExtentManager.step("Verifies items count is 1", "Verifies items count is 1");
		toDoPage.todoCountLabel.expectText(ONE_ITEMS_COUNT_TEXT);
		
		ExtentManager.step("Clicks clear completed link", "Clicks clear completed link");
		toDoPage.clearCompletedButton.click();
		
		ExtentManager.step("Verifies items count remains 1", "Verifies items count remains 1");
		toDoPage.todoCountLabel.expectText(ONE_ITEMS_COUNT_TEXT);
		
		ExtentManager.step("Verifies the item is removed", "Verifies the item is removed from the todo list");
		toDoPage.verifyItemRemoved(FIRST_ITEM);
		
		ExtentManager.step("Verifies the other item remained on the list as not completed", "Verifies the other item remained on the list as not completed");
		toDoPage.verifyItemNotChecked(SECOND_ITEM);
		
		ExtentManager.step("Verifies clear completed link is not available", "Verifies clear completed link is not available");
		toDoPage.clearCompletedButton.expectNotVisible();
		
	}
	

}
