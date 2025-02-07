/*
 * StarshipOS Copyright (c) 2025. R. A. James
 */

package org.starship.sys


import groovy.util.logging.Slf4j
import org.starship.jna.CLib

/**
 * Exception class representing a kernel panic in the system.
 * This exception triggers critical system actions such as filesystem syncing
 * and rebooting, designed to handle unrecoverable errors.
 *
 * <p><strong>Note:</strong> Only one panic can be triggered per runtime session
 * to prevent duplicates.</p>
 */
@Slf4j
class PanicException extends RuntimeException {

    // Reboot command
    int LINUX_REBOOT_CMD_HALT = 0xCDEF0123

    /**
     * Static flag to prevent multiple panics in a single runtime session.
     * Ensures only one kernel panic is triggered.
     */
    private static boolean panicTriggered = false

    /**
     * Constructs a new {@code PanicException} with the specified detail message.
     * This constructor triggers a system panic, which is a critical action.
     *
     * @param message the detail message, saved for later retrieval
     */
    PanicException(String message) {
        super(message)
        triggerPanic(message)
    }

    /**
     * Constructs a new {@code PanicException} with the specified detail message
     * and cause. This constructor also triggers a system panic.
     *
     * @param message the detail message
     * @param cause   the cause of the panic
     */
    PanicException(String message, Throwable cause) {
        super(message, cause)
        triggerPanic(message)
    }

    /**
     * Triggers a kernel panic in the system. This synchronized method ensures that
     * only one panic is triggered per runtime session. It performs the following:
     * <ul>
     *     <li>Logs the panic message to the console</li>
     *     <li>Prints an 80x24 ASCII-art panic screen to stdout</li>
     *     <li>Attempts to sync the filesystem and reboot the system</li>
     * </ul>
     *
     * @param message the panic message describing the error
     */
    private synchronized void triggerPanic(String message) {
        if (panicTriggered) {
            log.info("Panic already triggered. Skipping further actions.")
            return
        }
        panicTriggered = true // Mark panic as triggered

        // Capture system info and timestamp for the panic screen
        String timestamp = new Date().toString()
        String systemInfo = "${System.getProperty('os.name')} ${System.getProperty('os.version')}"

        // Generate the 80x24 panic screen
        String panicScreen = """
********************************************************************************
*                                                                              *
*                                  KERNEL PANIC                                *
*                                                                              *
********************************************************************************
*                                                                              *
*                Oops! Something went horribly wrong in the system.            *
*                                                                              *
*                                                                              *
*                Reason: ${message.padRight(60)}                  *
*                                                                              *
*                Timestamp: ${timestamp.padRight(54)}             *
*                System Info: ${systemInfo.padRight(54)}          *
*                                                                              *
*                                                                              *
*                We're syncing filesystems and shutting down now.   n          *
*                                                                              *
********************************************************************************
*                                                                              *
*                   !!! SYSTEM WILL NOT ATTEMPT TO REBOOT !!!                  *
*                                                                              *
********************************************************************************
"""
        // Print the panic screen to stdout (console)
        println panicScreen

        // Attempt critical system actions (file sync and reboot)
        try {
            // Sync the filesystem
            log.info("Syncing filesystems...")
            CLib.INSTANCE.sync()

            // Trigger a Linux-specific kernel panic via magic keys
            log.info(this.message, this)
            CLib.INSTANCE.reboot(LINUX_REBOOT_CMD_HALT)
        } catch (Exception e) {
            // Log any failures during panic procedures
            log.error("Failed to complete panic sequence: ${e.message}", e)
        }
    }
}
