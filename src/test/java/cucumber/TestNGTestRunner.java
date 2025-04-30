package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//cucumber cannot run on its own->TestNG or JUnit runners are required
@CucumberOptions(features="src/test/java/cucumber",glue="rahulshettyacademy.stepDefinition",
monochrome=true,tags = "@Regression", plugin= {"html:target/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

}