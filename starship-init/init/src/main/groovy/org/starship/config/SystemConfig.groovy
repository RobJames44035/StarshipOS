/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.config

import groovy.util.logging.Slf4j
import org.starship.jna.CLib

/**
 * SystemConfig is responsible for configuring the system through a custom DSL.
 * It allows setting the hostname, defining mount points, spawning tasks, 
 * and applying all configurations.
 *
 * Example usage in DSL:
 *
 * <pre>
 * system {
 *     hostname "starship"
 *     mount "nfs", on: "/mnt/data"
 *     spawn "backup.sh", name: "dailyBackup"
 * }
 * </pre>
 *
 * Methods:
 * - `hostname(String name)`: Sets the system hostname.
 * - `mount(String type, Map options)`: Defines a mount point with the type and options.
 * - `spawn(String command, Map args)`: Spawns a task with the given command and arguments.
 * - `apply()`: Applies all the specified configurations with logging.
 *
 * Stubs are used in place of actual execution for this implementation.
 *
 * Logs are provided using the Slf4j annotation for better traceability.
 */
@Slf4j
class SystemConfig {
    String hostname                        // Stores the hostname
//    List mounts = []                       // Stores mount points
//    List tasks = []                        // Stores spawned tasks

    // Set the system hostname
    void hostname(String name) {
        log.info "Setting hostname to: ${hostname}"
        hostname = name
        CLib.INSTANCE.sethostname(hostname, hostname.length())
    }

    // Define a mount point
//    void mount(String type, Map options) {
//        mounts << [type: type, on: options.on]
//    }

    // Spawn a task
//    void spawn(String command, Map args) {
//        tasks << [command: command, name: args.name]
//    }

    /**
    * Applies the configurations specified through the DSL.
    *
    * This method logs the operations to be performed for setting the
    * hostname, mounting filesystems, and spawning tasks. All operations
    * are stubbed and do not perform actual system changes.
    *
    * Logs:
    * - Hostname configuration if a hostname is set.
    * - Each mount point with its type and target location.
    * - Each spawned task with its name and command.
    */
    void apply() {
        if (hostname) {
            log.info "Setting hostname to: ${hostname}"
            CLib.INSTANCE.sethostname(hostname, hostname.length())
        }

        mounts.each { mount ->
            log.info "Mounting ${mount.type} on ${mount.on} (stubbed)"
            // Stub: Simulate mounting filesystems
        }

        tasks.each { task ->
            log.info "Spawning task '${task.name}': ${task.command} (stubbed)"
            // Stub: Simulate starting processes
        }
    }
}
