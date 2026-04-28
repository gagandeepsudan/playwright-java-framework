# Playwright Java Test Automation Framework

A test automation framework built with Playwright and Java,
using Page Object Model, TestNG, and GitHub Actions CI.

## What it tests

Automated end-to-end tests for [Saucedemo](https://www.saucedemo.com)
covering:
- User login (valid credentials)
- Locked out user cannot login (error message verified)
- Add product to cart and verify cart badge count
- Full checkout flow — enter details, complete order, confirm
- Checkout blocked when First Name is missing (error message verified)

## Tech stack

- Playwright — browser automation
- Java — programming language
- TestNG — test runner
- Maven — dependency management
- Page Object Model — framework structure
- GitHub Actions —CI pipeline (tests run automatically on every push)

## CI Status

Tests run automatically on every push via GitHub Actions.
[View latest CI runs](https://github.com/gagandeepsudan/playwright-java-framework/actions)

## Project structure

src/test/java/
├── pages/
│   ├── LoginPage.java
│   ├── InventoryPage.java
│   ├── CartPage.java
│   ├── CheckoutStepOnePage.java
│   ├── CheckoutStepTwoPage.java
│   └── ConfirmationPage.java
└── tests/
├── LoginTest.java
└── CheckoutFlowTest.java

## How to run locally

**Prerequisites:** Java 23, Maven 3.x

1. Clone the repo:
   git clone https://github.com/gagandeepsudan/playwright-java-framework.git

2. Install browsers (first time only):
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"

3. Run the tests:
   mvn test

## AI-assisted test generation

The checkout flow tests were generated using Claude as an AI
assistant, based on a structured prompt with user story,
acceptance criteria, and negative scenarios.

Prompt approach used:
- Defined the application URL and credentials
- Listed 6 acceptance criteria
- Specified 2 negative scenarios
- Set technical constraints (POM, TestNG, assertThat, no Thread.sleep)

What was changed after generation:
- Used InventoryPage instead of a new ProductsPage
  to avoid duplication
- Kept existing negative login test in LoginTest
  rather than duplicating it
- Retained all existing methods in InventoryPage
  and added only what was missing

## Engineering Decisions

### Why @BeforeClass instead of @BeforeMethod?
`@BeforeClass` is used because the checkout tests are dependent
on each other and need to share the same browser session and
application state. If `@BeforeMethod` ran before every test,
it would reopen the browser and reset the state each time,
causing dependent tests to fail because the previous steps
in the checkout flow would be lost.

### Risk of dependsOnMethods
If `testSuccessfulLogin` fails, tests 2–6 will be skipped
by TestNG because of `dependsOnMethods`. This is a risk because
the test report may show multiple skipped tests without confirming
whether those functionalities actually work or fail independently,
which can reduce visibility for the team and make defect analysis
harder.

### Credential storage
Credentials are currently hardcoded for portfolio purposes.
In a production framework the better approach would be to store
them in a `.properties` or `.env` file excluded from version
control via `.gitignore`, or use GitHub Actions secrets for
CI pipelines.

### CI/CD lesson learned
Tests passed locally but failed in CI because local runs use
a headed browser while CI environments are headless. Fixed by
setting `setHeadless(true)` in the browser launch options.
This is a common real-world CI/CD issue in test automation.

### What I would add next
- Move credentials to a config file or GitHub Actions secrets
- Add @DataProvider for data-driven login tests
- Add payment failure negative scenario
- Add session timeout scenario

### Assertion resilience
Using `hasText()` with an exact string is brittle because the
test can fail due to small UI text changes such as extra spaces,
punctuation, capitalisation, or minor wording updates — even
when the functionality still works correctly. A more resilient
approach is `containsText()` which validates only the important
part of the message:
java
assertThat(page.locator(CONFIRMATION_HEADER)).containsText("Thank you");
