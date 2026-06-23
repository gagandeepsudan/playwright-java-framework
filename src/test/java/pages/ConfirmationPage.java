package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ConfirmationPage extends BasePage {
    private final Locator confirmationHeader;

    public ConfirmationPage(Page page) {
        super(page);
        this.confirmationHeader= page.locator(".complete-header");
    }

    public void verifyOrderConfirmation() {
        assertThat(confirmationHeader)
                .containsText("Thank you for your order!");
    }
}