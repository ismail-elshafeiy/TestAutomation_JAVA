### The main Frameworks included in the project:
* Selenium Webdriver
* Rest-Assured
* TestNG
* Allure Report
* Extent Reports
* Apachi POI


### Project Design:
* Page Object Model (POM) design pattern
* Data Driven framework
* Fluent design approach
* Have a supporting Utilities package in *src/main/java* file path, named ***"Utilities"*** that includes many wrapper methods in static classes which services as a core engine for the project 




### How to run the project main test cases locally:
* A properties file ***"automationPractice.properties"*** can be found it *src/main/resources* file path including all the configurations needed in the execution
* After executing, you can easily generate the ***Allure Report*** by opening a command-line terminal on the project root path and type `mvn allure:serve` (needs to be able to execute mvn commands); Or you can find the Extent Report ***ExtentReports.html*** in the project root path for the latest execution
* Rub Project by opening a command-line terminal on the project root path and type `mvn `
### Test Cases
* Login Scenario
* DropDown List


