/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package compiler.c2;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/*
 * @test
 * @bug 8247408
 * @summary C2 should convert ((var&16) == 16) to ((var&16) != 0) for power-of-two constants
 * @library /test/lib /
 *
 * @requires vm.flagless
 * @requires os.arch=="aarch64" | os.arch=="amd64" | os.arch == "ppc64le" | os.arch == "riscv64"
 * @requires vm.debug == true & vm.compiler2.enabled
 *
 * @run driver compiler.c2.TestBit
 */
public class TestBit {

    static void runTest(String testName) throws Exception {
        String className = TestBit.class.getName();
        String[] procArgs = {
            "-Xbatch",
            "-XX:-TieredCompilation",
            "-XX:+PrintOptoAssembly",
            "-XX:CompileCommand=compileonly," + className + "::tst*",
            className, testName};

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(procArgs);
        OutputAnalyzer output = new OutputAnalyzer(pb.start());

        String expectedTestBitInstruction =
            "ppc64le".equals(System.getProperty("os.arch")) ? "ANDI" :
            "aarch64".equals(System.getProperty("os.arch")) ? "tb"   :
            "amd64".equals(System.getProperty("os.arch"))   ? "test" :
            "riscv64".equals(System.getProperty("os.arch")) ? "andi" : null;

        if (expectedTestBitInstruction != null) {
            output.shouldContain(expectedTestBitInstruction);
        } else {
            System.err.println("unexpected os.arch");
        }
    }

    static final int ITER = 100000; // ~ Tier4CompileThreshold + compilation time

    // dummy volatile variable
    public static volatile long c = 0;

    // C2 is expected to generate test bit instruction on the test
    static void tstBitLong(long value) {
        if (1L == (1L & value)) {
            c++;
        } else {
            c--;
        }
    }

    // C2 is expected to generate test bit instruction on the test
    static void tstBitInt(int value) {
        if (1 == (1 & value)) {
            c++;
        } else {
            c--;
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            // Fork VMs to check their debug compiler output
            runTest("tstBitLong");
            runTest("tstBitInt");
        }
        if (args.length > 0) {
            // We are in a forked VM to execute the named test
            String testName = args[0];
            switch (testName) {
            case "tstBitLong":
                for (int i = 0; i < ITER; i++) {
                    tstBitLong(i % 2);
                }
                break;
            case "tstBitInt":
                for (int i = 0; i < ITER; i++) {
                    tstBitInt(i % 2);
                }
                break;
            default:
                throw new RuntimeException("unexpected test name " + testName);
            }
        }
    }
}
