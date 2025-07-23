package com.coursera.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.coursera.utils.Course;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchedResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private static List<String> languages;
    private static List<String> levels;


    // CONSTRUCTOR
    public SearchedResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }


    // LOCATORS
    @FindBy(xpath = "//div[@data-testid='search-filter-group-Language']")
    WebElement languageTitle;

    @FindBy(xpath = "//button[@aria-label='Show more Language options']")
    WebElement seeMoreLanguageBtn;

    @FindBy(xpath = "//div[@data-testid='search-filter-group-Language']//div[@class='cds-checkboxAndRadio-labelText']/span/span")
    List<WebElement> languagesDiv;

    @FindBy(xpath = "//div[@data-testid='search-filter-group-Language']//input[@type='checkbox']")
    List<WebElement> languageCheckBoxes;

    @FindBy(xpath = "//div[@data-testid='search-filter-group-Level']")
    WebElement levelTitle;

    @FindBy(xpath = "//div[@data-testid='search-filter-group-Level']//div[@class='cds-checkboxAndRadio-labelText']/span/span")
    List<WebElement> levelDiv;

    @FindBy(xpath = "//div[@data-testid='search-filter-group-Level']//input[@type='checkbox']")
    List<WebElement> levelCheckBoxes;

    @FindBy(xpath = "//div[@class='cds-ProductCard-header']/div[2]/a/h3")
    List<WebElement> coursesTitleList;

    @FindBy(xpath = "//div[@class='cds-ProductCard-footer']//div[@class='cds-RatingStat-meter']/span")
    List<WebElement> coursesRatingList;

    @FindBy(xpath = "//div[@class='cds-ProductCard-footer']//div[@class='cds-CommonCard-metadata']/p")
    List<WebElement> coursesDurationList;


    // ACTIONS
    public void scrollToLanguageTitle() {
        js.executeScript("arguments[0].scrollIntoView(true)", languageTitle);
    }

    public void scrollToLevelTitle() {
        js.executeScript("arguments[0].scrollIntoView(true)", levelTitle);
    }

    public void clickSeeMoreLanguages() {
        seeMoreLanguageBtn.click();
    }

    public void selectLanguage(String language) {
        languages = new ArrayList<>();
        for (WebElement ldiv: languagesDiv) {
            String lang = ldiv.getText();
            int bracketIdx = lang.indexOf("(");
            languages.add(lang.substring(0, bracketIdx).trim());
        }
        int laIdx = languages.indexOf(language);
        languageCheckBoxes.get(laIdx).click();
        System.out.println("\nSelected Language :: " + language);
    }

    public void printAllLanguages() {
        System.out.println("\nTotal no of languages :: " + languages.size());
        for (String language: languages) {
            System.out.println(language);
        }
    }

    public void selectLevel(String level) {
        levels = new ArrayList<>();
        for (WebElement ldiv: levelDiv) {
            String lev = ldiv.getText();
            int bracketIdx = lev.indexOf("(");
            levels.add(lev.substring(0, bracketIdx).trim());
        }
        int leIdx = levels.indexOf(level);
        levelCheckBoxes.get(leIdx).click();
        System.out.println("\nSelected Level :: " + level);
    }

    public void printAllLevels() {
        System.out.println("\nTotal no of levels :: " + levels.size());
        for (String level: levels) {
            System.out.println(level);
        }
    }

    public void scrollToRenderCourses() throws InterruptedException {
        int idx = 4;
        while (idx > 0) {
            js.executeScript("window.scrollBy(0, 900);");
            Thread.sleep(1500);
            idx--;
        }
    }

    public List<Course> getCourses(int noOfCourse) {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < noOfCourse; i++) {
            Course c = new Course();
            c.setTitle(coursesTitleList.get(i).getText());
            c.setRating(coursesRatingList.get(i).getText());

            String duration = coursesDurationList.get(i).getText();
            c.setDuration(duration.substring(duration.lastIndexOf("·") + 1).trim());
            courses.add(c);
        }
        return courses;
    }

    public void navigateBackToHome() throws InterruptedException {
        driver.navigate().to("https://www.coursera.org/");
        System.out.println("\nNavigated to Home Screen...");
        Thread.sleep(1000);
    }
}