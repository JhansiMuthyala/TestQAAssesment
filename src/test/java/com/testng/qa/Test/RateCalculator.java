package com.testng.qa.Test;

import com.testng.qa.Utilities.Utility;
import io.qameta.allure.Description;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.time.Duration;
import java.util.List;

import static com.testng.qa.Base.TestBase.log;
public class RateCalculator {
    WebDriver driver;
    private WebDriverWait wait;
   private static final Logger log = (Logger) LogManager.getLogger(RateCalculator.class);


   @BeforeTest
    public void OpenBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        //options.addArguments("--guest");
        driver = new ChromeDriver(options);
      log.info("Initiated Chrome Browser");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @Description("Verify Price calculator screen")
    @Test
    public void VerifyRateCalculatorScreen() throws InterruptedException {

        driver.manage().window().maximize();
        String URL = "https://pos.com.my/send/ratecalculator/";
        driver.get(URL);
        log.info("Navigated to URL rateCalculator");
        Thread.sleep(3000);

        try {
            // Step 1: Wait for the "To Country" input field and clear existing data
            WebElement toCountryInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("country")));
            toCountryInput.clear();
            log.info("Cleared existing value in 'To Country' field");

            // Step 2: Enter "India" in the dropdown field
            toCountryInput.sendKeys("India");
            Thread.sleep(2000);
            log.info("Entered 'India' in 'To Country' field");

            // Step 3: Wait for dropdown options and select "India - IN"
            WebElement indiaOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='mat-autocomplete-0'][1]")));
            new Actions(driver).moveToElement(indiaOption).click().perform();
            log.info("Selected 'India - IN' from dropdown");

        } catch (TimeoutException e) {
            log.error("Timeout waiting for 'To Country' selection: " + e.getMessage());
        } catch (NoSuchElementException e) {
            log.error("'India - IN' not found in dropdown: " + e.getMessage());
        }

        // Step 4: Enter From Postcode
        WebElement fromPostcode = driver.findElement(By.xpath("//input[@placeholder='Postcode']"));
        fromPostcode.sendKeys("35600");
        log.info("Entered From PostCode");

        // Step 5: Enter Item Weight
        WebElement weightField = driver.findElement(By.xpath("//input[@formcontrolname='itemWeight']"));
        weightField.sendKeys("11");
        log.info("Entered Item Weight");
        Thread.sleep(2000);

        // Step 6: Click on Calculate button
        WebElement calculateButton = driver.findElement(By.xpath("//a[contains(text(),'Calculate')]"));
        calculateButton.click();
        log.info("Clicked on Calculate Button");

        // Step 7: Wait for quotes to be displayed
        List<WebElement> quotesList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div/dt[contains(text(),'Service Type')]"))); // Adjust selector if needed

        // Step 8: Assert that multiple quotes are displayed
        Assert.assertTrue(quotesList.size() > 1, "Multiple shipping quotes should be displayed!");
        log.info("Verified that multiple quotes are displayed");

        // Print the available quotes
        log.info("Shipping Quotes:");
        for (WebElement quote : quotesList) {
            log.info(quote.getText());
        }
    }

    @AfterTest
    public void closeBrowser(){
        driver.close();
        log.info("Closed Browser");
        log.info("<------ Completed Execution ---------->");


    }

}