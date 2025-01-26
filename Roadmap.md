# StarshipOS Roadmap

This document outlines the development roadmap for StarshipOS. The focus is on building a robust, modular, and production-ready OS with its custom Init system (`Init`) and supporting components like `BundleManager` and an SDK for developing apps.

The tasks are prioritized to ensure foundational stability early, with incremental steps toward a fully functional OS.

---

## Roadmap Tasks

### 1. Connect Remote Debugging to Init
- **Goal**: Enable remote debugging for the Init process.
- **Why**: Debugging Init is critical for understanding its runtime behavior and resolving issues quickly.
- **Key Steps**:
    - Set up remote JVM debugging.
    - Attach remote debugging tools (e.g., IntelliJ, VisualVM).
    - Verify logs and output can be monitored remotely.

---

### 2. Build a Bootable Flash Drive
- **Goal**: Create a StarshipOS image that boots from a USB flash drive using UEFI.
- **Why**: Running StarshipOS directly on hardware ensures that it behaves like a fully independent system.
- **Key Steps**:
    - Configure the image generator to create a bootable UEFI flash drive.
    - Test both with and without GRUB.
    - Verify Init starts as PID 1 post-boot.

---

### 3. Debug Init and Make It Production Quality
- **Goal**: Stabilize Init as the foundational process of StarshipOS.
- **Why**: Init is responsible for process management, resource cleanup, and supervisor loops. It must be robust and bug-free to support the whole system.
- **Key Steps**:
    - Monitor edge-case failures (e.g., missing heartbeats, zombie process handling).
    - Harden logging and error-handling mechanisms.
    - Write unit tests for key features.

---

### 4. Expand and Get BundleManager Functional
- **Goal**: Implement a functional version of BundleManager (beyond the stub).
- **Why**: BundleManager handles modular service management, making it essential for the OS and SDK workflows.
- **Key Steps**:
    - Establish dynamic bundle loading (e.g., JARs or scripts).
    - Add bundle lifecycle management (start, stop, restart).
    - Test bundle communications via heartbeats with Init.

---

### 5. Debug and Make BundleManager Production Quality
- **Goal**: Refine the BundleManager with rigorous testing and robustness improvements.
- **Why**: A stable BundleManager ensures services run reliably and have clear failure recovery mechanisms.
- **Key Steps**:
    - Expand the feature set (e.g., inter-bundle communication, configuration fetch).
    - Add error recovery logic for failed bundles.
    - Stress test with multiple bundles.

---

### 6. Provide Installation Scripts for Packaged Artifacts
- **Goal**: Develop scripts for installing/replacing system artifacts (e.g., bundles, configurations).
- **Why**: Well-written scripts make it easy to deploy and update the OS and its components for new environments or versions.
- **Key Steps**:
    - Define how artifacts are packaged.
    - Write installation scripts to deploy bundles, configs, and libraries.
    - Test script idempotence and reliability under edge cases.

---

### 7. Integrate D-Bus / dbus-cxx for IPC
- **Goal**: Integrate `dbus` and `dbus-cxx` to enable inter-process communication for StarshipOS modules and apps.
- **Why**: D-Bus is a flexible and widely-used message bus, providing a standardized way for applications and modules to communicate.
- **Key Steps**:
    - Set up dbus-daemon to run within StarshipOS.
    - Provide examples of services communicating via D-Bus.
    - Test performance and compatibility with the OS.

---

### 8. Design and Build SDK
- **Goal**: Develop an SDK for crafting apps and bundles for StarshipOS.
- **Why**: App developers will need tools, libraries, and APIs to build and deploy to StarshipOS.
- **Key Steps**:
    - Define SDK structure (libraries, scripts, docs).
    - Provide API bindings for system features (e.g., D-Bus, lifecycle hooks).
    - Write installation and usage guides.

---

### 9. Write a Hello World CLI App
- **Goal**: Develop a command-line "Hello World" app for StarshipOS.
- **Why**: A simple CLI app proves that the SDK, Init, and BundleManager can work together to deploy and execute a basic application.
- **Key Steps**:
    - Use the SDK to create a basic CLI app.
    - Deploy and execute the app as a bundle.
    - Verify output (e.g., print "Hello World" to the console).

---

### 10. Write a JavaFX Hello World
- **Goal**: Create a graphical "Hello World" app using JavaFX.
- **Why**: Demonstrates StarshipOS's ability to support advanced apps with GUIs.
- **Key Steps**:
    - Write a JavaFX app with a basic GUI (e.g., label/button).
    - Integrate JavaFX runtime support into the OS.
    - Test deployment as a bundle or standalone app.

---

## Notes on Task Order
The tasks are listed in a logical order, but some development can occur in parallel. For instance:
- Debugging Init (Task 3) can happen alongside building a bootable flash (Task 2).
- Expanding BundleManager (Task 4) can begin before Init is fully production quality but should prioritize functionality over perfection.

Focus on foundational tasks before moving toward user-facing deliverables like the Hello World apps.

---

## Conclusion
This roadmap provides structured steps to guide StarshipOS into becoming a robust custom operating system. Each task builds upon the previous ones, ensuring stability and modularity at every stage. As you progress, revisit and refine this roadmap to adapt to new challenges or innovations discovered along the way.

