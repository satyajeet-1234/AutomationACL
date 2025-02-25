package com.workforceScheduler.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.workforceScheduler.base.WebDriverFactory;
import com.workforceScheduler.utils.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {

	private static final Logger logger = LogManager.getLogger(TestListeners.class);
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	public TestListeners() {

	}

	// Initialize ExtentReports in onStart to create a single instance for the test
	// suite.
	@Override
	public void onStart(ITestContext context) {
		logger.info("=== Test Suite Started: " + context.getName() + " ===");
		if (extent == null) {
			extent = createExtentInstance();
		}
	}

	// Create and configure the ExtentReports instance.
	private ExtentReports createExtentInstance() {
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
		System.getProperty("user.dir") + "/reports/Automation_" +Utility.getCurrentTime() + ".html");
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setReportName("Automation Report");
		sparkReporter.config().setDocumentTitle("Sprint 1 Report");

		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter);
		return extentReports;
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("=== Test Suite Finished: " + context.getName() + " ===");
		if (extent != null) {
			extent.flush();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		logger.info("Test Started: " + result.getMethod().getMethodName());
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("✅ Test Passed: " + result.getMethod().getMethodName());
		WebDriver driver = WebDriverFactory.getBrowserInstance();
		String base64Screenshot =Utility.captureScreenshotInBase64(driver);
		extentTest.get().pass("Test Passed",
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.error("❌ Test Failed: " + result.getMethod().getMethodName());
		logger.error("Failure Reason: " + result.getThrowable().getMessage());
		WebDriver driver = WebDriverFactory.getBrowserInstance();
		String base64Screenshot =Utility.captureScreenshotInBase64(driver);
		extentTest.get().fail("Test Failed: " + result.getThrowable().getMessage(),
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.warn("⚠️ Test Skipped: " + result.getMethod().getMethodName());
		extentTest.get().skip("Test Skipped: " + result.getThrowable().getMessage());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		logger.warn("⚠️ Test Partially Passed: " + result.getMethod().getMethodName());
	}
}
