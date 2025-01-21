/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/*
 * @test
 * @bug 8238460
 * @summary Check that re-registering a native method of a boot class
 *          generates a warning when not done from a boot class
 *
 * @library /test/lib
 * @run main/native TestRegisterNativesWarning
 */

public class TestRegisterNativesWarning {

    static {
        System.loadLibrary("registerNativesWarning");
    }

    /*
     * We will replace:
     *   java/lang/Thread.java:    private static native void yield0();
     *
     * as it is simple and innocuous.
     */
    static native void test(Class<?> jlThread);

    // Using a nested class that invokes an enclosing method makes it
    // easier to setup and use the native library.
    static class Tester {
        public static void main(String[] args) throws Exception {
            System.out.println("Running test() in class loader " +
                               Tester.class.getClassLoader());
            test(Thread.class);
            Thread.yield();
        }
    }

    public static void main(String[] args) throws Exception {
        String warning = "Re-registering of platform native method: java.lang.Thread.yield0()V from code in a different classloader";

        String cp = Utils.TEST_CLASS_PATH;
        String libp = Utils.TEST_NATIVE_PATH;
        OutputAnalyzer output = ProcessTools.executeTestJava("-Djava.library.path=" + libp,
                                                             Tester.class.getName());
        output.shouldContain(warning);
        output.shouldHaveExitValue(0);
        output.reportDiagnosticSummary();

        // If we run everything from the "boot" loader there should be no warning
        output = ProcessTools.executeTestJava("-Djava.library.path=" + libp,
                                              "-Xbootclasspath/a:" + cp,
                                              "-Dsun.boot.library.path=" + libp,
                                              Tester.class.getName());
        output.shouldNotContain(warning);
        output.shouldHaveExitValue(0);
        output.reportDiagnosticSummary();
    }
}
