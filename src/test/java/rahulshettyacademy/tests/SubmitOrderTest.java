package rahulshettyacademy.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalog;

public class SubmitOrderTest extends BaseTest { // inheritance

	String productName = "ZARA COAT 3";
	@Test(dataProvider = "getData", groups ="Purchase")
	public void submitOrder(HashMap<String, String> input) throws InterruptedException, IOException 
	{
		// TODO Auto-generated method stub
		
		// LandingPage landingPage = launchApplication(); not required because of the
		// @BeforeMethod provided in the BaseTes
		ProductCatalog productCatalog = landingPage.loginApplication(input.get("email"),input.get("password"));
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalog.goToCartPage();
		Boolean match = cartPage.VerifyOrderDisplay(input.get("productName"));
		// Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationPage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER.")); // assertion should be inside
																						// your main page!!! its in
																						// right place now.
	}

	@Test (dependsOnMethods= {"submitOrder"})// created to verify the previous test result and how to connect is shown here
	public void OrderHistoryTest() 
	{
		//Zara Coat 3
		ProductCatalog productCatalog = landingPage.loginApplication("testuser1@example.com", "TestPass@123");
		OrderPage orderPage = productCatalog.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
	}
	
	//Extent reports--
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List <HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"/src/test/java/rahulshettyacademy/data/PurchaseOrder.json");
		return new Object[][] { {data.get(0)}, {data.get(1)} };
	}	
	
	//or
	/*@DataProvider
	public Object[][] getData()
	{
		return new Object[][] { {"virenshukla@gmail.com","Viren@123","ZARA COAT 3"}, {"rvajre@gmail.com","Viren@123","ADIDAS ORIGINAL"} };
	}*/
//	HashMap<String,String> map = new HashMap<String, String>();
//	map.put("email", "virenshukla@gmail.com");
//	map.put("password", "Viren@123");
//	map.put("productName", "ZARA COAT 3");
//	
//	HashMap<String,String> map1 = new HashMap<String, String>();
//	map1.put("email", "rvajre@gmail.com");
//	map1.put("password", "Viren@123");
//	map1.put("productName", "ADIDAS ORIGINAL");
	
	// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// WebElement prod = products.stream().filter(product ->
			// product.findElement(By.xpath("(//h5)")).getText().equalsIgnoreCase(productName)).findFirst()
			// .orElse(null);
			// prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
			// // A New type of cssSelector: for
			// selecting the 2nd element of the
			// same locator ":last-of-type" is used.
			// Thread.sleep(3000);
			// driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
			// ng-animating
			// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
			// driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
			// List<WebElement> cartProducts =
			// driver.findElements(By.cssSelector(".cartSection h3"));
			// Boolean match = cartProducts.stream()
			// .anyMatch(cartProduct ->
			// cartProduct.getText().equalsIgnoreCase(productName));
			// driver.findElement(By.cssSelector(".totalRow button")).click();
			/// Thread.sleep(5000);
			// String confirmMessage =
			// driver.findElement(By.cssSelector(".hero-primary")).getText();
}
