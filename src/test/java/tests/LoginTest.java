package tests;

import com.microsoft.playwright.*;

import config.PlaywrightConfig;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import com.microsoft.playwright.BrowserContext;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest {
    Playwright playwright;
    Browser browser;
    Page page;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;

    @BeforeMethod
    public void setup() {
        /*Setup*/
        //call Playwright
        playwright = Playwright.create();
//        // Read HEADLESS environment variable — CI sets it to true
//        // Locally it's not set, so defaults to false (visible browser)
//        boolean isHeadless= Boolean.parseBoolean(System
//                .getenv().getOrDefault("HEADLESS", "false"));
        //open Browser
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
//                .setHeadless(isHeadless));
        browser= PlaywrightConfig.createBrowser(playwright);
        BrowserContext context = browser.newContext(PlaywrightConfig.contextOptions());
        //open newpage
//        page = browser.newPage();
        page = context.newPage();
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        cartPage= new CartPage(page);
        loginPage.navigate();


    }
    @Test
            public void validateLogin() {
        loginPage.loginAs("standard_user","secret_sauce");

        /*Assert URL and Item*/
        Assert.assertTrue(inventoryPage.verifyCorrectUrlIsLoaded());

        /*Assert Item*/

        inventoryPage.assertProductIsVisible();
    }
    @Test
    public void LockedOutValidationError(){
        loginPage.loginAs("locked_out_user", "secret_sauce");
  assertThat(page.getByText("Epic sadface: Sorry, this user has been locked out.")).isVisible();
    }

    @Test
    public void userCanAddItemToCart(){
        loginPage.loginAs("standard_user", "secret_sauce");
        inventoryPage.addToCart("Sauce Labs Bolt T-Shirt");
        inventoryPage.goToCart();
        Assert.assertTrue(cartPage.validatePage());
        cartPage.assertProductInCart("Sauce Labs Bolt T-Shirt");

    }
    @Test
    public void inventoryPageLoadswith6Products(){
        loginPage.loginAs("standard_user", "secret_sauce");
        System.out.println("URL is: " + page.url());
        System.out.println("Count is: " + inventoryPage.getProductCount());
        Assert.assertTrue(inventoryPage.verifyCorrectUrlIsLoaded());

        Assert.assertEquals(inventoryPage.getProductCount(),6);
    }

    @AfterMethod
            public void teardown() {
        //teardown
        browser.close();
        playwright.close();

    }
}
