# Playwright Java Test Automation Framework

A test automation framework built with Playwright and Java,
using Page Object Model, TestNG, and GitHub Actions CI.

## What it tests

Automated end-to-end tests for [Saucedemo](https://www.saucedemo.com)
covering:
- User login (valid credentials)
- Locked out user error message
- Add product to cart and verify cart contents

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
│   └── CartPage.java
└── tests/
└── LoginTest.java

## How to run locally

**Prerequisites:** Java 23, Maven 3.x

1. Clone the repo:
   git clone https://github.com/gagandeepsudan/playwright-java-framework.git

2. Install browsers (first time only):
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"

3. Run the tests:
   mvn test