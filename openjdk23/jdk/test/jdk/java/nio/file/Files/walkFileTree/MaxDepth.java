/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @summary Unit test for Files.walkFileTree to test maxDepth parameter
 * @library ../..
 * @compile MaxDepth.java CreateFileTree.java
 * @run main MaxDepth
 */

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;
import java.util.*;

public class MaxDepth {
    public static void main(String[] args) throws IOException {
        Path top = CreateFileTree.create();
        try {
            test(top);
        } finally {
            TestUtil.removeAll(top);
        }
    }

    static void test(final Path top) throws IOException {
        for (int i=0; i<5; i++) {
            Set<FileVisitOption> opts = Collections.emptySet();
            final int maxDepth = i;
            Files.walkFileTree(top, opts, maxDepth, new SimpleFileVisitor<Path>() {
                // compute depth based on relative path to top directory
                private int depth(Path file) {
                    Path rp = file.relativize(top);
                    return (rp.getFileName().toString().equals("")) ? 0 : rp.getNameCount();
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    int d = depth(dir);
                    if (d == maxDepth)
                        throw new RuntimeException("Should not open directories at maxDepth");
                    if (d > maxDepth)
                        throw new RuntimeException("Too deep");
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    int d = depth(file);
                    if (d > maxDepth)
                        throw new RuntimeException("Too deep");
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}
