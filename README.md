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