package myTodoProject.infra.webDriver;

import java.io.File;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import myTodoProject.common.GlobalParameters;


public class ChromeDriverManager extends DriverManager{
	
	public static Logger logger = LogManager.getLogger(ChromeDriverManager.class.getName());

	 private ChromeDriverService chService;

	 /**
	  * Starts drivers service
	  */
	    @Override
	    public void startService() {
	        if (null == chService) {
	            try {
	                chService = new ChromeDriverService.Builder()
	                    .usingDriverExecutable(new File(GlobalParameters.SELENIUM_CHROME_DRIVER_PATH))
	                    .usingAnyFreePort()
	                    .build();
	                chService.start();
	            } catch (Exception e) {
	                logger.error(e.getMessage());
	            }
	        }
	    }

	    /**
	     * Stops driver service
	     */
	    @Override
	    public void stopService() {
	        if (null != chService && chService.isRunning())
	            chService.stop();
	    }

	    /**
	     * Creates driver
	     */
	    @Override
	    public void createDriver() {
	    	URL url = chService.getUrl();
	        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("test-type");
	        options.addArguments("--start-fullscreen");
	        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	        driver = new ThreadLocal<RemoteWebDriver>();
	        driver.set(new RemoteWebDriver(url, capabilities));
	        
	    }

}
