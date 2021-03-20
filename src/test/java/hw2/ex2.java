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
    String url, username, password;
    String[] serviceOptionsTop, serviceOptionsSide;
    List<WebElement> list;
    int i;
    Pattern patternWater, patternWind, patternSelen, patternYellow, patternWaterFalse, patternWindFalse;

    @Test
    public void test() {
        // Step 1: Open test site by URL
        webDriver.get(url);

        // Step 2: Assert Browser title
        softAssert.assertEquals(webDriver.getTitle(), "Home Page");
        webDriver.manage().window().maximize();

        // Step 3: Perform login
        webDriver.findElement((By.cssSelector("li.dropdown.uui-profile-menu"))).click();
        webDriver.findElement(By.cssSelector("#name")).sendKeys(username);
        webDriver.findElement(By.cssSelector("#password")).sendKeys(password);
        webDriver.findElement(By.cssSelector("#login-button")).click();

        // Step 4: Assert User name in the left-top side of screen that user is loggined
        String userName = webDriver.findElement(By.cssSelector("#user-name")).getText();
        softAssert.assertEquals(userName, "ROMAN IOVLEV");

        // Step 5: Click on "Service" subcategory in the header
        webDriver.findElement(By.cssSelector("li.dropdown>a.dropdown-toggle")).click();
        list = webDriver.findElements(By.cssSelector("ul.dropdown-menu>li>a"));
        Assert.assertFalse(list.isEmpty());
        // Check the drop down contains options
        i = 0;
        for (WebElement we : list) {
            softAssert.assertEquals(we.getText(), serviceOptionsTop[i++].toUpperCase(Locale.ROOT));
        }

        // Step 6: Click on Service subcategory in the left section
        webDriver.findElement(By.cssSelector("li.menu-title[index='3']")).click();
        list = webDriver.findElements(By.cssSelector("li.menu-title[index='3'] li[index]>a"));
        Assert.assertFalse(list.isEmpty(), "Step 6 list was empty.");
        // Check that drop down contains options
        i = 0;
        for (WebElement we : list) {
            softAssert.assertEquals(we.getText(), serviceOptionsSide[i++]);
        }

        // Step 7: Open through the header menu "Service" -> "Different elements" Page
        webDriver.findElement(By.cssSelector("ul.sub>li[index='8']")).click();

        // Step 8: Check interface on Different elements page
        // 4 checkboxes
        list = webDriver.findElements(By.cssSelector("input[type='checkbox']"));
        softAssert.assertEquals(list.size(), 4);
        // 4 radios
        list = webDriver.findElements(By.cssSelector("input[type='radio']"));
        softAssert.assertEquals(list.size(), 4);
        // 1 dropdown
        list = webDriver.findElements(By.cssSelector("select.uui-form-element"));
        softAssert.assertEquals(list.size(), 1);
        // 2 buttons
        webElement = webDriver.findElement(By.cssSelector("button.uui-button"));
        softAssert.assertTrue(webElement.isEnabled());
        webElement = webDriver.findElement(By.cssSelector("input.uui-button"));
        softAssert.assertTrue(webElement.isEnabled());

        // Step 9: Assert that there is Right Section
        webElement = webDriver.findElement(By.cssSelector("#mCSB_2"));
        softAssert.assertTrue(webElement.isDisplayed());

        // Step 10: Assert that there is Left Section
        webElement = webDriver.findElement(By.cssSelector("#mCSB_1"));
        softAssert.assertTrue(webElement.isDisplayed());

        // Step 11: Select checkboxes "Water" and "Wind"
        list = webDriver.findElements(By.cssSelector("label.label-checkbox"));
        Assert.assertFalse(list.isEmpty());
        for (WebElement we : list) {
            if (we.getText().equals("Water") || we.getText().equals("Wind")) {
                we.click();
            }
        }

        // Step 12: Assert that for each checkbox there is an individual log row
        //         and value is corresponded to the status of checkbox.
        list = webDriver.findElements(By.cssSelector("ul.panel-body-list.logs li"));
        Assert.assertFalse(list.isEmpty());
        softAssert.assertTrue(patternWind.matcher(list.get(0).getText()).matches());
        softAssert.assertTrue(patternWater.matcher(list.get(1).getText()).matches());

        // Step 13: Select radio "Selen"
        list = webDriver.findElements(By.cssSelector("label.label-radio"));
        Assert.assertFalse(list.isEmpty());
        for (WebElement we : list) {
            if (we.getText().equals("Selen")) {
                we.click();
            }
        }

        // Step 14: Assert that for radiobutton there is a log row
        //         and value is corresponded to the status of radiobutton.
        list = webDriver.findElements(By.cssSelector("ul.panel-body-list.logs li"));
        Assert.assertFalse(list.isEmpty());
        softAssert.assertTrue(patternSelen.matcher(list.get(0).getText()).matches());

        // Step 15: Select option "Yellow" in dropdown
        webDriver.findElement(By.cssSelector("select.uui-form-element")).click();
        list = webDriver.findElements(By.cssSelector("select.uui-form-element>option"));
        for (WebElement we : list) {
            if (we.getText().equals("Yellow")) {
                we.click();
            }
        }

        // Step 16: Assert that for dropdown there is a log row
        //         and value is corresponded to the selected value.
        list = webDriver.findElements(By.cssSelector("ul.panel-body-list.logs li"));
        Assert.assertFalse(list.isEmpty());
        softAssert.assertTrue(patternYellow.matcher(list.get(0).getText()).matches());

        // Step 17: Unselect and assert checkboxes "Water" and "Wind"
        list = webDriver.findElements(By.cssSelector("label.label-checkbox"));
        Assert.assertFalse(list.isEmpty());
        for (WebElement we : list) {
            if (we.getText().equals("Water") || we.getText().equals("Wind")) {
                we.click();
            }
        }

        // Step 18: Assert that for each checkbox there is an individual log row
        //         and value is corresponded to the status of checkbox.
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
        username = ("Roman");
        password = ("Jdi1234");
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