/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package testapp;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Launched by SetDefaultProvider to test startup with the default file system
 * provider overridden.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getDefault();
        if (fs.getClass().getModule() == Object.class.getModule())
            throw new RuntimeException("FileSystemProvider not overridden");

        // exercise the file system
        Path dir = Files.createTempDirectory("tmp");
        if (dir.getFileSystem() != fs)
            throw new RuntimeException("'dir' not in default file system");
        System.out.println("created: " + dir);

        Path foo = Files.createFile(dir.resolve("foo"));
        if (foo.getFileSystem() != fs)
            throw new RuntimeException("'foo' not in default file system");
        System.out.println("created: " + foo);

        // exercise interop with java.io.File
        File file = foo.toFile();
        Path path = file.toPath();
        if (path.getFileSystem() != fs)
            throw new RuntimeException("'path' not in default file system");
        if (!path.equals(foo))
            throw new RuntimeException(path + " not equal to " + foo);

        // exercise the file type detector
        String fileType = Files.probeContentType(Path.of("."));
    }
}
