package myTodoProject.infra.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import static j2html.TagCreator.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import j2html.tags.ContainerTag;

public class ExtentManager {
	
	private static final Logger logger = LogManager.getLogger(ExtentManager.class.getName());

	private static final ThreadLocal<ExtentReports> EXTENT_REPORTS = new ThreadLocal<ExtentReports>();
	private static final ThreadLocal<String> SUITE = ThreadLocal.withInitial(() -> "");
	public static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<ExtentTest>();
	private static final ThreadLocal<ExtentTest> STEP = new ThreadLocal<ExtentTest>();
	private static final Format DATE_FORMATTER = new SimpleDateFormat("YYYY-MM-dd__HH-mm-ss");
	private static final String REPORT_DEFAULT_LOCATION = System.getProperty("user.dir") + File.separator + "target" + File.separator + "reports";
	public static WebDriver webDriver;

	// shut down the freemarker logger
	static
	{
		System.setProperty("org.freemarker.loggerLibrary", "none");
	}

	// flush the report on jvm shut down
	static
	{
		Runtime.getRuntime().addShutdownHook(new Thread(ExtentManager::flush));
	}

	/**
	 * Get the single instance of ExtentReports if there is no instance yet, it
	 * will create it. Create new instance of ExtentReports.
	 *
	 * @return
	 */
	public static ExtentReports getInstance()
	{
		if (EXTENT_REPORTS.get() == null)
		{
			EXTENT_REPORTS.set(createInstance());
		}
		return EXTENT_REPORTS.get();
	}

	/**
	 * Create new instance of ExtentReports with HTML Reporter
	 *
	 * @return
	 */
	public static ExtentReports createInstance()
	{
		ExtentReports extentReports = new ExtentReports();
		initHtmlReporter(extentReports);
		extentReports.setAnalysisStrategy(AnalysisStrategy.SUITE);
		return extentReports;
	}

	/**
	 * configure a html reporter
	 * 
	 * @param extentReports
	 */
	private static void initHtmlReporter(ExtentReports extentReports)
	{
		final String htmlReportFilePath = getHtmlReportFilePath();
		if (htmlReportFilePath != null)
		{
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(htmlReportFilePath);
			htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setDocumentTitle(SUITE.get() + " Report");
			htmlReporter.config().setEncoding("utf-8");
			htmlReporter.config().setReportName(SUITE.get());
			htmlReporter.config().setJS(getJs());
			htmlReporter.config().setCSS(getCss());
			extentReports.attachReporter(htmlReporter);
		}
	}

	public static void startSuite(String suite)
	{
		SUITE.set(suite);
		EXTENT_REPORTS.set(createInstance());
	}

	public static void flush()
	{
		getInstance().flush();
	}

	public static void startTest(String testName, String description)
	{
		TEST.set(getInstance().createTest(testName, description));
	}

	public static void closeTest()
	{
		flush();
	}

	public static void assignCategory(String... category)
	{
		// if there is no test
		if (TEST.get() == null)
		{
			logger.error("Cannot assign category to Extent report. Test wasn't created.");
			return;
		}
		TEST.get().assignCategory(category);
	}

	public static void step(String name, String description)
	{
		if (TEST.get() == null)
		{
			logger.error("Cannot add step to Extent report. Test wasn't created.");
			return;
		}
		STEP.set(TEST.get().createNode(name, description));
	}

	public static void action(ActionResult actionResult)
	{
		// if there is no test
		if (TEST.get() == null)
		{
			// logger.error("Cannot add action to Extent report. Test wasn't
			// created.");
			return;
		}

		// if there is no step, create one with the test name
		if (STEP.get() == null)
		{
			final Test test = TEST.get().getModel();
			step(test.getName(), test.getDescription());
		}
		if (actionResult.isSuccess())
		{
			passStep(actionResult, null);
		}
		else
		{
			MediaEntityModelProvider media = getScreenshotsMediaEntity(actionResult);
			failStep(actionResult, media);
		}
		STEP.get().getModel().getLogContext().getLast().setTimestamp(new Date(actionResult.getTimestamp()));
	}

