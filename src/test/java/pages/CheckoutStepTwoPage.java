package pages;

import com.microsoft.playwright.Page;

public class CheckoutStepTwoPage {

    private final Page page;
    private final String FINISH_BTN = "[data-test='finish']";

    public CheckoutStepTwoPage(Page page) {
        this.page = page;
    }

    public void completeOrder() {
        page.click(FINISH_BTN);
    }
}