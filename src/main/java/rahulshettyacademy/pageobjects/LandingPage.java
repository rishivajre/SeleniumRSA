package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{
	
	WebDriver driver;	//current class driver
	
	public LandingPage(WebDriver driver)	//instance variable of another class
	{
		//Initializes at the beginning
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);	//this triggers initiation of the webelements like userEmail, userPassword, etc,.
												//this line is written under constructor bcuz this method will get executed first in this while script
	}
	
		
	//WebElement userEmail = 	driver.findElement(By.id("userEmail"));
	//Or in PageFactory above same line can be written as:	
	@FindBy (id="userEmail")
	WebElement userEmail;
	
	@FindBy (id="userPassword")
	WebElement userPassword;
	
	@FindBy (id="login")
	WebElement submit;
	
	@FindBy (css="[class*='toast']") //css->   ng-tns-c4-19 toast-message ng-star-inserted
	WebElement errorMessage;

	public ProductCatalog loginApplication(String email, String password)
	{
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		ProductCatalog productCatalog = new ProductCatalog(driver);
		return productCatalog;
	}
	
	public String getErrorMessage()
	{
		waitForWebELementToAppear(errorMessage);
		return errorMessage.getText();	//mentioning return type is important
	}
	
	public void gotTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
}
