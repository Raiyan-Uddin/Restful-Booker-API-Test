package com.restfulbooker.tests;

import com.microsoft.playwright.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Example Playwright test for UI automation (optional)
 * This demonstrates how to combine API testing with UI testing
 */
@Feature("Restful Booker UI")
public class PlaywrightExample {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeClass
    public void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterClass
    public void tearDownBrowser() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    @Description("Example test to navigate to Restful Booker API docs")
    @Step("Navigate to API documentation")
    public void testNavigateToApiDocs() {
        page.navigate("https://restful-booker.herokuapp.com/apidoc/index.html");

        // Verify page title contains expected text
        String title = page.title();
        Assert.assertTrue(title.contains("API"), "Page title should contain 'API'");
    }

    @Test(dependsOnMethods = "testNavigateToApiDocs")
    @Description("Example test to verify API endpoints are displayed")
    @Step("Verify API endpoints are visible")
    public void testVerifyApiEndpoints() {
        // Wait a moment for page to load
        page.waitForTimeout(2000);

        // Check if certain text is visible
        boolean isVisible = page.isVisible("text=/GET/");
        Assert.assertTrue(isVisible, "GET endpoint should be visible");
    }
}



