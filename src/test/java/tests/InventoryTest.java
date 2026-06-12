package tests;

import com.microsoft.playwright.*;
import config.PlaywrightConfig;
import org.testng.annotations.*;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class InventoryTest {
    static Playwright playwright;
    static Browser browser;
    Page page;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    BrowserContext context;

    @BeforeClass
    public static void launchBrowser() {
        playwright = Playwright.create();
        browser = PlaywrightConfig.createBrowser(playwright);
    }

    @BeforeMethod
    public void setup() {
        context = browser.newContext(PlaywrightConfig.contextOptions());
        page = context.newPage();
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        loginPage.navigate();
        loginPage.loginAs("standard_user", "secret_sauce");
        page.waitForURL("**/inventory.html");
    }

    @Test
    public void sortAtoZ() {
        inventoryPage.sortBy("za");
        inventoryPage.sortBy("az");
        List<String> names = inventoryPage.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        Collections.sort(sorted);
        assertEquals(names, sorted);
    }

    @Test
    public void sortZtoA() {
        inventoryPage.sortBy("za");
        List<String> names = inventoryPage.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(Collections.reverseOrder());
        assertEquals(names, sorted);
    }

    @Test
    public void sortPriceLowToHigh() {
        inventoryPage.sortBy("lohi");
        List<Double> prices = inventoryPage.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        assertEquals(prices, sorted);
    }

    @Test
    public void sortPriceHighToLow() {
        inventoryPage.sortBy("hilo");
        List<Double> prices = inventoryPage.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        assertEquals(prices, sorted);
    }

    @AfterMethod
    public void teardown() {
        page.close();
        context.close();
    }

    @AfterClass
    public static void closeBrowser() {
        browser.close();
        playwright.close();
    }
}