# Selenium Test Automation Framework

## 🎯 Project Overview

This is a comprehensive **Selenium Test Automation Framework** designed using industry-standard design patterns and best practices. The framework is built to test an e-commerce web application (Rahul Shetty Academy demo site) with a focus on **scalability**, **maintainability**, and **reusability**.

### 🌟 Key Features

- **Page Object Model (POM)** implementation
- **Factory Design Pattern** for driver initialization
- **Singleton Pattern** for configuration management
- **Builder Pattern** for test data creation
- **Observer Pattern** for test reporting and listening
- **Data-Driven Testing** with JSON and properties files
- **Cross-browser testing** support
- **Parallel execution** capabilities
- **Extent Reports** integration
- **Cucumber BDD** support
- **TestNG** test management
- **Maven** dependency management

---

## 🏗️ Architecture & Design Patterns

### 1. **Page Object Model (POM)**
**Location**: `src/main/java/com/projectname/pages/`

The framework implements the Page Object Model design pattern where each web page is represented as a class:

```java
public class LandingPage extends BaseComponent {
    @FindBy(id="userEmail")
    WebElement userEmail;
    
    @FindBy(id="userPassword") 
    WebElement userPassword;
    
    public ProductCatalog loginApplication(String email, String password) {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        submit.click();
        return new ProductCatalog(driver);
    }
}
```

**Benefits**:
- ✅ **Separation of concerns** - UI logic separated from test logic
- ✅ **Reusability** - Page methods can be used across multiple tests
- ✅ **Maintainability** - UI changes only require updates in page classes
- ✅ **Readability** - Tests become more readable and business-focused

### 2. **Factory Design Pattern**
**Location**: `src/main/java/com/projectname/core/BaseTest.java`

The Factory pattern is used for WebDriver initialization:

```java
public WebDriver initializeDriver() throws IOException {
    Properties prop = new Properties();
    String browserName = System.getProperty("browser") != null ? 
        System.getProperty("browser") : prop.getProperty("browser");
    
    if (browserName.contains("chrome")) {
        ChromeOptions options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
    } else if (browserName.equalsIgnoreCase("firefox")) {
        driver = new FirefoxDriver();
    }
    return driver;
}
```

**Benefits**:
- ✅ **Flexibility** - Easy to add new browser types
- ✅ **Configuration-driven** - Browser selection via properties/parameters
- ✅ **Encapsulation** - Driver creation logic centralized

### 3. **Base Component Pattern (Template Method)**
**Location**: `src/main/java/com/projectname/core/BaseComponent.java`

Common functionality shared across all page objects:

```java
public class BaseComponent {
    public static void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }
    
    public CartPage goToCartPage() {
        cartHeader.click();
        return new CartPage(driver);
    }
}
```

**Benefits**:
- ✅ **Code reuse** - Common actions available to all pages
- ✅ **Consistency** - Standardized waiting strategies
- ✅ **Inheritance** - Pages extend base functionality

### 4. **Observer Pattern (Test Listeners)**
**Location**: `src/main/java/com/projectname/listeners/TestListener.java`

Implements TestNG listeners for test lifecycle monitoring:

```java
public class TestListener extends BaseTest implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        String filePath = getSceenShot(result.getMethod().getMethodName(), driver);
        test.addScreenCaptureFromPath(filePath);
    }
}
```

**Benefits**:
- ✅ **Automatic reporting** - Test results captured automatically
- ✅ **Screenshot on failure** - Visual debugging support
- ✅ **Loose coupling** - Tests don't need to know about reporting

### 5. **Strategy Pattern (Cross-Browser Testing)**
**Location**: `src/main/java/com/projectname/core/BaseTest.java`

Different browser strategies can be selected at runtime:

```java
// Chrome Strategy
if(browserName.contains("chrome")) {
    ChromeOptions options = new ChromeOptions();
    if(browserName.contains("headless")) {
        options.addArguments("headless");
    }
    driver = new ChromeDriver(options);
}
// Firefox Strategy  
else if(browserName.equalsIgnoreCase("firefox")) {
    driver = new FirefoxDriver();
}
```

### 6. **Builder Pattern (Test Data)**
**Location**: `src/main/java/com/projectname/utils/DataReader.java`

For constructing complex test data objects:

```java
public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
    String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
}
```

---

## 📁 Project Structure

```
project-root/
│
├── pom.xml                          # Maven configuration & dependencies
├── testng.xml                       # TestNG suite configuration
├── .gitignore                       # Git ignore rules
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/projectname/
│   │           ├── core/                    # 🏗️ Framework base classes
│   │           │   ├── BaseTest.java        # Test foundation with driver management
│   │           │   └── BaseComponent.java   # Common page functionality
│   │           ├── config/                  # ⚙️ Configuration management
│   │           │   ├── GlobalData.properties
│   │           │   └── resources/
│   │           │       └── ExtentReporterNG.java
│   │           ├── environments/            # 🌍 Environment-specific configs
│   │           ├── logging/                 # 📝 Logging utilities
│   │           ├── utils/                   # 🛠️ Helper utilities
│   │           │   └── DataReader.java      # Test data management
│   │           ├── pages/                   # 📄 Page Object classes
│   │           │   ├── LandingPage.java
│   │           │   ├── ProductCatalog.java
│   │           │   ├── CartPage.java
│   │           │   ├── CheckoutPage.java
│   │           │   ├── OrderPage.java
│   │           │   └── ConfirmationPage.java
│   │           ├── listeners/               # 👂 TestNG listeners
│   │           │   ├── TestListener.java
│   │           │   └── RetryAnalyzer.java
│   │           └── factories/               # 🏭 Object creation patterns
│   │
│   └── test/
│       ├── java/
│       │   └── com/projectname/
│       │       ├── sprints/                 # 🏃 Sprint-based test organization
│       │       │   ├── sprint01/
│       │       │   │   ├── LoginTest.java
│       │       │   │   └── CheckoutTest.java
│       │       │   ├── sprint02/
│       │       │   │   └── CartTest.java
│       │       │   └── sprint03/
│       │       ├── runners/                 # 🏃‍♂️ Test runners
│       │       └── stepdefs/                # 🥒 Cucumber step definitions
│       │           └── LoginSteps.java
│       │
│       └── resources/
│           ├── config/                      # 📋 Environment configs
│           │   ├── dev.properties
│           │   ├── qa.properties
│           │   └── prod.properties
│           ├── testdata/                    # 📊 Test data files
│           │   ├── users.json
│           │   └── products.xlsx
│           └── features/                    # 🥒 Cucumber feature files
│               ├── login.feature
│               └── checkout.feature
│
└── target/                                  # 🎯 Build outputs
    ├── cucumber-reports/
    ├── extent-reports/
    ├── screenshots/
    └── test-output/
```

