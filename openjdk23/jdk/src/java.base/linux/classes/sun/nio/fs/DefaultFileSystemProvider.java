/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package sun.nio.fs;

import java.nio.file.FileSystem;

/**
 * Creates this platform's default FileSystemProvider.
 */

public class DefaultFileSystemProvider {
    private static final LinuxFileSystemProvider INSTANCE
        = new LinuxFileSystemProvider();

    private DefaultFileSystemProvider() { }

    /**
     * Returns the platform's default file system provider.
     */
    public static LinuxFileSystemProvider instance() {
        return INSTANCE;
    }

    /**
     * Returns the platform's default file system.
     */
    public static FileSystem theFileSystem() {
        return INSTANCE.theFileSystem();
    }
}
