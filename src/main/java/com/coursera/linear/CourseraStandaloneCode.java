package com.coursera.linear;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.coursera.utils.Course;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CourseraStandaloneCode {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.get("https://www.coursera.org/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        EdgeOptions options = new EdgeOptions();
        options.setAcceptInsecureCerts(true);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // send course
        driver.findElement(By
                        .xpath("//input[@id='search-autocomplete-input']"))
                .sendKeys("web development");

        // click on search
        driver.findElement(By
                        .xpath("//button[@class='nostyle search-button']//div[@class='magnifier-wrapper']"))
                .click();

        WebElement languageTitle = driver.findElement(By.xpath("//div[@data-testid='search-filter-group-Language']"));
        js.executeScript("arguments[0].scrollIntoView(true)", languageTitle);
        Thread.sleep(1000);

        // click on see more for - languages
        WebElement seeMoreLanguageBtn = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By
                                .xpath("//div[@data-testid='search-filter-group-Language']//span[normalize-space()='Show 27 more']")
                        ));
        seeMoreLanguageBtn.click();

        // list out the languages
        List<WebElement> languagesDiv = wait
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By
                                .xpath("//div[@data-testid='search-filter-group-Language']//div[@class='cds-checkboxAndRadio-labelText']/span/span"))
                );
        // list out the languages - checkboxes
        List<WebElement> languageCheckBoxes = wait
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By
                                .xpath("//div[@data-testid='search-filter-group-Language']//span[@class='cds-196']"))
                );

        WebElement levelTitle = driver.findElement(By.xpath("//div[@data-testid='search-filter-group-Language']"));
        js.executeScript("arguments[0].scrollIntoView(true)", levelTitle);
        Thread.sleep(1000);

        // list out the levels
        List<WebElement> levelDiv = wait
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By
                                .xpath("//div[@data-testid='search-filter-group-Level']//div[@class='cds-checkboxAndRadio-labelText']/span/span"))
                );
        // list out the levels - checkboxes
        List<WebElement> levelCheckBoxes = wait
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By
                                .xpath("//div[@data-testid='search-filter-group-Level']//span[@class='cds-196']"))
                );

        List<String> languages = new ArrayList<String>();
        for (WebElement ldiv: languagesDiv) {
            String lang = ldiv.getText();
            int bracketIdx = lang.indexOf("(");
            languages.add(lang.substring(0, bracketIdx).trim());
        }

        List<String> levels = new ArrayList<String>();
        for (WebElement ldiv: levelDiv) {
            String lev = ldiv.getText();
            int bracketIdx = lev.indexOf("(");
            levels.add(lev.substring(0, bracketIdx).trim());
        }

        String language = "English";
        int laIdx = languages.indexOf(language);

        String level = "Beginner";
        int leIdx = levels.indexOf(level);

        languageCheckBoxes.get(laIdx).click();
        levelCheckBoxes.get(leIdx).click();

        List<WebElement> coursesTitleList = wait
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By
                                .xpath("//div[@class='cds-ProductCard-header']/div[2]/a/h3"))
                );

        List<WebElement> coursesRatingList = wait
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By
                                .xpath("//div[@class='cds-ProductCard-footer']//div[@class='cds-RatingStat-meter']/span"))
                );

        List<WebElement> coursesDurationList = wait
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By
                                .xpath("//div[@class='cds-ProductCard-footer']//div[@class='cds-CommonCard-metadata']/p"))
                );

        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < Math.min(coursesTitleList.size(), coursesRatingList.size()); i++) {
            Course c = new Course();
            c.setTitle(coursesTitleList.get(i).getText());
            c.setRating(coursesRatingList.get(i).getText());

            String duration = coursesDurationList.get(i).getText();
            c.setDuration(duration.substring(duration.lastIndexOf("·") + 1).trim());
            courses.add(c);
        }

        System.out.println("\nTitle\t\t\t\t\tRating\tDuration");
        for (Course c: courses) {
            System.out.println(c.getTitle() + "\t" + c.getRating() + "\t" + c.getDuration());
        }

        System.out.println("\nTotal no of languages are :: " + languages.size());
        for (String l: languages) System.out.println(l);

        System.out.println("\nTotal no of levels are :: " + levels.size());
        for (String l: levels) System.out.println(l);

        js.executeScript("window.scrollBy(0, -document.body.scrollHeight)", "");
        Thread.sleep(1000);

        driver.navigate().to("https://www.coursera.org/");
        System.out.println("\nNavigated to Home Screen");
        Thread.sleep(1000);

        // scroll down for Enterprise
        // driver.findElement(By.xpath("//div[@aria-label='Banner']/ul/li[2]")).click();
        int idx = 5;
        while (idx > 0) {
            js.executeScript("window.scrollBy(0, 1000);");
            Thread.sleep(1000);
            idx--;
        }

        WebElement enterpriseLinkText = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .linkText("For Enterprise")
                ));
        // js.executeScript("arguments[0].scrollIntoView", enterpriseLinkText);
        // Thread.sleep(1000);

        if (enterpriseLinkText.isEnabled()) enterpriseLinkText.click();

		/* warning
		WebElement warningBtn = wait
									.until(ExpectedConditions
									.elementToBeClickable(By
									.xpath("//div[@class='mutiny-sidepop-button button-1-0-2 visible-1-0-7 open-1-0-4 mutiny-sidepop-open']"))
									);
		warningBtn.click();
		*/

        // scroll to form
        WebElement formElement = driver.findElement(By.xpath("//div[@class='BlockLayout_Container css-1or376v']"));
        js.executeScript("arguments[0].scrollIntoView(true)", formElement);

        // fill the form
        driver.findElement(By.xpath("//input[@id='FirstName']")).sendKeys("Aditya");
        driver.findElement(By.xpath("//input[@id='LastName']")).sendKeys("Shukla");
        driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("abc");
        driver.findElement(By.xpath("//input[@id='Phone']")).sendKeys("3473207854");

        Select org = new Select(driver.findElement(By.xpath("//select[@id='rentalField9']")));
        org.selectByVisibleText("Business");

        WebElement jobTitle = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By
                                .xpath("//input[@id='Title']"))
                );
        jobTitle.sendKeys("QA");

        driver.findElement(By.xpath("//input[@id='Company']")).sendKeys("CTS");

        Select com = new Select(driver.findElement(By.xpath("//select[@id='Employee_Range__c']")));
        com.selectByIndex(2);

        Select ques = new Select(driver.findElement(By.xpath("//select[@id='What_the_lead_asked_for_on_the_website__c']")));
        ques.selectByIndex(3);

        Select country = new Select(driver.findElement(By.xpath("//select[@id='Country']")));
        country.selectByIndex(2);

        WebElement state = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By
                                .xpath("//select[@id='State']"))
                );
        if (state.isDisplayed()) {
            Select st = new Select(state);
            st.selectByIndex(4);
        }

        driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();

        WebElement errorMsg = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By
                                .xpath("//div[@id='ValidMsgEmail']"))
                );

        System.out.println();
        if (errorMsg.isDisplayed()) {
            System.out.println(errorMsg.getText());
        }

        Thread.sleep(3000);

        driver.quit();
    }
}