Happy hacking! 🚀# StarshipOS Roadmap

This document outlines the development roadmap for StarshipOS. The focus is on building a robust, modular, and production-ready OS with its custom Init system (`Init`) and supporting components like `BundleManager` and an SDK for developing apps.

The tasks are prioritized to ensure foundational stability early, with incremental steps toward a fully functional OS.

---

## Roadmap Tasks

### 1. Connect Remote Debugging to Init
- **Goal**: Enable remote debugging for the Init process.
- **Why**: Debugging Init is critical for understanding its runtime behavior and resolving issues quickly.
- **Key Steps**:
    - Set up remote JVM debugging.
    - Attach remote debugging tools (e.g., IntelliJ, VisualVM).
    - Verify logs and output can be monitored remotely.

---

### 2. Build a Bootable Flash Drive
- **Goal**: Create a StarshipOS image that boots from a USB flash drive using UEFI.
- **Why**: Running StarshipOS directly on hardware ensures that it behaves like a fully independent system.
- **Key Steps**:
    - Configure the image generator to create a bootable UEFI flash drive.
    - Test both with and without GRUB.
    - Verify Init starts as PID 1 post-boot.

---

### 3. Debug Init and Make It Production Quality
- **Goal**: Stabilize Init as the foundational process of StarshipOS.
- **Why**: Init is responsible for process management, resource cleanup, and supervisor loops. It must be robust and bug-free to support the whole system.
- **Key Steps**:
    - Monitor edge-case failures (e.g., missing heartbeats, zombie process handling).
    - Harden logging and error-handling mechanisms.
    - Write unit tests for key features.

---

### 4. Expand and Get BundleManager Functional
- **Goal**: Implement a functional version of BundleManager (beyond the stub).
- **Why**: BundleManager handles modular service management, making it essential for the OS and SDK workflows.
- **Key Steps**:
    - Establish dynamic bundle loading (e.g., JARs or scripts).
    - Add bundle lifecycle management (start, stop, restart).
    - Test bundle communications via heartbeats with Init.

---

### 5. Debug and Make BundleManager Production Quality
- **Goal**: Refine the BundleManager with rigorous testing and robustness improvements.
- **Why**: A stable BundleManager ensures services run reliably and have clear failure recovery mechanisms.
- **Key Steps**:
    - Expand the feature set (e.g., inter-bundle communication, configuration fetch).
    - Add error recovery logic for failed bundles.
    - Stress test with multiple bundles.

---

### 6. Provide Installation Scripts for Packaged Artifacts
- **Goal**: Develop scripts for installing/replacing system artifacts (e.g., bundles, configurations).
- **Why**: Well-written scripts make it easy to deploy and update the OS and its components for new environments or versions.
- **Key Steps**:
    - Define how artifacts are packaged.
    - Write installation scripts to deploy bundles, configs, and libraries.
    - Test script idempotence and reliability under edge cases.

---

### 7. Integrate D-Bus / dbus-cxx for IPC
- **Goal**: Integrate `dbus` and `dbus-cxx` to enable inter-process communication for StarshipOS modules and apps.
- **Why**: D-Bus is a flexible and widely-used message bus, providing a standardized way for applications and modules to communicate.
- **Key Steps**:
    - Set up dbus-daemon to run within StarshipOS.
    - Provide examples of services communicating via D-Bus.
    - Test performance and compatibility with the OS.

---

### 8. Design and Build SDK
- **Goal**: Develop an SDK for crafting apps and bundles for StarshipOS.
- **Why**: App developers will need tools, libraries, and APIs to build and deploy to StarshipOS.
- **Key Steps**:
    - Define SDK structure (libraries, scripts, docs).
    - Provide API bindings for system features (e.g., D-Bus, lifecycle hooks).
    - Write installation and usage guides.

---

### 9. Write a Hello World CLI App
- **Goal**: Develop a command-line "Hello World" app for StarshipOS.
- **Why**: A simple CLI app proves that the SDK, Init, and BundleManager can work together to deploy and execute a basic application.
- **Key Steps**:
    - Use the SDK to create a basic CLI app.
    - Deploy and execute the app as a bundle.
    - Verify output (e.g., print "Hello World" to the console).

---

### 10. Write a JavaFX Hello World
- **Goal**: Create a graphical "Hello World" app using JavaFX.
- **Why**: Demonstrates StarshipOS's ability to support advanced apps with GUIs.
- **Key Steps**:
    - Write a JavaFX app with a basic GUI (e.g., label/button).
    - Integrate JavaFX runtime support into the OS.
    - Test deployment as a bundle or standalone app.

---

## Notes on Task Order
The tasks are listed in a logical order, but some development can occur in parallel. For instance:
- Debugging Init (Task 3) can happen alongside building a bootable flash (Task 2).
- Expanding BundleManager (Task 4) can begin before Init is fully production quality but should prioritize functionality over perfection.

Focus on foundational tasks before moving toward user-facing deliverables like the Hello World apps.

---

## Conclusion
This roadmap provides structured steps to guide StarshipOS into becoming a robust custom operating system. Each task builds upon the previous ones, ensuring stability and modularity at every stage. As you progress, revisit and refine this roadmap to adapt to new challenges or innovations discovered along the way.

Happy hacking! 🚀