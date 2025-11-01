package com.saucedemo.test.practices;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
	
	public ChromeDriver driver;
	
	@BeforeMethod
	public void beforeMethod() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	@Test
	public void userShouldAbleToLoginSuccessfullyWithValidCredentials() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
	}
	
	@Test
	public void userShouldNotAbleToLoginWithInvalidCredentials() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauc");
		driver.findElement(By.id("login-button")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//h3[@data-test='error']")).isDisplayed());
	}
	
	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}