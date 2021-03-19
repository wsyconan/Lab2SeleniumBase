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

import java.util.List;
import java.util.Locale;

/**
 * Unit test for simple App.
 */
public class ex1 {
    WebDriver webDriver;
    WebElement webElement;
    String url, str, mainTitle, jdiText, subHeader, jdiUrl;
    int i;
    List<WebElement> list;
    String[] headers, textUnderPic;

    @Test
    public void test() {
        // Step 1
        webDriver.get(url);

        // Step 2
        Assert.assertEquals(webDriver.getTitle(), "Home Page");
        webDriver.manage().window().maximize();

        // Step 3
        webDriver.findElement((By.cssSelector("li.dropdown.uui-profile-menu"))).click();
        webDriver.findElement(By.cssSelector("#name")).sendKeys("Roman");
        webDriver.findElement(By.cssSelector("#password")).sendKeys("Jdi1234");
        webDriver.findElement(By.cssSelector("#login-button")).click();

        // Step 4
        String userName = webDriver.findElement(By.cssSelector("#user-name")).getText();
        Assert.assertEquals(userName, "ROMAN IOVLEV");

        // Step 5
        Assert.assertEquals(webDriver.getTitle(), "Home Page");

        // Step 6
        list = webDriver.findElements(By.cssSelector("ul.uui-navigation.nav.navbar-nav.m-l8 a"));
        i = 0;
        for (WebElement we : list) {
            if (we.isDisplayed() && we.isEnabled()) {
                Assert.assertEquals(headers[i], we.getText());
                i++;
            }
        }

        // Step 7
        list = webDriver.findElements(By.cssSelector(".benefit-icon"));
        Assert.assertEquals(list.size(), 4);
        for (WebElement we : list) {
            Assert.assertTrue(we.isDisplayed());
        }

        // Step 8
        list = webDriver.findElements(By.cssSelector(".benefit-icon+.benefit-txt"));
        Assert.assertEquals(list.size(), 4);
        i = 0;
        for (WebElement we : list) {
            Assert.assertEquals(we.getText(), textUnderPic[i]);
            i++;
        }

        // Step 9
        webElement = webDriver.findElement(By.cssSelector("h3.main-title.text-center"));
        Assert.assertTrue(webElement.isDisplayed());
        str = webElement.getText();
        Assert.assertEquals(str, mainTitle);

        webElement = webDriver.findElement(By.cssSelector("p.main-txt.text-center"));
        Assert.assertTrue(webElement.isDisplayed());
        str = webElement.getText();
        Assert.assertEquals(str, jdiText.toUpperCase(Locale.ROOT));

        // Step 10
        list = webDriver.findElements(By.cssSelector("iframe"));
        Assert.assertFalse(list.isEmpty());

        // Step 11
        webDriver.switchTo().frame("second_frame");
        webElement = webDriver.findElement(By.cssSelector("img#epam-logo"));
        Assert.assertTrue(webElement.isDisplayed());

        // Step 12
        webDriver.switchTo().defaultContent();

        // Step 13
        webElement = webDriver.findElement(By.cssSelector("h3.text-center>a"));
        Assert.assertTrue(webElement.isDisplayed());
        str = webElement.getText();
        Assert.assertEquals(str, subHeader);

        // Step 14
        webElement = webDriver.findElement(By.cssSelector("h3.text-center>a[target=_blank]"));
        Assert.assertTrue(webElement.isDisplayed());
        str = webElement.getAttribute("href");
        Assert.assertEquals(str, jdiUrl);

        // Step 15
        webElement = webDriver.findElement(By.cssSelector("ul.sidebar-menu"));
        Assert.assertTrue(webElement.isDisplayed());

        // Step 16
        webElement = webDriver.findElement(By.cssSelector("footer"));
        Assert.assertTrue(webElement.isDisplayed());

        // Step 17
        webDriver.close();

        // Delay
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @BeforeTest
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        webDriver = new ChromeDriver();
        url = "https://jdi-testing.github.io/jdi-light/index.html";
        headers = new String[]{"HOME", "CONTACT FORM", "SERVICE", "METALS & COLORS"};
        textUnderPic = new String[]{"To include good practices\n" + "and ideas from successful\n" + "EPAM project",
                "To be flexible and\n" + "customizable",
                "To be multiplatform",
                "Already have good base\n" + "(about 20 internal and\n" +
                        "some external projects),\n" + "wish to get more…"
        };
        mainTitle = new String("EPAM FRAMEWORK WISHES…");
        jdiText = new String("LOREM IPSUM DOLOR SIT AMET, CONSECTETUR ADIPISICING ELIT, SED DO EIUSMOD TEMPOR " +
                "INCIDIDUNT UT LABORE ET DOLORE MAGNA ALIQUA. UT ENIM AD MINIM VENIAM, QUIS NOSTRUD EXERCITATION " +
                "ULLAMCO LABORIS NISI UT ALIQUIP EX EA COMMODO CONSEQUAT DUIS AUTE IRURE DOLOR IN " +
                "REPREHENDERIT IN VOLUPTATE VELIT ESSE CILLUM DOLORE EU FUGIAT NULLA PARIATUR."
        );
        subHeader = new String("JDI GITHUB");
        jdiUrl = new String("https://github.com/epam/JDI");
    }

    @AfterTest
    public void afterTest() { webDriver.quit(); }
}
