/*
 * StarshipOS Copyright (c) 2025. R.A.James
 *
 * Licensed under GPL2, GPL3 and Apache 2
 */

package org.starship;

public class CLibJNI {

    static {
        System.loadLibrary("libstarshipclib"); // Load the native library (libstarshipc.so)
    }

    // Native method declarations (these are implemented in C)
    public native int sethostname(String name, int len);

    public native int gethostname(byte[] name, int len);

    public native int mount(String source, String target, String filesystemType, long mountFlags, String data);

    public native int umount(String target);

    public native int umount2(String target, int flags);

    public native int mknod(String pathname, int mode, long dev);

    public native int reboot(int magic);

    public native void sync();

    public native int execve(String filename, String[] argv, String[] envp);

    public native String getenv(String name);

    public native int setenv(String name, String value, int overwrite);
}
