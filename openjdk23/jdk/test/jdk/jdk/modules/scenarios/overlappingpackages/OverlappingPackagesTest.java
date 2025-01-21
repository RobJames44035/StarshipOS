/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @library /test/lib
 * @modules jdk.compiler
 * @build OverlappingPackagesTest
 *        jdk.test.lib.compiler.CompilerUtils
 * @run testng OverlappingPackagesTest
 * @summary Basic test to ensure that startup fails if two or more modules
 *          in the boot Layer have the same package
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import jdk.test.lib.compiler.CompilerUtils;

import static jdk.test.lib.process.ProcessTools.executeTestJava;
import static org.testng.Assert.assertTrue;

@Test
public class OverlappingPackagesTest {

    private static final String TEST_SRC = System.getProperty("test.src");

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path MODS_DIR = Paths.get("mods");

    // the names of the modules in this test
    private static List<String> modules = Arrays.asList("m1", "m2", "test");


    /**
     * Compiles all modules used by the test
     */
    @BeforeTest
    public void compileAll() throws Exception {
        for (String mn : modules) {
            Path src = SRC_DIR.resolve(mn);
            Path mods = MODS_DIR.resolve(mn);
            assertTrue(CompilerUtils.compile(src, mods));
        }
        Path srcMisc = SRC_DIR.resolve("misc");
        Path modsMisc = MODS_DIR.resolve("misc");
        assertTrue(CompilerUtils.compile(srcMisc, modsMisc));
    }

    /**
     * Sanity check that the test runs without error.
     */
    public void testNoOverlappingPackages() throws Exception {
        int exitValue
            = executeTestJava("--module-path", MODS_DIR.toString(),
                              "-m", "test/test.Main")
                .outputTo(System.out)
                .errorTo(System.err)
                .getExitValue();

        assertTrue(exitValue == 0);
    }


    /**
     * Run the test with "--add-modules misc", the misc module has package
     * jdk.internal.misc and so should overlap with the base module.
     */
    public void testOverlapWithBaseModule() throws Exception {
        int exitValue
            = executeTestJava("--module-path", MODS_DIR.toString(),
                              "--add-modules", "misc",
                              "-m", "test/test.Main")
                .outputTo(System.out)
                .errorTo(System.err)
                .getExitValue();

        assertTrue(exitValue != 0);
    }

    /**
     * Run the test with "--add-modules m1,m2". Both modules have package p.
     */
    public void testOverlap() throws Exception {
        int exitValue
            = executeTestJava("--module-path", MODS_DIR.toString(),
                              "--add-modules", "m1,m2",
                              "-m", "test/test.Main")
                .outputTo(System.out)
                .errorTo(System.err)
                .getExitValue();

        assertTrue(exitValue != 0);
    }


}
