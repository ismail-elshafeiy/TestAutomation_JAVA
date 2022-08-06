# Technologies

* [JDK-17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
* [Maven](https://maven.apache.org/) as a build tool.
* [TestNG](https://testng.org/) as a unit test framework.
* [Selenium WebDriver](https://www.selenium.dev/documentation/en/) for GUI testing.
* [Rest assured](https://javadoc.io/doc/io.rest-assured/rest-assured/latest/index.html) for API testing.
* [Allure Report Framework](https://docs.qameta.io/allure/) for generating test execution report.
* [Extent Report Framework](https://www.extentreports.com/docs/versions/4/java/) for generating test execution report.
* [Docker](https://docs.docker.com/)
* [Selenium Grid](https://www.selenium.dev/documentation/grid/) for remote execution.
* [GitHub Actions](https://docs.github.com/en/actions) for continuous integration.

## Project Design patterns:

* Applying the [Page Object Model (POM) design pattern](https://www.browserstack.com/guide/page-object-model-in-selenium#:~:text=Page%20Object%20Model%2C%20also%20known,application%20as%20a%20class%20file.)
* [Fluent design](https://java-design-patterns.com/patterns/fluentinterface/) approach
* Data Driven framework
* [Managing test data](https://www.ontestautomation.com/managing-test-data-in-end-to-end-test-automation/?fbclid=IwAR3JVpSg8jkhxVMgcPzihHDPzSWebbPxLZ7RxX22QQeJlSwQBNhNiXq-koU) in end-to-end test automation by approach `Creating test data during test execution`

## Project Structure:

```bash
  ├── main
  │   ├── gui.pages
  │   ├── apis
  │   ├── framework --> utilities
  │   │   ├── actions
  │   │   ├── browser    
  │   └── resources
  │       ├── configerations --> properties file
  ├── test
  │   ├── testcases 
  │   │   ├── api
  │   │   ├── gui
  │   ├── resources
  │   │   ├── testData 
  ├── README.md
  ├── pom.xml
```



### Run the test cases locally using Terminal:
Prerequisites: jdk-17 and maven should be installed

* A properties file [project.properties](src/main/resources)  including all the configurations
* Set the test Data from [TestData](src/test/resources/TestData)
* Execute All testSuites using Command-line opening a command-line terminal on the project root path and run:
```bash
  mvn clean test
  ```
* After executing,generate the ***Allure Report*** by opening a command-line terminal on the project root path and run:
```bash
 mvn allure:serve
  ```
```bash
  mvn clean
  ```
* Find the Extent Report [ExtentReports.html](ExtentReports.html) in the project root path for the latest execution and
  open by any browser

### 2.1. Run the Test remotely using Selenium-Grid and Docker
Prerequisites: Docker Desktop should be installed
* To start selenium-grid using docker-compose; at the root directory of the project, run the following command <br />
```bash
docker-compose -f src/main/resources/docker-compose.yml up --scale chrome=2 --remove-orphans -d
```
* Open [http://localhost:4444/grid/console](http://localhost:4444/grid/console) to monitor selenium grid.
* Run the test using the following command <br />
```bash
mvn test
```




