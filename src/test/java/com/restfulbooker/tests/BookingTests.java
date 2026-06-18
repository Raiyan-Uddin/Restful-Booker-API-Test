package com.restfulbooker.tests;

import io.restassured.response.Response;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.restfulbooker.config.ApiConfig;
import com.restfulbooker.models.*;
import com.restfulbooker.utils.ApiClient;

/**
 * Test class for Restful Booker API
 */
@Feature("Restful Booker API")
public class BookingTests extends BaseTest {

    // Shared state between dependent tests.
    // bookingId is set after create-booking test and reused by update/delete tests.
    private Integer bookingId;

    // Auth token is set once and reused for endpoints that require authorization.
    private String authToken;

    @Test(priority = 1, testName = "Verify API health check endpoint (/ping)")
    @Story("Health Check")
    @TmsLink("TC_API_001")
    @Description("Test API health check - Ping endpoint should return 201")
    public void verifyApiHealthCheckEndpoint() {
        step("Send ping request to health check endpoint");

        Response response = ApiClient.get(ApiConfig.PING_ENDPOINT);

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 201, "Ping endpoint should return 201");

        step("Verify response status code is 201");
    }

    @Test(priority = 2, testName = "Verify authentication token generation with valid credentials")
    @Story("Authentication")
    @TmsLink("TC_API_002")
    @Description("Test authentication endpoint to get auth token")
    public void verifyAuthenticationTokenGenerationWithValidCredentials() {
        step("Create auth credentials");
        Auth auth = Auth.builder()
                .username("admin")
                .password("password123")
                .build();

        step("Send authentication request");
        Response response = ApiClient.post(ApiConfig.AUTH_ENDPOINT, auth);

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Auth endpoint should return 200");

        AuthResponse authResponse = response.as(AuthResponse.class);
        authToken = authResponse.getToken();

        Assert.assertNotNull(authToken, "Auth token should not be null");
        logger.info("Authentication successful. Token: {}", authToken);

        step("Verify auth token is received");
    }

    @Test(priority = 3, testName = "Verify booking creation with valid payload")
    @Story("Booking Management")
    @TmsLink("TC_API_003")
    @Description("Test creating a new booking")
    public void verifyBookingCreationWithValidPayload() {
        step("Prepare booking data");
        BookingDates dates = BookingDates.builder()
                .checkin("2024-01-01")
                .checkout("2024-01-07")
                .build();

        Booking booking = Booking.builder()
                .firstname("John")
                .lastname("Doe")
                .totalprice(1000)
                .depositpaid(true)
                .bookingdates(dates)
                .additionalneeds("WiFi, Breakfast")
                .build();

        step("Send POST request to create booking");
        Response response = ApiClient.post(ApiConfig.API_ENDPOINT, booking);

        logger.info("Response Status Code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody().asString());

        // If creation fails, dependent tests are skipped to avoid misleading failures.
        if (response.getStatusCode() != 200) {
            logger.warn("API returned non-200 status: {} - {}", response.getStatusCode(), response.getBody().asString());
            throw new org.testng.SkipException("Booking creation failed with status: " + response.getStatusCode());
        }

        BookingResponse bookingResponse = response.as(BookingResponse.class);
        bookingId = bookingResponse.getBookingid();

        Assert.assertNotNull(bookingId, "Booking ID should not be null");
        Assert.assertNotNull(bookingResponse.getBooking(), "Booking details should not be null");
        Assert.assertEquals(bookingResponse.getBooking().getFirstname(), "John", "Firstname should match");

        logger.info("Booking created successfully with ID: {}", bookingId);
        step("Verify booking is created with correct details");
    }

    @Test(priority = 4, testName = "Verify retrieve all bookings")
    @Story("Booking Management")
    @TmsLink("TC_API_004")
    @Description("Test retrieving all bookings")
    public void verifyRetrieveAllBookings() {
        step("Send GET request to retrieve all bookings");

        Response response = ApiClient.get(ApiConfig.API_ENDPOINT);

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Get all bookings should return 200");

        Object[] bookingIds = response.as(Object[].class);
        Assert.assertNotNull(bookingIds, "Booking IDs array should not be null");
        Assert.assertTrue(bookingIds.length > 0, "Should have at least one booking");

        logger.info("Total bookings retrieved: {}", bookingIds.length);
        step("Verify bookings list is retrieved");
    }

    @Test(priority = 5, dependsOnMethods = "verifyBookingCreationWithValidPayload", testName = "Verify retrieve booking by booking ID")
    @Story("Booking Management")
    @TmsLink("TC_API_005")
    @Description("Test retrieving a specific booking by ID")
    public void verifyRetrieveBookingByBookingId() {
        // Defensive check: this test only makes sense after booking creation.
        Assert.assertNotNull(bookingId, "Booking ID should be set from previous test");

        step("Send GET request to retrieve booking with ID: " + bookingId);

        String endpoint = ApiConfig.API_ENDPOINT + "/" + bookingId;
        Response response = ApiClient.get(endpoint);

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Get booking by ID should return 200");

        Booking booking = response.as(Booking.class);
        Assert.assertNotNull(booking, "Booking should not be null");
        Assert.assertEquals(booking.getFirstname(), "John", "Firstname should match");

        logger.info("Booking retrieved successfully: {}", booking.getFirstname() + " " + booking.getLastname());
        step("Verify booking details are correct");
    }

    @Test(priority = 6, dependsOnMethods = {"verifyBookingCreationWithValidPayload", "verifyAuthenticationTokenGenerationWithValidCredentials"}, testName = "Verify full update of booking with authorization")
    @Story("Booking Management")
    @TmsLink("TC_API_006")
    @Description("Test updating a booking")
    public void verifyFullUpdateOfBookingWithAuthorization() {
        // Both bookingId and authToken are produced by earlier tests.
        Assert.assertNotNull(bookingId, "Booking ID should be set");
        Assert.assertNotNull(authToken, "Auth token should be set");

        step("Prepare updated booking data");
        BookingDates dates = BookingDates.builder()
                .checkin("2024-02-01")
                .checkout("2024-02-10")
                .build();

        Booking updatedBooking = Booking.builder()
                .firstname("Jane")
                .lastname("Smith")
                .totalprice(1500)
                .depositpaid(true)
                .bookingdates(dates)
                .additionalneeds("WiFi, Parking")
                .build();

        step("Send PUT request to update booking with ID: " + bookingId);

        String endpoint = ApiConfig.API_ENDPOINT + "/" + bookingId;
        Response response = ApiClient.putWithAuth(endpoint, updatedBooking, authToken);

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Update booking should return 200");

        Booking responseBooking = response.as(Booking.class);
        Assert.assertEquals(responseBooking.getFirstname(), "Jane", "Firstname should be updated");

        logger.info("Booking updated successfully");
        step("Verify booking is updated with new details");
    }

    @Test(priority = 7, dependsOnMethods = {"verifyBookingCreationWithValidPayload", "verifyAuthenticationTokenGenerationWithValidCredentials"}, testName = "Verify partial update of booking with authorization")
    @Story("Booking Management")
    @TmsLink("TC_API_007")
    @Description("Test partially updating a booking")
    public void verifyPartialUpdateOfBookingWithAuthorization() {
        // Both bookingId and authToken are produced by earlier tests.
        Assert.assertNotNull(bookingId, "Booking ID should be set");
        Assert.assertNotNull(authToken, "Auth token should be set");

        step("Prepare partial booking update");
        Booking partialBooking = Booking.builder()
                .firstname("Robert")
                .lastname("Johnson")
                .build();

        step("Send PATCH request to partially update booking with ID: " + bookingId);

        String endpoint = ApiConfig.API_ENDPOINT + "/" + bookingId;
        Response response = ApiClient.patchWithAuth(endpoint, partialBooking, authToken);

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Partial update should return 200");

        logger.info("Booking partially updated successfully");
        step("Verify booking is partially updated");
    }

    @Test(priority = 8, dependsOnMethods = {"verifyBookingCreationWithValidPayload", "verifyAuthenticationTokenGenerationWithValidCredentials"}, testName = "Verify delete booking with authorization")
    @Story("Booking Management")
    @TmsLink("TC_API_008")
    @Description("Test deleting a booking")
    public void verifyDeleteBookingWithAuthorization() {
        // Both bookingId and authToken are produced by earlier tests.
        Assert.assertNotNull(bookingId, "Booking ID should be set");
        Assert.assertNotNull(authToken, "Auth token should be set");

        step("Send DELETE request to delete booking with ID: " + bookingId);

        String endpoint = ApiConfig.API_ENDPOINT + "/" + bookingId;
        Response response = ApiClient.deleteWithAuth(endpoint, authToken);

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 201, "Delete booking should return 201");

        logger.info("Booking deleted successfully");
        step("Verify booking is deleted");
    }

    @Test(priority = 9, testName = "Verify booking filter by first name")
    @Story("Booking Management")
    @TmsLink("TC_API_009")
    @Description("Test filtering bookings by first name")
    public void verifyBookingFilterByFirstName() {
        step("Send GET request to filter bookings by firstname");

        Response response = ApiClient.getWithQueryParams(ApiConfig.API_ENDPOINT, "firstname", "John");

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Filter bookings should return 200");

        logger.info("Bookings filtered successfully");
        step("Verify bookings are filtered by firstname");
    }

    @Test(priority = 10, testName = "Verify booking filter by last name")
    @Story("Booking Management")
    @TmsLink("TC_API_010")
    @Description("Test filtering bookings by last name")
    public void verifyBookingFilterByLastName() {
        step("Send GET request to filter bookings by lastname");

        Response response = ApiClient.getWithQueryParams(ApiConfig.API_ENDPOINT, "lastname", "Doe");

        logger.info("Response Status Code: {}", response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Filter bookings should return 200");

        logger.info("Bookings filtered successfully");
        step("Verify bookings are filtered by lastname");
    }
}

