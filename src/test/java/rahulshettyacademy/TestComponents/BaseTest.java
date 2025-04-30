package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class BaseTest {
	public WebDriver driver;
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws IOException
	{
		//Global properties class
		Properties prop = new Properties ();
		//FileInputStream fis = new FileInputStream("C:\\Users\\LENOVO\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");
		String projectPath = System.getProperty("user.dir");
        FileInputStream fis = new FileInputStream(projectPath + "\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");
		prop.load(fis);
		
		//String broswerName =prop.getProperty("browser");	//This line can only invoke chrome browser if called from cmd prompt
		String browserName =System.getProperty("browser") !=null ? System.getProperty("browser") : prop.getProperty("browser");	//it can invoke firfox or edge or other browsers if called from cmd prompt
		
		if(browserName.contains("chrome"))
		{
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless"))
			{
				options.addArguments("headless");
			}
			
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension (1440,900));
		}
		else if(browserName.equalsIgnoreCase("firefox")){
			//firefox
		driver = new FirefoxDriver();
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\LENOVO\\Documents\\geckodriver.exe");

		}
		else if(browserName.equalsIgnoreCase("edge")){
			//edge
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filPath) throws IOException {
	    //reading the json to string
	    String jsonContent = FileUtils.readFileToString(new File(filPath), StandardCharsets.UTF_8);
	    
	    //String to HashMap: it requires downloading the jackson dependency for conversion
	    //we just build this utility once and it will automatically push it into the framework
	    ObjectMapper mapper = new ObjectMapper();
	    
	    List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
	    });
	    return data;
	    //{map, map1}
	}
	
	public String getSceenShot(String testCaseName, WebDriver driver) throws IOException {		//String testCaseName will be named in later stage
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file= new File (System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
	}
	
	
	@BeforeMethod(alwaysRun =true)
	public LandingPage launchApplication() throws IOException
	{
		driver =initializeDriver();
		landingPage = new LandingPage(driver);
		//LandingPage landingPage = new LandingPage(driver);
		landingPage.gotTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun =true)
	public void tearDown()
	{
		driver.close();
	}
	
}
