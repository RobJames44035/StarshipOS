/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

//file:noinspection unused
//file:noinspection GroovyAssignabilityCheck
//file:noinspection GroovyInfiniteLoopStatement
package org.starship.init

import com.sun.jna.Native
import groovy.util.logging.Slf4j
import org.starship.config.LoggingConfig
import org.starship.config.SystemConfig
import org.starship.jna.CLib

import java.lang.management.ManagementFactory

//import org.starship.eventcore.SystemEventBus

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Main class responsible for initializing and managing the Starship system services.
 *
 * This class includes methods for managing the lifecycle of the BundleManager, 
 * handling heartbeats, and loading configuration files. Upon failure to 
 * initialize critical components, the system triggers a panic.
 */
@Slf4j
class Init implements SystemConfig, SystemConfig, LoggingConfig {

    static String PRIMARY_CONFIG_PATH = "/etc/starship/config.d/init.groovy"
    static String FALLBACK_CONFIG_PATH = "/default-init.groovy"

    // List of running child processes
    static final ConcurrentHashMap<String, Object> processTable = new ConcurrentHashMap<>()
    static final ConcurrentHashMap<String, Object> resourceTable = new ConcurrentHashMap<>()

    // Track the last heartbeat time
    static volatile long lastHeartbeatTimestamp = 0


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
        childProcesses.removeIf { process ->
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
            childProcesses.each { it.destroy() } // Terminate all child processes
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
                        umount(mountPoint)
                        log.info("Resource {} unmounted successfully.", mountPoint)
                    } catch (Exception e) {
                        log.error("Failed to unmount resource {}: {}", mountPoint, e.message, e)
                    }
                }
                mountedResources.clear()
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
            // A SINGLE method to consume the DSL and initialize StarshipOS.
            // Reads the DSL, parses it and handles ALL that's in the DSL.
