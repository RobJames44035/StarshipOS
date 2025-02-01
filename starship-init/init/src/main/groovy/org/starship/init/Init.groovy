/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

//file:noinspection unused
//file:noinspection GroovyAssignabilityCheck
//file:noinspection GroovyInfiniteLoopStatement
package org.starship.init

import groovy.util.logging.Slf4j
import org.starship.sys.PanicException

import java.lang.management.ManagementFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

//import org.starship.eventcore.SystemEventBus

/**
 * Main class responsible for initializing and managing the Starship system services.
 *
 * This class includes methods for managing the lifecycle of the BundleManager, 
 * handling heartbeats, and loading configuration files. Upon failure to 
 * initialize critical components, the system triggers a panic.
 */
@Slf4j
class Init {

    static String PRIMARY_CONFIG_PATH = "/etc/starship/config.d/init.cfg"
    static String FALLBACK_CONFIG_PATH = "/default-init.cff"

    // List of running child processes
    static final ConcurrentHashMap<String, Object> processTable = new ConcurrentHashMap<>()
    static final ConcurrentHashMap<String, Object> resourceTable = new ConcurrentHashMap<>()

    /**
     * This method is responsible for configuring the JVM and ensuring that the necessary system libraries
     * (such as libc) are properly loaded and available for use during runtime.
     *
     * Key functionalities:
     * - Logs the start and successful completion of the library loading process.
     * - Uses System.loadLibrary to load required native libraries.
     * - If library loading fails, logs the error and throws a RuntimeException to terminate execution.
     *
     * @throws RuntimeException if critical libraries cannot be loaded, halting the program.
     */
    static void main(String[] args) {
        // Check running as PID 1
        if (ManagementFactory.getRuntimeMXBean().getName().split("@")[0] != "1") {
            log.warn("Warning: This program is not running as PID 1.")
        } else {
            processTable.put("org.starship.init.Init", this)
        }

        try {
            log.info("Initializing StarshipOS...")
            setupShutdownHook()
            configureSystem()
            log.info("System up.")
            supervisorLoop()
        } catch (Exception e) {
            log.error("Error in supervisor loop: ${e.message}", e)
        }
    }

    /**
     * load the DSL from: 1) /etc/starship/conf.d/init.groovy 2) resource classpath:/default-init.groovy
     *
     * @return true if the method succeeded or false if it didn't.
     */
    static boolean configureSystem() {
        boolean success = false
        try {
            File configFile = new File(PRIMARY_CONFIG_PATH)
            String configContent = null

            if (!configFile.exists()) {
                log.warn("Primary config not found at $PRIMARY_CONFIG_PATH. Attempting to load fallback config: default-init.cfg")

                // Load fallback config directly into memory
                def fallbackStream = Init.class.classLoader.getResourceAsStream("default-init.cfg")
                if (fallbackStream == null) {
                    log.error("Fallback configuration 'default-init.cfg' not found in classpath resources.")

                    // Both primary and fallback configurations failed
                    throw new PanicException("No configuration found. System initialization cannot proceed.")
                }

                // Load the fallback config content into a String
                configContent = fallbackStream.text
                log.info("Fallback configuration loaded from resources.")
            } else {
                log.info("Primary configuration file found: ${configFile.absolutePath}")
            }

            // Use either the loaded fallback content or the primary config file
            if (!configContent) {
                InitUtil.getInstance().configureSystem(configFile)
            } else {
                InitUtil.getInstance().configureSystem(configContent)
            }

            return true // Successfully configured the system
        } catch (Exception e) {
            log.error("Error occurred during system configuration: ${e.message ?: 'Unknown error'}", e)
            throw new PanicException("PANIC: ${e.message ?: 'Unknown error'}", e)
        }
    }
    /**
     * Sets up a shutdown hook that performs necessary cleanup when the
     * application terminates.
     *
     * Registered actions include:
     * - Logging the shutdown signal,
     * - Terminating all spawned child processes,
     * - Logging the completion of the shutdown process.
     *
     * This ensures that all system resources are gracefully released during
     * application shutdown.
     */
    static void setupShutdownHook() {
        Runtime.runtime.addShutdownHook(new Thread({
            log.info("Shutdown signal received. Cleaning up...")
            processTable.each { it.destroy() } // Terminate all child processes
            log.info("All spawned processes terminated. Exiting gracefully.")
        }))
    }

