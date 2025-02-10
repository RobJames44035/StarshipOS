/*
 * StarshipOS Copyright (c) 2025. R. A. James
 */

//file:noinspection GroovyInfiniteLoopStatement
package org.starship.init

import groovy.util.logging.Slf4j
import org.starship.init.util.InitUtil
import org.starship.sys.PanicException
import org.starship.sys.SystemResources

import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit


/**
 * The Init class is responsible for initializing and supervising the StarshipOS system.
 * It handles various critical tasks such as loading system configurations,
 * managing system resources, and supervising processes to ensure smooth operation.
 * <p>
 * Key functionalities include:
 * <ul>
 *   <li>System initialization and configuration</li>
 *   <li>Reaping zombie processes</li>
 *   <li>Setting up a shutdown hook for cleanup</li>
 *   <li>Running a supervision loop to monitor the system</li>
 * </ul>
 */
@Slf4j
class Init {

    static final String PRIMARY_CONFIG_PATH = "/etc/starship/config.d/init.groovy"
    static final String FALLBACK_CONFIG_PATH = "default-init.groovy"
    static final int ZERO = 0

    // Tables for dynamically managing resources and services
    static SystemResources resources = SystemResources.getInstance()
    //    static Process osgiManager = null

    /**
     * Main entry point of the application.
     *
     * This method initializes the StarshipOS system by performing the following tasks:
     * - Verifies if the process is running as PID 1 (warns if it's not).
     * - Sets up a shutdown hook for cleanup during termination.
     * - Configures the system using the preferred or fallback configuration files.
     * - Enters the supervision loop to monitor and manage system stability.
     *
     * @param args Command-line arguments passed to the program.
     * @throws PanicException If a critical error occurs during initialization.
     */
    static void main(final String[] args) {
        try {
            if (ManagementFactory.getRuntimeMXBean().getName().split("@")[ZERO] != "1") {
                throw new PanicException("This program is not running as PID 1!")
            }

            log.info("Initializing StarshipOS...")
            log.info("Is TTY available for stdin: ${System.console()}")
            System.getenv().each {  String key, String value -> log.info("$key = $value") }

            setupShutdownHook()

            configureSystem()

            log.info("System is up.")
            int xit = InitUtil.interactiveShell()
            log.info("Interactive shell exited with code: $xit")
            if(xit == 0) {
                supervisorLoop()
            } else {
                throw new PanicException("Something REALLY BAD happened.")
            }
        } catch (Exception e) {
            log.error("Critical error during system initialization: ${e.message}", e)
            throw new PanicException("PANIC: ${e.message ?: 'Unknown error'}", e)
        }
    }

    /**
     * Configures the StarshipOS system using configuration files.
     *
     * This method attempts to load the primary system configuration file specified
     * by {@link #PRIMARY_CONFIG_PATH}. If the primary configuration file does not exist,
     * it falls back to using the default configuration file located at {@link #FALLBACK_CONFIG_PATH}.
     *
     * The method validates the existence of the configuration file or fallback resource, 
     * attempts to read its content, and performs system setup accordingly.
     *
     * @return true if the system configuration is successfully loaded and applied.
     * @throws PanicException if configuration files are missing or there is a failure
     *                        during the system setup process.
     */
    static boolean configureSystem() {
        try {
            File configFile = new File(PRIMARY_CONFIG_PATH)
            String configContent = null

            if (!configFile.exists()) {
                log.warn("Primary config not found at $PRIMARY_CONFIG_PATH. Attempting to load fallback config: $FALLBACK_CONFIG_PATH")
                InputStream fallbackStream = Init.class.classLoader.getResourceAsStream(FALLBACK_CONFIG_PATH)
                if (fallbackStream == null) {
                    log.error("Fallback configuration not found. Aborting!")
                    throw new PanicException("System configuration missing! Initialization cannot proceed.")
                }
                configContent = fallbackStream.text
                log.info("Fallback configuration loaded from resources.")
            } else {
                log.info("Primary configuration file located at: ${configFile.absolutePath}")
            }

            if (!configContent) {
                InitUtil.getInstance().configureSystem(configFile)
            } else {
                InitUtil.getInstance().configureSystem(configContent)
            }

            return true
        } catch (Exception e) {
            log.error("System configuration failed: ${e.message}", e)
            throw new PanicException("System configuration aborted due to critical errors.", e)
        }
    }

    /**
     * The main supervision loop responsible for maintaining system stability.
     *
     * This method continually performs various supervision tasks, such as:
     * - Reaping zombie processes to free system resources.
     * - Logging and monitoring system health at regular intervals.
     *
     * The loop runs indefinitely unless interrupted by an external shutdown signal.
     * Errors occurring during the loop are logged without halting its execution.
     *
     * @throws RuntimeException if an unexpected error occurs that affects the loop's functionality
     */
    static void supervisorLoop() {
        log.info("Starting Supervisor Loop...")
        while (true) {
            try {
                reapZombies()
                Thread.sleep(1000) // Supervisor polling interval
            } catch (Exception e) {
                log.error("Error in supervisor loop: ${e.message}", e)
            }
        }
    }

    /**
     * Reaps zombie processes from the system process table.
     *
     * Zombie processes are terminated child processes that have not been properly
     * waited on, which can consume system resources unnecessarily. This method
     * iterates through the process table, checks if each process has completed,
     * and removes it from the table if it has exited.
     *
     * Any errors encountered during the reaping process are logged without affecting
     * other processes in the table.
     */
    static void reapZombies() {
        log.info("\tReaping zombie processes...")
        resources.processTable.each { String name, Object process ->
            try {
                if (process instanceof Process) {
                    if (process?.waitFor(ZERO, TimeUnit.SECONDS)) {
                        log.info("Reaped zombie process: ${name}")
                        resources.processTable.remove(name)
                    }
                }
            } catch (Exception e) {
                log.error("Error reaping process ${name}: ${e.message}", e)
            }
        }
    }

    /**
     * Sets up a shutdown hook to handle cleanup during system termination.
     *
     * This method registers a shutdown hook using the Java {@link Runtime} class.
     * When the system receives a termination signal, the shutdown hook performs
     * the following cleanup tasks:
     * <ul>
     *   <li>Iterates through the process table and terminates all active processes.</li>
     *   <li>Logs the termination of each process for monitoring and debugging purposes.</li>
     *   <li>Closes the DBus registry to free up resources and ensure proper shutdown behavior.</li>
     * </ul>
     *
     * Any exceptions encountered during the shutdown process are logged without affecting
     * the completion of other cleanup tasks.
     */
    static void setupShutdownHook() {
        Runtime.runtime.addShutdownHook(new Thread({
            log.info("Shutdown hook triggered. Cleaning up...")
            resources.processTable.each { String name, Object process ->
                process?.destroy()
                log.info("Terminated process: ${name}")
            }
        }))
    }
}
