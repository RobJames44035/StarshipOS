/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package jdk.jfr.internal.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public final class DirectoryCleaner extends SimpleFileVisitor<Path> {

    public static void clear(Path path) throws IOException {
        Files.walkFileTree(path, new DirectoryCleaner());
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        Files.delete(path);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (exc != null) {
            throw exc;
        }
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
    }
}
