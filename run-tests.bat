@echo off
REM Test Execution Script for Restful Booker API Test Project
REM Usage: run-tests.bat [option]

setlocal enabledelayedexpansion

echo.
echo ================================================
echo  Restful Booker API Test Project
echo ================================================
echo.

if "%1"=="" (
    echo Select an option:
    echo.
    echo 1. Run all tests
    echo 2. Run specific test class
    echo 3. Run specific test method
    echo 4. Generate Allure report
    echo 5. View Allure report
    echo 6. Clean build
    echo 7. Build project
    echo.
    set /p choice="Enter your choice [1-7]: "
) else (
    set choice=%1
)

if "!choice!"=="1" (
    echo Running all tests...
    mvn clean test
    echo.
    echo Tests completed! Results available in target/allure-results/
    pause
    goto end
)

if "!choice!"=="2" (
    set /p testClass="Enter test class name (e.g., BookingTests): "
    echo Running test class: !testClass!...
    mvn clean test -Dtest=!testClass!
    echo.
    echo Tests completed!
    pause
    goto end
)

if "!choice!"=="3" (
    set /p testClass="Enter test class name (e.g., BookingTests): "
    set /p testMethod="Enter test method name (e.g., testCreateBooking): "
    echo Running test: !testClass!#!testMethod!...
    mvn clean test -Dtest=!testClass!#!testMethod!
    echo.
    echo Test completed!
    pause
    goto end
)

if "!choice!"=="4" (
    echo Generating Allure report...
    mvn allure:report
    echo.
    echo Report generated in target/allure-report/
    pause
    goto end
)

if "!choice!"=="5" (
    echo Opening Allure report...
    mvn allure:serve
    goto end
)

if "!choice!"=="6" (
    echo Cleaning build...
    mvn clean
    echo Build cleaned!
    pause
    goto end
)

if "!choice!"=="7" (
    echo Building project...
    mvn clean install -DskipTests
    echo Build completed!
    pause
    goto end
)

echo Invalid choice!
pause

:end
endlocal

