# CLAUDE.md — Playwright Java Framework

## Project Overview

A Playwright Java test automation framework built against the [Saucedemo](https://www.saucedemo.com) demo app. Demonstrates Page Object Model (POM) architecture, data-driven testing, API testing with schema validation, and CI/CD via GitHub Actions.

Portfolio: https://github.com/gagandeepsudan/playwright-java-framework

---

## Tech Stack

- **Language:** Java 21
- **Test framework:** TestNG
- **Automation library:** Microsoft Playwright for Java
- **Build tool:** Maven
- **CI/CD:** GitHub Actions
- **API testing:** Playwright APIRequestContext + JSON schema validation (Everit)

---

## Project Structure

```
src/
  test/
    java/
      pages/          # Page Object classes
      tests/          # Test classes
      utils/          # Helpers (e.g. schema loading)
    resources/
      schemas/        # JSON schema files for API validation
.github/
  workflows/
    playwright.yml    # GitHub Actions pipeline
pom.xml
```

---

## Page Classes

| Class | Responsibility |
|---|---|
| `BasePage` | Parent class for all page objects; holds shared `Page` and `navigateTo()` |
| `LoginPage` | Username/password fields, login button, error message |
| `InventoryPage` | Product listing, add-to-cart, cart icon badge, sort dropdown |
| `CartPage` | Cart item list, checkout button |
| `CheckoutStepOnePage` | Buyer info form (first name, last name, postcode) |
| `CheckoutStepTwoPage` | Order summary, finish button |
| `ConfirmationPage` | Order confirmation message assertion |

**POM conventions:**
- All page classes extend `BasePage` and call `super(page)` in their constructor. `BasePage` holds the shared `protected final Page page` and the common `navigateTo()` helper.
- Each page class takes `Page page` via its constructor — dependency injected from the test, never static state.
- **Locators:** declared as `private final Locator` fields and assigned in the constructor after `super(page)` — never initialized inline at the declaration.
- Methods are `void` actions (e.g. `loginAs()`, `addToCart()`). Page objects do things and expose state; they do not contain assertions.
- Assertions live in the **test classes**, using native Playwright `assertThat()` — no custom boolean wrapper methods.

---

## Test Classes

| Class | Coverage |
|---|---|
| `LoginTest` | Valid login, invalid credentials (data-driven via `@DataProvider`) |
| `CheckoutFlowTest` | End-to-end: login → add to cart → checkout → confirmation |
| `InventoryTest` | Sort dropdown: A-Z, Z-A, price low-to-high, price high-to-low |
| `ApiTest` | REST API tests against reqres.in: GET/POST, status codes, JSON schema validation |

**Test conventions:**
- `@BeforeMethod` sets up `BrowserContext` + `Page` and instantiates page objects; torn down in `@AfterMethod`. `Playwright` + `Browser` are created once per class.
- Browser runs **headless** in CI; can be set to headed locally via `-Dheadless=false`.
- `@DataProvider` supplies multiple data sets to parameterized tests (e.g. credential sets in `LoginTest`, sort options in `InventoryTest`).
- **Assertion choice:** use Playwright `assertThat()` for anything involving the UI / page state (it auto-waits and retries). Use TestNG `assertEquals()` for pure data comparisons already extracted into Java objects (e.g. comparing two sorted `List`s).
- **Order/sort tests:** never assert against the page's default order. Change to a different state first (e.g. select Z→A), then to the target state (A→Z), so the control must actually act for the test to pass — otherwise the test passes even if the dropdown is broken.

---

## API Testing

- Uses Playwright's `APIRequestContext` (not a separate HTTP library)
- Base URL: `https://reqres.in`
- Schema validation: Everit `json-schema` library, schemas stored in `src/test/resources/schemas/`
- Validates response body structure, not just status codes

---

## CI/CD — GitHub Actions

- Workflow file: `.github/workflows/playwright.yml`
- Trigger: push and pull request to `master`
- Java version: 21 (explicitly set via `setup-java` — resolves past version mismatch issues)
- Browser: Chromium headless
- Key historical issue: required a `playwright install --with-deps` step before the test run, or the browser launches then crashes on the headless CI runner

---

## Engineering Decisions

- **POM chosen** to separate test logic from UI interaction — improves maintainability
- **BasePage parent class** — shared `Page` and navigation live in one place; every page inherits via `super(page)`
- **Constructor injection** used for `Page` — avoids static state, makes tests independent
- **Native Playwright assertions in tests, not page objects** — reduces dead code, leverages built-in auto-waiting, keeps page objects free of assertions
- **@DataProvider** for parameterized tests — avoids duplicated test methods, makes adding data trivial
- **Schema validation in API tests** — validates contract, not just status code
- **Headless enforced in CI** via `-Dheadless=true`; configurable locally through a system property

---

## What NOT to Change

- Do not add Selenium or WebDriverManager — this is a Playwright-only project
- Do not switch to JUnit — TestNG is intentional (DataProvider, parallel support)
- Do not add Cucumber/BDD layer — project is kept intentionally lightweight for portfolio clarity
- Do not initialize Locators inline at the field declaration — always in the constructor (see POM conventions)

---

## Common Commands

```bash
# Run all tests
mvn test

# Run a specific test class
# NOTE: -Dtest= is case-sensitive and must match the class name EXACTLY
# (e.g. InventoryTest, not InventorySortTest)
mvn test -Dtest=LoginTest

# Run with a headed browser locally, slowed down so you can watch it
mvn test -Dheadless=false -DslowMo=500

# Install Playwright browsers (first-time setup)
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"
```