    /**
     * Registers a shutdown hook to ensure graceful cleanup during application termination.
     *
     * This method creates and registers a shutdown hook that triggers the `shutdown` method
     * when the JVM is shutting down. This ensures that critical resources, such as child processes
     * and mounted filesystems, are properly released even during unexpected exits or termination signals.
     */
    static void shutdown() {
        try {
            log.info("System shutdown initiated...")

            // 1. Stop all spawned child processes
            if (processTable.isEmpty()) {
                log.info("No active child processes to terminate.")
            } else {
                processTable.each { process ->
                    try {
                        if (process.isAlive()) {
                            log.info("Terminating child process (pid: {})...", process.pid())
                            process.destroy() // Attempt graceful termination
                            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                                log.warn("Forcefully killing process (pid: {})...", process.pid())
                                process.destroy() // Force termination if it doesn't stop in 5 seconds
                            }
                            log.info("Child process (pid: {}) terminated.", process.pid())
                        }
                    } catch (Exception e) {
                        log.error("Failed to terminate process (pid: {}): {}", process?.pid(), e.message, e)
                    }
                }
                // Clear the list of child processes
                processTable.clear()
                log.info("All child processes terminated.")
            }

            // 2. Unmount filesystems (example implementation for mounted resources)
            if (resourceTable.isEmpty()) {
                log.info("No mounted resources to unmount.")
            } else {
                resourceTable.each { mountPoint ->
                    try {
                        log.info("Unmounting resource: {}...", mountPoint)
                        umount(mountPoint)
                        log.info("Resource {} unmounted successfully.", mountPoint)
                    } catch (Exception e) {
                        log.error("Failed to unmount resource {}: {}", mountPoint, e.message, e)
                    }
                }
                resourceTable.clear()
                log.info("All mounted resources unmounted.")
            }

            // 3. Cleanup temporary files or logs (optional)
            log.info("Shutdown completed successfully. Goodbye!")

        } catch (Exception e) {
            log.error("Error during shutdown: {}", e.message, e)
            throw e
        }
    }

    /**
     * The supervision loop that runs indefinitely to monitor and manage system state.
     *
     * Key functionalities include:
     * - Periodically cleaning up zombie processes to maintain system stability.
     * - Monitoring the status of essential components, such as the BundleManager,
     *   and attempting restarts if necessary (logic commented out for now).
     *
     * The loop executes in intervals, allowing the system to perform checks and maintenance
     * tasks regularly, while handling any exceptions gracefully to ensure continuity.
     *
     * Logs the status of the loop and any errors encountered during execution.
     *
     * This method does not return and functions as the primary controller
     * for supervising the overall system behavior.
     */
    static void supervisorLoop() {
        while (true) {
            log.info("Supervision loop...")
            try {
                reapZombies() // Clean up zombie processes
                Thread.sleep(1000) // Supervisor polling interval
            } catch (Exception e) {
                log.error("Error in supervisor loop: ${e.message}", e)
            }
        }
    }

    /**
     * Reaps zombie processes spawned by the application.
     * This is a responsibility of a process running with PID 1,
     * typically in containerized or similar restricted environments.
     *
     * Ensures that defunct child processes are properly reaped by checking their status.
     * If a child process has exited:
     * - Logs its status,
     * - Removes it from the `childProcesses` list.
     *
     * Processes with PID 1 are safeguarded from being reaped.
     *
     * If any errors occur during the operation, they are logged, but the process
     * continues to ensure all manageable child processes are reaped.
     */
    static void reapZombies() {
        log.info("Zombie Reaper.")
        processTable.remove { process ->
            try {
                // Safeguard to prevent reaping PID 1
                if (process.pid() == 1) {
                    log.warn("Attempted to reap PID 1, ignoring.")
                    return false // Do not remove PID 1 from the childProcesses list
                }

                if (process.waitFor(0, TimeUnit.SECONDS)) {
                    log.info("Reaped zombie process with PID: {} and exit code: {}", process.pid(), process.exitValue())
                    return true // Process successfully reaped and should be removed
                }

            } catch (Exception e) {
                log.error("Error while attempting to reap process (PID: {}): {}", process?.pid(), e.message, e)
            }

            log.info("\tFinished Reaping Zombies.")
            return false // Process was not reaped or encountered an error
        }
    }

}
