/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

//file:noinspection unused
//file:noinspection GroovyAssignabilityCheck
//file:noinspection GroovyInfiniteLoopStatement
package org.starship.init

import com.sun.jna.Native
import groovy.util.logging.Slf4j
//import org.starship.eventcore.SystemEventBus
import org.starship.jna.CLib
import org.starship.sdk.process.ProcessWrapper
import org.starship.sys.PanicException
import org.starship.sys.SignalProcessor
import sun.misc.Signal
import sun.misc.SignalHandler

import java.lang.management.ManagementFactory
import java.nio.file.Files
import java.nio.file.Path
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
class Init {
    
    // Configurations
    static long HEARTBEAT_TIMEOUT_MS = 5000 // Time to wait for a heartbeat in ms
    static int MAX_RETRY_ATTEMPTS = 3      // Retry attempts to start the BundleManager
    static String BUNDLE_MANAGER_COMMAND = "java -cp /path/to/bundlemanager.jar com.starship.BundleManager"
    static String PRIMARY_CONFIG_PATH = "/etc/starship/config.d/default.config"
    static String FALLBACK_CONFIG_PATH = "/default-init.config"
    static String BUNDLE_MANAGER_SOCKET_PATH = "/tmp/bundlemanager.sock"
    static Process bundleManagerProcess = null   // Track the BundleManager process
    static long bundleManagerPid = -1            // Track PID of the BundleManager

    static final SignalProcessor signalProcessor = SignalProcessor.getInstance()
//    static final SystemEventBus eventBus = new SystemEventBus()

    // Track the last heartbeat time
    static volatile long lastHeartbeatTimestamp = 0

    // List of running child processes
    static List<Process> childProcesses = [].asSynchronized() as List<Process>
    private static final Set<String> mountedResources = ConcurrentHashMap.newKeySet()


    /**
     * Start the BundleManager as a subprocess.
     * Wait for the initial heartbeat within the defined timeout duration.
     * Retry starting the BundleManager if it fails, up to MAX_RETRY_ATTEMPTS.
     */
    static void startBundleManager() {
//        int attempts = 0
//        boolean started = false
//
//        while (attempts < MAX_RETRY_ATTEMPTS) {
//            attempts++
//            log.info("Attempting to start BundleManager (attempt ${attempts} of ${MAX_RETRY_ATTEMPTS})...")
//
//            try {
//                // Spawn the BundleManager process
//                bundleManagerProcess = new ProcessWrapper(BUNDLE_MANAGER_COMMAND.split(" "),
//                        [:]
//                ).start() as Process
//                log.info("Spawned BundleManager with PID: ${bundleManagerPid}")
//
//                // Monitor the first heartbeat
//                if (waitForHeartbeat()) {
//                    log.info("BundleManager is running and healthy (heartbeat received).")
//                    started = true
//                    break
//                } else {
//                    log.warn("No heartbeat received from BundleManager within timeout. Retrying...")
//                    stopBundleManager() // Clean up before retrying
//                }
//            } catch (Exception e) {
//                log.error("Failed to start BundleManager: ${e.message}", e)
//                stopBundleManager()
//            }
//        }
//
//        if (!started) {
//            log.error("Failed to start BundleManager after ${MAX_RETRY_ATTEMPTS} attempts. Triggering kernel panic.")
//            throw new PanicException("BundleManager failed to initialize.")
//        }
    }

    /**
     * Stop the BundleManager process if it is running.
     */
    static void stopBundleManager() {
//        if (bundleManagerProcess?.isAlive()) {
//            log.info("Stopping BundleManager (PID: ${bundleManagerPid})...")
//            bundleManagerProcess.destroy()
//            if (!bundleManagerProcess.waitFor(5, TimeUnit.SECONDS)) {
//                log.warn("Forcefully killing BundleManager (PID: ${bundleManagerPid})...")
//                bundleManagerProcess.destroyForcibly()
//            }
//            bundleManagerProcess = null
//            bundleManagerPid = -1
//        } else {
//            log.info("No active BundleManager process to stop.")
//        }
    }

    /**
     * Start listening for heartbeats over a UDS.
     */
    static void startHeartbeatListener() {
//        new Thread({
//            while (true) {
//                try {
//                    // Clean up the socket file if it exists
//                    Path socketPath = Path.of(BUNDLE_MANAGER_SOCKET_PATH)
//                    Files.deleteIfExists(socketPath)
//
//                    ServerSocket serverSocket = new ServerSocket()
//                    serverSocket.bind(new InetSocketAddress(BUNDLE_MANAGER_SOCKET_PATH, 0))
//
//                    log.info("Listening for heartbeats on UDS: ${BUNDLE_MANAGER_SOCKET_PATH}")
//
//                    while (true) {
//                        try (Socket clientSocket = serverSocket.accept()) {
//                            log.info("Client connected to heartbeat socket.")
//
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
//                            String message
//                            while ((message = reader.readLine()) != null) {
//                                if (message.trim() == "heartbeat") {
//                                    lastHeartbeatTimestamp = System.currentTimeMillis()
//                                    log.debug("Received heartbeat at ${lastHeartbeatTimestamp}")
//                                }
//                            }
//                        } catch (Exception e) {
//                            log.error("Error reading from UDS: ${e.message}", e)
//                        }
//                    }
//                } catch (Exception e) {
//                    log.error("Heartbeat listener failed. Restarting...", e)
//                }
//            }
//        }).start()
    }

