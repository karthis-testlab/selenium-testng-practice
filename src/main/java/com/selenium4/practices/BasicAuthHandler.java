package com.selenium4.practices;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BasicAuthHandler {

	public static void main(String[] args) {
		
		ChromeDriver chrome = new ChromeDriver();
		basic_auth(chrome, "admin", "admin");
		chrome.get("https://the-internet.herokuapp.com/");
		chrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		chrome.findElement(By.linkText("Basic Auth")).click();
		System.out.println(chrome.findElement(By.tagName("h3")).getText());
		chrome.quit();

		EdgeDriver edge = new EdgeDriver();
		basic_auth(edge, "admin", "admin");
		edge.get("https://the-internet.herokuapp.com/");
		edge.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		edge.findElement(By.linkText("Basic Auth")).click();
		System.out.println(edge.findElement(By.tagName("h3")).getText());
		edge.quit();
		
		FirefoxDriver firefox = new FirefoxDriver();
		firefox.get(basic_auth("admin", "admin", "the-internet.herokuapp.com"));		
		firefox.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		firefox.findElement(By.linkText("Basic Auth")).click();
		System.out.println(firefox.findElement(By.tagName("h3")).getText());
		firefox.quit();

	}
	
	/*
	 * 
	 * Selenium 4 introduced the HasAuthentication interface in Java to handle HTTP authentication pop-ups, 
	 * specifically for basic authentication. 
	 * 
	 * This provides a more robust and integrated way to manage credentials compared to previous workarounds.
	 * 
	 * How to use HasAuthentication in Selenium Java:
	 * 
	 * 1. Cast the Driver: 
	 * The HasAuthentication interface is implemented by ChromiumDriver, 
	 * which is a parent class of ChromeDriver and EdgeDriver. 
	 * Therefore, you need to cast your ChromeDriver or EdgeDriver instance to HasAuthentication.
	 * 
	 * WebDriver driver = new ChromeDriver();
	 * HasAuthentication authDriver = (HasAuthentication) driver;
	 * 
	 * 2. Register Credentials: Use the register method of the HasAuthentication interface to provide the username and password. 
	 * You can use UsernameAndPassword.of(username, password) to create the credentials.
	 * 
	 * authDriver.register(UsernameAndPassword.of("your_username", "your_password"));
	 * 
	 * Alternatively, you can register credentials conditionally using a Predicate to match specific URIs:
	 * 
	 *     authDriver.register(
	 *               uri -> uri.getHost().contains("example.com"),
	 *               UsernameAndPassword.of("your_username", "your_password"));
	 * 
	 * Navigate to the URL: After registering the credentials, you can navigate to the protected URL, and 
	 * Selenium will automatically handle the authentication using the provided credentials, preventing the pop-up from appearing.
	 * 
	 * driver.get("https://the-internet.herokuapp.com/basic_auth");
	 * 
	 * Important Considerations:
	 * Browser Support: This feature primarily works with Chromium-based browsers like Chrome and Edge, 
	 * as it leverages Chrome DevTools Protocol (CDP) for authentication handling. 
	 * Firefox does not implement the necessary interface for this method.
	 * 
	 * Selenium 4: Ensure you are using Selenium WebDriver version 4 or higher to utilize the HasAuthentication interface.
	 * 
	 * 
	 */

	public static void basic_auth(ChromiumDriver driver, String username, String password) {
		driver.register(UsernameAndPassword.of("admin", "admin"));
	}
	
	/*
	 * Handling basic authentication in Selenium Java with Firefox Driver can be achieved 
	 * by embedding the username and password directly into the URL.
	 * 
	 * Syntax:
	 * 
	 * "http://" + username + ":" + password + "@" + baseUrl;
	 * 
	 */

	public static String basic_auth(String username, String password, String domainname) {
		return "https://"+username+":"+password+"@"+domainname;
	}

}