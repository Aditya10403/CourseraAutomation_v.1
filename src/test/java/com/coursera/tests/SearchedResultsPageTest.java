package com.coursera.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.coursera.exception.CourseraException;
import com.coursera.pages.SearchedResultsPage;
import com.coursera.utils.Course;
import com.coursera.utils.ExcelUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchedResultsPageTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SearchedResultsPageTest.class);
    private final ExcelUtil excelUtil = new ExcelUtil();

    @Test(priority = 4)
    public void testScrollToLanguageTitle() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);

            searchedResultsPage.scrollToLanguageTitle();
            logger.info("testScrollToLanguageTitle :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testScrollToLanguageTitle :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 5)
    public void testClickSeeMoreLanguages() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);

            searchedResultsPage.clickSeeMoreLanguages();
            logger.info("testClickSeeMoreLanguages :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testClickSeeMoreLanguages :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 6)
    public void testSelectLanguage() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);

            String language = excelUtil.readInputDataByRowAndCol(1, 1);
            searchedResultsPage.selectLanguage(language);
            logger.info("testSelectLanguage :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testSelectLanguage :: FAILED !!!");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test(priority = 7)
    public void testPrintAllLanguages() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);

            searchedResultsPage.printAllLanguages();
            logger.info("testPrintAllLanguages :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testPrintAllLanguages :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }


    @Test(priority = 8)
    public void testScrollToLevelTitle() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);

            searchedResultsPage.scrollToLevelTitle();
            logger.info("testScrollToLevelTitle :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testScrollToLevelTitle :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 9)
    public void testSelectLevel() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);

            String level = excelUtil.readInputDataByRowAndCol(1, 2);
            searchedResultsPage.selectLevel(level);
            logger.info("testSelectLevel :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testSelectLLevel :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 10)
    public void testPrintAllLevels() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);

            searchedResultsPage.printAllLevels();
            logger.info("testPrintAllLevels :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testPrintAllLevels :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 11, dependsOnMethods = { "testSelectLanguage", "testSelectLevel" })
    public void testScrollToRenderCourses() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);
            searchedResultsPage.scrollToRenderCourses();

            logger.info("testScrollToRenderCourses :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testScrollToRenderCourses :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 12, dependsOnMethods = { "testScrollToRenderCourses" })
    public void testGetCourses() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);
            List<List<String>> data = new ArrayList<>();

            int noOfCourses = Integer.parseInt(excelUtil.readInputDataByRowAndCol(1, 3));
            List<Course> courses = searchedResultsPage.getCourses(noOfCourses);

            for (int i = 0; i < courses.size(); i++) {
                String title = courses.get(i).getTitle();
                String rating = courses.get(i).getRating();
                String duration = courses.get(i).getDuration();

                List<String> row = Arrays.asList(String.valueOf(i+1), title, rating, duration);
                data.add(row);
            }

            excelUtil.writeOutputData(data);
            
            /*
             * print in console to check
            System.out.println("\nTitle\t\t\t\t\tRating\tDuration");
    		for (Course c: courses) {
    			System.out.println(c.getTitle() + "\t" + c.getRating() + "\t" + c.getDuration());
    		}
    		*/

            logger.info("testGetCourses :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testGetCourses :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }

    @Test(priority = 13)
    public void testNavigateBackToHome() {
        try {
            SearchedResultsPage searchedResultsPage = new SearchedResultsPage(driver);

            searchedResultsPage.navigateBackToHome();
            logger.info("testNavigateBackToHome :: PASSED !!");
        } catch (Exception | Error e) {
            logger.error("testNavigateBackToHome :: FAILED !!!");
            throw new CourseraException(e.getMessage());
        }
    }
}
