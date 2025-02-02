/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.jna


import com.sun.jna.Native

class CLibWrapper {

    private static final CLib C_LIB = CLib.INSTANCE

    /**
     * Changes the hostname of the system.
     *
     * @param hostname The new hostname to set.
     */
    static void setHostname(String hostname) {
        if (!hostname) {
            throw new IllegalArgumentException("Hostname must not be null or empty")
        }

        def result = C_LIB.sethostname(hostname, hostname.length() + 1)
        if (result != 0) {
            throw new IllegalStateException("sethostname failed: ${Native.getLastError()}")
        }
    }

    /**
     * Retrieves the current hostname of the system.
     *
     * @return The current hostname.
     */
    static String getHostname() {
        def buffer = new byte[256] // Max hostname length on Linux
        def result = C_LIB.gethostname(buffer, buffer.length)
        if (result != 0) {
            throw new IllegalStateException("gethostname failed: ${Native.getLastError()}")
        }
        new String(buffer).trim()
    }


    /**
     * Mounts a filesystem.
     *
     * @param source      The source block device or directory (nullable for tmpfs, etc.).
     * @param target      The target mount point.
     * @param fsType      The filesystem type (e.g., "ext4").
     * @param mountFlags  Mount flags (e.g., CLib.MS_RDONLY, CLib.MS_NOEXEC).
     * @param data        Additional mount data (optional).
     */
    static void mount(String source, String target, String fsType, long mountFlags, String data) {
        if (!target || !fsType) {
            throw new IllegalArgumentException("Target and filesystem type must not be null")
        }

        def result = C_LIB.mount(source, target, fsType, mountFlags, data)
        if (result != 0) {
            throw new IllegalStateException("mount failed: ${Native.getLastError()}")
        }
    }

    /**
     * Unmounts a filesystem.
     *
     * @param target The target mount point to unmount.
     */
    static void umount(String target) {
        if (!target) {
            throw new IllegalArgumentException("Target must not be null")
        }

        def result = C_LIB.umount(target)
        if (result != 0) {
            throw new IllegalStateException("umount failed: ${Native.getLastError()}")
        }
    }

    /**
     * Forcefully unmounts a filesystem.
     *
     * @param target The target mount point to unmount.
     */
    static void umount2(String target) {
        if (!target) {
            throw new IllegalArgumentException("Target must not be null")
        }

        def result = C_LIB.umount2(target, (int) CLib.MNT_FORCE)
        if (result != 0) {
            throw new IllegalStateException("umount2 (force) failed: ${Native.getLastError()}")
        }
    }

    /**
     * Performs an lstat system call to retrieve file information.
     *
     * @param path The file path to analyze.
     * @return A Stat object containing file information.
     */
    static CLib.Stat lstat(String path) {
        if (!path) {
            throw new IllegalArgumentException("Path must not be null")
        }

        def stat = new CLib.Stat()
        def result = C_LIB.lstat(path, stat)
        if (result != 0) {
            throw new IllegalStateException("lstat failed for $path: ${Native.getLastError()}")
        }
        stat
    }

    /**
     * Creates a special or ordinary file.
     *
     * @param pathname The path to create the file.
     * @param mode     The file mode (e.g., CLib.S_IFCHR for a char device).
     * @param dev      The device ID or 0 for regular files.
     */
    static void mknod(String pathname, int mode, long dev) {
        if (!pathname) {
            throw new IllegalArgumentException("Pathname must not be null")
        }

        def result = C_LIB.mknod(pathname, mode, dev)
        if (result != 0) {
            throw new IllegalStateException("mknod failed for $pathname: ${Native.getLastError()}")
        }
    }

    /**
     * Reboots the system.
     *
     * @param magic The reboot magic constant (e.g., CLib.LINUX_REBOOT_CMD_HALT).
     */
    static void reboot(int magic) {
        def result = C_LIB.reboot(magic)
        if (result != 0) {
            throw new IllegalStateException("Reboot failed: ${Native.getLastError()}")
        }
    }

    /**
     * Executes a program, replacing the current process.
     *
     * @param filename The path to the executable.
     * @param argv     Arguments (must be null-terminated).
     * @param envp     Environment variables (must be null-terminated).
     */
    @SuppressWarnings('GroovyUnusedAssignment')
    static void execve(String filename, String[] argv, String[] envp) {
        if (!filename || !argv || !envp) {
            throw new IllegalArgumentException("Filename, argv, and envp must not be null")
        }

        def result = C_LIB.execve(filename, argv, envp)
        throw new IllegalStateException("execve failed: ${Native.getLastError()}")
    }


}
