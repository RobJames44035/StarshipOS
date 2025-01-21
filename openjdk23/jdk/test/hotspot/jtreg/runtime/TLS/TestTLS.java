/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @summary Test with extra TLS size.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @requires os.family == "linux"
 * @compile T.java
 * @run main/native TestTLS
 */
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;


public class TestTLS {
    public static void main(String args[]) throws Exception {
        test01();
    }

    // Testcase 1. Run with stack size adjusted for TLS, expect success
    public static void test01() throws Exception {
        ProcessBuilder pb = ProcessTools.createNativeTestProcessBuilder("stack-tls", "-add_tls");
        pb.environment().put("CLASSPATH", Utils.TEST_CLASS_PATH);
        new OutputAnalyzer(pb.start())
            .shouldHaveExitValue(0);
    }

    // Testcase 2. Run with no stack size adjustment and expect failure.
    // Potential failures include StackOverflowError, thread creation failures,
    // crashes, and etc. The test case can be used to demonstrate the TLS issue
    // but is excluded from running in regular testing.
    public static void test02() throws Exception {
        ProcessBuilder pb = ProcessTools.createNativeTestProcessBuilder("stack-tls");
        pb.environment().put("CLASSPATH", Utils.TEST_CLASS_PATH);
        new OutputAnalyzer(pb.start())
            .shouldHaveExitValue(1);
    }
}