//            configureSystem() <- TODO: This is where we configure the system at startup
            log.info("System up.")
            supervisorLoop()
        } catch (Exception e) {
            log.error("Error in supervisor loop: ${e.message}", e)
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
     * Mount a filesystem.
     * @param type the source of the mount (e.g., "proc", "sysfs")
     * @param path the mount point (e.g., "/proc", "/sys")
     * @param fsType the filesystem type (e.g., "proc", "sysfs")
     * @param flags optional mount flags
     * @param data optional data passed to the mount
     */
    @Override
    void mount(String type, String path, String fsType, long flags, String data) {
        // Validate required inputs
        if (!type || !path || !fsType) {
            throw new IllegalArgumentException("Invalid input: [type, path, fsType] must not be null or empty.")
        }

        // Call CLib's mount function
        int result
        try {
            // If 'data' is null, CLib will handle it properly
            result = CLib.INSTANCE.mount(type, path, fsType, flags, data)
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while calling the native mount function.", e)
        }

        // Handle the result
        if (result != 0) {
            // On failure, fetch errno and generate an appropriate error message
            int errno = Native.getLastError() // JNA retrieves errno this way
            String errorMessage = "Mount failed for type='${type}' at path='${path}'. errno=${errno} (${strerror(errno)})"
            throw new RuntimeException(errorMessage)
        }

        // Success log
        log.info("Successfully mounted type='${type}' to path='${path}' with filesystem='${fsType}'")
    }

    /**
     * Unmount a filesystem at the specified path.
     * @param path the mount point path to unmount
     */
    @Override
    void umount(String path) {
        try {
            // Validate input
            if (!path || path.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid input: The path to unmount must not be null or empty.")
            }

            // Call CLib's umount function
            int result = CLib.INSTANCE.umount(path)

            // Handle result
            if (result != 0) {
                int errno = Native.getLastError() // Get error code
                String errorMessage = "Unmount failed for path='${path}'. errno=${errno} (${strerror(errno)})"
                throw new RuntimeException(errorMessage)
            }

            // Log success
            log.info("Successfully unmounted path='${path}'")
        } catch (IllegalArgumentException e) {
            // Handle invalid input gracefully
            log.error("Invalid input: ${e.message}")
            throw e
        } catch (RuntimeException e) {
            // Handle runtime errors (e.g., native umount errors)
            log.error("[Runtime error during unmount: ${e.message}")
            throw e
        } catch (Exception e) {
            // Catch-all for unexpected errors
            log.error("Unexpected error during unmount: ${e.message}")
            throw new RuntimeException("An unexpected error occurred during unmount.", e)
        }
    }

    /**
     * Check if a given path is already mounted.
     * @param path the mount point path to check
     * @return true if mounted, false otherwise
     */
    @Override
    boolean mountpoint(String path) {
        boolean success = false
        try {
            // Validate input
            if (!path || path.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid input: The path must not be null or empty.")
            }

            // Allocate two Stat structures: one for the path, one for its parent
            CLib.Stat statPath = new CLib.Stat()
            CLib.Stat statParent = new CLib.Stat()

            // Call lstat for the given path
            int resultPath = CLib.INSTANCE.lstat(path, statPath)
            if (resultPath != 0) {
                int errno = Native.getLastError()
                throw new RuntimeException("Failed to retrieve lstat for path='${path}'. errno=${errno} (${strerror(errno)})")
            }

            // Call lstat for the parent directory
            String parentPath = path.endsWith("/") ? "${path}.." : "${path}/.."
            int resultParent = CLib.INSTANCE.lstat(parentPath, statParent)
            if (resultParent != 0) {
                int errno = Native.getLastError()
                throw new RuntimeException("Failed to retrieve lstat for parent path='${parentPath}'. errno=${errno} (${strerror(errno)})")
            }

            // Compare st_dev of the path and its parent to determine if it's a mount point
            success = (statPath.st_dev != statParent.st_dev)
        } catch (IllegalArgumentException e) {
            log.error("Invalid input: ${e.message}", e)
        } catch (RuntimeException e) {
            log.error("Runtime error in mountpoint check: ${e.message}", e)
        } catch (Exception e) {
            log.error("Unexpected error in mountpoint check: ${e.message}", e)
        }
        return success
    }

    /**
     * Create the /dev/console device, if it does not exist.
     */
    @Override
    void makeConsole() {
        File console = new File("/dev/console")
        if (!console.exists()) {
            log.info("/dev/console missing, creating...")

            // Use mknod or equivalent to create the console device
            int mode = 0x2000 | 0x0100 | 0x0080 // S_IFCHR | S_IRUSR | S_IWUSR
            long deviceId = (5L << 8) | 1
            int result = makeConsoleNative("/dev/console", mode, deviceId)

            if (result != 0) {
                throw new RuntimeException("Failed to create /dev/console. Errno: ${getErrno()}")
            }

            log.info("/dev/console successfully created.")
        } else {
            log.info("/dev/console already exists. Skipping...")
        }
    }

    /**
    * Create a device node for /dev/console with specified mode and device ID.
    *
    * @param path the path where the device node should be created
    * @param mode the mode (permissions and type) for the device node
    * @param deviceId the device ID, including major and minor numbers
    * @return 0 if successful, -1 otherwise
    * @throws IllegalArgumentException if the input path is invalid
    * @throws RuntimeException if an error occurs during the mknod system call
    */
    int makeConsoleNative(String path, int mode, long deviceId) {
        try {
            // Validate the inputs
            if (!path || path.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid input: The path must not be null or empty.")
            }

            // Convert deviceId to major and minor numbers
            int major = (int) (deviceId >> 8) // Extracting the major number (high byte)
            int minor = (int) (deviceId & 0xFF) // Extracting the minor number (low byte)

            // Call mknod native function
            int result = CLib.INSTANCE.mknod(path, mode, (major << 8 | minor))

            // Check for errors
            if (result != 0) {
                int errno = Native.getLastError()
                throw new RuntimeException("mknod failed for path='${path}', mode='${mode}', deviceId='${deviceId}'. errno=${errno} (${strerror(errno)})")
            }

            return 0 // Success
        } catch (Exception e) {
            log.error("[ERROR] Failed to create device node: ${e.message}", e)
            return -1 // Failure
        }
    }

    /**
    * load the DSL from: 1) /etc/starship/conf.d/init.groovy 2) resource classpath:/default-init.groovy
    *
    * @return true if the method succeeded or false if it didn't.
    */
    static boolean configureSystem() {
        boolean success = false // start with a `failing` system init
        try {
            // Load and Evaluate the DSL
            boolean loadConfig = InitUtil.loadConfig(PRIMARY_CONFIG_PATH, FALLBACK_CONFIG_PATH)
            if (!InitUtil.dslContent) {
                log.error("No configuration file available to initialize the system.")
                success = false
            }

            // Parse and Execute DSL
            success = InitUtil.executeDsl(InitUtil.dslContent)
        } catch (Exception e) {
            log.error("Error occurred while configuring the system: {}", e.message, e)
        }
        return success
    }
}
