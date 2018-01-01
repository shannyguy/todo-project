package myTodoProject.infra.pages;

public class Pages {
	
	public static ToDoPage todoPage;
	
	public static void initPages(){
		if(todoPage == null){
			todoPage = new ToDoPage();
		}
	}

}
