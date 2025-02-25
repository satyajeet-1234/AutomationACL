package com.workforceScheduler.utils;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionUtils {

	private static final Logger logger = LogManager.getLogger(ActionUtils.class);

	private final WebDriver driver;
	private final WebDriverWait wait;
	private final Actions actions;

	/**
	 * Constructor to initialize WebDriver, WebDriverWait, and Actions.
	 *
	 * @param driver WebDriver instance
	 */
	public ActionUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10s default wait
		this.actions = new Actions(driver);
	}

	/**
	 * Clicks on an element.
	 *
	 * @param locator By locator of the element
	 */
	public void click(By locator) {
		WebElement element = getElement(locator);
		logger.info("Clicking on element: {}", locator);
		element.click();
	}

	/**
	 * Sends text to an input field after clearing it.
	 *
	 * @param locator By locator of the element
	 * @param text    Text to enter
	 */
	public void sendKeys(By locator, String text) {
		WebElement element = getElement(locator);
		logger.info("Sending keys '{}' to element: {}", text, locator);
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * Clears text from an input field.
	 *
	 * @param locator By locator of the element
	 */
	public void clear(By locator) {
		WebElement element = getElement(locator);
		logger.info("Clearing text from element: {}", locator);
		element.clear();
	}

	/**
	 * Hovers over an element.
	 *
	 * @param locator By locator of the element
	 */
	public void hover(By locator) {
		WebElement element = getElement(locator);
		logger.info("Hovering over element: {}", locator);
		actions.moveToElement(element).perform();
	}

	/**
	 * Performs a double-click on an element.
	 *
	 * @param locator By locator of the element
	 */
	public void doubleClick(By locator) {
		WebElement element = getElement(locator);
		logger.info("Double-clicking on element: {}", locator);
		actions.doubleClick(element).perform();
	}

	/**
	 * Performs a right-click (context click) on an element.
	 *
	 * @param locator By locator of the element
	 */
	public void rightClick(By locator) {
		WebElement element = getElement(locator);
		logger.info("Right-clicking on element: {}", locator);
		actions.contextClick(element).perform();
	}

	private WebElement getElement(By locator) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Finds an element using WebDriverWait.
	 *
	 * @param locator By locator of the element
	 * @return WebElement if found
	 * @throws FrameworkException if the element is not found
	 */

}
