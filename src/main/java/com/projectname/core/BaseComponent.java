package com.projectname.core;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.projectname.pages.CartPage;
import com.projectname.pages.OrderPage;

public class BaseComponent {
	
	static WebDriver driver;
	public BaseComponent(WebDriver driver) {
		BaseComponent.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy (css = "[routerlink*='cart']")
	WebElement cartHeader;

	@FindBy (css = "[routerlink*='myorders']")
	WebElement orderHeader;
	
	public static void waitForELementToAppear(By findBy) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
	}
	
	public void waitForWebELementToAppear(WebElement findBy) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	public CartPage goToCartPage()
	{
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	public OrderPage goToOrdersPage()
	{
		orderHeader.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}
	
	public void waitForELementToDisappear(WebElement spinner) throws InterruptedException
	{
		Thread.sleep(1000);
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
	}
	
}
