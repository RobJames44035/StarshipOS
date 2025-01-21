/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @summary Verifies that an attempt to call Selector.open() on a non-default
 *          file system succeeds.
 * @build CustomFileSystem CustomFileSystemProvider
 * @run main/othervm -Djava.nio.file.spi.DefaultFileSystemProvider=CustomFileSystemProvider CustomFileSystem
 */

public class CustomFileSystem {
    public static void main(String args[]) throws java.io.IOException {
        java.nio.channels.Selector.open();
    }
}
