package com.coursera.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.coursera.utils.DriverSetup;

public class BaseTest {

    // creating web driver instance
    public static WebDriver driver;

    // Logger for logging info and errors
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    // setting up the driver before each test
    @BeforeTest
    @Parameters({"browser", "url"})
    public void setUp(String browser, String url) {
        try {
            DriverSetup.initializeDriver(browser, url);
            driver = DriverSetup.getDriver();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    // quit the WebDriver
    @AfterTest
    public void tearDown() {
        DriverSetup.quitDriver();
        logger.info("Driver closed successfully");
    }

}