---

## 🚀 Scalability Features

### 1. **Modular Architecture**
- **Sprint-based organization** allows teams to work on different modules
- **Loose coupling** between components
- **Plugin architecture** for adding new capabilities

### 2. **Parallel Execution**
TestNG configuration supports parallel execution:

```xml
<suite name="Selenium Test Suite" parallel="tests" thread-count="3">
    <test name="Sprint01 Tests">
        <classes>
            <class name="com.projectname.sprints.sprint01.LoginTest"/>
            <class name="com.projectname.sprints.sprint01.CheckoutTest"/>
        </classes>
    </test>
</suite>
```

### 3. **Cross-Environment Support**
- Environment-specific property files
- Runtime environment selection
- Database connection configurations

### 4. **Cross-Browser Testing**
- Support for Chrome, Firefox, Edge
- Headless execution support
- Mobile browser testing capability

---

## 🔧 Maintainability Features

### 1. **Configuration Management**
- **Centralized configuration** in properties files
- **Environment-specific** settings
- **Runtime parameter** override capability

### 2. **Error Handling & Debugging**
- **Automatic screenshots** on test failures
- **Comprehensive logging** with different levels
- **Retry mechanism** for flaky tests
- **Detailed reporting** with Extent Reports

### 3. **Code Organization**
- **Clear separation** of concerns
- **Meaningful naming** conventions
- **Comprehensive documentation**
- **Version control** with Git

### 4. **Data Management**
- **External test data** files (JSON, Excel)
- **Data-driven testing** approach
- **Environment-specific** data sets

---

## 🛠️ Technologies & Tools

| Category | Technology | Purpose |
|----------|------------|---------|
| **Build Tool** | Maven | Dependency management & build automation |
| **Testing Framework** | TestNG | Test execution & management |
| **BDD Framework** | Cucumber | Behavior-driven development |
| **Web Automation** | Selenium WebDriver | Browser automation |
| **Driver Management** | WebDriverManager | Automatic driver management |
| **Reporting** | Extent Reports | Rich HTML reports |
| **Data Parsing** | Jackson | JSON data handling |
| **Utilities** | Apache Commons | File operations |
| **Browser Support** | Chrome, Firefox, Edge | Cross-browser testing |

---

## 🏃‍♂️ Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher
- Chrome/Firefox browser

### Setup & Execution

1. **Clone the repository**
```bash
git clone <repository-url>
cd SeleniumFrameworkDesign
```

2. **Install dependencies**
```bash
mvn clean install
```

3. **Run tests**
```bash
# Run all tests
mvn test

# Run specific test suite
mvn test -Dsurefire.suiteXmlFiles=testSuites/testng.xml

# Run with specific browser
mvn test -Dbrowser=chrome

# Run in headless mode
mvn test -Dbrowser=chromeheadless
```

4. **View reports**
- **Extent Reports**: `target/extent-reports/index.html`
- **TestNG Reports**: `test-output/index.html`
- **Screenshots**: `target/screenshots/`

---

## 📊 Test Execution Strategy

### 1. **Smoke Tests**
Critical path testing for immediate feedback:
```xml
<groups>
    <run>
        <include name="smoke"/>
    </run>
</groups>
```

### 2. **Regression Tests**
Comprehensive testing for stable builds:
```xml
<groups>
    <run>
        <include name="regression"/>
    </run>
</groups>
```

### 3. **Sprint-based Testing**
Organized by development sprints for better tracking.

---

## 🎯 Best Practices Implemented

1. **DRY Principle** - Don't Repeat Yourself
2. **SOLID Principles** - Single Responsibility, Open/Closed, etc.
3. **Explicit Waits** - No hardcoded sleeps
4. **Page Factory** - Lazy initialization of elements
5. **Data-Driven Testing** - External test data
6. **Error Handling** - Comprehensive exception management
7. **Logging** - Detailed execution logs
8. **Version Control** - Git with proper .gitignore

---

## 🔮 Future Enhancements

- **API Testing** integration
- **Mobile Testing** support with Appium
- **Docker** containerization
- **CI/CD** pipeline integration
- **Performance Testing** capabilities
- **Database Testing** utilities
- **Visual Testing** with AI

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Follow coding standards
4. Add appropriate tests
5. Update documentation
6. Submit a pull request

---

## 📞 Support

For questions and support:
- **Email**: support@projectname.com
- **Documentation**: [Project Wiki](link-to-wiki)
- **Issues**: [GitHub Issues](link-to-issues)

---

*This framework represents modern test automation practices and serves as a solid foundation for scalable, maintainable automation projects.*
