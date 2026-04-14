package pages;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class InventoryPage {
    private final Page page;
    public InventoryPage(Page page){
        this.page= page;
    }
    public boolean verifyCorrectUrlIsLoaded(){
        return page.url().contains("inventory.html");
    }

    public void assertProductIsVisible(){

        assertThat(page.getByText("Products")).isVisible();
    }

    public int getProductCount(){
        return page.locator(".inventory_item").count();
    }
    public void addToCart(String productName){
//     page.getByText(productName).locator("add-to-cart-sauce-labs-bolt-t-shirt").getByText("Add to cart").click();
       String dataTestId = "add-to-cart-" + productName.toLowerCase()
               .replace(" ", "-");
       page.locator("[data-test=\"" + dataTestId + "\"]").click();
    }
    public void goToCart(){
        page.locator(".shopping_cart_link").click();
    }

}
