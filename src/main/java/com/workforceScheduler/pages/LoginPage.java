package com.workforceScheduler.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.workforceSchedule.ai.SmartElementFinder;
import com.workforceScheduler.utils.ActionUtils;
import com.workforceScheduler.utils.WebDriverWaits;

public class LoginPage {
	   // WebDriver instance
    private final WebDriver driver;

    // Utility classes
    private final ActionUtils actionUtils;
    private final WebDriverWaits waitUtils;
    protected SmartElementFinder smartFinder;

    // Locators for login page elements
    private static final By username = By.xpath("//input[@placeholder='Username']");
    private static final By password = By.xpath("//input[@placeholder='Password']");
    private static final By loginButton= By.xpath("//span[contains(@id, '__button0-inner')]");
    //private static final By ERROR_MESSAGE = By.cssSelector(".error-message");

    /**
     * Constructor to initialize the LoginPage.
     *
     * @param driver The WebDriver instance.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.actionUtils = new ActionUtils(driver);
        this.waitUtils = new WebDriverWaits(driver, 10);
        this.smartFinder = new SmartElementFinder(driver);
    }

    /**
     * Enters the username into the username field.
     *
     * @param username The username to enter.
     */
    public void enterUsername(String username) {
    	
       waitUtils.waitForElementToBeVisible(this.username).sendKeys(username);						
    }

    /**
     * Enters the password into the password field.
     *
     * @param password The password to enter.
     */
    public void enterPassword(String password) {
     
    	 waitUtils.waitForElementToBeVisible(this.password).sendKeys(password);
    }

    /**
     * Clicks the login button.
     */
    public WebElement clickLoginButton() {
    	return smartFinder.smartFindElement(loginButton, "Login Button");
     
    	//waitUtils.waitForElementToBeClickable(loginButton).click();
        
    }

    /**
     * Performs the login action by entering the username, password, and clicking the login button.
     *
     * @param username The username to enter.
     * @param password The password to enter.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
 

    /**
     * Returns the error message displayed on the login page.
     *
     * @return The error message text.
     */
   /* public String getErrorMessage() {
       // waitUtils.waitForElementToBeVisible(ERROR_MESSAGE, 10);
        //return driver.findElement(ERROR_MESSAGE).getText();
    	
    }

    /**
     * Checks if the login page is loaded by verifying the visibility of the username field.
     *
     * @return True if the username field is visible, otherwise false.
     */
//    public boolean isLoginPageLoaded() {
//        try {
//           // waitUtils.waitForElementToBeVisible(USERNAME_FIELD, 10);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
}


