package com.coursera.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;

public class HomePage {

    private final WebDriver driver;

    // CONSTRUCTOR
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    // LOCATORS
    @FindBy(xpath = "//input[@id='search-autocomplete-input']")
    WebElement searchInput;

    @FindBy(xpath = "//button[@class='nostyle search-button']//div[@class='magnifier-wrapper']")
    WebElement searchButton;


    // ACTIONS
    public boolean checkValidSite() {
        return Objects.equals(driver.getCurrentUrl(), "https://www.coursera.org/")
                && Objects.equals(driver.getTitle(), "Coursera | Degrees, Certificates, & Free Online Courses");
    }

    public boolean checkSearchVisibility() {
        return searchInput.isDisplayed() && searchButton.isDisplayed();
    }

    public void searchCourse(String course) throws InterruptedException {
        searchInput.sendKeys(course);
        searchButton.click();
    }
}
