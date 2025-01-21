/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import jdk.test.lib.process.ProcessTools;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

/*
 * @test
 * @summary Make sure that jimages are consistent when created by jlink. Copies test jdk and runs against original.
 * @bug 8252730
 * @modules jdk.jlink
 *          jdk.management
 *          jdk.unsupported
 *          jdk.charsets
 * @library /test/lib
 * @run main JLinkReproducible3Test
 */
public class JLinkReproducible3Test {

    public static void main(String[] args) throws Exception {
        Path image1 = Paths.get("./image1");
        Path image2 = Paths.get("./image2");

        Path copyJdk1Dir = Path.of("./copy-jdk1-tmpdir");
        Files.createDirectory(copyJdk1Dir);

        Path copyJdk2Dir = Path.of("./copy-jdk2-tmpdir");
        Files.createDirectory(copyJdk2Dir);

        Path jdkTestDir = Path.of(
                Optional.of(
                        System.getProperty("test.jdk"))
                        .orElseThrow(() -> new RuntimeException("Couldn't load JDK Test Dir"))
        );

        copyJDK(jdkTestDir, copyJdk1Dir);
        copyJDK(jdkTestDir, copyJdk2Dir);

        Path copiedJlink1 = Optional.of(
                Paths.get(copyJdk1Dir.toString(), "bin", "jlink"))
                .orElseThrow(() -> new RuntimeException("Unable to load copied jlink")
                );

        Path copiedJlink2 = Optional.of(
                Paths.get(copyJdk2Dir.toString(), "bin", "jlink"))
                .orElseThrow(() -> new RuntimeException("Unable to load copied jlink")
                );

        runCopiedJlink(copiedJlink1.toString(), "--add-modules", "java.base,jdk.management,jdk.unsupported,jdk.charsets", "--output", image1.toString());
        runCopiedJlink(copiedJlink2.toString(), "--add-modules", "java.base,jdk.management,jdk.unsupported,jdk.charsets", "--output", image2.toString());

        long mismatch = Files.mismatch(image1.resolve("lib").resolve("modules"), image2.resolve("lib").resolve("modules"));
        if (mismatch != -1L) {
            throw new RuntimeException("jlink producing inconsistent result in modules. Mismatch in modules file occurred at byte position " + mismatch);
        }
    }

    private static void runCopiedJlink(String... args) throws Exception {
        var process = new ProcessBuilder(args);
        var res = ProcessTools.executeProcess(process);
        res.shouldHaveExitValue(0);
    }

    private static void copyJDK(Path src, Path dst) throws Exception {
        Files.walk(src).skip(1).forEach(file -> {
            try {
                Files.copy(file, dst.resolve(src.relativize(file)), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        });
    }
}

