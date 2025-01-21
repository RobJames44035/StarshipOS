/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8033441
 * @summary Test to ensure that line numbers are now present with the -XX:+PrintOptoAssembly command line option
 *
 * @requires vm.flagless
 * @requires vm.compiler2.enabled & vm.debug == true
 *
 * @library /test/lib
 * @run driver compiler.arguments.TestPrintOptoAssemblyLineNumbers
 */

package compiler.arguments;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

//    THIS TEST IS LINE NUMBER SENSITIVE

public class TestPrintOptoAssemblyLineNumbers {
    public static void main(String[] args) throws Throwable {
        // create subprocess to run some code with -XX:+PrintOptoAssembly enabled
        String[] procArgs = new String[] {
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:-TieredCompilation",
            "-XX:+PrintOptoAssembly",
            CheckC2OptoAssembly.class.getName()
        };

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(procArgs);
        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldHaveExitValue(0);

        if (oa.getOutput().contains("TestPrintOptoAssemblyLineNumbers$CheckC2OptoAssembly::main @ bci:11")) {
            // if C2 optimizer invoked ensure output includes line numbers:
            oa.stdoutShouldContain("TestPrintOptoAssemblyLineNumbers$CheckC2OptoAssembly::main @ bci:11 (line 72)");
        }
    }

    public static class CheckC2OptoAssembly { // contents of this class serves to just invoke C2
        public static boolean foo(String arg) {
            return arg.contains("45");
        }

        public static void main(String[] args) {
            int count = 0;
            for (int x = 0; x < 200_000; x++) {
                if (foo("something" + x)) { // <- test expects this line of code to be on line 72
                    count += 1;
                }
            }
            System.out.println("count: " + count);
        }
    }
}
