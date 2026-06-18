package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutStepTwoPage extends BasePage {


    private final Locator finishBtn;

    public CheckoutStepTwoPage(Page page) {
        super(page);
        this.finishBtn= page.locator("[data-test='finish']");
    }

    public void completeOrder() {
        finishBtn.click();
    }
}