/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class SetupGetCallerClass {

    private static final String BOOT_CLASS
            = boot.GetCallerClass.class.getSimpleName() + ".class";
    private static final String TEST_CLASSES
            = System.getProperty("test.classes", "build/classes");

    public static void main(String[] args) throws IOException {
        Path source = Path.of(TEST_CLASSES, "boot").resolve(BOOT_CLASS);
        Path dest = Path.of("bcp", "boot");
        Path target = dest.resolve(BOOT_CLASS);
        Files.createDirectories(dest);
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
