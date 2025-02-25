/*
 * StarshipOS Copyright (c) 2025. R.A.James
 *
 * Licensed under GPL2, GPL3 and Apache 2
 */


/**
 * Provides a wrapper for the CLibJNI library, offering several system operations such as 
 * setting and retrieving the hostname, mounting and unmounting filesystems, rebooting, 
 * and executing processes.
 *
 * <p>This class ensures that appropriate checks are performed before interacting with 
 * native system calls by using the CLibJNI Java bridge.</p>
 *
 * <ul>
 *     <li>{@link #setHostname(String)}: Sets the system hostname.</li>
 *     <li>{@link #getHostname()}: Retrieves the system hostname.</li>
 *     <li>{@link #mount(String, String, String, long, String)}: Mounts a filesystem.</li>
 *     <li>{@link #umount(String)}: Unmounts a filesystem.</li>
 *     <li>{@link #umount2(String, int)}: Unmounts a filesystem with additional options.</li>
 *     <li>{@link #reboot(int)}: Reboots the system using a specific command.</li>
 *     <li>{@link #sync()}: Synchronizes filesystem buffers to disk.</li>
 *     <li>{@link #execve(String, String [ ], String [ ])}: Executes a program with arguments and environment.</li>
 * </ul>
 *
 * <p>Each method leverages the underlying native library to provide essential system 
 * functionalities, while logging errors or important information for diagnostics 
 * and proper error handling.</p>
 *
 * <p>Note: The native system calls invoked by this class may require elevated 
 * permissions to execute successfully.</p>
 */
package org.starship.jna

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.starship.CLibJNI

/**
 * A utility wrapper class providing higher-level, user-friendly Java methods
 * for interacting with the native system operations implemented in the 
 * {@link CLibJNI} library. This class offers essential system management 
 * functionalities, including hostname management, filesystem operations, 
 * process execution, and system synchronization.
 *
 * <p>All methods in this class perform necessary validations and log errors 
 * in case of failures. Most operations require elevated permissions to execute 
 * successfully. It is primarily designed for system-level applications 
 * requiring direct interaction with the underlying OS.</p>
 *
 * <p>Methods provided include:</p>
 * <ul>
 *   <li>{@link #setHostname(String)} — Updates the system hostname.</li>
 *   <li>{@link #getHostname()} — Retrieves the current system hostname.</li>
 *   <li>{@link #mount(String, String, String, long, String)} — Mounts a filesystem.</li>
 *   <li>{@link #umount(String)} — Unmounts a filesystem.</li>
 *   <li>{@link #umount2(String, int)} — Unmounts a filesystem with additional flags.</li>
 *   <li>{@link #reboot(int)} — Reboots the system with the given command.</li>
 *   <li>{@link #sync()} — Synchronizes filesystem buffers to disk.</li>
 *   <li>{@link #execve(String, String [ ], String [ ])} — Executes a process with specified arguments and environment.</li>
 * </ul>
 *
 * <p><b>Note:</b> Incorrect usage of these functions may affect the stability or 
 * functionality of the system. Proper error handling and validations should always 
 * be considered before invoking these methods.</p>
 *
 * @see CLibJNI* @author R.A.
 * @version 1.0
 */
@Slf4j
@CompileStatic
class CLibWrapper {

    private static final CLibJNI CLIB = new CLibJNI() // Use the Java bridge

    static void setHostname(String hostname) {
        if (!hostname) {
            log.error("Hostname must not be null or empty")
            return
        }

        int result = CLIB.sethostname(hostname, hostname.length() + 1)
        if (result != 0) {
            log.error("Failed to set hostname")
        }
    }

    static String getHostname() {
        byte[] buffer = new byte[256]
        int result = CLIB.gethostname(buffer, buffer.length)
        if (result != 0) {
            log.error("Failed to get hostname")
        }
        return new String(buffer).trim()
    }

    static void mount(String source, String target, String fsType, long flags, String data) {
        if (!target || !fsType) {
            log.error("Target (${target}) and filesystem type (${fsType}) must not be null.")
            return
        }
        int result = CLIB.mount(source, target, fsType, flags, data)
        if (result != 0) {
            log.error("Failed to mount filesystem ${target}")
        } else {
            log.info("Successfully mounted ${target}")
        }
    }

    static void umount(String target) {
        if (!target) {
            log.error("Target must not be null.")
            return
        }
        int result = CLIB.umount(target)
        if (result != 0) {
            log.error("Failed to umount target ${target}")
        }
    }

    static void umount2(String target, int flags) {
        if (!target) {
            log.error("Target must not be null.")
            return
        }
        int result = CLIB.umount2(target, flags)
        if (result != 0) {
            log.error("Failed to forcefully unmount target ${target}")
        }
    }

    static void reboot(int cmd) {
        int result = CLIB.reboot(cmd)
        if (result != 0) {
            log.error("Failed to reboot")
        }
    }

    static void sync() {
        CLIB.sync()
    }

    static void execve(String file, String[] argv, String[] envp) {
        if (!file) {
            log.error("File must not be null.")
            return
        }
        int result = CLIB.execve(file, argv, envp)
        if (result != 0) {
            log.error("Failed to execute file ${file}")
        }
    }
}
