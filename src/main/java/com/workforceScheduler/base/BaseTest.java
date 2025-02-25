package com.workforceScheduler.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.workforceScheduler.dataProvider.ConfigReader;
import com.workforceScheduler.listeners.TestListeners;

@Listeners(com.workforceScheduler.listeners.TestListeners.class)
public class BaseTest {

    protected WebDriver driver; // Protected so that test classes can use it

    @BeforeMethod
    public void setUp() throws InterruptedException {
        String browser = ConfigReader.getProperty("browser");
        try {
            // Retrieve a driver instance from the ThreadLocal-based WebDriverFactory
            driver = WebDriverFactory.getDriver(browser);
        } catch (Exception e) {
            throw new IllegalStateException("WebDriver initialization failed for browser: " + browser, e);
        }
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseUrl"));
    }

   /* @AfterMethod
    public void tearDown() throws InterruptedException {
        // Quit the driver and remove it from ThreadLocal
        WebDriverFactory.quitDriver();
    }
    */
}