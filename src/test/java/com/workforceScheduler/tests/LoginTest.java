package com.workforceScheduler.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

		// Validate Home Page Loaded (update as per app behavior)
		//Assert.assertEquals(driver.getTitle(), "Home Page");
	}

	@Test(priority=2,dataProvider = "testLoginData", dataProviderClass = CustomDataProvider.class)
	public void testInvalidLogin(String invalidUser, String wrongPassword) {
		loginPage.enterUsername(invalidUser);
		loginPage.enterPassword(wrongPassword);
		loginPage.clickLoginButton();
		System.out.println(invalidUser);

		// Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not
		// displayed.");
	}

	@Test (priority=3,dataProvider = "testLoginData", dataProviderClass = CustomDataProvider.class)
	public void testEmptyCredentials(String emptyUsername, String emptyPassword) {
		loginPage.enterUsername(emptyUsername);
		loginPage.enterPassword(emptyPassword);
		loginPage.clickLoginButton();

		// Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not
		// displayed.");
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
