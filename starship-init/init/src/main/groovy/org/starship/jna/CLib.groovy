/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.jna

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure

interface CLib extends Library {

    // Mount-related constants
    long MNT_FORCE = 1L
    long MS_RDONLY = 1L
    long MS_NOEXEC = 8L
    long DEV_CONSOLE = (5L << 8) | 1L // Device ID for /dev/console: c 5 1

    // File permission constants
    int S_IFCHR = 0x2000 // Character special file
    int S_IRUSR = 0x0100 // Read permission, owner
    int S_IWUSR = 0x0080 // Write permission, owner

    // Reboot command
    int LINUX_REBOOT_CMD_HALT = 0xCDEF0123

    // Load the native library
    CLib INSTANCE = Native.load("c", CLib.class)

    //---------------------------------
    // File System Management
    //---------------------------------

    int lstat(String path, Stat stat)

    class Stat extends Structure {
        long st_dev // Device ID
        long st_ino // Inode number

        @Override
        protected List<String> getFieldOrder() {
            ["st_dev", "st_ino"]
        }
    }

    int mknod(String pathname, int mode, long dev)

    int mount(String source, String target, String filesystemType, long mountFlags, String data)

    int umount(String target)

    int umount2(String target, int flags)

    //---------------------------------
    // System Information
    //---------------------------------

    int sethostname(String name, int len)

    int gethostname(byte[] name, int len)

    //---------------------------------
    // Process and Signal Management
    //---------------------------------

    int kill(int pid, int sig)

    int waitpid(int pid, Pointer status, int options)

    int fork()

    int execve(String filename, String[] argv, String[] envp)

    void _exit(int status)

    //---------------------------------
    // Time & Timing Functions
    //---------------------------------

    int sleep(int seconds)

    long time(Pointer tloc)

    //---------------------------------
    // System Utility Functions
    //---------------------------------

    void sync()

    int reboot(int magic)
}