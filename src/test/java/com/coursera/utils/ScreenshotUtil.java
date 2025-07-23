package com.coursera.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    // create file path for ss
    private String getSSFilePath(String ssName) {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_hhmmssa").format(new Date());
        return System.getProperty("user.dir") +
                File.separator + "screenshots" +
                File.separator + ssName + "_" + timeStamp + ".png";
    }

    // method to capture ss
    public String captureScreenshot(WebDriver driver, String ssName) {
        String ssFilePath = getSSFilePath(ssName);

        // take ss and save to file
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(ssFilePath);

        try {
            // copying ss file from src to dest
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("📸 Screenshot saved at :: " + ssFilePath);
        } catch (IOException e) {
            System.err.println("❌ Failed to save screenshot :: " + e.getMessage());
        }
        return ssFilePath;
    }

    // method to capture screenshot as Base64 (for embedding)
    public String capturesScreenshotBase64(WebDriver driver) {
        String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        System.out.println("📸 Screenshot captured as Base64 for embedding.");
        return base64Screenshot;
    }
}