    /**
     * Wait for a heartbeat from the running BundleManager.
     *
     * @return true if heartbeat is received within the timeout, false otherwise.
     */
    static boolean waitForHeartbeat() {
//        long startTime = System.currentTimeMillis()
//
//        while ((System.currentTimeMillis() - startTime) < HEARTBEAT_TIMEOUT_MS) {
//            if (isHeartbeatReceived()) {
//                return true
//            }
//            Thread.sleep(500) // Poll every 500ms to reduce CPU usage
//        }
//        return false
    }

    /**
     * Check if a heartbeat has been received within the timeout period.
     *
     * @return true if a recent heartbeat was received, false otherwise.
     */
    static boolean isHeartbeatReceived() {
//        return (System.currentTimeMillis() - lastHeartbeatTimestamp) < HEARTBEAT_TIMEOUT_MS
    }

    /**
     * Load a configuration file dynamically
     *
     * @param configPath Path to config file.
     */
    static void loadConfig(String configPath) {
        try {
            File configFile = new File(configPath)
            if (!configFile.exists()) {
                log.warn("Configuration file not found: ${configPath}. Attempting fallback configuration...")

                // Attempt to load fallback config from the classpath
                URL fallbackConfigUrl = Init.class.getClassLoader().getResource(FALLBACK_CONFIG_PATH)
                if (fallbackConfigUrl == null) {
                    try {
                        log.warn("Fallback configuration not found. Starting interactive shell: /bin/sh")
                        Process process = new ProcessBuilder("/bin/sh").inheritIO().start()

                        // Wait for the shell process to exit
                        int exitCode = process.waitFor()
                        log.info("Interactive shell exited with code: $exitCode")
                    } catch (Exception e) {
                        throw new PanicException("Failed to start interactive shell: ${e.getMessage()}", e)
                    }
                } else {
                    log.info("Using fallback configuration from: ${fallbackConfigUrl}")
                    evaluate(new File(fallbackConfigUrl.toURI()))
                }
            } else {
                // Evaluate primary configuration
                log.info("Loading configuration from: ${configPath}")
                evaluate(configFile)
            }
        } catch (Exception e) {
            // Handle unexpected errors and call kernel panic
            throw new PanicException("Failed to load configuration due to an error: ${e.message}", e)
        }
    }

