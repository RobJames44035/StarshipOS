/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Test the native process builder API.
 * @library /test/lib
 * @run main/native TestNativeProcessBuilder
 */


import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestNativeProcessBuilder {
    public static void main(String args[]) throws Exception {
        ProcessBuilder pb = ProcessTools.createNativeTestProcessBuilder("jvm-test-launcher");
        pb.environment().put("CLASSPATH", Utils.TEST_CLASS_PATH);
        new OutputAnalyzer(pb.start())
            .shouldHaveExitValue(0)
            .stdoutShouldContain("Hello Test");
    }

    public static class Test {
        public static void test() {
            System.out.println("Hello Test");
        }
    }
}
