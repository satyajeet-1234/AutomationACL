package com.workforceScheduler.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverWaits {

	private final WebDriver driver;
	private final WebDriverWait wait;

	// Constructor to initialize WebDriver and WebDriverWait
	public WebDriverWaits(WebDriver driver, int timeoutInSeconds) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	}

	// Wait for an element to be visible
	public WebElement waitForElementToBeVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Wait for an element to be clickable
	public WebElement waitForElementToBeClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// Wait for an element to be present in the DOM
	public WebElement waitForElementToBePresent(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	// Wait for an element to be invisible
	public boolean waitForElementToBeInvisible(By locator) {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	// Wait for text to be present in an element
	public boolean waitForTextToBePresentInElement(By locator, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	// Wait for an alert to be present
	public void waitForAlertToBePresent() {
		wait.until(ExpectedConditions.alertIsPresent());
	}

	// Wait for a specific condition (custom condition)
	public <T> T waitForCondition(ExpectedCondition<T> condition) {
		return wait.until(condition);
	}

	// Wait for a specific amount of time (in seconds)
	public void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Thread was interrupted", e);
		}
	}

}
