package tests;

import com.microsoft.playwright.*;

import config.PlaywrightConfig;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import com.microsoft.playwright.BrowserContext;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest {
    Playwright playwright;
    Browser browser;
    Page page;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    BrowserContext context;



    @BeforeMethod
    public void setup() {

        playwright = Playwright.create();

        browser= PlaywrightConfig.createBrowser(playwright);
        context = browser.newContext(PlaywrightConfig.contextOptions());

        page = context.newPage();
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        cartPage= new CartPage(page);
        loginPage.navigate();


    }
    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true},
                {"locked_out_user", "secret_sauce", false},
                {"invalid_user", "secret_sauce", false},
        };
    }

    @Test (dataProvider = "loginData")
            public void validateLogin(String username, String password,boolean shouldSucceed) {
        loginPage.loginAs(username, password);
        if(shouldSucceed)
        {
            assertThat(page).hasURL(Pattern.compile(".*inventory.html"));
            assertThat(page.getByText("Products")).isVisible();
        }
        else
        {
            assertThat(page.getByText("Epic sadface")).isVisible();
        }
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
        assertThat(page).hasURL(Pattern.compile(".*cart.html"));
        cartPage.verifyProductInCart("Sauce Labs Bolt T-Shirt");

    }
    @Test
    public void inventoryPageLoadswith6Products(){
        loginPage.loginAs("standard_user", "secret_sauce");
        System.out.println("URL is: " + page.url());
        System.out.println("Count is: " + inventoryPage.getProductCount());
       assertThat(page).hasURL(Pattern.compile(".*inventory.html"));
        assertThat(page.locator(".inventory_item")).hasCount(6);
    }

    @AfterMethod
    public void teardown() {
        page.close();
        context.close();
        browser.close();
        playwright.close();

    }
}
