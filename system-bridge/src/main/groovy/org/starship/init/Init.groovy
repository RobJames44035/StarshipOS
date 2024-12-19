//file:noinspection unused
//file:noinspection GroovyAssignabilityCheck
package org.starship.init

import com.sun.jna.Native
import groovy.util.logging.Slf4j
import org.starship.jna.CLib
import org.starship.sys.PanicException
import sun.misc.Signal
import sun.misc.SignalHandler

import java.lang.management.ManagementFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

@Slf4j
class Init {
    // Configuration paths
    static final String PRIMARY_CONFIG_PATH = "/etc/starship/config.d/default.config"
    static final String FALLBACK_CONFIG_PATH = "resources/default-init.config"

    // List of running child processes
    static List<Process> childProcesses = [].asSynchronized() as List<Process>
    static Set<String> mountedResources = ConcurrentHashMap.newKeySet()

    // Load a configuration file dynamically
    static void loadConfig(String configPath) {
        try {
            File configFile = new File(configPath)
            if (!configFile.exists()) {
                log "Configuration file not found: ${configPath}. Attempting fallback configuration..."

                // Attempt to load fallback config from the classpath
                URL fallbackConfigUrl = Init.class.getClassLoader().getResource(FALLBACK_CONFIG_PATH)
                if (fallbackConfigUrl == null) {
                    throw new PanicException("Fallback configuration not found in classpath: ${FALLBACK_CONFIG_PATH}")
                } else {
                    log("Using fallback configuration from: ${fallbackConfigUrl}")
                    evaluate(new File(fallbackConfigUrl.toURI()))
                }
            } else {
                // Evaluate primary configuration
                println "Loading configuration from: ${configPath}"
                evaluate(configFile)
            }
        } catch (Exception e) {
            // Handle unexpected errors and call kernel panic
            throw new PanicException("Failed to load configuration due to an error: ${e.message}", e)
        }
    }

    // Include directive for additional configuration files
    static void include(String path) {
        println "Including configuration from: ${path}"
        loadConfig(path) // Recursive call to load the included configuration
    }

    // Reap zombie processes (PID 1 responsibility)
    static void reapZombies() {
        childProcesses.removeIf { process ->
            try {
                if (process.waitFor(0, TimeUnit.SECONDS)) {
                    log("Reaped zombie process with exit code: ${process.exitValue()}")
                    return true
                }
            } catch (Exception e) {
                log("Error while reaping process: ${e.message}")
            }
            return false
        }
    }

    // Shutdown hook for cleanup during SIGTERM or errors
    static void setupShutdownHook() {
        Runtime.runtime.addShutdownHook(new Thread({
            println "Shutdown signal received. Cleaning up..."
            childProcesses.each { it.destroy() } // Terminate all child processes
            println "All spawned processes terminated. Exiting gracefully."
        }))
    }

    // Spawn a child process and register it
    static void spawnProcess(String command, String name) {
        try {
            println "Spawning process '${name}': ${command}"
            ProcessBuilder builder = new ProcessBuilder(command.split(" "))
            Process process = builder.start()
            childProcesses.add(process)
        } catch (Exception e) {
            println "Failed to spawn process '${name}': ${e.message}"
        }
    }

    // Evaluate the configuration for the init process
    static void evaluate(File configFile) {
        if (configFile == null || !configFile.exists()) {
            throw new IllegalArgumentException("Configuration file ${configFile?.name} does not exist or is null.")
        }

        try {
            log("Evaluating configuration file: ${configFile.absolutePath}")

            // Create a new GroovyShell instance
            GroovyShell shell = new GroovyShell()

            // Execute the DSL config file
            shell.evaluate(configFile)

            log("Configuration file evaluated successfully.")
        } catch (Exception e) {
            log("Error while evaluating configuration file: ${e.message}")
            throw e
        }
    }

