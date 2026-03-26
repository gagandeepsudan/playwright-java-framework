package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

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
        // Read HEADLESS environment variable — CI sets it to true
        // Locally it's not set, so defaults to false (visible browser)
        boolean isHeadless= Boolean.parseBoolean(System
                .getenv().getOrDefault("HEADLESS", "false"));
        //open Browser
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(isHeadless));
        //open newpage
        page = browser.newPage();
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        cartPage= new CartPage(page);
        loginPage.navigate();


    }
    @Test
            public void validateLogin() {
        loginPage.loginAs("standard_user","secret_sauce");

        /*Assert URL and Item*/
        Assert.assertTrue(inventoryPage.verifyUrl());

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
    @AfterMethod
            public void teardown() {
        //teardown
        browser.close();
        playwright.close();

    }
}
