package myTodoProject.common;

/**
 * This class contains all the global environment parameters.
 *
 * At runtime - the values for all the parameters will be loaded from a properties file.
 *
 * In order to specify another file, you need to use Java System Properties (VM arguments). The property name is "<b>propertiesFile</b>".<br>
 * The syntax to pass it Java is: <b>-DpropertiesFile="Path to the properties file"</b>. <br>
 *
 * For example: <b>-DpropertiesFile=./resources/properties/Staging.properties</b> <br>
 *
 * You can export this class to a properties file by running it as a Java application. In the "Save" dialog - select a file to save it to.
 */
public class GlobalParameters {

	public static String SELENIUM_BROSWER;
	public static String SELENIUM_IS_ENABLED_LOGGING;
	public static String SELENIUM_CHROME_DRIVER_PATH;
}
