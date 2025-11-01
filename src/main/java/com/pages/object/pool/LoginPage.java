package com.pages.object.pool;

public class LoginPage {
	
	public LoginPage printMessage() {
		System.out.println("I'm in LoginPage");
		return this;
	}
	
	public InventoryPage moveToInventoryPage() {
		return PageObjectPool.getObject(InventoryPage.class);
	}

}