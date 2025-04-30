package rahulshettyacademy.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.retryTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class ErrorValidationsTest extends BaseTest { // inheritance

	@Test(groups = { "ErrorHandling" }, retryAnalyzer= retryTest.class)
	public void LoginErrorValidation() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		String productName = "ZARA COAT 3";
		// LandingPage landingPage = launchApplication(); not required because of the
		// @BeforeMethod provided in the BaseTes
		ProductCatalog productCatalog = landingPage.loginApplication("virenshukfla@gmail.com", "Virenf@123");
		Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMessage());// intentionally removed or in
																							// the 1st string to get the
																							// failed test to grt SS in
																							// the report!
	}

}
