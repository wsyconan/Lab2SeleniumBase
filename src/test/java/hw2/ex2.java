package hw2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex2 {
    WebDriver webDriver;
    WebElement webElement;
    SoftAssert softAssert;
    String url, str;
    String[] serviceOptionsTop, serviceOptionsSide;
    List<WebElement> list;
    int i;
    Pattern patternWater, patternWind, patternSelen, patternYellow, patternWaterFalse, patternWindFalse;
    Matcher matcher;

    @Test
    public void test() {
        // Step 1
        webDriver.get(url);

        // Step 2
        softAssert.assertEquals(webDriver.getTitle(), "Home Page");
        webDriver.manage().window().maximize();

        // Step 3
        webDriver.findElement((By.cssSelector("li.dropdown.uui-profile-menu"))).click();
        webDriver.findElement(By.cssSelector("#name")).sendKeys("Roman");
        webDriver.findElement(By.cssSelector("#password")).sendKeys("Jdi1234");
        webDriver.findElement(By.cssSelector("#login-button")).click();

        // Step 4
        String userName = webDriver.findElement(By.cssSelector("#user-name")).getText();
        softAssert.assertEquals(userName, "ROMAN IOVLEV");

        // Step 5
        webDriver.findElement(By.cssSelector("li.dropdown>a.dropdown-toggle")).click();
        list = webDriver.findElements(By.cssSelector("ul.dropdown-menu>li>a"));
        Assert.assertFalse(list.isEmpty());
        i = 0;
        for (WebElement we : list) {
            softAssert.assertEquals(we.getText(), serviceOptionsTop[i++].toUpperCase(Locale.ROOT));
        }

        // Step 6
        webDriver.findElement(By.cssSelector("li.menu-title[index='3']")).click();
        list = webDriver.findElements(By.cssSelector("li.menu-title[index='3'] li[index]>a"));
        Assert.assertFalse(list.isEmpty(), "Step 6 list was empty.");
        i = 0;
        for (WebElement we : list) {
            softAssert.assertEquals(we.getText(), serviceOptionsSide[i++]);
        }

        // Step 7
        webDriver.findElement(By.cssSelector("ul.sub>li[index='8']")).click();

        // Step 8
        list = webDriver.findElements(By.cssSelector("input[type='checkbox']"));
        softAssert.assertEquals(list.size(), 4);
        list = webDriver.findElements(By.cssSelector("input[type='radio']"));
        softAssert.assertEquals(list.size(), 4);
        list = webDriver.findElements(By.cssSelector("select.uui-form-element"));
        softAssert.assertEquals(list.size(), 1);
        webElement = webDriver.findElement(By.cssSelector("button.uui-button"));
        softAssert.assertTrue(webElement.isEnabled());
        webElement = webDriver.findElement(By.cssSelector("input.uui-button"));
        softAssert.assertTrue(webElement.isEnabled());

        // Step 9
        webElement = webDriver.findElement(By.cssSelector("#mCSB_2"));
        softAssert.assertTrue(webElement.isDisplayed());

        // Step 10
        webElement = webDriver.findElement(By.cssSelector("#mCSB_1"));
        softAssert.assertTrue(webElement.isDisplayed());

        // Step 11
        list = webDriver.findElements(By.cssSelector("label.label-checkbox"));
        Assert.assertFalse(list.isEmpty());
        for (WebElement we : list) {
            if (we.getText().equals("Water") || we.getText().equals("Wind")) {
                we.click();
            }
        }

        // Step 12
        list = webDriver.findElements(By.cssSelector("ul.panel-body-list.logs li"));
        Assert.assertFalse(list.isEmpty());
        softAssert.assertTrue(patternWind.matcher(list.get(0).getText()).matches());
        softAssert.assertTrue(patternWater.matcher(list.get(1).getText()).matches());

        // Step 13
        list = webDriver.findElements(By.cssSelector("label.label-radio"));
        Assert.assertFalse(list.isEmpty());
        for (WebElement we : list) {
            if (we.getText().equals("Selen")) {
                we.click();
            }
        }

        // Step 14
        list = webDriver.findElements(By.cssSelector("ul.panel-body-list.logs li"));
        Assert.assertFalse(list.isEmpty());
        softAssert.assertTrue(patternSelen.matcher(list.get(0).getText()).matches());

        // Step 15
        webDriver.findElement(By.cssSelector("select.uui-form-element")).click();
        list = webDriver.findElements(By.cssSelector("select.uui-form-element>option"));
        for (WebElement we : list) {
            if (we.getText().equals("Yellow")) {
                we.click();
            }
        }

        // Step 16
        list = webDriver.findElements(By.cssSelector("ul.panel-body-list.logs li"));
        Assert.assertFalse(list.isEmpty());
        softAssert.assertTrue(patternYellow.matcher(list.get(0).getText()).matches());

        // Step 17
        list = webDriver.findElements(By.cssSelector("label.label-checkbox"));
        Assert.assertFalse(list.isEmpty());
        for (WebElement we : list) {
            if (we.getText().equals("Water") || we.getText().equals("Wind")) {
                we.click();
            }
        }

        // Step 18
        list = webDriver.findElements(By.cssSelector("ul.panel-body-list.logs li"));
        Assert.assertFalse(list.isEmpty());
        softAssert.assertTrue(patternWindFalse.matcher(list.get(0).getText()).matches());
        softAssert.assertTrue(patternWaterFalse.matcher(list.get(1).getText()).matches());

        softAssert.assertAll();
        webDriver.close();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeTest
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        softAssert = new SoftAssert();
        url = "https://jdi-testing.github.io/jdi-light/index.html";
        serviceOptionsSide = new String[]{
                "Support", "Dates", "Complex Table", "Simple Table", "Search",
                "User Table", "Table with pages", "Different elements", "Performance"
        };
        serviceOptionsTop = new String[]{
                "Support", "Dates", "Search", "Complex Table", "Simple Table",
                "User Table", "Table with pages", "Different elements", "Performance"
        };
        patternWater = Pattern.compile("[0-9]+:[0-9]+:[0-9]+ Water: condition changed to true");
        patternWind = Pattern.compile("[0-9]+:[0-9]+:[0-9]+ Wind: condition changed to true");
        patternSelen = Pattern.compile("[0-9]+:[0-9]+:[0-9]+ metal: value changed to Selen");
        patternYellow = Pattern.compile("[0-9]+:[0-9]+:[0-9]+ Colors: value changed to Yellow");
        patternWaterFalse = Pattern.compile("[0-9]+:[0-9]+:[0-9]+ Water: condition changed to false");
        patternWindFalse = Pattern.compile("[0-9]+:[0-9]+:[0-9]+ Wind: condition changed to false");
    }

    @AfterTest
    public void afterTest() { webDriver.quit(); }
}