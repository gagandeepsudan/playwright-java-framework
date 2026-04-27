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

        playwright = Playwright.create();

        browser= PlaywrightConfig.createBrowser(playwright);
        BrowserContext context = browser.newContext(PlaywrightConfig.contextOptions());

        page = context.newPage();
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        cartPage= new CartPage(page);
        loginPage.navigate();


    }
    @Test
            public void validateLogin() {
        loginPage.loginAs("standard_user","secret_sauce");
        Assert.assertTrue(inventoryPage.verifyCorrectUrlIsLoaded());



        inventoryPage.verifyProductIsVisible();
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
        cartPage.verifyProductInCart("Sauce Labs Bolt T-Shirt");

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

        browser.close();
        playwright.close();

    }
}
