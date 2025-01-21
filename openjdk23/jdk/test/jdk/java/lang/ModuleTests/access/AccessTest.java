/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import jdk.test.lib.compiler.CompilerUtils;
import static jdk.test.lib.process.ProcessTools.executeTestJava;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @test
 * @library /test/lib
 * @modules jdk.compiler
 * @build AccessTest jdk.test.lib.compiler.CompilerUtils
 * @run testng AccessTest
 * @summary Driver for test that checks access to access to types in
 *          exported and non-exported packages.
 */

@Test
public class AccessTest {

    private static final String TEST_SRC = System.getProperty("test.src");

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path MODS_DIR = Paths.get("mods");


    // the names of the modules in this test
    private static List<String> modules = Arrays.asList("test", "target");


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
    }

    /**
     * Run the test
     */
    public void runTest() throws Exception {
        int exitValue
            = executeTestJava("--module-path", MODS_DIR.toString(),
                              "--add-modules", "target",
                              "-Dsun.reflect.enableStrictMode=true",
                              "-m", "test/test.Main")
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue();

        assertTrue(exitValue == 0);
    }

}
