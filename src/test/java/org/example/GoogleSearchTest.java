package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleSearchTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        // Set up WebDriver path (assuming it's stored in resources)
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
    }

    @BeforeEach
    public void setupTest() {
        // Create new WebDriver instance
        driver = new ChromeDriver();
        // Maximize browser window
        driver.manage().window().maximize();
    }

    @Test
    public void testGoogleSearch() {
        // Navigate to Google.com
        driver.get("https://www.google.com");
        // Find the search field and enter text
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("JUnit 5");
        // Submit the search form
        searchField.submit();
        // Search results
        List<WebElement> searchResults = driver.findElements(By.cssSelector("div#search > div > div > div"));
        // Assert that the number of search results is greater than 0
        assertThat(searchResults.size())
                .as("Number of search results should be greater than 0")
                .isGreaterThan(0);
    }

    @AfterEach
    public void teardown() {
        // Close the browser
        driver.quit();
    }

}

