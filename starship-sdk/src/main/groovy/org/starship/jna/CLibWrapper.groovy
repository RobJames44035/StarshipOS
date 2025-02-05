/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.jna

class CLibWrapper {

    // Static boolean to switch between native calls and ProcessBuilder
//    static final boolean nativeCall = false // Set to `true` to use JNA native calls

//    private static final CLib C_LIB = null // CLib.INSTANCE

    /**
     * Changes the hostname of the system.
     *
     * @param hostname The new hostname to set.
     */
    static void setHostname(String hostname) {
        if (!hostname) {
            throw new IllegalArgumentException("Hostname must not be null or empty")
        }

//        if (nativeCall) {
            // Native operation using JNA
//            def result = C_LIB.sethostname(hostname, hostname.length() + 1)
//            if (result != 0) {
//                throw new IllegalStateException("sethostname failed: ${Native.getLastError()}")
//            }
//        } else {
            // Fallback to ProcessBuilder - Use traditional echo to /proc/sys/kernel/hostname
            try {
                def process = new ProcessBuilder("sh", "-c", "echo ${hostname} > /proc/sys/kernel/hostname").start()
                process.waitFor()
                if (process.exitValue() != 0) {
                    throw new IllegalStateException("setHostname failed using ProcessBuilder")
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException("Failed to set hostname using ProcessBuilder")
            }
//        }
    }

    /**
     * Retrieves the current hostname of the system.
     *
     * @return The current hostname.
     */
    static String getHostname() {
//        if (nativeCall) {
            // Native operation using JNA
//            def buffer = new byte[256] // Max hostname length on Linux
//            def result = C_LIB.gethostname(buffer, buffer.length)
//            if (result != 0) {
//                throw new IllegalStateException("gethostname failed: ${Native.getLastError()}")
//            }
//            return new String(buffer).trim()
//        } else {
            // Fallback to ProcessBuilder
            try {
                def process = new ProcessBuilder("hostname").start()
                def result = process.inputStream.text.trim()
                return result
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException("Failed to get hostname using ProcessBuilder")
            }
//        }
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

//        if (nativeCall) {
            // Native operation using JNA
//            def result = C_LIB.mount(source, target, fsType, mountFlags, data)
//            if (result != 0) {
//                throw new IllegalStateException("mount failed: ${Native.getLastError()}")
//            }
//        } else {
            // Fallback to ProcessBuilder (requires explicit command-line mount invocation)
            try {
                def command = source ? ["mount", "-t", fsType, source, target] : ["mount", "-t", fsType, "none", target]
                def process = new ProcessBuilder(command).start()
                process.waitFor()
                if (process.exitValue() != 0) {
                    throw new IllegalStateException("mount failed using ProcessBuilder")
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException("Failed to mount using ProcessBuilder")
            }
//        }
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

//        if (nativeCall) {
            // Native operation using JNA
//            def result = C_LIB.umount(target)
//            if (result != 0) {
//                throw new IllegalStateException("umount failed: ${Native.getLastError()}")
//            }
//        } else {
            // Fallback to ProcessBuilder
            try {
                def process = new ProcessBuilder("umount", target).start()
                process.waitFor()
                if (process.exitValue() != 0) {
                    throw new IllegalStateException("umount failed using ProcessBuilder")
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException("Failed to unmount using ProcessBuilder")
            }
//        }
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

//        if (nativeCall) {
            // Native operation using JNA
//            def result = C_LIB.umount2(target, (int) CLib.MNT_FORCE)
//            if (result != 0) {
//                throw new IllegalStateException("umount2 (force) failed: ${Native.getLastError()}")
//            }
//        } else {
            // Fallback to ProcessBuilder
            try {
                def process = new ProcessBuilder("umount", "-f", target).start()
                process.waitFor()
                if (process.exitValue() != 0) {
                    throw new IllegalStateException("umount2 failed using ProcessBuilder")
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException("Failed to force unmount using ProcessBuilder")
            }
//        }
    }

    /**
     * Reboots the system.
     *
     * @param cmd The reboot command, e.g., CLib.LINUX_REBOOT_CMD_RESTART.
     */
    static void reboot(int cmd) {
//        if (nativeCall) {
            // Native operation using JNA
//            def result = C_LIB.reboot(cmd)
//            if (result != 0) {
//                throw new IllegalStateException("reboot failed: ${Native.getLastError()}")
//            }
//        } else {
            // Fallback to ProcessBuilder - Trigger a reboot using /proc/sysrq-trigger
            try {
                def process = new ProcessBuilder("sh", "-c", "echo b > /proc/sysrq-trigger").start()
                process.waitFor()
                if (process.exitValue() != 0) {
                    throw new IllegalStateException("Reboot failed using ProcessBuilder")
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException("Failed to reboot using ProcessBuilder")
            }
//        }
    }

    /**
     * Synchronizes the filesystem buffers.
     */
    static void sync() {
//        if (nativeCall) {
//             Native operation using JNA
//            C_LIB.sync()
//        } else {
            // Fallback to ProcessBuilder - Directly call the sync binary
            try {
                def process = new ProcessBuilder("sync").start()
                process.waitFor()
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException("Failed to sync using ProcessBuilder")
            }
//        }
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
            throw new IllegalArgumentException("File must not be null")
        }

//        if (nativeCall) {
            // Native operation using JNA
//            def result = C_LIB.execve(file, argv, envp)
//            if (result != 0) {
//                throw new IllegalStateException("execve failed: ${Native.getLastError()}")
//            }
//        } else {
            // Fallback to ProcessBuilder - Execute the file with arguments
            try {
                def command = [file] + ((argv ?: []) as String)
                def process = new ProcessBuilder(command).inheritIO().start()
                process.waitFor()
                if (process.exitValue() != 0) {
                    throw new IllegalStateException("execve failed using ProcessBuilder")
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException("Failed to execve using ProcessBuilder")
            }
//        }
    }
}