    @SuppressWarnings('GroovySynchronizationOnNonFinalField')
    static void unmountResource(String mountPoint) {
        synchronized (mountedResources) {
            if (!mountedResources.contains(mountPoint)) {
                log.warn("Attempt to unmount an unknown or already unmounted resource: {}", mountPoint)
                return
            }

            try {
                log.info("Unmounting resource: {} using kernel call...", mountPoint)

                // Call the umount function from libc
                int result = CLib.INSTANCE.umount(mountPoint)

                if (result == 0) {
                    log.info("Successfully unmounted resource: {}", mountPoint)
                    mountedResources.remove(mountPoint) // Remove from tracking
                } else {
                    // Retrieve the errno value for additional error information
                    String errorMessage = Native.getLastError()
                    log.error("Failed to unmount resource {}. Error code: {}", mountPoint, errorMessage)
                }
            } catch (Exception e) {
                log.error("Exception occurred while unmounting resource {}: {}", mountPoint, e.message, e)
            }
        }
    }

    static void shutdown() {
        try {
            log.info("System shutdown initiated...")

            // 1. Stop all spawned child processes
            if (childProcesses.isEmpty()) {
                log.info("No active child processes to terminate.")
            } else {
                childProcesses.each { process ->
                    try {
                        if (process.isAlive()) {
                            log.info("Terminating child process (pid: {})...", process.pid())
                            process.destroy() // Attempt graceful termination
                            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                                log.warn("Forcefully killing process (pid: {})...", process.pid())
                                process.destroyForcibly() // Force termination if it doesn't stop in 5 seconds
                            }
                            log.info("Child process (pid: {}) terminated.", process.pid())
                        }
                    } catch (Exception e) {
                        log.error("Failed to terminate process (pid: {}): {}", process?.pid(), e.message, e)
                    }
                }
                // Clear the list of child processes
                childProcesses.clear()
                log.info("All child processes terminated.")
            }

            // 2. Unmount filesystems (example implementation for mounted resources)
            if (mountedResources.isEmpty()) {
                log.info("No mounted resources to unmount.")
            } else {
                mountedResources.each { mountPoint ->
                    try {
                        log.info("Unmounting resource: {}...", mountPoint)
                        unmountResource(mountPoint)
                        log.info("Resource {} unmounted successfully.", mountPoint)
                    } catch (Exception e) {
                        log.error("Failed to unmount resource {}: {}", mountPoint, e.message, e)
                    }
                }
                mountedResources.clear()
                log.info("All mounted resources unmounted.")
            }

            // OPTIONAL: 3. Cleanup temporary files or logs
            File tempDir = new File("/tmp")
            if (tempDir.exists() && tempDir.isDirectory()) {
                log.info("Cleaning up temporary directory: /tmp")
                boolean cleanedUp = tempDir.deleteDir()
                if (cleanedUp) {
                    log.info("Temporary directory cleaned up successfully.")
                } else {
                    log.warn("Failed to clean up temporary directory: /tmp")
                }
            }

            // 4. Log shutdown completion message
            log.info("System shutdown completed successfully. Goodbye!")

        } catch (Exception e) {
            log.error("Error during shutdown: {}", e.message, e)
            throw e
        }
    }

    // Main method for initialization
    static void main(String[] args) {
        // Check that the process is running as PID 1
        if (ManagementFactory.getRuntimeMXBean().getName().split("@")[0] != "1") {
            log("Warning: This script is not running as PID 1.")
        }

        try {
            log("Initializing StarshipOS as PID 1...")

            // Setup shutdown hook
            setupShutdownHook()

            // Load the primary configuration
            loadConfig(PRIMARY_CONFIG_PATH)

            Signal.handle(new Signal("TERM"), new SignalHandler() {
                @Override
                void handle(Signal signal) {
                    log.info("Caught SIGTERM, shutting down gracefully...")
                    shutdown()
                }
            })

            Signal.handle(new Signal("INT"), new SignalHandler() {
                @Override
                void handle(Signal signal) {
                    log.info("Caught SIGINT, shutting down gracefully...")
                    shutdown()
                }
            })


            // Supervision loop
            //noinspection GroovyInfiniteLoopStatement
            while (true) {
                reapZombies() // Clean up zombie processes
                Thread.sleep(1000) // Reduce CPU usage in the loop
            }
        } catch (Exception e) {
            throw new PanicException("Initialization failed due to: ${e.message}", e)
        }
    }
}
