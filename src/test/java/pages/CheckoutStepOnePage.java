package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutStepOnePage extends BasePage {
    private final Locator FIRST_NAME_FIELD;
    private final Locator LAST_NAME_FIELD;
    private final Locator POSTCODE_FIELD;
    private final Locator CONTINUE_BTN;
    private final Locator ERROR_MESSAGE;

    public CheckoutStepOnePage(Page page) {
        super(page);
        this.FIRST_NAME_FIELD = page.locator("[data-test='firstName']");
        this.LAST_NAME_FIELD = page.locator("[data-test='lastName']");
        this.POSTCODE_FIELD= page.locator("[data-test='postalCode']");
        this.CONTINUE_BTN= page.locator("[data-test='continue']");
        this.ERROR_MESSAGE = page.locator("[data-test='error']");

    }

    public void enterDetails(String firstName, String lastName, String postcode) {
        FIRST_NAME_FIELD.fill(firstName);
        LAST_NAME_FIELD.fill(lastName);
        POSTCODE_FIELD.fill(postcode);
    }

    public void clickContinue() {
        CONTINUE_BTN.click();
    }

    public void verifyFirstNameRequiredError() {
        assertThat(ERROR_MESSAGE)
                .containsText("Error: First Name is required");
    }
}