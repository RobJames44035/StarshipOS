# StarshipOS Roadmap

This document outlines the development roadmap for StarshipOS. The focus is on building a robust, modular, and production-ready OS with its custom Init system (`Init`) and supporting components like `BundleManager` and an SDK for developing apps.

The tasks are prioritized to ensure foundational stability early, with incremental steps toward a fully functional OS.

# Project Roadmap: Object-Oriented, Fileless, Event-Driven OS

## Epic 1: Setup and Basic System Architecture

### Story 1.1: Build Semi-Functional Init System
**As a developer**, I want to set up a basic init system based on Groovy and Buildroot so that the system can initialize and start processes.
- **Tasks**:
  - Define init system structure (process management, boot process).
  - Implement basic Groovy DSL for system initialization.
  - Test system to ensure it boots to a shell.

### Story 1.2: Memory Management and JVM Process Allocation
**As a developer**, I want to map a large swap partition and allocate memory directly to JVM processes so that they have access to enough memory.
- **Tasks**:
  - Implement memory mapping to the swap partition.
  - Integrate with JVM processes to dynamically allocate memory.
  - Test memory allocation and ensure processes are stable.

---

## Epic 2: Core Object-Oriented System Features

### Story 2.1: Event-Driven Architecture Implementation
**As a developer**, I want to implement an event-driven architecture (pub/sub) so that processes can interact asynchronously.
- **Tasks**:
  - Design the pub/sub model and define event classes.
  - Implement system to handle events (publish, subscribe).
  - Create example events to simulate real-world interactions (e.g., process events).

### Story 2.2: Build OSGi Container for System Services
**As a developer**, I want to create an OSGi container to manage both system and userland services so that services are modular and can be started/stopped independently.
- **Tasks**:
  - Set up the Apache Felix framework for OSGi.
  - Integrate services as OSGi bundles.
  - Test OSGi container for proper service management.

### Story 2.3: ActiveMQ Integration
**As a developer**, I want to integrate ActiveMQ into the system to handle messaging between processes and components.
- **Tasks**:
  - Set up ActiveMQ instance.
  - Integrate ActiveMQ with the pub/sub system.
  - Create sample messages and test system communication.

---

## Epic 3: Memory Management and Dynamic Placement

### Story 3.1: Entropy and Flag Integration in Objects
**As a developer**, I want to modify Java's `Object` class to include entropy and flags so that objects can have dynamic memory placement.
- **Tasks**:
  - Implement `entropy` (0-1) and flags in `Object`.
  - Modify memory allocation logic based on entropy values.
  - Test the integration and verify that the flags/entropy are working correctly.

### Story 3.2: GC Modifications with Machine Learning
**As a developer**, I want to modify the garbage collector to use machine learning models for intelligent object placement between RAM and SSD.
- **Tasks**:
  - Implement a machine learning model to predict object behavior.
  - Integrate model with GC for dynamic placement of objects.
  - Test GC performance and optimize memory usage.

---

## Epic 4: Basic User Interface (JShell)

### Story 4.1: Develop Basic JShell Interface
**As a user**, I want to interact with the system via JShell so that I can test and issue commands to the OS.
- **Tasks**:
  - Set up JShell environment.
  - Create basic commands for interacting with the OS.
  - Test system interaction through the JShell interface.

---

## Epic 5: 3D JavaFX GUI Development

### Story 5.1: Basic 3D Rendering Setup
**As a developer**, I want to set up a 3D JavaFX rendering pipeline to display the first-person shooter GUI.
- **Tasks**:
  - Implement a basic 3D scene.
  - Add camera and navigation controls (WASD, mouse).
  - Test basic rendering and controls.

### Story 5.2: Interactive Event-Driven 3D Interface
**As a user**, I want to interact with the 3D world using events so that I can control objects and navigate the scene.
- **Tasks**:
  - Integrate event system with 3D interface (e.g., user input, object interaction).
  - Ensure smooth transitions between actions (e.g., moving, interacting).
  - Test interaction within the 3D space.

---

## Epic 6: VR Adaptation (Future)

### Story 6.1: Research VR Integration
**As a developer**, I want to research how to integrate VR into the OS so that the user can interact with the system in an immersive way.
- **Tasks**:
  - Investigate VR frameworks (e.g., OpenVR, Oculus SDK).
  - Explore how to integrate VR controls (e.g., hand tracking, movement).
  - Develop a prototype for VR interaction.

### Story 6.2: VR Event-Driven Interaction
**As a user**, I want to interact with the system in VR using event-driven controls, so that I can control the system in an immersive environment.
- **Tasks**:
  - Implement VR input handling (gesture tracking, movement).
  - Integrate with the event system for VR actions.
  - Test user interactions in VR and refine experience.

---

## Milestones:

- **M1**: Semi-functional init system with memory allocation (3 months).
- **M2**: Event-driven architecture and OSGi container (6 months).
- **M3**: Basic JShell interface and core memory management (9 months).
- **M4**: 3D JavaFX GUI and interactive system (12 months).
- **M5**: VR adaptation and user testing (24 months).
