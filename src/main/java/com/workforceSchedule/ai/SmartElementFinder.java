package com.workforceSchedule.ai;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

public class SmartElementFinder {
    private WebDriver driver;
    private AIElementDetector aiDetector;

    public SmartElementFinder(WebDriver driver) {
        this.driver = driver;
        this.aiDetector = new AIElementDetector();
    }

    /**
     * Tries to find an element using a normal locator. If it fails, uses AI detection.
     *
     * @param locator      Traditional Selenium locator.
     * @param elementLabel Description of the element for AI detection.
     * @return The WebElement if found.
     */
    public WebElement smartFindElement(By locator, String elementLabel) {
        try {
            return driver.findElement(locator);
        } catch (NoSuchElementException e) {
            System.out.println("Locator failed for: " + elementLabel + ". Using AI detection.");
            try {
                // Capture a screenshot of the current page.
            	Thread.sleep(5000);
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // Define the project directory and create a folder for screenshots.
                String projectPath = System.getProperty("user.dir");
                File screenshotDir = new File(projectPath, "screenshots");
                if (!screenshotDir.exists()) {
                    screenshotDir.mkdirs(); // Create the directory if it doesn't exist.
                }

                // Save the screenshot in the project directory.
                File projectScreenshot = new File(screenshotDir, "screenshot.png");
                FileHandler.copy(screenshot, projectScreenshot);

                // Use AI detection to find the element's bounding box from the project screenshot.
                Rectangle rect = aiDetector.detectElement(projectScreenshot, elementLabel);
                if (!(driver instanceof JavascriptExecutor)) {
                    throw new IllegalStateException("Driver does not support JavaScript execution.");
                }
                if (rect != null) {
                	// Calculate the center coordinates of the detected element.
                    int centerX = rect.getX() + (rect.getWidth() / 2);
                    int centerY = rect.getY() + (rect.getHeight() / 2);
                    
                    // Build the dynamic JavaScript using String.format and the new MouseEvent constructor.
                    String script = String.format(
                        "var evt = new MouseEvent('click', {" +
                        "   bubbles: true," +
                        "   cancelable: true," +
                        "   view: window," +
                        "   clientX: %d," +
                        "   clientY: %d" +
                        "});" +
                        "var target = document.elementFromPoint(%d, %d);" +
                        "if(target) { target.dispatchEvent(evt); } else { console.error('No element found at coordinates: %d, %d'); }",
                        centerX, centerY, centerX, centerY, centerX, centerY
                    );
                    
                    // Ensure the driver supports JavascriptExecutor, then execute the script.
                    if (driver instanceof JavascriptExecutor) {
                        ((JavascriptExecutor) driver).executeScript(script);
                    } else {
                        throw new IllegalStateException("Driver does not support JavaScript execution.");
                    }
                    
                    // Optionally, wait for any UI updates and then try to locate the element again.
                    Thread.sleep(5000);
                    return driver.findElement(locator);
                } else {
                    throw new NoSuchElementException("AI detection did not locate the element: " + elementLabel);
                }
            } catch (Exception ex) {
                throw new NoSuchElementException("AI fallback failed for: " + elementLabel, ex);
            }
        }
    }
}