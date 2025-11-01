package com.selenium4.practices;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class Selenium4RelativeLocators {

	public static void main(String[] args) {
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("file:///C:/Users/KAVITHA/eclipse-workspace/selenium-testng-practice/practice-html-template/button-template.html");
		System.out.println(driver.findElement(with(By.tagName("p")).above(driver.findElement(By.xpath("//button[text()='Home']")))).getText());

	}

}
