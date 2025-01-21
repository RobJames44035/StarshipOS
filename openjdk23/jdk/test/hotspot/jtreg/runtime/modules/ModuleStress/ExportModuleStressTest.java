/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8156871
 * @summary package in the boot layer is repeatedly exported to unique module created in layers on top of the boot layer
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile ../CompilerUtils.java
 * @run driver ExportModuleStressTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class ExportModuleStressTest {

    private static final String TEST_SRC = System.getProperty("test.src");
    private static final String TEST_CLASSES = System.getProperty("test.classes");

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path MODS_DIR = Paths.get(TEST_CLASSES, "mods");

    /**
     * Compiles all module definitions used by the test
     */
    public static void main(String[] args) throws Exception {

        boolean compiled;
        // Compile module jdk.test declaration
        compiled = CompilerUtils.compile(
            SRC_DIR.resolve("jdk.test"),
            MODS_DIR.resolve("jdk.test"));
        if (!compiled) {
            throw new RuntimeException("Test failed to compile module jdk.test");
        }

        // Compile module jdk.translet declaration
        compiled = CompilerUtils.compile(
            SRC_DIR.resolve("jdk.translet"),
            MODS_DIR.resolve("jdk.translet"),
            "--add-exports=jdk.test/test=jdk.translet",
            "-p", MODS_DIR.toString());
        if (!compiled) {
            throw new RuntimeException("Test failed to compile module jdk.translet");
        }

        // Sanity check that the test, jdk.test/test/Main.java
        // runs without error.
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-p", MODS_DIR.toString(),
            "-m", "jdk.test/test.Main");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("failed: 0")
              .shouldHaveExitValue(0);
    }
}
