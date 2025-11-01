package com.pages.object.pool;

public class InventoryPage {
	
	public InventoryPage printMessage() {
		System.out.println("I'm in InventoryPage");
		return this;
	}
	
	public LoginPage moveBackToHomePage() {		
		return PageObjectPool.getObject(LoginPage.class);
	}

}