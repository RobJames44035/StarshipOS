/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import jdk.test.lib.util.FileUtils;
import jdk.test.lib.compiler.CompilerUtils;
import static jdk.test.lib.process.ProcessTools.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

/**
 * @test
 * @bug 8087335
 * @summary Tests for Class.forName(Module,String)
 * @library /test/lib
 * @modules jdk.compiler
 * @build jdk.test.lib.Platform
 *        jdk.test.lib.util.FileUtils
 *        jdk.test.lib.compiler.CompilerUtils
 *        jdk.test.lib.process.ProcessTools
 *        TestDriver TestMain TestLayer
 * @run testng TestDriver
 */

public class TestDriver {

    private static final String TEST_SRC =
            Paths.get(System.getProperty("test.src")).toString();
    private static final String TEST_CLASSES =
            Paths.get(System.getProperty("test.classes")).toString();

    private static final Path MOD_SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path MOD_DEST_DIR = Paths.get("mods");

    private static final String[] modules = new String[] {"m1", "m2"};

    /**
     * Compiles all modules used by the test.
     */
    @BeforeClass
    public void setup() throws Exception {
        assertTrue(CompilerUtils.compile(
                        MOD_SRC_DIR, MOD_DEST_DIR,
                        "--module-source-path",
                        MOD_SRC_DIR.toString()));

        copyDirectories(MOD_DEST_DIR.resolve("m1"), Paths.get("mods1"));
        copyDirectories(MOD_DEST_DIR.resolve("m2"), Paths.get("mods2"));
    }

    @Test
    public void test() throws Exception {
        String[] options = new String[] {
                "-cp", TEST_CLASSES,
                "--module-path", MOD_DEST_DIR.toString(),
                "--add-modules", String.join(",", modules),
                "-m", "m2/p2.test.Main"
        };
        runTest(options);
    }

    @Test
    public void testUnnamedModule() throws Exception {
        String[] options = new String[] {
                "-cp", TEST_CLASSES,
                "--module-path", MOD_DEST_DIR.toString(),
                "--add-modules", String.join(",", modules),
                "TestMain"
        };
        runTest(options);
    }

    @Test
    public void testLayer() throws Exception {
        String[] options = new String[] {
                "-cp", TEST_CLASSES,
                "TestLayer"
        };

        runTest(options);
    }

    private void runTest(String[] options) throws Exception {
        assertTrue(executeTestJava(options)
                        .outputTo(System.out)
                        .errorTo(System.err)
                        .getExitValue() == 0);
    }

    private void copyDirectories(Path source, Path dest) throws IOException {
        if (Files.exists(dest))
            FileUtils.deleteFileTreeWithRetry(dest);
        Files.walk(source, Integer.MAX_VALUE)
                .filter(Files::isRegularFile)
                .forEach(p -> {
                    try {
                        Path to = dest.resolve(source.relativize(p));
                        Files.createDirectories(to.getParent());
                        Files.copy(p, to);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }
}
