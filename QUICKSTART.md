# Quick Start Guide - Restful Booker API Test Project

## 🚀 Getting Started

### Step 1: Import Project into IntelliJ IDEA

1. Open IntelliJ IDEA
2. Click **File > Open**
3. Navigate to `D:\1. Intellij Idea\Restful-Booker-API-Test`
4. Click **Open**
5. When prompted, select **Open as Project**

### Step 2: Verify Project Setup

The project structure should look like:
```
restful-booker-api-tests/
├── pom.xml
├── README.md
├── QUICKSTART.md (this file)
├── .gitignore
├── src/
│   └── test/
│       ├── java/com/restfulbooker/
│       │   ├── config/ApiConfig.java
│       │   ├── models/*.java (POJO classes)
│       │   ├── tests/*.java (Test classes)
│       │   └── utils/*.java (Utility classes)
│       └── resources/
│           ├── testng.xml
│           ├── log4j2.xml
│           └── config.properties
└── target/ (generated after build)
```

### Step 3: Build the Project

In IntelliJ IDEA terminal:
```bash
mvn clean install -DskipTests
```

Or use IntelliJ's built-in Maven tool:
1. Right-click on `pom.xml`
2. Select **Run Maven > clean install**

### Step 4: Run Tests

#### Run All Tests:
```bash
mvn clean test
```

#### Run Specific Test Class:
```bash
mvn clean test -Dtest=BookingTests
```

#### Run Specific Test Method:
```bash
mvn clean test -Dtest=BookingTests#testCreateBooking
```

#### Run Tests from IntelliJ:
1. Right-click on `BookingTests.java`
2. Select **Run 'BookingTests'**

### Step 5: View Allure Report

After tests complete:

```bash
# Generate report
mvn allure:report

# View report in browser
mvn allure:serve
```

## 📝 Project Structure Explanation

### Configuration Files

- **pom.xml**: Maven configuration with all dependencies
- **testng.xml**: TestNG suite configuration and listeners
- **log4j2.xml**: Logging configuration
- **config.properties**: Environment and runtime configuration

### Source Code Packages

#### `config/`
- **ApiConfig.java**: Centralized API configuration (base URL, endpoints, timeouts)

#### `models/`
POJOs for API request/response serialization:
- **Booking.java**: Booking entity model
- **BookingDates.java**: Booking date range model
- **BookingResponse.java**: Booking response with ID
- **Auth.java**: Authentication credentials
- **AuthResponse.java**: Authentication response with token

#### `tests/`
- **BaseTest.java**: Base test class with common setup and utilities
- **BookingTests.java**: Main test suite with 10+ test cases
- **PlaywrightExample.java**: Example Playwright UI automation tests

#### `utils/`
- **ApiClient.java**: REST API client wrapper with common HTTP methods (GET, POST, PUT, PATCH, DELETE)
- **TestDataProvider.java**: TestNG DataProvider for test data management

## 🧪 Test Cases Included

The project includes comprehensive test cases:

1. **testPingEndpoint** - API health check
2. **testAuthentication** - Token generation
3. **testCreateBooking** - Create new booking
4. **testGetAllBookings** - Retrieve all bookings
5. **testGetBookingById** - Get specific booking
6. **testUpdateBooking** - Full update with authentication
7. **testPartialUpdateBooking** - Partial update using PATCH
8. **testDeleteBooking** - Delete booking with authentication
9. **testFilterBookingsByFirstName** - Filter by first name
10. **testFilterBookingsByLastName** - Filter by last name

## 🔌 API Endpoints Tested

```
Base URL: https://restful-booker.herokuapp.com

GET     /ping              → Health check
POST    /auth              → Authentication
GET     /booking           → Get all bookings
POST    /booking           → Create booking
GET     /booking/{id}      → Get booking by ID
PUT     /booking/{id}      → Update booking
PATCH   /booking/{id}      → Partial update
DELETE  /booking/{id}      → Delete booking
```

## 📊 Allure Report Features

The project generates detailed Allure reports with:
- ✅ Test execution timeline
- ✅ Test status (passed/failed/skipped)
- ✅ Detailed test steps
- ✅ API request/response logging
- ✅ Environment information
- ✅ Feature and Story categorization

## 🛠️ Key Classes & Usage

### ApiClient Usage

```java
// GET request
Response response = ApiClient.get(ApiConfig.API_ENDPOINT);

// POST request
Response response = ApiClient.post(ApiConfig.API_ENDPOINT, booking);

// PUT with authentication
Response response = ApiClient.putWithAuth(endpoint, booking, token);

// DELETE with authentication
Response response = ApiClient.deleteWithAuth(endpoint, token);
```

### Model Building

```java
// Create booking using builder pattern
Booking booking = Booking.builder()
        .firstname("John")
        .lastname("Doe")
        .totalprice(1000)
        .depositpaid(true)
        .bookingdates(BookingDates.builder()
                .checkin("2024-01-01")
                .checkout("2024-01-07")
                .build())
        .additionalneeds("WiFi")
        .build();
```

### Logging

Logs are automatically saved to:
- Console output (visible in IDE)
- `logs/restful-booker-tests.log` file

## 🔐 Authentication

Default credentials (from API documentation):
```
Username: admin
Password: password123
```

The token-based authentication flow:
```java
1. Send POST to /auth with credentials
2. Receive token in response
3. Use token as Cookie header: "token=<token_value>"
4. Send requests with token for protected endpoints
```

## 🐛 Troubleshooting

### Tests won't run
```bash
mvn clean install
mvn test
```

### Allure report not displaying
```bash
# Reinstall allure
mvn clean install

# Generate fresh report
mvn clean test allure:report allure:serve
```

### Port already in use (Allure)
The report server uses port 4040 by default. If in use:
```bash
# Stop the server and try again
mvn allure:serve
```

### Compilation errors
Ensure Maven and JDK are properly installed:
```bash
mvn --version
java -version
```

## 📚 Next Steps

1. **Customize API endpoints** - Update `ApiConfig.java` for different environments
2. **Add more test cases** - Extend `BookingTests.java` with additional scenarios
3. **Configure CI/CD** - Add GitHub Actions or Jenkins pipeline
4. **Add UI tests** - Extend `PlaywrightExample.java` with more scenarios
5. **Custom reporting** - Extend Allure annotations with more details

## 📖 Useful Links

- **API Documentation**: https://restful-booker.herokuapp.com/apidoc/index.html
- **REST Assured**: https://rest-assured.io/
- **TestNG**: https://testng.org/
- **Allure**: https://docs.qameta.io/allure/
- **Playwright**: https://playwright.dev/java/

## 💡 Tips

- Use `@DataProvider` in `TestDataProvider.java` for parameterized tests
- Leverage `step()` method in `BaseTest` for better Allure reporting
- Check logs in `logs/` directory for detailed execution information
- Customize `log4j2.xml` for different logging levels per package
- Use `ApiConfig.java` to manage different environments

---

**Happy Testing!** 🎉

For detailed information, see [README.md](README.md)

