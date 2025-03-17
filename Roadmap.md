# StarshipOS Roadmap

This document outlines the development roadmap for StarshipOS. The focus is on building a robust, modular, and
production-ready OS with its custom Init system (`Init`) and supporting components like `BundleManager` and an SDK for
developing apps. Over time, StarshipOS will evolve into a highly scalable, **object-oriented, fileless, event-driven**
OS, with support for advanced features like artificial intelligence, 3D interfaces, and eventually VR.

The roadmap tasks are prioritized to ensure foundational stability early, with incremental steps toward a fully
functional and innovative OS.

---

## Project Roadmap: Object-Oriented, Fileless, Event-Driven OS

### Epic 1: Setup and Basic System Architecture

#### Story 1.1: Build Semi-Functional Init System
**As a developer**, I want to set up a basic init system based on Groovy and Buildroot so that the system can initialize and start processes.

- **Tasks**:
  - Define the init system structure (process management, boot process).
  - Implement a basic Groovy DSL for system initialization.
  - Test the system to ensure it boots to a shell.

#### Story 1.2: Memory Management and JVM Process Allocation

**As a developer**, I want to map a swap partition and allocate memory directly to JVM processes so that they have
access to sufficient memory.

- **Tasks**:
  - Implement memory mapping to a swap partition.
  - Integrate with JVM processes for dynamic memory allocation.
  - Test memory allocation logic and ensure stability.

#### Story 1.3: Ext2/4 Disk Image Integration

**As a developer**, I want to use an ext2/4 disk image to improve compatibility and prepare for future flash drive
booting capabilities.

- **Tasks**:
  - Replace QCOW2 with ext2/4 as the default filesystem image.
  - Test read/write access for the ext2/4 image.
  - Ensure compatibility with tools for creating bootable flash drives.
  - Prepare scripts for converting ext2/4 images to USB bootable drives.

---

### Epic 2: Core Object-Oriented System Features

#### Story 2.1: Event-Driven Architecture Implementation
**As a developer**, I want to implement an event-driven architecture (pub/sub) so that processes can interact asynchronously.

- **Tasks**:
  - Design the pub/sub model and define core event classes.
  - Implement a system to handle events (publish, subscribe).
  - Create example events to simulate real-world interactions (e.g., process events).

#### Story 2.2: Build OSGi Container for System Services

**As a developer**, I want to create an OSGi container to manage system and userland services so that services are
modular and can be started/stopped independently.

- **Tasks**:
  - Set up Apache Felix or another OSGi implementation.
  - Convert system services to modular OSGi bundles.
  - Test the system for proper service management.

#### Story 2.3: Messaging System with ActiveMQ

**As a developer**, I want to integrate ActiveMQ into the system to enable efficient messaging between processes.

- **Tasks**:
  - Set up a lightweight ActiveMQ instance.
  - Integrate ActiveMQ with the core event system.
  - Test messaging across system components.

---

### Epic 3: Advanced Memory Management and Optimization

#### Story 3.1: Entropy and Flag Integration in Objects

**As a developer**, I want to enhance the Java `Object` class by adding custom fields such as `entropy` and flags to
enable dynamic memory placement.

- **Tasks**:
  - Modify Java's `Object` class to include `entropy` (0-1) and flags.
  - Adjust object memory allocation based on entropy/flags.
  - Test changes and verify system performance improvements.

#### Story 3.2: Garbage Collector Machine Learning Integration

**As a developer**, I want to modify the garbage collector to use machine learning for intelligent object placement
between RAM and SSD.

- **Tasks**:
  - Develop and train a basic machine learning model to predict object lifecycle.
  - Integrate the model with the garbage collector.
  - Test and optimize GC to ensure memory usage is optimal.

---

### Epic 4: Basic User Interface (Interactive Shell)

#### Story 4.1: Develop Basic Shell Interface

**As a user**, I want to interact with the OS using a shell interface so that I can test and issue commands to the
system.

- **Tasks**:
  - Create a lightweight shell environment (e.g., JShell or Groovy scripting).
  - Implement basic commands for system interactions (e.g., list processes, start services).
  - Test interaction between the shell and core system.

#### Story 4.2: Disk Image Debugging and Management

**As a developer**, I want to use the shell to debug and manage ext2/4 disk images directly from the OS.

- **Tasks**:
  - Add commands for mounting and inspecting ext2/4 disk images.
  - Integrate filesystem tools like `fsck` for on-demand repair or validation.

---

### Epic 5: 3D JavaFX GUI

#### Story 5.1: Basic 3D Rendering

**As a developer**, I want to set up a basic 3D rendering pipeline using JavaFX to lay the foundation for a 3D
interactive GUI.

- **Tasks**:
  - Implement a simple 3D scene.
  - Add camera and navigation controls (WASD, mouse).
  - Test rendering and basic scene interaction.

#### Story 5.2: Event-Driven 3D Interface

**As a user**, I want to interact with the 3D GUI using events so that I can control objects and navigate dynamically.

- **Tasks**:
  - Connect the core event system to the 3D interface (e.g., input handling, object interaction).
  - Implement animations and smooth transitions for user actions.
  - Test 3D interactions for responsiveness.

---

### Epic 6: Bootable Flash Drive and VR Support (Future)

#### Story 6.1: Bootable Flash Drive Creation

**As a developer**, I want to generate bootable flash drives from ext2/4 disk images so that StarshipOS can run on real
hardware.

- **Tasks**:
  - Develop a script to write ext2/4 images to flash drives (e.g., using `dd`).
  - Test bootability and compatibility across hardware configurations.
  - Optimize boot performance for embedded systems.

#### Story 6.2: Research VR Integration

**As a developer**, I want to research VR frameworks to create an immersive OS interface in virtual environments.

- **Tasks**:
  - Investigate VR toolkits (e.g., OpenVR, Oculus SDK).
  - Research input handling for VR controls (gesture tracking, movement).
  - Develop a prototype for VR interaction with the OS.

---

## Milestones

### **M1**: Foundation and Basic Functionality (3 months)

- Semi-functional init system capable of booting and managing processes.
- Memory allocation and ext2/4 disk management.

### **M2**: Core System and Pub/Sub Architecture (6 months)

- Event-driven architecture implemented with ActiveMQ.
- OSGi container supporting modular service management.

### **M3**: Memory Enhancements and Shell Interface (9 months)

- Advanced memory management with custom GC improvements.
- Functional shell for interacting with the OS.

### **M4**: 3D GUI and Flash Drive Support (12 months)

- 3D interactive GUI implemented in JavaFX.
- Support for bootable flash drives from ext2/4 disk images.

### **M5**: VR Integration and Testing (24 months)

- Immersive VR interaction prototype with event-driven mechanics.
- Stability testing for core system features and VR implementation.

---

This roadmap is subject to updates as the project progresses, with feedback from users and contributors helping to
refine priorities and add new features.