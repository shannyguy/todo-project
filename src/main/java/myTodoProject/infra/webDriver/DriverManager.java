package myTodoProject.infra.webDriver;

import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class DriverManager {

	protected ThreadLocal<RemoteWebDriver> driver;
    protected abstract void startService();
    protected abstract void stopService();
    protected abstract void createDriver();

    /**
     * Stops web driver
     */
    public void quitDriver() {
    	
        if (null != driver) {
            driver.get().quit();
            driver = null;
        }
        stopService();

    }

    /**
     * Gets the web driver
     * @return the web driver
     */
    public ThreadLocal<RemoteWebDriver> getDriver() {
        if (null == driver) {
            startService();
            createDriver();
        }
        return driver;
    }

}
