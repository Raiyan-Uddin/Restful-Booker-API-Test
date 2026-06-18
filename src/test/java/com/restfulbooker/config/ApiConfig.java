package com.restfulbooker.config;

/**
 * Configuration class for API endpoint and properties
 */
public class ApiConfig {

    public static final String BASE_URL = "https://restful-booker.herokuapp.com";
    public static final String API_ENDPOINT = BASE_URL + "/booking";
    public static final String AUTH_ENDPOINT = BASE_URL + "/auth";
    public static final String PING_ENDPOINT = BASE_URL + "/ping";

    // Default timeout values (in seconds)
    public static final int REQUEST_TIMEOUT = 10;
    public static final int RESPONSE_TIMEOUT = 10;

    // Headers
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String ACCEPT_JSON = "application/json";
}

