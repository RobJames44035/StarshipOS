/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 * Utility class for zipfs tests.
 */

class Utils {
    private Utils() { }

    /**
     * Creates a JAR file of the given name with 0 or more named entries.
     *
     * @return Path to the newly created JAR file
     */
    static Path createJarFile(String name, String... entries) throws IOException {
        Path jarFile = Paths.get("basic.jar");
        Random rand = new Random();
        try (OutputStream out = Files.newOutputStream(jarFile);
             JarOutputStream jout = new JarOutputStream(out)) {
            int len = 100;
            for (String entry: entries) {
                JarEntry je = new JarEntry(entry);
                jout.putNextEntry(je);
                byte[] bytes = new byte[len];
                rand.nextBytes(bytes);
                jout.write(bytes);
                jout.closeEntry();
                len += 1024;
            }
        }
        return jarFile;
    }
}
