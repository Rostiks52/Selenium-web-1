package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

 class CardOrderTest {

    private WebDriver driver;
    ChromeOptions options;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

        @Test
        void shouldTestV1() {
            driver.get("http://localhost:9999");
            List<WebElement> elements = driver.findElements(By.className("input__control"));
            elements.get(0).sendKeys("Владимир Ленин");
            elements.get(1).sendKeys("+79221234567");
            driver.findElement(By.className("checkbox__box")).click();
            driver.findElement(By.className("button")).click();
            String text = driver.findElement(By.tagName("p")).getText();
            assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

        }
}
