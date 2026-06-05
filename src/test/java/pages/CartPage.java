package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;



import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartPage extends BasePage {
    private final Locator CHECKOUT_BTN ;

    public CartPage(Page page){
        super(page);
        this.CHECKOUT_BTN= page.locator("#checkout");
    }



    public void verifyProductInCart(String productName){
    assertThat(page.locator(".inventory_item_name").getByText(productName))
            .isVisible();
    }
    public void proceedToCheckout(){
        CHECKOUT_BTN.click();
    }

}
