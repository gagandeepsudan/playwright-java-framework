package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutStepOnePage extends BasePage {
    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator postcodeField;
    private final Locator continueBtn;
    private final Locator errorMessage;

    public CheckoutStepOnePage(Page page) {
        super(page);
        this.firstNameField = page.locator("[data-test='firstName']");
        this.lastNameField = page.locator("[data-test='lastName']");
        this.postcodeField= page.locator("[data-test='postalCode']");
        this.continueBtn= page.locator("[data-test='continue']");
        this.errorMessage = page.locator("[data-test='error']");

    }

    public void enterDetails(String firstName, String lastName, String postcode) {
        firstNameField.fill(firstName);
        lastNameField.fill(lastName);
        postcodeField.fill(postcode);
    }

    public void clickContinue() {
        continueBtn.click();
    }

    public void verifyFirstNameRequiredError() {
        assertThat(errorMessage)
                .containsText("Error: First Name is required");
    }
}