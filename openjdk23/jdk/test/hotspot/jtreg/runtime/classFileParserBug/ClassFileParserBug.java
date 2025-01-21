/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8040018
 * @library /test/lib
 * @summary Check for exception instead of assert.
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @compile LambdaMath.jcod
 * @run driver ClassFileParserBug
 */

import java.io.File;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class ClassFileParserBug {
    public static void main(String args[]) throws Throwable {

        System.out.println("Regression test for bug 8040018");
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("LambdaMath");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("java.lang.ClassFormatError: Bad length on BootstrapMethods");
        output.shouldHaveExitValue(1);
    }
}
