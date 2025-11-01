package com.saucedemo.test.practices;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InventoryTest {

	public ChromeDriver driver;
	public WebDriverWait wait;

	@BeforeMethod
	public void beforeMethod() {
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_leak_detection", false); 
		options.setExperimentalOption("prefs", prefs);
		options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.get("https://www.saucedemo.com/");	
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("user-name")))).sendKeys("standard_user");
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("password")))).sendKeys("secret_sauce");
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("login-button")))).click();		
	}

	@Test
	public void userShouldAbleToAddFirstProductIntoTheCart() {
		Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//button[text()='Add to cart']"))))
		      .stream()
		      .findFirst()
		      .get()
		      .click();
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text()='Remove']")))).isDisplayed());		
	}
	
	@Test
	public void userShouldAbleSeeToShopingCartBadge() {
		Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//button[text()='Add to cart']"))))
	          .stream()
	          .findFirst()
	          .get()
	          .click();
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text()='Remove']")))).isDisplayed());
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@data-test='shopping-cart-badge']")))).isDisplayed());		
	}
	
	@Test
	public void userShouldAbleToRemoveTheAlreadyAddedCartItem() {
		Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
		Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//button[text()='Add to cart']"))))
	          .stream()
	          .findFirst()
	          .get()
	          .click();
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text()='Remove']")))).isDisplayed());
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@data-test='shopping-cart-badge']")))).isDisplayed());	
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text()='Remove']")))).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[text()='Remove']")));
		Assert.assertEquals(driver.findElements(By.xpath("//button[text()='Remove']")).size(), 0);
		Assert.assertTrue(driver.findElements(By.xpath("//span[@data-test='shopping-cart-badge']")).isEmpty());
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}