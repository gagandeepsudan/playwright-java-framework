package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ConfirmationPage extends BasePage {
    private final Locator CONFIRMATION_HEADER;

    public ConfirmationPage(Page page) {
        super(page);
        this.CONFIRMATION_HEADER= page.locator(".complete-header");
    }

    public void verifyOrderConfirmation() {
        assertThat(CONFIRMATION_HEADER)
                .containsText("Thank you for your order!");
    }
}