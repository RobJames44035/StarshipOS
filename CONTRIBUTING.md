# Contributing to StarshipOS

Thank you for your interest in contributing to StarshipOS! Your help is greatly appreciated, and we value all forms of contributions, from reporting issues to writing code and improving documentation.

---

## Table of Contents
1. [Code of Conduct](#code-of-conduct)
2. [How to Contribute](#how-to-contribute)
3. [Getting Started](#getting-started)
4. [Reporting Issues](#reporting-issues)
5. [Submitting Code Changes](#submitting-code-changes)
6. [Style Guide](#style-guide)
7. [Communication](#communication)

---

## Code of Conduct
Please follow our [Code of Conduct](./CODE_OF_CONDUCT.md) to ensure a respectful and inclusive environment for all contributors.

---

## How to Contribute
There are many ways to contribute to StarshipOS:
- Report bugs or suggest features.
- Review and provide feedback on open pull requests.
- Write and improve code or documentation.
- Create or improve tests (unit, functional, or integration).
- Help triage issues and fix bugs.

---

## Getting Started
1. Fork the repository and clone it to your local machine:
   ```bash
   git clone https://github.com/YOUR_USERNAME/StarshipOS.git
   cd StarshipOS
   ```

2. Set up your development environment:
   - Install the required tools for development:
      - Git
      - Java Development Kit (JDK) 17+
      - [Maven](https://maven.apache.org/install.html)
   - Refer to the [setup guide](./SETUP.md) if available or follow the README for additional environment setup
     instructions.

3. Check out the current issues:
   Look for issues in the [issue tracker](https://github.com/YOUR_USERNAME/StarshipOS/issues) and find ones labeled
   `good first issue` or `help wanted` for simpler tasks.

4. Create a new branch for your work:
   ```bash
   git checkout -b your-branch-name
   ```

5. Make your changes, commit them, and push to your fork:
   ```bash
   git add .
   git commit -m "Description of your changes"
   git push origin your-branch-name
   ```

6. Submit a pull request (PR):
   - Open a PR in the [StarshipOS repository](https://github.com/YOUR_USERNAME/StarshipOS/pulls), describing your
     changes and referencing any related issues using `Fixes #<issue_number>`.

---

## Reporting Issues

When reporting issues, please provide as much information as possible, including:

- A detailed description of the problem or a feature request.
- Steps to reproduce the issue, if applicable.
- Relevant logs, screenshots, or error messages.
- The StarshipOS version and environment details (e.g., OS, JDK version).

Please check the [issue tracker](https://github.com/YOUR_USERNAME/StarshipOS/issues) for duplicates before opening a new
issue.

---

## Submitting Code Changes

1. **Keep Changes Focused:** Create PRs that address a single issue or implement a specific feature for easier review.
2. **Strict Adherence to Style:** Follow the Java and Groovy coding standards mentioned in
   the [Style Guide](#style-guide).
3. **Write Tests:** Add relevant unit, functional, and integration tests where applicable:
   - **Unit Tests**: For testing isolated components or methods.
   - **Functional Tests**: To confirm your changes work as intended in operational scenarios.
   - **Integration Tests**: To validate multiple components working together within the system.
4. **Verify Your Changes Locally:** Ensure all tests pass before opening a PR. Run:
   ```bash
   mvn clean install
   ```
   - This will build the project and run all tests defined in the test suite.

5. **Add Documentation:** Update relevant documentation if your changes affect features, APIs, or behavior.

6. **Collaborate on Feedback:** Be prepared to refine your changes based on suggestions from maintainers or other
   contributors.

---

## Style Guide

StarshipOS adheres to **strict Java and Groovy coding standards**. Please ensure that your contributions follow these
guidelines:

1. **Java and Groovy Style Rules**:
   - Format your code according to the **Google Java Style Guide** (or the project's defined Java/Groovy standards if
     stricter).
   - Use proper indentation (4 spaces for Java, 2 or 4 spaces for Groovy, depending on current sources).
   - Avoid overly long lines (maximum line width: 120 characters, unless exceptions are required for clarity).
   - Use meaningful variable and method names that adhere to CamelCase conventions for Java/Groovy.

2. **Clean and Concise Coding Practices**:
   - Avoid overly complicated logic—prefer readability over cleverness.
   - Encapsulate logic in helper methods rather than inlining lengthy sequences.
   - Always remove dead code and unused imports.

3. **Documentation and Comments**:
   - Provide meaningful comments, especially for complex or non-obvious logic.
   - Use proper Javadoc (`/** */`) for public classes, methods, and fields.

4. **Testing Standards**:
   - Follow a **test-first approach** where possible (e.g., TDD).
   - Group related tests logically and name them descriptively.

5. **Consistent Build Tool Dependencies:**
   - Ensure changes to dependencies in `pom.xml` are approved and necessary.

To ensure your code meets the required formatting rules, use the project's coding tools or plugins:

- Run the following Maven command to verify code format and tests:
  ```bash
  mvn spotless:apply verify
  mvn clean install
  ```

---

## Communication

Effective communication helps ensure success for everyone. Here’s how you can stay updated or contribute to discussions:

- **GitHub Issues and Discussions**: Use [GitHub Discussions](https://github.com/YOUR_USERNAME/StarshipOS/discussions)
  for general queries or feature suggestions.
- **Real-Time Conversations**: Join our chat on **[Slack/Discord/Matrix]** (replace with the link to your community
  platform).
- **Contributor Meetings**: Participate in virtual developer meetups (details announced in GitHub Discussions or
  forums).

For any critical or sensitive matters, you can directly email the maintainers at **[your-email@domain.com]**.

---

Thank you again for contributing to StarshipOS! We’re excited to have you as part of our galaxy-spanning mission 🚀.
