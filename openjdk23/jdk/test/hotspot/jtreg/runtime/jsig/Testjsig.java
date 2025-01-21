/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test Testjsig.java
 * @bug 8017498
 * @bug 8020791
 * @bug 8021296
 * @bug 8022301
 * @bug 8025519
 * @summary sigaction(sig) results in process hang/timed-out if sig is much greater than SIGRTMAX
 * @requires os.family != "windows"
 * @library /test/lib
 * @compile TestJNI.java
 * @run driver Testjsig
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class Testjsig {

    public static void main(String[] args) throws Throwable {

        // Get the JDK, library and class path properties
        String libpath = System.getProperty("java.library.path");

        // Create a new java process for the TestJNI Java/JNI test
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-Djava.library.path=" + libpath + ":.",
            "TestJNI",
            "100");

        // Start the process and check the output
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        output.shouldContain("old handler");
    }
}
