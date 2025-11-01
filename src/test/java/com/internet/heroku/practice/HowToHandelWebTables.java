package com.internet.heroku.practice;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HowToHandelWebTables {

	public static void main(String[] args) {
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/tables");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		
		WebElement table = driver.findElement(id("table1"));
		int indexOfDue = table.findElements(xpath("//thead/tr/th"))
		     .stream()
		     .map(element -> element.getText())
		     .collect(Collectors.toList())
		     .indexOf("Due") + 1;		
		
		double[] dueArray = table.findElements(cssSelector("tr td:nth-child("+indexOfDue+")"))
		     .stream()
		     .map(element -> element.getText().replaceAll("[^0-9.]", ""))
		     .map(Double::parseDouble)
		     .mapToDouble(Double::doubleValue)
		     .toArray();
		
		List<Double> dueList = table.findElements(cssSelector("tr td:nth-child("+indexOfDue+")"))
			     .stream()
			     .map(element -> element.getText().replaceAll("[^0-9.]", ""))
			     .map(Double::parseDouble)
			     .collect(Collectors.toList());
		
		System.out.println(Arrays.toString(dueArray));
		System.out.println(dueList);
		
		for (int i = 1; i < dueArray.length; i++) {
			for (int j = 0; j < dueArray.length - i; j++) {
				if (dueArray[j] > dueArray[j + 1]) {
					Double temp = dueArray[j + 1];
					dueArray[j + 1] = dueArray[j];
					dueArray[j] = temp;
				}
			}
		}

		for (int i = 1; i < dueList.size(); i++) {
			for (int j = 0; j < dueList.size() - i; j++) {
				if (dueList.get(j) > dueList.get(j + 1)) {
					double temp = dueList.get(j + 1);
					dueList.set(j + 1, dueList.get(j));
					dueList.set(j, temp);
				}
			}
		}	
		
		System.out.println(Arrays.toString(dueArray));
		System.out.println(dueList);
		
		driver.quit();
				
		

	}

}
