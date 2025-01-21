/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test TestEmptyBootstrapMethodsAttr
 * @bug 8041918
 * @library /test/lib
 * @summary Test empty bootstrap_methods table within BootstrapMethods attribute
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @compile emptynumbootstrapmethods1.jcod emptynumbootstrapmethods2.jcod
 * @run driver TestEmptyBootstrapMethodsAttr
 */

import java.io.File;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestEmptyBootstrapMethodsAttr {

    public static void main(String args[]) throws Throwable {
        System.out.println("Regression test for bug 8041918");

        // Test case #1:
        // Try loading class with empty bootstrap_methods table where no
        // other attributes are following BootstrapMethods in attribute table.
        String className = "emptynumbootstrapmethods1";

        // ======= execute test case #1
        // Expect a lack of main method, this implies that the class loaded correctly
        // with an empty bootstrap_methods and did not generate a ClassFormatError.
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Duser.language=en", "-Duser.country=US", className);
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("java.lang.ClassFormatError");
        output.shouldContain("Main method not found in class " + className);
        output.shouldHaveExitValue(1);

        // Test case #2:
        // Try loading class with empty bootstrap_methods table where an
        // AnnotationDefault attribute follows the BootstrapMethods in the attribute table.
        className = "emptynumbootstrapmethods2";

        // ======= execute test case #2
        // Expect a lack of main method, this implies that the class loaded correctly
        // with an empty bootstrap_methods and did not generate ClassFormatError.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Duser.language=en", "-Duser.country=US", className);
        output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("java.lang.ClassFormatError");
        output.shouldContain("Main method not found in class " + className);
        output.shouldHaveExitValue(1);
    }
}
