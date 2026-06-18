package com.restfulbooker.tests;

import io.restassured.RestAssured;
import io.qameta.allure.Allure;
import org.testng.annotations.BeforeSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.restfulbooker.config.ApiConfig;

/**
 * Base test class with common setup
 */
public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        logger.info("Setting up REST Assured configuration");
        RestAssured.baseURI = ApiConfig.BASE_URL;
        RestAssured.requestSpecification = RestAssured.given()
                .contentType("application/json")
                .accept("application/json");
    }

    /**
     * Add step to Allure report
     */
    protected void step(String stepName) {
        Allure.step(stepName);
        logger.info("Step: {}", stepName);
    }

    /**
     * Add step with description to Allure report
     */
    protected void step(String stepName, String description) {
        Allure.step(stepName + " - " + description);
        logger.info("Step: {} - {}", stepName, description);
    }
}


