package com.selenium4.grid.practices;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandaloneGrid {

	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.password_manager_leak_detection", false); 
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--start-maximized");
		WebDriver driver = new RemoteWebDriver(new URI("http://localhost:4444/").toURL(), options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.get("https://www.saucedemo.com/");		
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		driver.findElements(By.xpath("//button[text()='Add to cart']")).getFirst().click();
		String productName = driver.findElement(By.xpath("//button[text()='Remove']/../preceding-sibling::div/a")).getText();
		driver.findElement(By.className("shopping_cart_link")).click();
		Assert.assertEquals(driver.findElement(By.className("inventory_item_name")).getText(), productName);
		driver.findElement(By.xpath("//button[text()='Remove']")).click();
		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("cart_item"))));
		driver.findElement(By.id("continue-shopping")).click();
		driver.findElement(By.id("react-burger-menu-btn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link"))).click();
	}

}
