package myTodoProject.infra.webDriver;

public class DriverManagerFactory {
	
	/**
	 * Gets the driver manager fits the selected browser
	 * @param type browser type
	 * @return the web driver manager
	 */
	 public static DriverManager getManager(DriverType type) {

	        DriverManager driverManager;

	        switch (type) {
	            case CHROME:
	                driverManager = new ChromeDriverManager();
	                break;
	            case IE:
	                driverManager = null;
	                break;
	            default:
	                driverManager = null;
	                break;
	        }
	        return driverManager;
	        
	 }
}
