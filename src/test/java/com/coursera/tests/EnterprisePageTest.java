package com.coursera.tests;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.coursera.exception.CourseraException;
import com.coursera.pages.EnterprisePage;
import com.coursera.utils.ExcelUtil;

public class EnterprisePageTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(EnterprisePageTest.class);
    private final ExcelUtil excelUtil = new ExcelUtil();

    @Test(priority = 14)
    public void testScrollToEnterprise() {
        try {
            EnterprisePage enterprisePage = new EnterprisePage(driver);
            enterprisePage.scrollToEnterprise();
            logger.info("testScrollToEnterprise :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testScrollToEnterprise :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 15)
    public void testNavigateToEnterprise() {
        try {
            EnterprisePage enterprisePage = new EnterprisePage(driver);
            enterprisePage.navigateToEnterprise();
            logger.info("testNavigateToEnterprise :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testNavigateToEnterprise :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }


    @DataProvider(name = "formData")
    public Object[][] formData() throws IOException {
        List<String[]> details = excelUtil.readFormDetails();
        Object[][] data = new Object[details.size()][];
        for (int i = 0; i < details.size(); i++) {
            data[i] = details.get(i);
        }
        return data;
    }


    @Test(priority = 16, dataProvider = "formData")
    public void testFillForm(
            String firstName,
            String lastName,
            String email,
            String phone,
            String jobTitle,
            String company) {
        try {
            EnterprisePage enterprisePage = new EnterprisePage(driver);

            System.out.println("\nFilling data for " + firstName + " " + lastName);
            System.out.println(
                    "Name : " + firstName + " " + lastName +
                            "\nEmail : " + email +
                            "\nPhone No. : " + phone +
                            "\nJob Title : " + jobTitle +
                            "\nCompany : " + company
            );

            enterprisePage.fillForm(
                    firstName, lastName, email, phone, "Business",
                    jobTitle, 3, "India", "West Bengal"
            );

            enterprisePage.clickSubmit();
            Thread.sleep(3800);

            Assert.assertEquals(
                    driver.getTitle(),
                    "Thank you for your interest | Coursera for Business"
            );
            logger.info("testFillForm :: PASSED !!");

            driver.navigate().back();
            Thread.sleep(1000);

        } catch (Exception | Error e) {
            logger.error("testFillForm :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }
}
