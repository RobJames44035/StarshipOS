/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package jdk.internal.agent;

import java.io.File;
import java.io.IOException;

/*
 * Windows implementation of sun.management.FileSystem
 */
@SuppressWarnings({"removal", "restricted"})
public class FileSystemImpl extends FileSystem {

    public boolean supportsFileSecurity(File f) throws IOException {
        String path = f.getAbsolutePath();
        if (path.indexOf(0) >= 0) {
            throw new IOException("illegal filename");
        }
        return isSecuritySupported0(f.getAbsolutePath());
    }

    public boolean isAccessUserOnly(File f) throws IOException {
        String path = f.getAbsolutePath();
        if (path.indexOf(0) >= 0) {
            throw new IOException("illegal filename");
        }
        if (!isSecuritySupported0(path)) {
            throw new UnsupportedOperationException("File system does not support file security");
        }
        return isAccessUserOnly0(path);
    }

    // Native methods

    static native void init0();

    static native boolean isSecuritySupported0(String path) throws IOException;

    static native boolean isAccessUserOnly0(String path) throws IOException;

    // Initialization

    static {
        System.loadLibrary("management_agent");
        init0();
    }
}
