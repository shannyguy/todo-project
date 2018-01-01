package myTodoProject.tests;

import org.testng.annotations.Test;

import myTodoProject.infra.pages.Pages;
import myTodoProject.infra.pages.ToDoPage;
import myTodoProject.infra.report.ExtentManager;
import myTodoProject.infra.testManager.TestBasic;

public class MarkAsCompleteTest extends TestBasic{
	
	private final static String FIRST_ITEM = "First item";
	private final static String SECOND_ITEM = "Second item";
	private final static String ITEMS_COUNT_TEXT = "%s items left";
	
	@Test(testName = "Mark as complete", description = "Verifies todo item is marked as complete when checking it's checbox and items count label is updated", groups =
		{ "sanity" })
	public void markItemAsComplete(){
		ToDoPage toDoPage = Pages.todoPage;	
		
		ExtentManager.step("Sets a new item", "Sets a new todo item");
		toDoPage.wait4Visibility();
		toDoPage.addNewItem(FIRST_ITEM);
		
		ExtentManager.step("Verifies the item was added", "Verifies the item was added to the list");
		toDoPage.verifyItemAdded(FIRST_ITEM);
		
		ExtentManager.step("Mark the added item as complete", "Mark the added item as complete by clicking its checkbox");
		toDoPage.markItemAsCompleted(FIRST_ITEM);
		
		ExtentManager.step("Verifies the item is marked as completed", "Verifies the item is marked as completed");
		toDoPage.verifyItemMarkedAsCompleted(FIRST_ITEM);
		
		ExtentManager.step("Verifies items count is 0", "Verifies items count is 0");
		toDoPage.todoCountLabel.expectText(String.format(ITEMS_COUNT_TEXT, 0));
		
		ExtentManager.step("Verifies clear completed link is displayed", "Verifies clear completed link is displayed");
		toDoPage.clearCompletedButton.wait4Visibility();
	}
	
	@Test(testName = "Mark all items as complete", description = "Verifies all items are marked as complete when checking 'check all' checkbox", groups =
		{ "sanity" })
	public void markAllItemsAsComplete(){
		ToDoPage toDoPage = Pages.todoPage;
		
		ExtentManager.step("Sets two new items", "Sets two new items");
		toDoPage.wait4Visibility();
		toDoPage.addNewItem(FIRST_ITEM);
		toDoPage.addNewItem(SECOND_ITEM);
		
		ExtentManager.step("Verifies items count is 2", "Verifies items count is 2");
		toDoPage.todoCountLabel.expectText(String.format(ITEMS_COUNT_TEXT, 2));
		
		ExtentManager.step("Verifies 'Mark all' checkbox is not checked", "Verifies 'Mark all' checkbox is not checked");
		toDoPage.toggleAllButton.expectNotChecked();
		
		ExtentManager.step("Clicks 'Mark all' checkbox", "Clicks 'Mark all' checkbox");
		toDoPage.toggleAllButton.click();
		
		ExtentManager.step("Verifies 'Mark all' checkbox is checked", "Verifies 'Mark all' checkbox is checked");
		toDoPage.toggleAllButton.expectChecked();
		
		ExtentManager.step("Verifies both items are checked", "Verifies both items are checked");
		toDoPage.verifyItemChecked(FIRST_ITEM);
		toDoPage.verifyItemChecked(SECOND_ITEM);
		
		ExtentManager.step("Verifies both items are marked as completed", "Verifies both items are marked as completed");
		toDoPage.verifyItemMarkedAsCompleted(FIRST_ITEM);
		toDoPage.verifyItemMarkedAsCompleted(SECOND_ITEM);
		
		ExtentManager.step("Verifies items count is 0", "Verifies items count is 0");
		toDoPage.todoCountLabel.expectText(String.format(ITEMS_COUNT_TEXT, 0));
		
		ExtentManager.step("Verifies clear completed link is displayed", "Verifies clear completed link is displayed");
		toDoPage.clearCompletedButton.wait4Visibility();
				
		ExtentManager.step("Clicks 'Mark all' checkbox", "Clicks 'Mark all' checkbox");
		toDoPage.toggleAllButton.click();
		
		ExtentManager.step("Verifies 'Mark all' checkbox is not checked", "Verifies 'Mark all' checkbox not checked");
		toDoPage.toggleAllButton.expectNotChecked();
		
		ExtentManager.step("Verifies both items are not checked", "Verifies both items are not checked");
		toDoPage.toggleAllButton.expectNotChecked();
		
		ExtentManager.step("Mark both items as complete", "Mark both items as complete by clicking their checkbox");
		toDoPage.markItemAsCompleted(FIRST_ITEM);
		toDoPage.markItemAsCompleted(SECOND_ITEM);
		
		ExtentManager.step("Verifies 'Mark all' checkbox is checked", "Verifies 'Mark all' checkbox is checked");
		toDoPage.toggleAllButton.expectChecked();
	}
	
	

}