    /**
     * Includes additional configuration files for processing.
     *
     * @param path The path to the configuration file to include.
     */
    static void include(String path) {
        log.info("Including configuration from: ${path}")
        loadConfig(path) // Recursive call to load the included configuration
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
     * Spawns a new process using the provided command and associates it
     * with a logical name for logging and tracking purposes.
     *
     * This method uses a `ProcessBuilder` to execute the command, tracks
     * the process, and logs the details of the spawned process.
     *
     * If the process fails to start, it logs an error detailing the cause.
     *
     * @param command The command to execute as a new process.
     * @param name A logical name to associate with the spawned process for tracking.
     */
    static void spawnProcess(String command, String name) {
        try {
            log.info("Spawning process '${name}': ${command}")
            ProcessWrapper wrapper = new ProcessWrapper(command.split(" "), [:])
            Process process = wrapper.start()
            childProcesses.add(process)
            log.info("Process '${name}' spawned with PID: ${process.pid()}.")
        } catch (Exception e) {
            log.error("Failed to spawn process '${name}': ${e.message}", e)
        }
    }

    /**
     * Evaluates a Groovy configuration file using a GroovyShell.
     *
     * This method ensures that the configuration file provided is valid and exists,
     * and then processes it by evaluating its contents. If there are any issues
     * during evaluation, such as syntax errors or runtime exceptions, the error
     * is logged and rethrown.
     *
     * @param configFile The configuration file to evaluate.
     * @throws IllegalArgumentException if the configuration file is null or does not exist.
     * @throws Exception if an error occurs during the evaluation of the configuration file.
     */
    static void evaluate(File configFile) {
        if (configFile == null || !configFile.exists()) {
            throw new IllegalArgumentException("Configuration file ${configFile?.name} does not exist or is null.")
        }

        try {
            log.info("Evaluating configuration file: ${configFile.absolutePath}")

            // Create a new GroovyShell instance
            GroovyShell shell = new GroovyShell()

            // Execute the DSL config file
            shell.evaluate(configFile)

            log.info("Configuration file evaluated successfully.")
        } catch (Exception e) {
            log.error("Error while evaluating configuration file: ${e.message}", e)
            throw e
        }
    }

    /**
     * Unmounts a specified resource from the system.
     *
     * This method ensures synchronized access to the list of mounted resources,
     * verifies if the specified resource is currently mounted, and attempts to
     * unmount it. If a regular unmount fails and forced unmount is specified,
     * it retries with a forced unmount.
     *
     * - Logs warnings if the resource is not found or already unmounted.
     * - Uses kernel calls to perform the unmount operation.
     * - Handles potential errors and retries as necessary for forced unmount.
     *
     * @param mountPoint The mount point of the resource to unmount.
     * @param force A boolean indicating whether the unmount should be forced.
     */
    static void unmountResource(String mountPoint, boolean force) {
        synchronized (mountedResources) {
            if (!mountedResources.contains(mountPoint)) {
                log.warn("Attempt to unmount an unknown or already unmounted resource: {}", mountPoint)
                return
            }

            try {
                log.info("Unmounting resource: {} using kernel call{}...",
                        mountPoint, force ? " (forced)" : "")

                // Call the appropriate umount function from libc depending on forced unmount or not
                int result
                if (force) {
                    // Forceful unmount using umount2 with MNT_FORCE flag
                    result = CLib.INSTANCE.umount2(mountPoint, CLib.MNT_FORCE)
                } else {
                    // Regular unmount
                    result = CLib.INSTANCE.umount(mountPoint)
                }

                if (result == 0) {
                    log.info("Successfully unmounted resource: {}", mountPoint)
                    mountedResources.remove(mountPoint) // Remove from tracking
                } else {
                    // Retrieve the errno value for additional error information
                    String errorMessage = Native.getLastError()
                    log.error("Failed to unmount resource {}. Error code: {}", mountPoint, errorMessage)

                    // If regular unmount fails and force is requested, try forced unmount
                    if (!force) {
                        log.warn("Retrying forced unmount for resource: {}", mountPoint)
                        unmountResource(mountPoint, true)
                    }
                }
            } catch (Exception e) {
                log.error("Exception occurred while unmounting resource {}: {}", mountPoint, e.message, e)
            }
        }
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
                        unmountResource(mountPoint)
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
     * Handles configuring the JVM and loading the system libraries.
     *
     * This method is responsible for ensuring that the system libraries
     * required to perform low-level operations are properly loaded and
     * available to the runtime.
     */
    static void configureSystemLibraries() {
        try {
            log.info("Configuring JVM and loading system libraries...")

            // Example of loading native library
            System.loadLibrary("c")         // Loads the libc library
            System.loadLibrary("pthread")   // POSIX threads
            System.loadLibrary("rt")        // Real-Time Extensions
            System.loadLibrary("dl")        // Dynamic loading
            System.loadLibrary("m")         // Math
            System.loadLibrary("stdc++")    // std C++

            log.info("System libraries loaded successfully.")
        } catch (Exception e) {
            log.error("Failed to load system libraries: {}", e.message, e)
            throw new RuntimeException("Critical error: Unable to load necessary libraries.", e)
        }
    }

    /**
    * Launches the system shell (`/bin/sh`) and waits for the user to exit the shell.
    *
    * This method is useful for interactive debugging or as an emergency fallback
    * mechanism to provide a shell environment for troubleshooting.
    *
    * - Launches `/bin/sh` using `ProcessBuilder`.
    * - Redirects I/O of the child process to the current process using `inheritIO()`.
    * - Waits for the shell process to terminate before returning control.
    *
    * Any exceptions thrown during this process are caught and printed to the error stream.
    *
    * Example usage:
    * ```
    * dropToShell()
    * ```
    *
    * Notes:
    * - Ensure adequate permissions are in place to execute `/bin/sh`.
    * - `println` is used to indicate when the process returns to the main application.
    *
    * @throws Exception if an error occurs during the shell launch or execution.
    */
    static void dropToShell() {
        try {
            // Launch /bin/sh to drop to a shell
            ProcessBuilder pb = new ProcessBuilder("/bin/sh")
            pb.inheritIO() // Ensures input/output redirection works properly
            Process shell = pb.start()
            shell.waitFor() // Wait for the shell to exit
            println "Returning to Init..."
        } catch (Exception e) {
            e.printStackTrace()
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
        }

        try {
            log.info("Initializing StarshipOS as PID 1...")
            SignalProcessor.getInstance().init()
            setupShutdownHook()
            startHeartbeatListener() // Start UDS listener for heartbeats
            startBundleManager()
            loadConfig(PRIMARY_CONFIG_PATH)

            Signal.handle(new Signal("INT"), new SignalHandler() {
                @Override
                void handle(Signal sig) {
                    println "\nCaught CTRL-C (SIGINT)! Dropping to /bin/sh..."
                    dropToShell()
                }
            })

            
            // Supervision loop
            while (true) {
                log.info("Supervision loop...")
                try {
                    reapZombies() // Clean up zombie processes

                    // Check if BundleManager is alive and sending heartbeats
//                    if (!isHeartbeatReceived()) {
//                        log.warn("BundleManager is not running. Attempting to restart...")
//                        startBundleManager()
//                    }

                    Thread.sleep(1000) // Supervisor polling interval
                } catch (Exception e) {
                    log.error("Error in supervisor loop: ${e.message}", e)
                }
            }
        } catch (Exception e) {
            log.error("Error in supervisor loop: ${e.message}", e)
            throw new PanicException("Fatal error in supervisor loop.")
        }
    }
}
