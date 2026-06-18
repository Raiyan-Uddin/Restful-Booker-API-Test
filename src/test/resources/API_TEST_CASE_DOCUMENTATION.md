# Restful Booker API Test Case Documentation

## Document Info
- Project: Restful Booker API Automation
- Base URL: `https://restful-booker.herokuapp.com`
- API Doc: `https://restful-booker.herokuapp.com/apidoc/index.html`
- Last Updated: 2026-06-18
- Owner: QA Automation

## Test Case Template
Use this structure for adding new cases:
- Test ID
- Subject
- Module
- Priority
- Preconditions
- Test Data
- Steps
- Expected Result
- Postcondition
- Automation Status

---

## Test Cases

### TC_API_001
- **Subject:** Verify API health check endpoint (`/ping`)
- **Module:** Health Check
- **Priority:** High
- **Preconditions:** API service is reachable.
- **Test Data:** None
- **Steps:**
  1. Send `GET /ping` request.
- **Expected Result:**
  - HTTP status is `201`.
  - Response is returned without server error.
- **Postcondition:** None
- **Automation Status:** Automated (`BookingTests.verifyApiHealthCheckEndpoint`)

### TC_API_002
- **Subject:** Verify authentication token generation with valid credentials
- **Module:** Authentication
- **Priority:** High
- **Preconditions:** API service is reachable.
- **Test Data:**
  - `username=admin`
  - `password=password123`
- **Steps:**
  1. Send `POST /auth` with valid JSON body.
- **Expected Result:**
  - HTTP status is `200`.
  - Response contains non-empty `token`.
- **Postcondition:** Token is available for authorized APIs.
- **Automation Status:** Automated (`BookingTests.verifyAuthenticationTokenGenerationWithValidCredentials`)

### TC_API_003
- **Subject:** Verify booking creation with valid payload
- **Module:** Booking - Create
- **Priority:** High
- **Preconditions:** API service is reachable.
- **Test Data:**
  - `firstname=John`
  - `lastname=Doe`
  - `totalprice=1000`
  - `depositpaid=true`
  - `bookingdates={checkin: 2024-01-01, checkout: 2024-01-07}`
  - `additionalneeds=WiFi, Breakfast`
- **Steps:**
  1. Send `POST /booking` with valid booking JSON body.
- **Expected Result:**
  - HTTP status is `200`.
  - Response contains `bookingid`.
  - Response booking details match request values.
- **Postcondition:** A booking record is created.
- **Automation Status:** Automated with guarded skip on non-200 (`BookingTests.verifyBookingCreationWithValidPayload`)

### TC_API_004
- **Subject:** Verify retrieve all bookings
- **Module:** Booking - Read
- **Priority:** Medium
- **Preconditions:** API service is reachable.
- **Test Data:** None
- **Steps:**
  1. Send `GET /booking`.
- **Expected Result:**
  - HTTP status is `200`.
  - Response is a non-empty list of booking IDs.
- **Postcondition:** None
- **Automation Status:** Automated (`BookingTests.verifyRetrieveAllBookings`)

### TC_API_005
- **Subject:** Verify retrieve booking by booking ID
- **Module:** Booking - Read
- **Priority:** High
- **Preconditions:**
  - A valid booking exists.
  - Booking ID is available.
- **Test Data:** `bookingId=<created_booking_id>`
- **Steps:**
  1. Send `GET /booking/{id}`.
- **Expected Result:**
  - HTTP status is `200`.
  - Response body contains booking details for the given ID.
- **Postcondition:** None
- **Automation Status:** Automated (`BookingTests.verifyRetrieveBookingByBookingId`)

### TC_API_006
- **Subject:** Verify full update of booking with authorization
- **Module:** Booking - Update (PUT)
- **Priority:** High
- **Preconditions:**
  - Valid auth token is available.
  - Valid booking ID exists.
- **Test Data:**
  - Updated booking payload
  - `token=<auth_token>`
- **Steps:**
  1. Send `PUT /booking/{id}` with updated payload and token cookie.
