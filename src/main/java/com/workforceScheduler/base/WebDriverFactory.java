package com.workforceScheduler.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

	public class WebDriverFactory {

	    // ThreadLocal to store WebDriver per thread (supports parallel execution)
	    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	    // Private constructor to prevent instantiation
	    private WebDriverFactory() {}

	    /**
	     * Returns a WebDriver instance for the specified browser.
	     * If no instance exists for the current thread, one is created and stored.
	     *
	     * @param browser The name of the browser (e.g., "chrome", "firefox", "edge").
	     * @return A WebDriver instance for the specified browser.
	     * @throws IllegalArgumentException If the browser is not supported.
	     */
	    public static WebDriver getDriver(String browser) {
	        if (tlDriver.get() == null) {
	            WebDriver driver;
	            switch (browser.toLowerCase()) {
	                case "chrome":
	                    WebDriverManager.chromedriver().setup();
	                    driver = new ChromeDriver();
	                    break;
	                case "firefox":
	                    WebDriverManager.firefoxdriver().setup();
	                    driver = new FirefoxDriver();
	                    break;
	                case "edge":
	                    WebDriverManager.edgedriver().setup();
	                    driver = new EdgeDriver();
	                    break;
	                default:
	                    throw new IllegalArgumentException("Browser not supported: " + browser);
	            }
	            tlDriver.set(driver);
	        }
	        return tlDriver.get();
	    }

	    /**
	     * Returns the WebDriver instance for the current thread.
	     * 
	     * @return The current WebDriver instance.
	     */
	    public static WebDriver getBrowserInstance() {
	        return tlDriver.get();
	    }

	    /**
	     * Quits the WebDriver instance for the current thread and removes it from ThreadLocal.
	     */
	    public static void quitDriver() {
	        if (tlDriver.get() != null) {
	            tlDriver.get().quit();
	            tlDriver.remove();
	        }
	    }
	}


