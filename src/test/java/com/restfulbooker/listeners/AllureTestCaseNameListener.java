package com.restfulbooker.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.TmsLink;
import java.lang.reflect.Method;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

/**
 * Keeps Allure test names aligned with test case documentation format:
 * "TC_API_XXX - Subject".
 */
public class AllureTestCaseNameListener implements ITestListener {

    private static final String TEST_CASE_CONTEXT_KEY = "testCase";
    private static final Logger logger = LoggerFactory.getLogger(AllureTestCaseNameListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String displayName = resolveDisplayName(result);
        setCurrentTestCase(displayName);
        updateAllureTestName(displayName);
        logger.info("=== Test Case: {} ===", displayName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        clearCurrentTestCase();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        clearCurrentTestCase();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String displayName = resolveDisplayName(result);
        setCurrentTestCase(displayName);
        updateAllureTestName(displayName);
        logger.info("=== Test Case Skipped: {} ===", displayName);
        clearCurrentTestCase();
    }

    // Stores test name in Log4j ThreadContext so the log pattern can print it.
    private void setCurrentTestCase(String displayName) {
        ThreadContext.put(TEST_CASE_CONTEXT_KEY, displayName);
    }

    // Removes thread-local value to avoid leaking one test's name into another test.
    private void clearCurrentTestCase() {
        ThreadContext.remove(TEST_CASE_CONTEXT_KEY);
    }

    // Replaces the default method name in Allure with "TC_API_XXX - Subject".
    private void updateAllureTestName(String displayName) {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(displayName));
    }

    private String resolveDisplayName(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method == null) {
            return result.getName();
        }

        Test test = method.getAnnotation(Test.class);
        TmsLink tmsLink = method.getAnnotation(TmsLink.class);

        String subject = method.getName();
        if (test != null && test.testName() != null && !test.testName().trim().isEmpty()) {
            subject = test.testName().trim();
        }

        if (tmsLink != null && tmsLink.value() != null && !tmsLink.value().trim().isEmpty()) {
            return tmsLink.value().trim() + " - " + subject;
        }

        return subject;
    }
}

