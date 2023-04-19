package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Пример использования самых базовых методов библиотеки Selenium.
 */
public class GoogleSearchTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        // Помещаем в переменные окружения путь до драйвера. Set up WebDriver path.
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
    }

    @BeforeEach
    public void setupTest() {
        // Создаём экземпляр драйвера. Create new WebDriver instance.
        driver = new ChromeDriver();
        // Растягиваем окно браузера на весь экран. Maximize browser window.
        driver.manage().window().maximize();
        // Настройка неявных ожиданий. Implicit wait.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testGoogleSearch() {
        // Навигация на Google.com. Navigate to Google.com.
        driver.get("https://www.google.com");
        // Поиск поля ввода, вписываем текст и жмём Enter (Submit).
        // Find the search field, enter text and submit.
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("JUnit 5");
        searchField.submit();
        // Использованиех явных ожиданий. Explicit wait.
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#search > div > div > div")));
        // Результаты поиска на странице. Search results.
        List<WebElement> searchResults = driver.findElements(By.cssSelector("div#search > div > div > div"));
        // Проверка, что количество результатов больше 0.
        // Assert that the number of search results is greater than 0.
        assertThat(searchResults.size())
                .as("Number of search results should be greater than 0")
                .isGreaterThan(0);
    }

    @Test
    public void testGoogleSearchWithFluentWait() {
        // Навигация на Google.com. Navigate to Google.com.
        driver.get("https://www.google.com");
        // Поиск поля ввода, вписываем текст и жмём Enter (Submit).
        // Find the search field, enter text and submit.
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("JUnit 5");
        searchField.submit();
        // Использованиех гибких ожиданий. FluentWait.
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)
                .until(driver -> driver.findElement(By.cssSelector("div#search > div > div > div")));
        // Результаты поиска на странице. Search results.
        List<WebElement> searchResults = driver.findElements(By.cssSelector("div#search > div > div > div"));
        // Проверка, что количество результатов больше 0.
        // Assert that the number of search results is greater than 0.
        assertThat(searchResults.size())
                .as("Number of search results should be greater than 0")
                .isGreaterThan(0);
    }

    @AfterEach
    public void teardown() {
        // Закрываем все окна брайзера и процесс драйвера. Quit the browser.
        driver.quit();
        // Закрываем окно брайзера. Close the browser.
        // driver.quit();
    }

}

