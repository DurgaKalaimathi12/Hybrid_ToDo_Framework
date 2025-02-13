# ToDo Application Hybrid Automation Framework - README

# Overview
This automation framework is designed for efficient test execution using Selenium and TestNG in Hybrid Model. It supports cross-browser testing, parallel execution, reporting, and integration with CI/CD as well. The framework optimizes test execution time while ensuring high test coverage and maintainability.


# Key Features
- Page Object Model (POM) Implementation for better maintainability
- Parallel Execution using TestNG for faster test runs by setting parallel and thread-count parameters in the testng.xml
- Headless Browser Execution for fast testing in non-GUI environments
- Cross-Browser Testing using WebDriver
- Dynamic Locators Handling for robust automation
- Custom Logging & Reporting using Extent Reports
- Integration with CI/CD Pipelines (GitLab CI/CD, Jenkins [yet to be integrated if needed])
- Test Data Management using Apache POI for Excel  with specific data for task name, action, and test case details (Add, Delete, Complete, Filter, Amend)


## Project Structure

src/main/java/
	base/                 # Base classes
	pages/                # Page Object Model classes
	tests/                # Test classes
	utils/                # Utility classes (Excel, Logging, etc.)
src/test/java/
	test/                 # Test classes
src/test/resources/
	config/               # Configuration files (test data, properties, etc.)
	testng.xml            # TestNG execution file

reports/                  # Generated test reports
pom.xml                   # Maven dependencies
README.txt                # Project documentation


# How It Works
1. Test Execution Flow:
   - Tests are defined in the 'ToDoTest'class inside Testpackage using TestNG.
   - Each test interacts with web elements through POM classes (Page Object).
   - Test data is read from external sources (configReader or ExcelReader).
   - WebDriver is initialized based on config.properties settings.
   - After execution, reports are generated using Extent Reports.

2. Test Design & Structure:
   - Test classes are structured based on functionalities and modules.
   - Each test case follows the Arrange-Act-Assert pattern:
     - Arrange: Setup test data and preconditions.
     - Act: Perform the test actions using page methods.
     - Assert: Validate expected vs actual results.


3. Running the Tests:
     Pre-requisite is to download MAVEN from "https://maven.apache.org/download.cgi" 
     and ensure your env variables for maven and Java is available in JAVA_HOME, M2_HOME and Path variables
   - Execute all tests using:
     bash
     mvn clean test
     
   - Run specific test suites with:
     bash
     mvn test -DsuiteXmlFile=testng.xml
     
   - Execute parallel tests by modifying "testng.xml":
     xml
     <suite name="Suite" parallel="methods" thread-count="2">
     


4. Test Data Management:
   - Test data is stored in Excel files and read using Apache POI.
   - Data-driven tests using @Dataprovider fetch input values from Excel and iterate test cases dynamically.
   - TestDataProvider.java class fetch the data from Excel using ExcelReader.java and assign to testcase from ToDoTest.java as per the provider name passed.

5. Generating Reports:
   - After execution, reports are stored in "test-output" folder as ExtentReport.html.
   - Open "ExtentReport.html" in browser to view the test execution summary.

6. CI/CD Integration:
   - The framework can be integrated with Jenkins or GitLab for automated test execution.
   - Sample GitLab CI/CD configuration:
     yaml
     test-automation:
       script:
         - mvn clean test
       artifacts:
         paths:
           - reports/



# Conclusion
This framework provides a scalable and efficient approach to Selenium test automation, ensuring robust and maintainable test scripts. Its integration with CI/CD and extensive reporting features make it ideal for enterprise-level testing.
