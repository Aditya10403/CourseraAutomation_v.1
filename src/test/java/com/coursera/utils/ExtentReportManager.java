package com.coursera.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.coursera.tests.BaseTest;

public class ExtentReportManager implements ITestListener {

    // UI of report
    private ExtentSparkReporter sparkReporter;
    // To populate common info in report
    private ExtentReports extentReport;
    // Creating test case entries in the report and update status of the test methods
    private ExtentTest extentTest;

    // Util for taking screenshots
    private final ScreenshotUtil screenshotUtil = new ScreenshotUtil();

    // To create file path for report
    private String getReportFilePath(String browser) {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a").format(new Date());
        return System.getProperty("user.dir") + "/reports/Coursera_" + browser + "_report_" + timeStamp + ".html";
    }

    @Override
    public void onStart(ITestContext context) {
        String browser = context.getCurrentXmlTest().getParameter("browser").toUpperCase();
        String reportPath = getReportFilePath(browser);

        // Initialize extent spark reports
        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Coursera Automation"); // Title of report
        sparkReporter.config().setReportName("Coursera Test Results"); // Name of report
        sparkReporter.config().setTheme(Theme.DARK);

        // Initialize extent reports and attach reporter
        extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);

        // System info for report
        extentReport.setSystemInfo("Tested URL", "https://www.coursera.org/");
        extentReport.setSystemInfo("Environment", "QA");
        extentReport.setSystemInfo("Tester", "Aditya Shukla");
        extentReport.setSystemInfo("Browser", browser);
        extentReport.setSystemInfo("OS", System.getProperty("os.name"));
        extentReport.setSystemInfo("Java version", System.getProperty("java.version"));
        extentReport.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReport.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Creates a new test in report
        extentTest = extentReport.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // captures screenshot on test failure
        String testName = result.getName();

        // Log test success in report
        extentTest.pass("✅ Test case PASSED ➡️ " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = BaseTest.driver;

        // captures screenshot on test failure
        String testName = result.getName();
        // String ssFilePath = screenshotUtil.captureScreenshot(driver, testName);
        String base64path = screenshotUtil.capturesScreenshotBase64(driver);

        // log test failure in report
        extentTest.fail("❌ Test case FAILED ➡️ " + result.getName());
        extentTest.fail("❌ Cause ➡️ " + result.getThrowable());

        /**
         * Giving "about:blank#blocked" for image
         */
        // extentTest.addScreenCaptureFromPath(ssFilePath);
        extentTest.addScreenCaptureFromBase64String(base64path, testName);
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        WebDriver driver = BaseTest.driver;

        // captures screenshot on test failure
        String testName = result.getName();
        // String ssFilePath = screenshotUtil.captureScreenshot(driver, testName);
        String base64path = screenshotUtil.capturesScreenshotBase64(driver);

        // Log test skipped in report
        extentTest.skip("⚠️ Test case SKIPPED ➡️ " + result.getName());
        extentTest.skip("⚠️ Cause ➡️ " + result.getThrowable());
        extentTest.addScreenCaptureFromBase64String(base64path, testName);
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush report
        extentReport.flush();
    }
}
