# Beginner Line-by-Line Guide (Restful Booker API Test Project)

This guide explains what each important line (or small line group) is doing in beginner-friendly language.

## 1) `src/test/java/com/restfulbooker/config/ApiConfig.java`

- Line 1: Declares the package (folder namespace) for this class.
- Lines 3-5: JavaDoc comment saying this file stores configuration values.
- Line 6: Starts `ApiConfig` class.
- Line 8: Base URL used by all API calls.
- Line 9: Full booking endpoint (`/booking`).
- Line 10: Full auth endpoint (`/auth`).
- Line 11: Full ping endpoint (`/ping`).
- Line 13: Comment header for timeout values.
- Line 14: Request timeout constant in seconds.
- Line 15: Response timeout constant in seconds.
- Line 17: Comment header for headers.
- Line 18: Constant value for JSON content type.
- Line 19: Constant value for JSON accept header.
- Line 20: Closes class.

## 2) `src/test/java/com/restfulbooker/utils/ApiClient.java`

- Line 1: Package declaration.
- Lines 3-9: Imports libraries used for HTTP requests, logging, and config.
- Lines 10-12: JavaDoc comment for utility purpose.
- Line 13: Starts `ApiClient` class.
- Line 15: Creates logger object for this class.
- Lines 17-25: `baseRequest()` creates common request settings (`Content-Type` and `Accept` as JSON).
- Lines 27-32: `baseRequestWithAuth(token)` reuses base settings and adds auth cookie.
- Lines 34-39: `extractResponse(...)` converts Rest Assured chain into final `Response`.
- Lines 41-47: `get(endpoint)` sends a basic GET request.
- Lines 49-57: `getWithPathParam(...)` sends GET with path parameter.
- Lines 59-67: `getWithQueryParams(...)` sends GET with query parameter.
- Lines 69-77: `post(endpoint, body)` sends POST with JSON body.
- Lines 79-87: `postWithAuth(...)` sends authenticated POST request.
- Lines 89-97: `put(endpoint, body)` sends full update request.
- Lines 99-107: `putWithAuth(...)` sends authenticated full update request.
- Lines 109-117: `patch(endpoint, body)` sends partial update request.
- Lines 119-127: `patchWithAuth(...)` sends authenticated partial update request.
- Lines 129-133: `delete(endpoint)` sends delete request without token.
- Lines 135-139: `deleteWithAuth(...)` sends authenticated delete request.
- Line 140: Closes class.

## 3) `src/test/java/com/restfulbooker/tests/BaseTest.java`

- Line 1: Package declaration.
- Lines 3-8: Imports for Rest Assured, Allure, TestNG, logger, config.
- Lines 10-12: JavaDoc comment.
- Line 13: Starts `BaseTest` class.
- Line 15: Shared logger for test classes.
- Lines 17-24: `@BeforeSuite` setup runs once before all tests.
- Line 19: Logs setup start.
- Line 20: Sets base URI globally for all requests.
- Lines 21-23: Sets default request specification (JSON in/out).
- Lines 26-28: JavaDoc for simple step method.
- Lines 29-32: `step(stepName)` adds a step to Allure and logs it.
- Lines 34-36: JavaDoc for step method with description.
- Lines 37-40: `step(stepName, description)` adds detailed step to Allure and logs it.
- Line 41: Closes class.

## 4) `src/test/java/com/restfulbooker/listeners/AllureTestCaseNameListener.java`

- Line 1: Package declaration.
- Lines 3-11: Imports for Allure, reflection, logging context, and TestNG listener.
- Lines 13-16: JavaDoc: keeps Allure names in `TC_API_xxx - Subject` format.
- Line 17: Starts listener class implementing `ITestListener`.
- Line 19: Key name used in thread context map (`testCase`).
- Line 20: Logger for listener class.
- Lines 22-28: `onTestStart` runs when a test begins.
- Line 24: Builds final display name for current test.
- Line 25: Saves display name in thread context (used by logs).
- Line 26: Updates Allure display name.
- Line 27: Writes start log line.
- Lines 30-33: `onTestSuccess` clears thread context after pass.
- Lines 35-38: `onTestFailure` clears thread context after fail.
- Lines 40-47: `onTestSkipped` sets name, logs skip, and clears context.
- Lines 49-52: `setCurrentTestCase(...)` stores test name in thread-local context.
- Lines 54-57: `clearCurrentTestCase()` removes thread-local value.
- Lines 59-62: `updateAllureTestName(...)` sets readable name in Allure report.
- Lines 64-83: `resolveDisplayName(...)` builds best display name:
  - gets Java method from TestNG result,
  - reads `@Test(testName = ...)`,
  - reads `@TmsLink("TC_API_xxx")`,
  - combines them into `TC_API_xxx - Subject`.
