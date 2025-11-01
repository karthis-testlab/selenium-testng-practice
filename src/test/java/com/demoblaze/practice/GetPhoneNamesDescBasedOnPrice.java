package com.demoblaze.practice;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetPhoneNamesDescBasedOnPrice {

	public static void main(String[] args) throws InterruptedException {
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://demoblaze.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.linkText("Phones")).click();
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("div.card-block"), 7));
		List<WebElement> productCards = driver.findElements(By.cssSelector("div.card-block"));
		List<Map<String, Object>> productList = new ArrayList<Map<String,Object>>();
		for (WebElement eachCard : productCards) {
			String productName = eachCard.findElement(By.cssSelector(".card-title a")).getText();
			String price = eachCard.findElement(By.cssSelector("h5")).getText();
			
			
			if(Integer.parseInt(price.replaceAll("[^0-9]", "")) >= 360 && Integer.parseInt(price.replaceAll("[^0-9]", "")) <= 700) {
				Map<String, Object> phoneMap = new HashMap<>();
				phoneMap.put("name", productName);
				phoneMap.put("price", Integer.parseInt(price.replaceAll("[^0-9]", "")));
				productList.add(phoneMap);				
			}			
			
		}		
			
		productList.sort(new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {				
				return ((Integer) o2.get("price")).compareTo(((Integer) o1.get("price")));
			}			
		});
		System.out.println(productList);
		
		int j = 1;
		while (j < productList.size()) {
			for (int i = 0; i < productList.size() - 1; i++) {
				if ((int) productList.get(i).get("price") < (int) productList.get(i + 1).get("price")) {
					Map<String, Object> temp = productList.get(i);
					productList.set(i, productList.get(i + 1));
					productList.set(i + 1, temp);
				}
			}
			j++;
		}
		for (Map<String, Object> map : productList) {
			System.out.println(map.get("name")+" - $"+map.get("price"));
		}
		driver.quit();

	}

}
