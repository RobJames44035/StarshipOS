/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8280902 8281000 8281003 8281006 8281001
 * @summary Test uses custom launcher that starts VM using JNI that verifies
 *          various API called with a null caller class function properly.
 * @library /test/lib
 * @modules java.base/jdk.internal.module
 *          jdk.compiler
 * @build NullCallerTest
 *        jdk.test.lib.compiler.CompilerUtils
 * @run main/native NullCallerTest
 */

// Test disabled on AIX since we cannot invoke the JVM on the primordial thread.

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jdk.test.lib.Platform;
import jdk.test.lib.compiler.CompilerUtils;
import jdk.test.lib.process.ProcessTools;

public class NullCallerTest {

    private static final String TEST_SRC = System.getProperty("test.src");

    // the module name of the test module
    private static final String TEST_MODULE = "n";

    private static final Path SRC_DIR    = Paths.get(TEST_SRC, "src");
    private static final Path MODS_DIR   = Paths.get("mods");
    private static final Path TEST_MOD_DIR = MODS_DIR.resolve(TEST_MODULE);
    private static final Path TEST_MOD_SRC_DIR = SRC_DIR.resolve(TEST_MODULE);

    /*
     * Build the test module called 'n' which opens the package 'open'
     * to everyone.  There is also a package 'closed' which is neither
     * opened or exported.
     */
    static void compileTestModule() throws Exception {
        // javac -d mods/$TESTMODULE src/$TESTMODULE/**
        boolean compiled = CompilerUtils.compile(SRC_DIR.resolve(TEST_MODULE), TEST_MOD_DIR);
        assert (compiled);

        // copy resources
        var resources = List.of("open/test.txt", "closed/test.txt", "open/NullCallerResource.properties");
        resources.stream().forEach(r -> {
            try {
                Files.copy(TEST_MOD_SRC_DIR.resolve(r), TEST_MOD_DIR.resolve(r));
            } catch (IOException e) {
                throw new RuntimeException("Failed to copy resource: " + r, e);
            }
        });
    }

    public static void main(String[] args) throws Exception {

        // build the module used for the test
        compileTestModule();

        ProcessBuilder pb = ProcessTools.createNativeTestProcessBuilder("NullCallerTest");
        System.out.println("Launching: " + pb.command() + " shared library path: " +
                               pb.environment().get(Platform.sharedLibraryPathVariableName()));
        ProcessTools.executeProcess(pb).shouldHaveExitValue(0);
    }

}