- Line 84: Closes class.

## 5) `src/test/java/com/restfulbooker/tests/BookingTests.java`

- Line 1: Package declaration.
- Lines 3-12: Imports for response handling, Allure annotations, TestNG, config, models, and API client.
- Lines 14-16: JavaDoc for this test class.
- Line 17: Allure feature tag for grouping tests.
- Line 18: Class starts and extends `BaseTest`.
- Line 20: `bookingId` shared between dependent tests.
- Line 21: `authToken` shared between authenticated tests.

### Test 1 (`TC_API_001`) - Health Check
- Sends GET `/ping`.
- Verifies status code is `201`.

### Test 2 (`TC_API_002`) - Authentication
- Creates `Auth` object with username/password.
- Sends POST `/auth`.
- Verifies `200`, extracts token, stores in `authToken`.

### Test 3 (`TC_API_003`) - Create Booking
- Builds `BookingDates` + `Booking` payload.
- Sends POST `/booking`.
- If status is not `200`, logs warning and skips dependent tests.
- On success, extracts `bookingId` and validates response fields.

### Test 4 (`TC_API_004`) - Get All Bookings
- Sends GET `/booking`.
- Verifies `200` and array is not empty.

### Test 5 (`TC_API_005`) - Get Booking by ID
- Depends on create booking test.
- Builds `/booking/{id}` endpoint and verifies returned booking.

### Test 6 (`TC_API_006`) - Full Update (PUT)
- Depends on create + auth tests.
- Creates updated booking payload.
- Sends authenticated PUT and verifies updated name.

### Test 7 (`TC_API_007`) - Partial Update (PATCH)
- Depends on create + auth tests.
- Sends authenticated PATCH with partial payload.
- Verifies status `200`.

### Test 8 (`TC_API_008`) - Delete Booking
- Depends on create + auth tests.
- Sends authenticated DELETE.
- Verifies delete status `201`.

### Test 9 (`TC_API_009`) - Filter by First Name
- Sends GET `/booking?firstname=John` using query param helper.
- Verifies status `200`.

### Test 10 (`TC_API_010`) - Filter by Last Name
- Sends GET `/booking?lastname=Doe` using query param helper.
- Verifies status `200`.

## 6) Model classes (`src/test/java/com/restfulbooker/models/*.java`)

The following classes use the same pattern:
- `Auth.java`
- `AuthResponse.java`
- `Booking.java`
- `BookingDates.java`
- `BookingResponse.java`

Line-by-line pattern inside each model:
- Package/import lines: set namespace and JSON annotations.
- `@JsonIgnoreProperties(ignoreUnknown = true)`: ignore extra JSON fields safely.
- `@JsonProperty("...")`: map Java field to JSON key.
- Empty constructor: required by JSON parser.
- Full constructor: quick way to create object with all values.
- Getter methods: read field values.
- Setter methods: update field values.
- `builder()` and inner `*Builder` class: fluent way to create objects:
  - set fields one by one,
  - call `.build()` to create final object.

## 7) `src/test/java/com/restfulbooker/tests/PlaywrightExample.java`

- Package/import lines: load Playwright, Allure, and TestNG.
- Fields: browser objects used during UI test.
- `@BeforeClass setupBrowser()`: opens browser and page before UI tests.
- `@AfterClass tearDownBrowser()`: closes all browser resources safely.
- `testNavigateToApiDocs()`: opens API doc URL and checks title has `API`.
- `testVerifyApiEndpoints()`: waits briefly and checks `GET` text is visible.

---

If you want, I can create **Part 2** with strict per-line explanation for every single line number in `BookingTests.java` and each model class separately.

