package pages;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ConfirmationPage {

    private final Page page;
    private final String CONFIRMATION_HEADER = ".complete-header";

    public ConfirmationPage(Page page) {
        this.page = page;
    }

    public void verifyOrderConfirmation() {
        assertThat(page.locator(CONFIRMATION_HEADER))
                .containsText("Thank you for your order!");
    }
}