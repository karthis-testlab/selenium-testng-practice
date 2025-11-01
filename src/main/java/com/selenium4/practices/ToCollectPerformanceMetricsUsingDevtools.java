package com.selenium4.practices;

import java.util.List;
import java.util.Optional;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v139.performance.Performance;
import org.openqa.selenium.devtools.v139.performance.model.Metric;

public class ToCollectPerformanceMetricsUsingDevtools {

	public static void main(String[] args) {
		
		//Map<String, Long> metrics = new ConcurrentHashMap<>();
		
		ChromeDriver driver = new ChromeDriver();
		
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		// 1. Enable Performance Domain
        devTools.send(Performance.enable(Optional.empty()));
        
        driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
		
		// Wait for page load and events to fire (you might need a more sophisticated wait here)
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 2. Listen for the 'metric' event
        devTools.addListener(Performance.metrics(), metrics -> {
            List<Metric> performanceMetrics = metrics.getMetrics();
            for (Metric metric : performanceMetrics) {
            	System.out.println(metric.getName() +" - "+ metric.getValue());
            }
        });	
		
		// Clean up
		devTools.send(Performance.disable());
		devTools.disconnectSession();
		
		driver.quit();

	}

}