	private static MediaEntityModelProvider getScreenshotsMediaEntity(ActionResult actionResult)
	{
		Optional<String> screenshotsUrl = getScreenshotsUrl(actionResult);
		MediaEntityModelProvider media = null;
		if (screenshotsUrl.isPresent())
		{
			try
			{
				media = MediaEntityBuilder.createScreenCaptureFromPath(screenshotsUrl.get()).build();
			}
			catch (IOException e)
			{
				logger.error("Failed to add screenshots url to the extent-report. url: " + screenshotsUrl.get(), e);
			}
		}
		return media;
	}

	private static void failStep(ActionResult actionResult, MediaEntityModelProvider media)
	{
		STEP.get().fail(div(getActionNameHtml(actionResult), div(getErrorOfAction(actionResult), br(), getHtmlOfAction(actionResult))).render(),
				media);
	}

	private static void passStep(ActionResult actionResult, MediaEntityModelProvider media)
	{
		STEP.get().pass(div(getActionNameHtml(actionResult), getHtmlOfCollapsible(getHtmlOfAction(actionResult))).render(), media);
	}

	private static Optional<String> getScreenshotsUrl(ActionResult actionResult)
	{
		Optional<String> screenshotsUrl = Optional.empty();
		String imgUrl = "";
		try
		{
			imgUrl = getScreenhot(webDriver, "image");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		screenshotsUrl = Optional.of(imgUrl);
		return screenshotsUrl;
	}

	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception
	{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = REPORT_DEFAULT_LOCATION + File.separator + "images" + File.separator + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	private static String getHtmlReportFilePath()
	{
		String reportDir = System.getProperty("htmlReportDir", REPORT_DEFAULT_LOCATION);
		try
		{
			FileUtils.forceMkdir(new File(reportDir));
			String reportName = System.getProperty("htmlReportFile",
					"Report_" + SUITE.get().replaceAll("\\W+", "_") + "_" + DATE_FORMATTER.format(new Date()) + ".html");
			return reportDir + File.separator + reportName;
		}
		catch (IOException e)
		{
			logger.error("Filed to create directory for the HTML Report file");
			return null;
		}

	}

	private static ContainerTag getHtmlOfCollapsible(ContainerTag... domContents)
	{
		return div(domContents).withClasses("att-collapse-body");
	}

	private static ContainerTag getHtmlOfAction(ActionResult actionResult)
	{
		return div(table(tr(th("Name"), th("Value")), each(actionResult.getParameters(), param -> tr(td(param.getName()), td(param.getValue())))))
				.withClass("code-block");
	}

	private static ContainerTag getActionNameHtml(ActionResult actionResult)
	{
		return div(actionResult.getActionName()).withClass("att-collapse-header");
	}

	private static ContainerTag getErrorOfAction(ActionResult actionResult)
	{
		return div(div(actionResult.getErrorMessage()).withClasses("code-block"));
	}

	private static String getJs()
	{
		return getTextFromResourceFile("./src/main/resources/extent/extent-repot.js");
	}

	private static String getCss()
	{
		return getTextFromResourceFile("./src/main/resources/extent/extent-report.css");
	}

	private static String getTextFromResourceFile(String path)
	{
		String text = null;
		try (FileInputStream inputStream = new FileInputStream(path))
		{
			text = IOUtils.toString(inputStream, Charset.defaultCharset());
		}
		catch (IOException e)
		{
			logger.error("Failed to read file: " + path, e);
		}
		return text;

	}

	/**
	 * Assign test category to test
	 * 
	 * @param name
	 * @return
	 */
	public synchronized static ExtentTest assignTestCategoty(String name)
	{
		return TEST.get().assignCategory(name);
	}

}
