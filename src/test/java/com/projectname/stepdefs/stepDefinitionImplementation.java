package com.projectname.stepdefs;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.projectname.core.BaseTest;
import com.projectname.pages.CartPage;
import com.projectname.pages.CheckoutPage;
import com.projectname.pages.ConfirmationPage;
import com.projectname.pages.LandingPage;
import com.projectname.pages.ProductCatalog;

public class stepDefinitionImplementation extends BaseTest {
	public ConfirmationPage confirmationPage;
	public LandingPage landingPage;
	public ProductCatalog productCatalog;
	@Given ("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage=launchApplication();
		
	}
	
    @Given ("^Logged in with username (.+) and password (.+)$")	//(.+) represents any character present in the cucumber script
    public void logged_in_username_and_password(String username, String password)
    {
    	productCatalog=landingPage.loginApplication(username,password);
    }
    
    @When ("^I add product (.+) to Cart$")
    public void I_add_product_to_Cart (String productName) throws InterruptedException
    {
    	List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
    }
    
    @And ("^Checkout (.+) and submit the order$")
    public void Checkout_and_submit_the_order(String productName)
    {
    	CartPage cartPage = productCatalog.goToCartPage();
		Boolean match = cartPage.VerifyOrderDisplay(productName);
    	CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
    }
    
    //Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage
    @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed_confirmationPage(String string)
    {
    	String confirmMessage = confirmationPage.getConfirmationPage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
    }
    
   // Then "Incorrect email  password." is displayed
    @Then ("{string} message is displayed")	//make sure the string written here matches exactly from the .feature file!
    public void something_message_is_displayed (String errorMsg) 
    {
		Assert.assertEquals(errorMsg, landingPage.getErrorMessage());
		driver.close();
		
    }
    
}
