A Java-based test automation project using **RestAssured**, **Cucumber**, and **JUnit** for testing REST APIs of
https://api.apilayer.com/fixer/latest endpoint


## Project Overview

This project is designed to test the functionality of REST API endpoints. It uses:
- **Cucumber** for writing test scenarios in a Gherkin format.
- **RestAssured** for handling HTTP requests and responses.
- **JUnit** for test execution and assertions.

### Key Features
- Modular test structure
- Configurable API key and base URL
- Real-time API response validation
- Automated reporting with detailed logs

## Technologies Used

- **Java 21**
- **Maven** for project dependencies
- **RestAssured** for HTTP requests
- **Cucumber** for BDD-style scenarios
- **JUnit** for test assertions and execution
- **Lombok** for reducing boilerplate code (like getters and setters)

### Prerequisites

- **Java** installed (Java 11+ recommended)
- **Maven** installed (for dependency management)

## **Running Test:**

Run the below Maven command.

    API_KEY=your_api_key mvn clean test

For details about apiKey refer to the official documentation: [Fixer API Documentation](https://apilayer.com/marketplace/fixer-api)