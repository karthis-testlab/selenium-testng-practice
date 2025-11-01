package com.pages.object.pool;

//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.pages.object.pool.PageObjectPool.*;

public class PageTest {

	LoginPage loginPage;
	InventoryPage inventoryPage;

	//@BeforeClass
	public void beforeClass() {
		loginPage = getObject(LoginPage.class);
		inventoryPage = getObject(InventoryPage.class);
	}

	//@AfterMethod
	public void afterMethod() {
		releaseObject(LoginPage.class, loginPage);
		releaseObject(InventoryPage.class, inventoryPage);
	}

	@Test
	public void test1() {

		getObject(LoginPage.class).printMessage().moveToInventoryPage().printMessage();

	}

	@Test
	public void test2() {

		getObject(InventoryPage.class).printMessage().moveBackToHomePage().printMessage();

	}

}