- **Expected Result:**
  - HTTP status is `200`.
  - Response reflects updated fields.
- **Postcondition:** Booking data is updated.
- **Automation Status:** Automated (`BookingTests.verifyFullUpdateOfBookingWithAuthorization`)

### TC_API_007
- **Subject:** Verify partial update of booking with authorization
- **Module:** Booking - Update (PATCH)
- **Priority:** Medium
- **Preconditions:**
  - Valid auth token is available.
  - Valid booking ID exists.
- **Test Data:**
  - Partial payload (`firstname`, `lastname`)
  - `token=<auth_token>`
- **Steps:**
  1. Send `PATCH /booking/{id}` with partial payload and token cookie.
- **Expected Result:**
  - HTTP status is `200`.
  - Only target fields are updated.
- **Postcondition:** Booking record partially updated.
- **Automation Status:** Automated (`BookingTests.verifyPartialUpdateOfBookingWithAuthorization`)

### TC_API_008
- **Subject:** Verify delete booking with authorization
- **Module:** Booking - Delete
- **Priority:** High
- **Preconditions:**
  - Valid auth token is available.
  - Valid booking ID exists.
- **Test Data:**
  - `bookingId=<created_booking_id>`
  - `token=<auth_token>`
- **Steps:**
  1. Send `DELETE /booking/{id}` with token cookie.
- **Expected Result:**
  - HTTP status is `201`.
  - Booking is removed from system.
- **Postcondition:** Booking no longer retrievable.
- **Automation Status:** Automated (`BookingTests.verifyDeleteBookingWithAuthorization`)

### TC_API_009
- **Subject:** Verify booking filter by first name
- **Module:** Booking - Search
- **Priority:** Low
- **Preconditions:** API service is reachable.
- **Test Data:** `firstname=John`
- **Steps:**
  1. Send `GET /booking?firstname=John`.
- **Expected Result:**
  - HTTP status is `200`.
  - Response returns matching booking IDs list.
- **Postcondition:** None
- **Automation Status:** Automated (`BookingTests.verifyBookingFilterByFirstName`)

### TC_API_010
- **Subject:** Verify booking filter by last name
- **Module:** Booking - Search
- **Priority:** Low
- **Preconditions:** API service is reachable.
- **Test Data:** `lastname=Doe`
- **Steps:**
  1. Send `GET /booking?lastname=Doe`.
- **Expected Result:**
  - HTTP status is `200`.
  - Response returns matching booking IDs list.
- **Postcondition:** None
- **Automation Status:** Automated (`BookingTests.verifyBookingFilterByLastName`)

---

## Suggested Additional Negative Test Cases (Manual/To Automate)

### TC_API_011
- **Subject:** Authentication with invalid credentials
- **Module:** Authentication
- **Priority:** High
- **Preconditions:** API service is reachable.
- **Test Data:** Invalid username/password
- **Steps:**
  1. Send `POST /auth` with invalid credentials.
- **Expected Result:**
  - HTTP status indicates auth failure as per API behavior.
  - Error reason is returned.
- **Postcondition:** No valid token generated.
- **Automation Status:** Not Automated

### TC_API_012
- **Subject:** Update booking without token
- **Module:** Booking - Update Security
- **Priority:** High
- **Preconditions:** Valid booking ID exists.
- **Test Data:** Update payload without token
- **Steps:**
  1. Send `PUT /booking/{id}` without auth cookie.
- **Expected Result:**
  - Unauthorized response as per API behavior.
- **Postcondition:** No change in booking data.
- **Automation Status:** Not Automated

### TC_API_013
- **Subject:** Delete booking with invalid token
- **Module:** Booking - Delete Security
- **Priority:** High
- **Preconditions:** Valid booking ID exists.
- **Test Data:** Invalid token
- **Steps:**
  1. Send `DELETE /booking/{id}` with invalid token.
- **Expected Result:**
  - Unauthorized response as per API behavior.
- **Postcondition:** Booking remains unchanged.
- **Automation Status:** Not Automated

