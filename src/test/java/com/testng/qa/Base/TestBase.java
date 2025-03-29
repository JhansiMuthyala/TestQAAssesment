package com.testng.qa.Base;

import com.testng.qa.Utilities.Utility;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.util.logging.Logger;

public class TestBase {
    public static WebDriver driver;
    public static Logger log;

//calling the Log4j

    @BeforeSuite
    public void setLog4j() throws AWTException {

        Utility.ConfigureLog4j();
        System.out.println("Before Suite");
        log.info("Suite Execution Started");
    }


    @AfterMethod
    public void AfterMethod_ScreenShot(ITestResult result) throws Exception {

        if(ITestResult.FAILURE==result.getStatus()) {
            Utility.takeScreenshotAtEndOfTest(driver, result.getName());
            log.info("Test failed for testcase"+result.getName());
            log.info("<------ Test Method failed Exit Method ---------->");

        }

    }

}



