# The main Frameworks included in the project:

* Selenium Webdriver
* Rest-Assured
* TestNG
* Allure Report
* Extent Reports
* Apachi POI

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

## Project Design:

* Page Object Model (POM) design pattern
* Data Driven framework
* Fluent design approach

## Pre-requests

* Install Java [JDK 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
* Install [Maven](https://maven.apache.org/download.cgi)
* Install Allure Report

### Install and Execution:

### How to run the project main test cases locally:

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





