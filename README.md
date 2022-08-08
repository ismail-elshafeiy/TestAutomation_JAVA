<img src="src/test/resources/images/automation.png" alt="Automation" style="display:block; margin-left:auto; margin-right:auto;"/>
# Technologies

* Java Development Kit [JDK-17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows) as a Development
  Environment.
* [Maven](https://maven.apache.org/) as a build tool, that automate the process of compiling computer source code into
  binary code.
* [TestNG](https://testng.org/) as a Unit Test Automation framework.
* [Selenium WebDriver](https://www.selenium.dev/documentation/en/) for Web App GUI Test Automation.
* [Appium](https://appium.io/) for Mobile App GUI Test Automation.
* [Rest assured](https://javadoc.io/doc/io.rest-assured/rest-assured/latest/index.html) for REST/GraphQl API Test Automation.
* [Allure Report Framework](https://docs.qameta.io/allure/) for generating test execution report.
* [Extent Report Framework](https://www.extentreports.com/docs/versions/4/java/) for generating test execution report.
* [Data Driven Testing framework](https://www.guru99.com/data-driven-testing.html) to read from data files and store
  them in variables in test scripts.
* [Docker](https://docs.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/overview/) for running Selenium Grid.
* [Selenium Grid](https://www.selenium.dev/documentation/grid/) for remote execution.
* [GitHub Actions](https://docs.github.com/en/actions) for continuous integration.
* Applying Hybrid Test Automation Framework

## Project Design patterns:

* [WebElement Extension Methods](https://toolsqa.com/selenium-webdriver/c-sharp/webelement-extensions-method/) (Selenium
  Wrapper).
* Applying
  the [Page Object Model (POM) design pattern](https://www.browserstack.com/guide/page-object-model-in-selenium#:~:text=Page%20Object%20Model%2C%20also%20known,application%20as%20a%20class%20file.).
* [Fluent design](https://java-design-patterns.com/patterns/fluentinterface/) approach.
* [Managing test data](https://www.ontestautomation.com/managing-test-data-in-end-to-end-test-automation/?fbclid=IwAR3JVpSg8jkhxVMgcPzihHDPzSWebbPxLZ7RxX22QQeJlSwQBNhNiXq-koU)
  in end-to-end test automation by approach `Creating test data during test execution`.

## Project Structure:

```bash
  ├── main
  │   ├── com.project 
  │   │   ├── gui.pages
  │   │   └──  apis
  │   ├── framework --> engine
  │   │   ├── gui.actions
  │   │   ├── api.actions
  │   │   ├── browser
  │   │   └── tools  
  │   └── resources
  │       └──  configerations --> properties file
  ├── test
  │   ├── testcases 
  │   │   ├── api
  │   │   └── gui
  │   ├── testSenarios
  │   │   └── end_to_end
  │   └── resources
  │       ├── testData
  │       ├── uploads files
  │       └── screenshots
  ├── pom.xml
  └── README.md
```

## Run the Test cases locally using IntelliJ IDEA

Pre-requisites: jdk-17 and maven should be installed

* A properties file [project.properties](src/main/resources)  including all the configurations
* Set the test Data from [TestData](src/test/resources/TestData)
* Edit your run configuration templates before running your tests by following these steps:
  <br/>- Open 'Edit Run/Debug Configurations' dialog > Edit Configurations... > Edit configuration templates...
  <br/>- Select <b>TestNG</b> > Listeners > and add this listener:
  `engine.tools.listeners.TestngListener`
* Execute All testSuites using Command-line opening a command-line terminal on the project root path and run the
  following command:

```bash
  mvn clean test
  ```

* After executing,generate the ***Allure Report*** by opening a command-line terminal on the project root path and run
  the following command:

```bash
 mvn allure:serve
  ```

```bash
CTRL+C to stop the server and type 'y'
```

```bash
  mvn clean
  ```

* Find the Extent Report [ExtentReports.html](ExtentReports.html) in the project root path for the latest execution and
  open by any browser

## Run the Test remotely using Selenium-Grid and Docker:

Pre-requisites: Docker Desktop should be installed.

* To start selenium-grid using docker-compose; at the root directory of the project, run the following command:

```bash
docker-compose -f src/main/resources/docker-compose.yml up --scale chrome=2 --remove-orphans -d
```

* Open [http://localhost:4444/grid/console](http://localhost:4444/grid/console) to monitor selenium grid.
* Run the test using the following command:

```bash
mvn test
```

* To end/down selenium grid; at the root directory, run the following command:

```bash:
docker-compose -f src/main/resources/docker-compose.yml down --remove-orphans
```




