/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.nio.file.Path;
import java.nio.file.Paths;

import static jdk.test.lib.process.ProcessTools.executeTestJava;
import jdk.test.lib.compiler.CompilerUtils;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @test
 * @bug 8087335
 * @library /test/lib
 * @modules jdk.compiler
 * @build ResourcesTest jdk.test.lib.compiler.CompilerUtils
 * @run testng ResourcesTest
 * @summary Driver for basic test of ClassLoader getResource and getResourceAsStream
 */

@Test
public class ResourcesTest {

    private static final String TEST_SRC = System.getProperty("test.src");

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path CLASSES_DIR = Paths.get("classes");
    private static final Path MODS_DIR = Paths.get("mods");

    /**
     * Compiles the modules used by the test and the test Main
     */
    @BeforeTest
    public void compileAll() throws Exception {
        boolean compiled;

        // javac --module-source-path mods -d mods src/**
        compiled = CompilerUtils
            .compile(SRC_DIR,
                     MODS_DIR,
                     "--module-source-path", SRC_DIR.toString());
        assertTrue(compiled);

        // javac --module-path mods -d classes Main.java
        compiled = CompilerUtils
            .compile(Paths.get(TEST_SRC, "Main.java"),
                     CLASSES_DIR,
                     "--module-path", MODS_DIR.toString(),
                     "--add-modules", "m1,m2");
        assertTrue(compiled);
    }

    /**
     * Run the test
     */
    public void runTest() throws Exception {
        int exitValue
            = executeTestJava("--module-path", MODS_DIR.toString(),
                              "--add-modules", "m1,m2",
                              "-cp", CLASSES_DIR.toString(),
                              "Main")
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue();

        assertTrue(exitValue == 0);
    }
}

