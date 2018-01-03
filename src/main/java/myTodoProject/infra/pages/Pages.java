package myTodoProject.infra.pages;

/**
 * Responsible for initiating all application pages
 *
 */
public class Pages {
	
	public static ToDoPage todoPage;
	public static MainPage mainPage;
	
	public static void initPages(){
		if(mainPage == null){
			mainPage = new MainPage();
		}
		if(todoPage == null){
			todoPage = new ToDoPage();
		}
	}

}
