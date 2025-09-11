package com.projectname.sprints.sprint01;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

// Import your actual page objects and base test classes here, for example:
// import com.projectname.sprints.sprint01.pageobjects.LandingPage;
// import com.projectname.sprints.sprint01.pageobjects.ProductCatalog;

public class LoginTest {

	@Test
	public void LoginErrorValidation() throws InterruptedException, IOException {
		String productName = "ZARA COAT 3";
		// LandingPage landingPage = launchApplication(); not required because of the
		// @BeforeMethod provided in the BaseTest
		// ProductCatalog productCatalog = landingPage.loginApplication("virenshukfla@gmail.com", "Virenf@123");
		// Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMessage());
		// Uncomment and update the above lines with correct references to your page objects
	}

}
