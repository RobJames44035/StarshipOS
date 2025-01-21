/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test TestSunBootLibraryPath.java
 * @bug 8227021
 * @summary Confirm using too-long paths in sun.boot.library.path causes failure and useful error message.
 * @author afarley
 * @library /test/lib
 * @build jdk.test.lib.process.ProcessTools
 * @run driver TestSunBootLibraryPath
 */

import jdk.test.lib.process.ProcessTools;

public class TestSunBootLibraryPath {
    static String expectedErrorMessage = "The VM tried to use a path that exceeds the maximum path length for this system.";

    public static void main(String[] args) throws Exception {
        // Allows us to re-use this class as a do-nothing test class simply by passing a "Do-Nothing" argument.
        if (args.length == 0) {
            // Grab any path.
            String tooLongPath = System.getProperty("sun.boot.library.path");
            // Add enough characters to make it "too long".
            tooLongPath += "a".repeat(5000);
            // Start a java process with this property set, and check that:
            // 1) The process failed and
            // 2) The error message was correct.
            ProcessTools.executeTestJava("-Dsun.boot.library.path=" + tooLongPath,
                                         "TestSunBootLibraryPath",
                                         "'Do-Nothing'")
                                         .shouldNotHaveExitValue(0)
                                         .stdoutShouldContain(expectedErrorMessage);
        } else if (!args[0].equals("Do-Nothing")) {
            // Fail, to prevent accidental args from causing accidental test passing.
            throw new IllegalArgumentException("Test was launched with an invalid argument.");
        }
    }
}
