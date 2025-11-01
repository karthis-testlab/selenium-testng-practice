package com.uibank.practice;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmountTransferCheck {
	
	public static void main(String[] args) {
		ChromeDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.manage().window().maximize();
		driver.get("https://uibank.uipath.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.id("username")).sendKeys("FebApiuser");
		driver.findElement(By.id("password")).sendKeys("Eagle@123");
		driver.findElement(By.xpath("//button[text()='Sign In']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-dialog-container")));
		driver.findElement(By.xpath("//button[normalize-space(text())='I agree to the Privacy Policy']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[normalize-space(text())='Apply For New Account']")));
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.linkText("Clemens"))).click().perform();
		//driver.executeScript("arguments[0].click();", driver.findElement(By.linkText("Clemens")));
		String style = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[contains(@class, 'webTable')]/tbody/tr/td[contains(@class,'mat-column-amount')]"))).getAttribute("style");
	    System.out.println(style);
	}

}