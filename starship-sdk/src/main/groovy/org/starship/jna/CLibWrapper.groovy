/*
 * StarshipOS Copyright (c) 2025. R. A. James
 */

package org.starship.jna

import groovy.util.logging.Slf4j

@Slf4j
class CLibWrapper {

    private static final CLib CLIB = CLib.INSTANCE

    /**
     * Changes the hostname of the system.
     *
     * @param hostname The new hostname to set.
     */
    static void setHostname(String hostname) {
        if (!hostname) {
            log.error("Hostname must not be null or empty")
        }

        // Native operation using JNA
        int result = CLIB.sethostname(hostname, hostname.length() + 1)
        if (result != 0) {
            log.error("sethostname failed.")
        }
    }

    /**
     * Retrieves the current hostname of the system.
     *
     * @return The current hostname.
     */
    static String getHostname() {
        // Native operation using JNA
        byte[] buffer = new byte[256] // Max hostname length on Linux
        int result = CLIB.gethostname(buffer, buffer.length)
        if (result != 0) {
            log.error("gethostname failed.")
        }
        return new String(buffer).trim()
    }

    /**
     * Mounts a filesystem.
     *
     * @param source The source block device or directory (nullable for tmpfs, etc.).
     * @param target The target mount point.
     * @param fsType The filesystem type (e.g., "ext4").
     * @param mountFlags Mount flags (e.g., CLib.MS_RDONLY, CLib.MS_NOEXEC).
     * @param data Additional mount data (optional).
     */
    static void mount(String source, String target, String fsType, long mountFlags, String data) {
        if (!target || !fsType) {
            log.error("Target (${target}) and filesystem type  (${fsType}) must not be null.")
        }
        // Native operation using JNA
        int result = CLIB.mount(source, target, fsType, mountFlags, data)
        if (result != 0) {
            log.error("Mounting ${target} failed.")
        } else {
            log.info("Mounted ${target}.")
        }
    }

    /**
     * Unmounts a filesystem.
     *
     * @param target The target mount point to unmount.
     */
    static void umount(String target) {
        if (!target) {
            log.error("Target must not be null.")
        }

        // Native operation using JNA
        int result = CLIB.umount(target)
        if (result != 0) {
            if(umount2(target) != 0) {
                log.error("umount failed.")
            }
        }
    }

    /**
     * Forcefully unmounts a filesystem.
     *
     * @param target The target mount point to unmount.
     */
    static void umount2(String target) {
        if (!target) {
            log.error("Target must not be null.")
        }

        // Native operation using JNA
        int result = CLIB.umount2(target, (int) CLib.MNT_FORCE)
        if (result != 0) {
            log.error("umount2 (force) failed.")
        }
    }

    /**
     * Reboots the system.
     *
     * @param cmd The reboot command, e.g., CLib.LINUX_REBOOT_CMD_RESTART.
     */
    static void reboot(int cmd) {
        // Native operation using JNA
        int result = CLIB.reboot(cmd)
        if (result != 0) {
            log.error("reboot failed.")
        }
    }

    /**
     * Synchronizes the filesystem buffers.
     */
    static void sync() {
        // Native operation using JNA
        CLIB.sync()
    }

    /**
     * Executes a program.
     *
     * @param file The program to execute.
     * @param argv Arguments for the program.
     * @param envp Environment variables for the execution.
     */
    static void execve(String file, String[] argv, String[] envp) {
        if (!file) {
            log.error("File must not be null.")
        }

        // Native operation using JNA
        int result = CLIB.execve(file, argv, envp)
        if (result != 0) {
            log.error("execve failed.")
        }
    }
}
