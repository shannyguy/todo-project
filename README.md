# TodoMVC Test automation framework

An automation framework designed for testing http://todomvc.com/ "Todo" application

## Requirements

In order to utilize this project you need to have the following installed locally:

* Maven
* Chrome
* Java 1.8

## Running the tests

TestNG is the framework used for running the tests.
Configure TestNG to run the following suite: src/main/resources/testNGSuites/mySuite.xml (Run-> Run Configurations...)

### Tests cases

The test cases for the applications are located in:  src/main/resources/documents/Todo_application_test_cases.xlsx

### Implemented test cases

* Add todo item
* Remove todo item
* Mark as completed
* Mark all as completed
* Clear completed

## Reporting

Open-source reporting library ExtentReports was integrated with TestNG to provide clear test execution report.
The generated reports are located in: target/reports

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
