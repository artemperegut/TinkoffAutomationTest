package com.example;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class YandexMarketTest {

    @Test
    public void startDriver() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C://Users/BloodMaster/Downloads/!Code/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

        //Перейти по адресу https://market.yandex.ru/
        driver.get("https://market.yandex.ru");

        //Нажать по ссылке "Каталог"
        driver.findElement(By.cssSelector("li[data-name='catalog']")).click();

        //Перейти по в раздел "Электроника" -> "Мобильные телефоны"
        List<WebElement> listElements = driver.findElements(By.cssSelector(".catalog-simple__list-item"));
        for(WebElement element : listElements) {
            if(element.getText().contains("Мобильные телефоны")) {
                element.click();
                break;
            }
        }

        //Нажать по "расширенный поиск" блока выбора по параметрам
        driver.findElement(By.xpath("//*[contains(text(),'Расширенный поиск')]")).click();

        //Ввести Цену, руб. "от" значение 5125
        driver.findElement(By.name("glf-pricefrom-var")).sendKeys("5125");

        //Ввести Цену, руб. "до" значение 10123
        driver.findElement(By.name("glf-priceto-var")).sendKeys("10123");

        //Кликнуть на чекбокс "В продаже"
        driver.findElement(By.cssSelector("label[for='glf-onstock-select']")).click();

        //Раскрыть блок "Тип"
        driver.findElement(By.xpath("//*[contains(text(), 'Тип')]")).click();

        //Кликнуть на селектбокс "смартфон"
        driver.findElement(By.cssSelector("label[for='glf-2142542726-1195192805']")).click();
        //driver.findElement(By.xpath("label[contains(@class, 'checkbox__label') and text()='смартфон']")).click();

        //Кликнуть на селектбокс "Android"
        driver.findElement(By.id("glf-2134007594-select")).click();
        //driver.findElement(By.xpath("label[contains(@class, 'checkbox__label') and text()='Android']")).click();

        Thread.sleep(2000); //иначе StaleElementReferenceException

        //Случано выбрать 3 устройства из представленных на странице, имеющих рейтинг от "3,5" до "4,5", и вывести в лог информацию в формате "номер девайса на странице - наименование девайса - стоимость девайса (от-до)"
        List<WebElement> listMobile = driver.findElements(By.className("n-snippet-card"));
        List<String> a = new ArrayList<String>();
        int number = 0;
        for(WebElement element : listMobile) {
            Double raiting = Double.parseDouble(element.findElement(By.className("rating")).getText());
            String mobileName = element.findElement(By.className("snippet-card__header-text")).getText();
            String mobilePriceFrom = element.findElement(By.className("snippet-card__price")).getText();
            String mobilePriceTo = element.findElement(By.className("snippet-card__subprice")).getText();
            number++;

            if(raiting>=3.5 && raiting<=4.5) {
                a.add(number + " - " + mobileName + " - " + mobilePriceFrom  + " " + mobilePriceTo);
            }
        }

        for(int i = 0; i <3; i++) {
            Random random = new Random();
            System.out.println(a.get(random.nextInt(a.size())));
        }

        driver.close();
        driver.quit();
    }
}
