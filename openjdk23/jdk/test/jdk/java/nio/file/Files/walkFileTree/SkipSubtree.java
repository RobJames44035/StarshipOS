/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @summary Unit test for Files.walkFileTree to test SKIP_SUBTREE return value
 * @library ../..
 * @compile SkipSubtree.java CreateFileTree.java
 * @run main SkipSubtree
 * @key randomness
 */
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SkipSubtree {

    static final Random rand = new Random();
    static final Set<Path> skipped = new HashSet<>();

    // check if this path should have been skipped
    static void check(Path path) {
        do {
            if (skipped.contains(path))
                throw new RuntimeException(path + " should not have been visited");
            path = path.getParent();
        } while (path != null);
    }

    // indicates if the subtree should be skipped
    static boolean skip(Path path) {
        if (rand.nextInt(3) == 0) {
            skipped.add(path);
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        Path top = CreateFileTree.create();
        try {
            test(top);
        } finally {
            TestUtil.removeAll(top);
        }
    }

    static void test(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                check(dir);
                if (skip(dir))
                    return FileVisitResult.SKIP_SUBTREE;
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                check(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException x) {
                if (x != null)
                    throw new RuntimeException(x);
                check(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
