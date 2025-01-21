/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;

import jdk.test.lib.util.JarUtils;

public class SetupJar {

    private static final String PROVIDER
            = "META-INF/services/java.nio.charset.spi.CharsetProvider";
    private static final String TEST_DIR = System.getProperty("test.dir", ".");

    public static void main(String args[]) throws Exception {
        Path xdir = Files.createDirectories(Paths.get(TEST_DIR, "xdir"));
        Path provider = xdir.resolve(PROVIDER);
        Files.createDirectories(provider.getParent());
        Files.write(provider, "FooProvider".getBytes(), CREATE);

        String[] files = {"FooCharset.class", "FooProvider.class"};
        for (String f : files) {
            Path source = Paths.get(System.getProperty("test.classes")).resolve(f);
            Path target = xdir.resolve(source.getFileName());
            Files.copy(source, target, REPLACE_EXISTING);
        }

        JarUtils.createJarFile(Paths.get("test.jar"), xdir);
    }
}
