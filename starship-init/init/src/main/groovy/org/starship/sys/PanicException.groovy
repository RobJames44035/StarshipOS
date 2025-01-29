/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

//file:noinspection unused
package org.starship.sys

import com.sun.jna.Native
import groovy.util.logging.Slf4j
import org.starship.jna.CLib

/**
 * Exception class representing a kernel panic in the system.
 * This exception triggers critical system actions such as filesystem syncing 
 * and rebooting, designed to handle unrecoverable errors.
 *
 * <p><strong>Note:</strong> Only one panic can be triggered per runtime session
 * to prevent duplicates.</p>
 *
 * <p>Usage:</p>
 * <ul>
 *     <li>Throwing this exception with a message will initiate a panic.</li>
 *     <li>Optionally, a cause can be provided for additional context.</li>
 * </ul>
 *
 * Example usage:
 * <pre>{@code
 * if (someCriticalError) {
 *     throw new PanicException("Critical error occurred");
 * }
 *}</pre>
 */
@Slf4j
class PanicException extends RuntimeException {

    /**
    * Static flag that tracks if the panic has been triggered during
    * the current runtime session. This ensures that only one
    * kernel panic is initiated per session, avoiding duplicate triggers
    * and redundant system shutdowns.
    *
    * <p>When this flag is set to {@code true}, subsequent attempts to
    * trigger a panic are skipped with a log message indicating that
    * a panic has already been triggered.</p>
    */
    private static boolean panicTriggered = false

    
    /**
    * Creates a new {@code PanicException} with the specified detail message.
    * This exception triggers a kernel panic in the system, ensuring a controlled
    * shutdown or reset in response to critical errors. The panic action
    * synchronizes the filesystem and attempts a reboot using Linux-specific
    * mechanisms.
    *
    * <p>The constructor invokes the {@code triggerPanic} method to initiate
    * the system panic. If a panic has already been triggered in the runtime session,
    * the method will log the duplicate attempt and skip further actions.</p>
    *
    * @param message the detail message describing the reason for the panic
    */
    PanicException(String message) {
        super(message)
        triggerPanic(message)
    }
    
    /**
    * Constructs a new {@code PanicException} with the specified detail message
    * and cause. This constructor triggers a system panic, which is a critical
    * action involving filesystem syncing and rebooting.
    *
    * @param message the detail message, saved for later retrieval by the
    * {@link Throwable#getMessage()} method
    * @param cause the cause (saved for later retrieval by the
    * {@link Throwable#getCause()} method). A {@code null} value
    *                is permitted, indicating the cause is nonexistent or unknown.
    */
    PanicException(String message, Throwable cause) {
        super(message, cause)
        triggerPanic(message)
    }
    
    /**
    * Triggers a kernel panic in the system. This synchronized method ensures that
    * only one panic is initiated per runtime session by using the {@code panicTriggered}
    * flag. Upon triggering, the method:
    * <ul>
    *     <li>Logs the panic message to the console.</li>
    *     <li>Attempts to call libc routines to sync the filesystem and initiate 
    *         a kernel panic using a Linux-specific reboot magic number.</li>
    *     <li>Catches and logs any exceptions that may occur during the process.</li>
    * </ul>
    *
    * @param message the panic message that describes the critical failure
    */
    private synchronized void triggerPanic(String message) {
        if (panicTriggered) {
            log.info("Panic already triggered. Skipping.")
            return
        }
        //noinspection GroovyAccessToStaticFieldLockedOnInstance
        panicTriggered = true // Mark panic as triggered

        println "KERNEL PANIC: ${message}"

        try {
            // Load libc dynamically and execute panic-related calls
            //noinspection GroovyAssignabilityCheck
            CLib libc = Native.load("c", CLib)
            libc.sync()                     // Sync filesystems
            libc.reboot(0xfee1dead as int)  // Trigger Linux kernel panic using the magic number
        } catch (Exception e) {
            log.error("Failed to execute kernel panic: ${e.message}", e)
        }
    }
}
