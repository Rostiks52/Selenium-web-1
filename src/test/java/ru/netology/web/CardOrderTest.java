package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static java.lang.System.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;

import java.util.List;

class CardOrderTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldReturnHappyPath() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Владимир Ленин");
        elements.get(1).sendKeys("+79221234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.tagName("p")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    @Test
    void shouldValidateIfAllFieldsAreEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String text1 = driver.findElement(cssSelector("[data-test-id=name] [class=input__sub]")).getText();
        assertEquals("Поле обязательно для заполнения", text1.trim());
        String text2 = driver.findElement(cssSelector("[data-test-id=phone] [class=input__sub]")).getText();
        assertEquals("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.", text2.trim());
    }

    @Test
    void shouldNoticeIfFieldNameIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79221234567");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String text = driver.findElement(cssSelector("[data-test-id=name] [class=input__sub]")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNoticeIfFieldTelIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Владимир Ленин");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String text = driver.findElement(cssSelector("[data-test-id=phone] [class=input__sub]")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNoticeIfFieldNameContainsLatinSymbols() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Vladimir Lenin");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79221234567");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String text = driver.findElement(cssSelector("[data-test-id=name] [class=input__sub]")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldNoticeIfFieldNameContainsDigits() {
        driver.get("http://localhost:9999");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("9876541");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79221234567");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector("button")).click();
        String text = driver.findElement(cssSelector("[data-test-id=name] [class=input__sub]")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

}
