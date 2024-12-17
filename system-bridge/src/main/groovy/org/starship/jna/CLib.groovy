package org.starship.jna

import com.sun.jna.Library
import com.sun.jna.Native

interface CLib extends Library {
    // Bind to libc
    CLib INSTANCE = Native.load("c", CLib.class)

    // int umount(const char *target)
    int umount(String target)

    // int umount2(const char *target, int flags) // Support for forced unmount
    int umount2(String target, int flags)

    // Add other system calls, e.g.:
    /**
     * Mount a filesystem.
     *
     * @param source the source of the filesystem (e.g., a block device or "none" for virtual filesystems).
     * @param target the directory where the filesystem will be mounted.
     * @param filesystemtype the type of the filesystem (e.g., "ext4", "tmpfs").
     * @param mountflags bitmask of flags used for mounting.
     * @param data optional data string with filesystem-specific options.
     * @return 0 on success, -1 on failure (check errno using Native.getLastError()).
     */
    int mount(String source, String target, String filesystemtype, long mountflags, String data)

}
