package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class InventoryPage extends BasePage{
    private final Locator inventoryItems;

    private final Locator ADD_TO_CART_BTN;
    private final Locator cartButton;
    private final Locator CART_ICON;
    private final Locator sortDropdown;
    private final Locator productNames;
    private final Locator productPrices;

    public InventoryPage(Page page) {
        super(page);
        this.inventoryItems= page.locator(".inventory_item");
        this.CART_ICON=page.locator(".shopping_cart_link");
        this.cartButton=page.locator(".shopping_cart_badge");
        this.ADD_TO_CART_BTN= page.locator("[data-test^='add-to-cart-']").first();
        this.sortDropdown = page.locator(".product_sort_container");
        this.productNames = page.locator(".inventory_item_name");
        this.productPrices = page.locator(".inventory_item_price");

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

    public void sortBy(String sortOption) {
        sortDropdown.selectOption(sortOption);
    }

    public List<String> getProductNames() {
        return productNames.allTextContents();
    }

    public List<Double> getProductPrices() {
        return productPrices.allTextContents().stream()
                .map(p -> Double.parseDouble(p.replace("$", "")))
                .collect(Collectors.toList());
    }
}