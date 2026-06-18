package com.restfulbooker.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for making REST API calls
 */
public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    /**
     * Creates the common request setup used by every API call.
     */
    private static RequestSpecification baseRequest() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    /**
     * Same as baseRequest(), but adds auth cookie for secured endpoints.
     */
    private static RequestSpecification baseRequestWithAuth(String token) {
        return baseRequest().header("Cookie", "token=" + token);
    }

    /**
     * Keeps response extraction in one place for readability.
     */
    private static Response extractResponse(Response response) {
        return response.then().extract().response();
    }

    /**
     * Make a GET request
     */
    public static Response get(String endpoint) {
        logger.info("Making GET request to: {}", endpoint);
        return extractResponse(baseRequest().when().get(endpoint));
    }

    /**
     * Make a GET request with path parameter
     */
    public static Response getWithPathParam(String endpoint, String pathParam, Object value) {
        logger.info("Making GET request to: {} with pathParam: {} = {}", endpoint, pathParam, value);
        return extractResponse(baseRequest()
                .pathParam(pathParam, value)
                .when()
                .get(endpoint));
    }

    /**
     * Make a GET request with query parameters
     */
    public static Response getWithQueryParams(String endpoint, String paramName, Object paramValue) {
        logger.info("Making GET request to: {} with queryParam: {} = {}", endpoint, paramName, paramValue);
        return extractResponse(baseRequest()
                .queryParam(paramName, paramValue)
                .when()
                .get(endpoint));
    }

    /**
     * Make a POST request
     */
    public static Response post(String endpoint, Object body) {
        logger.info("Making POST request to: {} with body: {}", endpoint, body);
        return extractResponse(baseRequest()
                .body(body)
                .when()
                .post(endpoint));
    }

    /**
     * Make a POST request with authentication token
     */
    public static Response postWithAuth(String endpoint, Object body, String token) {
        logger.info("Making POST request to: {} with authentication token", endpoint);
        return extractResponse(baseRequestWithAuth(token)
                .body(body)
                .when()
                .post(endpoint));
    }

    /**
     * Make a PUT request
     */
    public static Response put(String endpoint, Object body) {
        logger.info("Making PUT request to: {} with body: {}", endpoint, body);
        return extractResponse(baseRequest()
                .body(body)
                .when()
                .put(endpoint));
    }

    /**
     * Make a PUT request with authentication token
     */
    public static Response putWithAuth(String endpoint, Object body, String token) {
        logger.info("Making PUT request to: {} with authentication token", endpoint);
        return extractResponse(baseRequestWithAuth(token)
                .body(body)
                .when()
                .put(endpoint));
    }

    /**
     * Make a PATCH request
     */
    public static Response patch(String endpoint, Object body) {
        logger.info("Making PATCH request to: {} with body: {}", endpoint, body);
        return extractResponse(baseRequest()
                .body(body)
                .when()
                .patch(endpoint));
    }

    /**
     * Make a PATCH request with authentication token
     */
    public static Response patchWithAuth(String endpoint, Object body, String token) {
        logger.info("Making PATCH request to: {} with authentication token", endpoint);
        return extractResponse(baseRequestWithAuth(token)
                .body(body)
                .when()
                .patch(endpoint));
    }

    /**
     * Make a DELETE request
     */
    public static Response delete(String endpoint) {
        logger.info("Making DELETE request to: {}", endpoint);
        return extractResponse(baseRequest().when().delete(endpoint));
    }

    /**
     * Make a DELETE request with authentication token
     */
    public static Response deleteWithAuth(String endpoint, String token) {
        logger.info("Making DELETE request to: {} with authentication token", endpoint);
        return extractResponse(baseRequestWithAuth(token).when().delete(endpoint));
    }
}

