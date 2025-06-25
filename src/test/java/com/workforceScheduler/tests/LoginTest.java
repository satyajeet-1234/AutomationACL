package com.workforceScheduler.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.workforceScheduler.base.BaseTest;
import com.workforceScheduler.dataProvider.CustomDataProvider;
import com.workforceScheduler.pages.LoginPage;

public class LoginTest extends BaseTest {

	private LoginPage loginPage;

	@BeforeMethod
	public void initPageObjects() {
	    loginPage = new LoginPage(driver);
	}
    
	
        @Test(priority=1, dataProvider = "testLoginData", dataProviderClass = CustomDataProvider.class)
        public void testValidLogin(String username, String password) {
                loginPage.enterUsername(username);
                loginPage.enterPassword(password);
                loginPage.clickLoginButton();

                // Basic check that login navigates away from the login page
                Assert.assertFalse(driver.getTitle().toLowerCase().contains("login"), "User should not remain on login page");
        }

	@Test(priority=2,dataProvider = "testLoginData", dataProviderClass = CustomDataProvider.class)
        public void testInvalidLogin(String invalidUser, String wrongPassword) {
                loginPage.enterUsername(invalidUser);
                loginPage.enterPassword(wrongPassword);
                loginPage.clickLoginButton();
                System.out.println(invalidUser);

                // Verify that user stays on login page after invalid credentials
                Assert.assertTrue(driver.getTitle().toLowerCase().contains("login"), "User should remain on login page");
        }

	@Test (priority=3,dataProvider = "testLoginData", dataProviderClass = CustomDataProvider.class)
        public void testEmptyCredentials(String emptyUsername, String emptyPassword) {
                loginPage.enterUsername(emptyUsername);
                loginPage.enterPassword(emptyPassword);
                loginPage.clickLoginButton();

                // Verify that login fails with empty credentials
                Assert.assertTrue(driver.getTitle().toLowerCase().contains("login"), "User should remain on login page");
        }
	
	/*
	 * // // @Test // public void testForgotPassword() { // loginPage = new
	 * LoginPage(driver); // // loginPage.clickForgotPassword(); // // // Validate
	 * forgot password redirection (update URL as per actual behavior) //
	 * Assert.assertTrue(driver.getCurrentUrl().contains("forgot-password")); // }
	 * // // @Test // public void testLoginPageLoads() { // loginPage = new
	 * LoginPage(driver); // //
	 * Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login Button not // //
	 * displayed."); // } //
	 */
}
