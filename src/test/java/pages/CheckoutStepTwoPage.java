package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutStepTwoPage extends BasePage {


    private final Locator FINISH_BTN;

    public CheckoutStepTwoPage(Page page) {
        super(page);
        this.FINISH_BTN= page.locator("[data-test='finish']");
    }

    public void completeOrder() {
        FINISH_BTN.click();
    }
}