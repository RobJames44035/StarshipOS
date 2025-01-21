/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8148854
 * @summary Ensure class name loaded by app class loader is format checked by default
 * @requires vm.flagless
 * @library /test/lib
 * @compile BadHelloWorld.jcod
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver FormatCheckingTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class FormatCheckingTest {
    public static void main(String args[]) throws Throwable {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("BadHelloWorld");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("java.lang.ClassFormatError: Illegal class name");
        output.shouldHaveExitValue(1);
    }
}
