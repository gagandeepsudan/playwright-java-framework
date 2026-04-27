package pages;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartPage {
    private final Page page;
    private final String CHECKOUT_BTN = "#checkout";

    public CartPage(Page page){
        this.page=page;
    }

    public boolean validatePage(){
        return page.url().contains("cart.html");
    }
    public void verifyProductInCart(String productName){
    assertThat(page.locator(".inventory_item_name").getByText(productName))
            .isVisible();
    }
    public void proceedToCheckout(){
        page.click(CHECKOUT_BTN);
    }

}
