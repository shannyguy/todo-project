package myTodoProject.infra.pages;


import org.openqa.selenium.By;
import myTodoProject.infra.elements.Button;
import myTodoProject.infra.elements.WebElementActions;




public class MainPage extends WebElementActions{
	
	private String url = "http://todomvc.com";
	
	public Button todoAngularLink;

	public MainPage() {
		super(By.className("container"));
		todoAngularLink = new Button(By.xpath("//a[@href = 'examples/angularjs']"));
	}

	public void navigateTo()
	{
		navigateTo(url);
	}
}
