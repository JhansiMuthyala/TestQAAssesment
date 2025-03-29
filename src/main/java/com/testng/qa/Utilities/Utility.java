package com.testng.qa.Utilities;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utility  {
    public static void ConfigureLog4j() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("_MMddyyyy_HHmmss");
        System.setProperty("current_date", dateFormat.format(new Date()));

        try {
            Configurator.initialize(null, "src/main/resources/log4j2.xml");
            LogManager.getLogger(Utility.class).info("Log4j2 has been initialized successfully!");
        } catch (Exception e) {
            System.err.println("Error initializing Log4j2: " + e.getMessage());
        }
    }
    public static void takeScreenshotAtEndOfTest(WebDriver driver, String Screeshotname) throws IOException {
        File ScreenshortFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String CopyFilePath ="C:\\Users\\USER\\IdeaProjects\\TestQAAssesment\\ScreenShots\\";
        FileUtils.copyFile(ScreenshortFile,
                new File(CopyFilePath+Screeshotname+".png"));
    }



}
