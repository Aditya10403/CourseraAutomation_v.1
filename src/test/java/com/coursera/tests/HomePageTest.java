package com.coursera.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coursera.exception.CourseraException;
import com.coursera.pages.HomePage;
import com.coursera.utils.ExcelUtil;

public class HomePageTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(HomePageTest.class);
    private final ExcelUtil excelUtil = new ExcelUtil();

    @Test(priority = 1)
    public void testValidSite() {
        try {
            HomePage homePage = new HomePage(driver);

            Assert.assertTrue(
                    homePage.checkValidSite(),
                    "Site URL or Title is incorrect"
            );
            logger.info("testValidSite :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testValidSite :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 2, dependsOnMethods = { "testValidSite" })
    public void testSearchVisibility() {
        try {
            HomePage homePage = new HomePage(driver);

            Assert.assertTrue(
                    homePage.checkSearchVisibility(),
                    "Search input or button is not visible"
            );
            logger.info("testSearchVisibility :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testSearchVisibility :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 3, dependsOnMethods = { "testSearchVisibility" })
    public void testSearchCourse() {
        try {
            HomePage homePage = new HomePage(driver);

            String course = excelUtil.readInputDataByRowAndCol(1, 0);
            homePage.searchCourse(course);
            logger.info("testSearchCourse :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testSearchCourse :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }
}
