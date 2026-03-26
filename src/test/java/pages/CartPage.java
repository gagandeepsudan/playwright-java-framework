package pages;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartPage {
    private Page page;
    public CartPage(Page page){
        this.page=page;
    }

    public boolean validatePage(){
        return page.url().contains("cart.html");
    }
    public void assertProductInCart(String productName){
    assertThat(page.locator(".inventory_item_name").getByText(productName))
            .isVisible();


    }
}
