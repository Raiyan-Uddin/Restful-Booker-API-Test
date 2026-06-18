# Restful Booker API Test Project

A comprehensive automated API testing framework for the Restful Booker API using Java, Maven, REST Assured, Playwright, and Allure Reports.

## 📋 Table of Contents
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [Running Tests](#running-tests)
- [Allure Reports](#allure-reports)
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)

## 🔧 Prerequisites

- **Java**: JDK 11 or higher
- **Maven**: 3.6.0 or higher
- **Git**: For version control (optional)
- **IDE**: IntelliJ IDEA or any Java IDE
- **API**: Restful Booker API (https://restful-booker.herokuapp.com)

## 📁 Project Structure

```
restful-booker-api-tests/
├── pom.xml                          # Maven configuration file
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── com/restfulbooker/
│   │   │       ├── config/
│   │   │       │   └── ApiConfig.java         # API configuration
│   │   │       ├── models/
│   │   │       │   ├── Auth.java              # Auth model
│   │   │       │   ├── AuthResponse.java      # Auth response model
│   │   │       │   ├── Booking.java           # Booking model
│   │   │       │   ├── BookingDates.java      # Booking dates model
│   │   │       │   └── BookingResponse.java   # Booking response model
│   │   │       ├── tests/
│   │   │       │   ├── BaseTest.java          # Base test class
│   │   │       │   └── BookingTests.java      # Booking test cases
│   │   │       └── utils/
│   │   │           ├── ApiClient.java         # API client utilities
│   │   │           └── TestDataProvider.java  # Test data provider
│   │   └── resources/
│   │       ├── testng.xml                     # TestNG configuration
│   │       └── log4j2.xml                     # Log4j2 configuration
├── logs/                            # Log files (generated at runtime)
└── target/                          # Build output
    └── allure-report/              # Allure report (generated after test run)
```

## 🚀 Setup & Installation

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/restful-booker-api-tests.git
cd restful-booker-api-tests
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Verify Installation
```bash
mvn -version
java -version
```

## 🧪 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn clean test -Dtest=BookingTests
```

### Run Specific Test Method
```bash
mvn clean test -Dtest=BookingTests#testCreateBooking
```

### Run Tests in Parallel
Update the `thread-count` in `src/test/resources/testng.xml`:
```xml
<suite name="Restful Booker API Tests" parallel="methods" thread-count="4">
```

## 📊 Allure Reports

### Generate Allure Report
After running tests, generate the Allure report:
```bash
mvn allure:report
```

### View Allure Report
```bash
mvn allure:serve
```

This will automatically open the Allure report in your default browser.

### Report Location
- Results: `target/allure-results/`
- Report: `target/allure-report/`

## ✨ Features

### 🎓 Beginner-Friendly Learning Guide
- Line-by-line explanations are available in `src/test/resources/BEGINNER_LINE_BY_LINE_GUIDE.md`
- Strict detailed line-by-line explanations are available in `src/test/resources/BEGINNER_STRICT_LINE_BY_LINE_PART2.md`
- Use this guide while reading Java files to understand what each block is doing

### ✅ Comprehensive API Testing
- **Health Check**: Ping endpoint verification
- **Authentication**: Token generation and validation
- **CRUD Operations**: Create, Read, Update, Delete bookings
- **Filtering**: Filter bookings by firstname and lastname
- **Partial Updates**: PATCH request testing

### 📝 Allure Report Integration
- Detailed test steps with descriptions
- Environment variables tracking
- Test categorization by Feature and Story
- Screenshot/attachment support
- Test execution timeline

### 🛠️ Utilities
- **ApiClient**: Wrapper for common HTTP methods (GET, POST, PUT, PATCH, DELETE)
- **TestDataProvider**: Reusable test data with DataProvider annotations
- **BaseTest**: Common setup and teardown for all tests

### 📋 Logging
- SLF4J with Log4j2 backend
- Separate file and console logging
- Rolling file appender with size-based rotation
- Colored console output for better readability

### 📦 Dependencies
- **REST Assured**: REST API testing
- **TestNG**: Test framework
- **Allure**: Test reporting
- **Playwright**: Browser automation (for UI tests)
- **Jackson**: JSON processing
- **Lombok**: Reduce boilerplate code
- **AssertJ**: Fluent assertions
- **Log4j2**: Logging framework

## 🔌 API Endpoints

### Base URL
```
https://restful-booker.herokuapp.com
```

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/ping` | Health check |
| POST | `/auth` | Generate auth token |
| GET | `/booking` | Get all bookings |
| POST | `/booking` | Create new booking |
| GET | `/booking/{id}` | Get booking by ID |
| PUT | `/booking/{id}` | Update booking |
| PATCH | `/booking/{id}` | Partial update |
| DELETE | `/booking/{id}` | Delete booking |

### Query Parameters
- `firstname`: Filter by first name
- `lastname`: Filter by last name
- `checkin`: Filter by check-in date (YYYY-MM-DD)
- `checkout`: Filter by check-out date (YYYY-MM-DD)

## 📝 Example Test

```java
@Test
public void testCreateBooking() {
    // Create booking data
    Booking booking = Booking.builder()
            .firstname("John")
            .lastname("Doe")
            .totalprice(1000)
            .depositpaid(true)
            .bookingdates(BookingDates.builder()
                    .checkin("2024-01-01")
                    .checkout("2024-01-07")
                    .build())
            .additionalneeds("WiFi, Breakfast")
            .build();
    
    // Create booking
    Response response = ApiClient.post(ApiConfig.API_ENDPOINT, booking);
    
    // Assertions
    Assert.assertEquals(response.getStatusCode(), 200);
    BookingResponse bookingResponse = response.as(BookingResponse.class);
    Assert.assertNotNull(bookingResponse.getBookingid());
}
```

## 🔐 Authentication

The API uses token-based authentication. To authenticate:

```java
Auth auth = Auth.builder()
        .username("admin")
        .password("password123")
        .build();

Response response = ApiClient.post(ApiConfig.AUTH_ENDPOINT, auth);
AuthResponse authResponse = response.as(AuthResponse.class);
String token = authResponse.getToken();
```

Use the token for protected endpoints:
```java
ApiClient.putWithAuth(endpoint, body, token);
ApiClient.deleteWithAuth(endpoint, token);
```

## 📚 Configuration Files

### pom.xml
Main Maven configuration with all dependencies and plugins.

### testng.xml
TestNG suite configuration:
- Test classes to run
- Parallel execution settings
- Listeners (Allure integration)

### log4j2.xml
Logging configuration:
- Console and file appenders
- Log levels
- Rolling file strategies

## 🐛 Troubleshooting

### Tests Won't Run
```bash
# Clear build files and rebuild
mvn clean install
```

### Allure Report Not Generating
```bash
# Ensure allure results are present
mvn clean test allure:report allure:serve
```

### Port Already in Use (for Allure server)
```bash
# Kill process and try again
mvn allure:serve
```

### API Connection Issues
- Verify internet connection
- Check API status: https://restful-booker.herokuapp.com/apidoc/index.html
- Verify BaseURL in `ApiConfig.java`

## 📧 Contact & Support

For issues or questions, please refer to:
- API Documentation: https://restful-booker.herokuapp.com/apidoc/index.html
- REST Assured Docs: https://rest-assured.io/
- TestNG Docs: https://testng.org/
- Allure Docs: https://docs.qameta.io/allure/

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🎯 Best Practices

1. **Test Data**: Use TestDataProvider for reusable test data
2. **Logging**: Always log important steps for better debugging
3. **Assertions**: Use AssertJ for more readable assertions
4. **API Client**: Use ApiClient utility methods instead of direct RestAssured calls
5. **Test Naming**: Use descriptive test method names
6. **Allure Annotations**: Add @Feature, @Story, @Description for better reporting

## 🔄 Continuous Integration

Add this to your CI/CD pipeline:
```bash
mvn clean test
mvn allure:report
```

Then publish the Allure report from `target/allure-report/` directory.

---

**Happy Testing!** 🎉

