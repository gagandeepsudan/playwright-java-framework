# CLAUDE.md — Playwright Java Framework

## Project Overview

A Playwright Java test automation framework built against the [Saucedemo](https://www.saucedemo.com) demo app. Demonstrates Page Object Model (POM) architecture, data-driven testing, API testing with schema validation, and CI/CD via GitHub Actions.

Portfolio: https://github.com/gagandeepsudan/playwright-java-framework

---

## Tech Stack

- **Language:** Java 11
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
    ci.yml            # GitHub Actions pipeline
pom.xml
```

---

## Page Classes

| Class | Responsibility |
|---|---|
| `LoginPage` | Username/password fields, login button, error message |
| `InventoryPage` | Product listing, add-to-cart, cart icon badge |
| `CartPage` | Cart item list, checkout button |
| `CheckoutStepOnePage` | Buyer info form (first name, last name, postcode) |
| `CheckoutStepTwoPage` | Order summary, finish button |
| `ConfirmationPage` | Order confirmation message assertion |

**POM conventions:**
- Each page class takes `Page page` via constructor — dependency injected from test
- Locators defined as private fields using `page.locator()`
- Methods return `void` or the next page object where appropriate
- Assertions use native Playwright `assertThat()` — no custom wrappers
  - Locator fields are declared private final and initialized in the constructor — never inline at declaration.



---

## Test Classes

| Class | Coverage |
|---|---|
| `LoginTest` | Valid login, invalid credentials (data-driven via `@DataProvider`) |
| `CheckoutFlowTest` | End-to-end: login → add to cart → checkout → confirmation |
| `ApiTest` | REST API tests against reqres.in: GET/POST, status codes, JSON schema validation |

**Test conventions:**
- `@BeforeMethod` sets up `Playwright`, `Browser`, `BrowserContext`, `Page` — torn down in `@AfterMethod`
- Browser runs **headless** in CI; can be set to headed locally
- `@DataProvider` supplies multiple credential sets to `LoginTest`

---

## API Testing

- Uses Playwright's `APIRequestContext` (not a separate HTTP library)
- Base URL: `https://reqres.in`
- Schema validation: Everit `json-schema` library, schemas stored in `src/test/resources/schemas/`
- Validates response body structure, not just status codes

---

## CI/CD — GitHub Actions

- Trigger: push and pull request to `main`
- Java version: 11 (explicitly set — resolves past version mismatch issues)
- Browser: Chromium headless
- Key historical issue: required `playwright install` step before test run

---

## Engineering Decisions (Interview Talking Points)

- **POM chosen** to separate test logic from UI interaction — improves maintainability
- **Constructor injection** used for `Page` — avoids static state, makes tests independent
- **Native Playwright assertions** used instead of custom methods — reduces dead code, leverages built-in auto-waiting
- **@DataProvider** for login tests — avoids duplicated test methods, makes adding credentials trivial
- **Schema validation in API tests** — validates contract, not just status code
- **Headless enforced in CI** via environment-aware config

---

## What NOT to Change

- Do not add Selenium or WebDriverManager — this is a Playwright-only project
- Do not switch to JUnit — TestNG is intentional (DataProvider, parallel support)
- Do not add Cucumber/BDD layer — project is kept intentionally lightweight for portfolio clarity

---

## Common Commands

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=LoginTest

# Run with headed browser (local only)
# Set headless=false in test setup before running
mvn test
```
