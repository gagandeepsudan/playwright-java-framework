package pages;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutStepOnePage {

    private final Page page;
    private final String FIRST_NAME_FIELD = "[data-test='firstName']";
    private final String LAST_NAME_FIELD  = "[data-test='lastName']";
    private final String POSTCODE_FIELD   = "[data-test='postalCode']";
    private final String CONTINUE_BTN     = "[data-test='continue']";
    private final String ERROR_MESSAGE    = "[data-test='error']";

    public CheckoutStepOnePage(Page page) {
        this.page = page;
    }

    public void enterDetails(String firstName, String lastName, String postcode) {
        page.fill(FIRST_NAME_FIELD, firstName);
        page.fill(LAST_NAME_FIELD, lastName);
        page.fill(POSTCODE_FIELD, postcode);
    }

    public void clickContinue() {
        page.click(CONTINUE_BTN);
    }

    public void verifyFirstNameRequiredError() {
        assertThat(page.locator(ERROR_MESSAGE))
                .containsText("Error: First Name is required");
    }
}