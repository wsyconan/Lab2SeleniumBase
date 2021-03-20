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
    String url, str, mainTitle, jdiText, subHeader, jdiUrl, username, password;
    int i;
    List<WebElement> list;
    String[] headers, textUnderPic;

    @Test
    public void test() {
        // Step 1: Open test site by URL
        webDriver.get(url);

        // Step 2: Assert Browser title
        Assert.assertEquals(webDriver.getTitle(), "Home Page");
        // maximize window
        webDriver.manage().window().maximize();

        // Step 3: Perform login
        webDriver.findElement((By.cssSelector("li.dropdown.uui-profile-menu"))).click();
        webDriver.findElement(By.cssSelector("#name")).sendKeys(username);
        webDriver.findElement(By.cssSelector("#password")).sendKeys(password);
        webDriver.findElement(By.cssSelector("#login-button")).click();

        // Step 4: Assert User name in the left-top side of screen that user is loggined
        String userName = webDriver.findElement(By.cssSelector("#user-name")).getText();
        Assert.assertEquals(userName, "ROMAN IOVLEV");

        // Step 5: Assert Browser title
        Assert.assertEquals(webDriver.getTitle(), "Home Page");

        // Step 6: Assert that there are 4 items on the header section are displayed and they have proper texts
        list = webDriver.findElements(By.cssSelector("ul.uui-navigation.nav.navbar-nav.m-l8 a"));
        i = 0;
        for (WebElement we : list) {
            if (we.isDisplayed() && we.isEnabled()) {
                Assert.assertEquals(headers[i++], we.getText());
            }
        }

        // Step 7: Assert that there are 4 images on the Index Page and they are displayed
        list = webDriver.findElements(By.cssSelector(".benefit-icon"));
        Assert.assertEquals(list.size(), 4);
        for (WebElement we : list) {
            Assert.assertTrue(we.isDisplayed());
        }

        // Step 8: Assert that there are 4 texts on the Index Page under icons and they have proper text
        list = webDriver.findElements(By.cssSelector(".benefit-icon+.benefit-txt"));
        Assert.assertEquals(list.size(), 4);
        i = 0;
        for (WebElement we : list) {
            Assert.assertEquals(textUnderPic[i++], we.getText());
        }

        // Step 9: Assert a text of the main headers
        webElement = webDriver.findElement(By.cssSelector("h3.main-title.text-center"));
        Assert.assertTrue(webElement.isDisplayed());
        str = webElement.getText();
        Assert.assertEquals(str, mainTitle);

        webElement = webDriver.findElement(By.cssSelector("p.main-txt.text-center"));
        Assert.assertTrue(webElement.isDisplayed());
        str = webElement.getText();
        Assert.assertEquals(str, jdiText.toUpperCase(Locale.ROOT));

        // Step 10: Assert that there is the iframe in the center of page
        list = webDriver.findElements(By.cssSelector("iframe"));
        Assert.assertFalse(list.isEmpty());

        // Step 11: Switch to the iframe
        webDriver.switchTo().frame("second_frame");
        // Check that there is Epam logo in the left top conner of iframe
        webElement = webDriver.findElement(By.cssSelector("img#epam-logo"));
        Assert.assertTrue(webElement.isDisplayed());

        // Step 12: Switch to original window back
        webDriver.switchTo().defaultContent();

        // Step 13: Assert a text of the sub header
        webElement = webDriver.findElement(By.cssSelector("h3.text-center>a"));
        Assert.assertTrue(webElement.isDisplayed());
        str = webElement.getText();
        Assert.assertEquals(str, subHeader);

        // Step 14: Assert that JDI GITHUB is a link and has a proper URL
        webElement = webDriver.findElement(By.cssSelector("h3.text-center>a[target=_blank]"));
        Assert.assertTrue(webElement.isDisplayed());
        str = webElement.getAttribute("href");
        Assert.assertEquals(str, jdiUrl);

        // Step 15: Assert that there is Left Section
        webElement = webDriver.findElement(By.cssSelector("ul.sidebar-menu"));
        Assert.assertTrue(webElement.isDisplayed());

        // Step 16: Assert that there is Footer
        webElement = webDriver.findElement(By.cssSelector("footer"));
        Assert.assertTrue(webElement.isDisplayed());

        // Step 17: Close Browser
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
        username = ("Roman");
        password = ("Jdi1234");
        headers = new String[]{"HOME", "CONTACT FORM", "SERVICE", "METALS & COLORS"};
        textUnderPic = new String[]{"To include good practices\n" + "and ideas from successful\n" + "EPAM project",
                "To be flexible and\n" + "customizable",
                "To be multiplatform",
                "Already have good base\n" + "(about 20 internal and\n" +
                        "some external projects),\n" + "wish to get more…"
        };
        mainTitle = ("EPAM FRAMEWORK WISHES…");
        jdiText = ("LOREM IPSUM DOLOR SIT AMET, CONSECTETUR ADIPISICING ELIT, SED DO EIUSMOD TEMPOR " +
                "INCIDIDUNT UT LABORE ET DOLORE MAGNA ALIQUA. UT ENIM AD MINIM VENIAM, QUIS NOSTRUD EXERCITATION " +
                "ULLAMCO LABORIS NISI UT ALIQUIP EX EA COMMODO CONSEQUAT DUIS AUTE IRURE DOLOR IN " +
                "REPREHENDERIT IN VOLUPTATE VELIT ESSE CILLUM DOLORE EU FUGIAT NULLA PARIATUR."
        );
        subHeader = ("JDI GITHUB");
        jdiUrl = ("https://github.com/epam/JDI");
    }

    @AfterTest
    public void afterTest() { webDriver.quit(); }
}
