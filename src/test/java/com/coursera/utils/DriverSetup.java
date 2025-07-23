package com.coursera.utils;

import java.time.Duration;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import com.coursera.exception.CourseraException;

public class DriverSetup {

    // creates driver instance
    @Getter
    private static WebDriver driver;

    // logger for logging info and errs
    private static final Logger logger = LogManager.getLogger(DriverSetup.class);

    // initializing driver based on browser and URL
    public static void initializeDriver(String browser, String url) throws CourseraException {
        if (driver == null) {
            try {
                logger.info("Initializing Browser for :: {}", browser);
                switch (browser.toLowerCase()) {
                    case "edge":
                        driver = new EdgeDriver();
                        break;
                    case "chrome":
                        driver = new ChromeDriver();
                        break;
                    default:
                        logger.error("INVALID BROWSER :: {}", browser);
                        throw new CourseraException("Invalid browser: " + browser);
                }
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                driver.get(url);
            } catch (Exception e) {
                logger.error("Driver Initialization FAILED !!!");
                throw new CourseraException(e.getMessage());
            }
        }
    }

    // method to quit the instance
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
