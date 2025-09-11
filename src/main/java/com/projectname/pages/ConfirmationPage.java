package com.projectname.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.projectname.core.BaseComponent;

public class ConfirmationPage extends BaseComponent {

	WebDriver driver;

	public ConfirmationPage(WebDriver driver) 
	{
		// initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (css =".hero-primary")
	WebElement confirmationMessage;
	
	public String getConfirmationPage()
	{
		return confirmationMessage.getText();
	}
}
