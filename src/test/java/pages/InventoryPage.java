package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class InventoryPage extends BasePage{
    private final Locator inventoryItems;

    private final Locator ADD_TO_CART_BTN;
    private final Locator cartButton;
    private final Locator CART_ICON;

    public InventoryPage(Page page) {
        super(page);
        this.inventoryItems= page.locator(".inventory_item");
        this.CART_ICON=page.locator(".shopping_cart_link");
        this.cartButton=page.locator(".shopping_cart_badge");
        this.ADD_TO_CART_BTN= page.locator("[data-test^='add-to-cart-']").first();


    }

    public int getProductCount() {
        return inventoryItems.count();
    }

    public void addToCart(String productName) {
        page.getByText(productName)
                .locator("xpath=ancestor::div[@class='inventory_item']")
                .getByText("Add to cart")
                .click();
    }

    public void addFirstProductToCart() {
        ADD_TO_CART_BTN.click();
    }

     // ADDED — was in ProductsPage, missing from InventoryPage
    public void verifyCartBadgeCount(String expectedCount) {
        assertThat(cartButton).hasText(expectedCount);
    }

    public void goToCart() {
        CART_ICON.click();
    }
}