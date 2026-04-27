package pages;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class InventoryPage {

    private final Page page;
    private final String ADD_TO_CART_BTN = "[data-test='add-to-cart-sauce-labs-backpack']";
    private final String CART_BADGE      = ".shopping_cart_badge";
    private final String CART_ICON       = ".shopping_cart_link";

    public InventoryPage(Page page) {
        this.page = page;
    }

    public boolean verifyCorrectUrlIsLoaded() {
        return page.url().contains("inventory.html");
    }

    public void verifyProductIsVisible() {
        assertThat(page.getByText("Products")).isVisible();
    }

    public int getProductCount() {
        return page.locator(".inventory_item").count();
    }

    public void addToCart(String productName) {
        String dataTestId = "add-to-cart-" + productName.toLowerCase()
                .replace(" ", "-");
        page.locator("[data-test='" + dataTestId + "']").click();
    }

    public void addFirstProductToCart() {
        page.click(ADD_TO_CART_BTN);
    }

    // ✅ ADDED — was in ProductsPage, missing from InventoryPage
    public void verifyCartBadgeCount(String expectedCount) {
        assertThat(page.locator(CART_BADGE)).hasText(expectedCount);
    }

    public void goToCart() {
        page.click(CART_ICON);
    }
}