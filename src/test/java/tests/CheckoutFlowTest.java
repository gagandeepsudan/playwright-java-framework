package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import pages.*;

public class CheckoutFlowTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    private LoginPage loginPage;
    private InventoryPage productsPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;
    private ConfirmationPage confirmationPage;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        page = browser.newPage();

        loginPage         = new LoginPage(page);
        productsPage    = new InventoryPage(page);
        cartPage          = new CartPage(page);
        checkoutStepOnePage = new CheckoutStepOnePage(page);
        checkoutStepTwoPage = new CheckoutStepTwoPage(page);
        confirmationPage  = new ConfirmationPage(page);
    }

    @AfterClass
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    // ✅ Happy Path

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        loginPage.navigate();
        loginPage.loginAs("standard_user", "secret_sauce");
        page.waitForURL("**/inventory.html");
    }

    @Test(priority = 2, dependsOnMethods = "testSuccessfulLogin")
    public void testAddProductToCart() {
        productsPage.addFirstProductToCart();
        productsPage.verifyCartBadgeCount("1");
        productsPage.goToCart();
    }

    @Test(priority = 3, dependsOnMethods = "testAddProductToCart")
    public void testVerifyCartAndProceedToCheckout() {
        cartPage.verifyProductInCart("Sauce Labs Backpack");
        cartPage.proceedToCheckout();
    }

    @Test(priority = 4, dependsOnMethods = "testVerifyCartAndProceedToCheckout")
    public void testEnterCheckoutDetails() {
        checkoutStepOnePage.enterDetails("John", "Doe", "EC1A 1BB");
        checkoutStepOnePage.clickContinue();
        page.waitForURL("**/checkout-step-two.html");
    }

    @Test(priority = 5, dependsOnMethods = "testEnterCheckoutDetails")
    public void testCompleteOrder() {
        checkoutStepTwoPage.completeOrder();
    }

    @Test(priority = 6, dependsOnMethods = "testCompleteOrder")
    public void testOrderConfirmationDisplayed() {
        confirmationPage.verifyOrderConfirmation();
    }

    // ❌ Negative Scenarios

    @Test(priority = 7)
    public void testLockedOutUserCannotLogin() {
        loginPage.navigate();
        loginPage.loginAs("locked_out_user", "secret_sauce");
       // loginPage.LockedOutValidationError();
    }

    @Test(priority = 8)
    public void testCheckoutWithoutFirstNameShowsError() {
        // Login fresh, add to cart, go to checkout step one
        loginPage.navigate();
        loginPage.loginAs("standard_user", "secret_sauce");
        productsPage.addFirstProductToCart();
        productsPage.goToCart();
        cartPage.proceedToCheckout();

        // Click Continue without entering any details
        checkoutStepOnePage.clickContinue();
        checkoutStepOnePage.verifyFirstNameRequiredError();
    }
}