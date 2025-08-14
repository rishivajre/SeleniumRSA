# SeleniumRSA - E-commerce Test Automation Framework

A comprehensive Selenium WebDriver test automation framework built using Java, TestNG, and Cucumber for end-to-end testing of e-commerce web applications.

## 🚀 Features

- **Page Object Model (POM)** - Clean and maintainable test structure
- **TestNG Integration** - Powerful testing framework with parallel execution support
- **Cucumber BDD** - Behavior-driven development with Gherkin syntax
- **ExtentReports** - Rich HTML test reports with screenshots
- **Cross-browser Testing** - Support for Chrome, Firefox, and Edge
- **Data-driven Testing** - JSON-based test data management
- **Maven Integration** - Easy dependency management and build automation
- **WebDriverManager** - Automatic browser driver management

## 🏗️ Project Structure

```
SeleniumRSA/
├── src/
│   ├── main/java/rahulshettyacademy/
│   │   ├── AbstractComponents/       # Base components and utilities
│   │   ├── pageobjects/             # Page Object Model classes
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutPage.java
│   │   │   ├── ConfirmationPage.java
│   │   │   ├── LandingPage.java
│   │   │   ├── OrderPage.java
│   │   │   └── ProductCatalog.java
│   │   └── resources/               # Configuration and resources
│   └── test/java/
│       ├── cucumber/                # Cucumber BDD tests
│       │   ├── ErrorValidations.feature
│       │   ├── SubmitOrder.feature
│       │   └── TestNGTestRunner.java
│       └── rahulshettyacademy/
│           ├── TestComponents/      # Test base classes and utilities
│           ├── data/               # Test data files
│           ├── stepDefinition/     # Cucumber step definitions
│           └── tests/              # TestNG test classes
├── testSuites/                     # TestNG XML suite files
├── pom.xml                         # Maven configuration
└── README.md
```

## 🛠️ Prerequisites

- **Java 8** or higher
- **Maven 3.6+**
- **Chrome/Firefox/Edge** browser installed
- **Git** for version control

## ⚡ Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/rishivajre/SeleniumRSA.git
cd SeleniumRSA
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Run Tests

#### Run All Tests
```bash
mvn test
```

#### Run Specific Test Suite
```bash
# Regression Tests
mvn test -PRegression

# Purchase Order Tests
mvn test -PPurchaseTest

# Error Validation Tests
mvn test -PErrorValidationTest

# Cucumber BDD Tests
mvn test -PCucumberTests
```

#### Run Individual Test Classes
```bash
# Run specific test class
mvn test -Dtest=SubmitOrderTest

# Run specific test method
mvn test -Dtest=SubmitOrderTest#submitOrder
```

## 📊 Test Reports

After test execution, reports are generated in:
- **ExtentReports**: `reports/index.html`
- **TestNG Reports**: `test-output/index.html`

## 🔧 Configuration

### Browser Configuration
The framework supports multiple browsers. Configure in your test base class:
- Chrome (default)
- Firefox
- Edge

### Test Data Management
Test data is managed through JSON files located in `src/test/java/rahulshettyacademy/data/`:
- `PurchaseOrder.json` - Contains test user credentials and product information

Example test data structure:
```json
[
  {
    "email": "testuser1@example.com",
    "password": "TestPass@123",
    "productName": "ZARA COAT 3"
  }
]
```

## 🧪 Writing Tests

### Page Object Model Example
```java
public class LandingPage extends AbstractComponent {
    WebDriver driver;
    
    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(id = "userEmail")
    WebElement userEmail;
    
    @FindBy(id = "userPassword")
    WebElement passwordEle;
    
    public ProductCatalog loginApplication(String email, String password) {
        userEmail.sendKeys(email);
        passwordEle.sendKeys(password);
        submit.click();
        return new ProductCatalog(driver);
    }
}
```

### TestNG Test Example
```java
@Test(dataProvider = "getData", groups = {"Purchase"})
public void submitOrder(HashMap<String, String> input) throws IOException {
    ProductCatalog productCatalog = landingPage.loginApplication(
        input.get("email"), 
        input.get("password")
    );
    
    List<WebElement> products = productCatalog.getProductList();
    productCatalog.addProductToCart(input.get("productName"));
    
    CartPage cartPage = productCatalog.goToCartPage();
    Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
    Assert.assertTrue(match);
    
    CheckoutPage checkoutPage = cartPage.goToCheckout();
    checkoutPage.selectCountry("India");
    ConfirmationPage confirmationPage = checkoutPage.submitOrder();
    String confirmMessage = confirmationPage.getConfirmationMessage();
    Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
}
```

### Cucumber BDD Example
```gherkin
Feature: Error validation

@ErrorValidation
Scenario Outline: Login Error Validation
  Given I landed on Ecommerce Page
  When Logged in with username <name> and password <password>
  Then "Incorrect email or password." message is displayed

Examples:
  | name              | password   |
  | test@example.com  | wrongpass  |
```

## 🏃‍♂️ CI/CD Integration

This framework can be easily integrated with CI/CD pipelines:

### GitHub Actions Example
```yaml
name: Selenium Tests
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 8
        uses: actions/setup-java@v2
        with:
          java-version: '8'
          distribution: 'adopt'
      - name: Run tests
        run: mvn clean test
```

### Jenkins Pipeline Example
```groovy
pipeline {
    agent any
    tools {
        maven 'Maven-3.8.1'
        jdk 'JDK-8'
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'reports',
                        reportFiles: 'index.html',
                        reportName: 'Extent Report'
                    ])
                }
            }
        }
    }
}
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 Best Practices

- **Page Object Model**: Each page should have its own class with locators and actions
- **Implicit Waits**: Use WebDriverWait for dynamic elements
- **Test Independence**: Each test should be independent and able to run in isolation
- **Data Separation**: Keep test data separate from test logic
- **Meaningful Assertions**: Use descriptive assertion messages
- **Clean Code**: Follow Java coding standards and naming conventions

## 🐛 Troubleshooting

### Common Issues

1. **WebDriver Executable Not Found**
   - Solution: WebDriverManager automatically handles driver downloads

2. **Element Not Found**
   - Solution: Add explicit waits using WebDriverWait

3. **Tests Running Too Slow**
   - Solution: Use parallel execution in TestNG suite files

4. **Browser Not Opening**
   - Solution: Check if the browser is installed and update browser drivers

## 📚 Dependencies

Major dependencies included in this framework:
- **Selenium WebDriver 4.21.0** - Web automation
- **TestNG 7.7.1** - Testing framework
- **Cucumber 7.12.0** - BDD framework
- **ExtentReports 5.0.8** - Reporting
- **WebDriverManager 5.3.3** - Driver management
- **Jackson 2.15.2** - JSON processing

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Rishi Vajre**
- GitHub: [@rishivajre](https://github.com/rishivajre)

## 🙏 Acknowledgments

- Rahul Shetty Academy for the excellent Selenium course
- Selenium WebDriver community for continuous support
- TestNG and Cucumber teams for amazing frameworks
