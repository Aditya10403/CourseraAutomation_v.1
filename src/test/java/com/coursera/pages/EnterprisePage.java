package com.coursera.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EnterprisePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    // CONSTRUCTOR
    public EnterprisePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }


    // LOCATORS
    @FindBy(linkText = "For Enterprise")
    WebElement enterpriseLinkText;

    @FindBy(xpath = "//input[@id='FirstName']")
    WebElement firstName;

    @FindBy(xpath = "//input[@id='LastName']")
    WebElement lastName;

    @FindBy(xpath = "//input[@id='Email']")
    WebElement email;

    @FindBy(xpath = "//input[@id='Phone']")
    WebElement phone;

    @FindBy(xpath = "//select[@id='rentalField9']")
    WebElement organization;

    @FindBy(xpath = "//input[@id='Title']")
    WebElement jobTitle;

    /**
     * Company and employee-range field has been removed in recent updates
     */
    /*
    @FindBy(xpath = "//input[@id='Company']")
    WebElement company;

    @FindBy(xpath = "//select[@id='Employee_Range__c']")
    WebElement employeeRange;
    */

    @FindBy(xpath = "//select[@id='What_the_lead_asked_for_on_the_website__c']")
    WebElement question;

    @FindBy(xpath = "//select[@id='Country']")
    WebElement countries;

    @FindBy(xpath = "//select[@id='State']")
    WebElement states;

    @FindBy(xpath = "//button[normalize-space()='Submit']")
    WebElement submitButton;

    @FindBy(xpath = "//button[normalize-space()='Please Wait']")
    WebElement pleaseWaitBtn;

    @FindBy(id = "ValidMsgEmail")
    WebElement errorMsg;

    @FindBy(xpath = "//div[@data-track-component='EntWebsiteHow_c4b_corp_thanks_for_your_interest_in_coursera_for_business']//h1[@data-testid='how_module_hero_heading']")
    WebElement successMsg;


    // ACTIONS
    public void scrollToEnterprise() throws InterruptedException {
        int idx = 3;
        while (idx > 0) {
            js.executeScript("window.scrollBy(0, 1200);");
            Thread.sleep(1000);
            idx--;
        }
        js.executeScript("arguments[0].scrollIntoView", enterpriseLinkText);
        Thread.sleep(1000);
    }

    public void navigateToEnterprise() {
        enterpriseLinkText.click();
    }

    public void enterFirstName(String firstName) {
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        this.email.clear();
        this.email.sendKeys(email);
    }

    public void enterPhone(String phone) {
        this.phone.clear();
        this.phone.sendKeys(phone);
    }

    public void selectOrganization(String org) {
        new Select(organization).selectByVisibleText(org);
    }

    public void enterJobTitle(String jobTitle) {
        this.jobTitle.clear();
        this.jobTitle.sendKeys(jobTitle);
    }

    /**
     * Company field has been removed in recent updates
     */
    /*
    public void enterCompany(String company) {
        this.company.clear();
        this.company.sendKeys(company);
    }
    */

    /**
     * EmployeeRange field has been removed in recent updates
     */
    /*
    public void selectEmployeeRange(int employeeRangeIdx) {
        new Select(employeeRange).selectByIndex(employeeRangeIdx);
    }
    */

    public void selectQuestion(int questionIdx) {
        new Select(question).selectByIndex(questionIdx);
    }

    public void selectCountry(String country) {
        new Select(countries).selectByVisibleText(country);
    }

    public void selectState(String state) {
        if (states.isDisplayed()) {
            new Select(states).selectByVisibleText(state);
        }
    }

    public void fillForm(
            String firstName,
            String lastName,
            String email,
            String phone,
            String organisation,
            String jobTitle,
            int questionIdx,
            String country,
            String state
    ) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPhone(phone);
        selectOrganization(organisation);
        enterJobTitle(jobTitle);
        selectQuestion(questionIdx);
        selectCountry(country);
        selectState(state);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public boolean isErrorMsgDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMsg));
            return errorMsg.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

}
