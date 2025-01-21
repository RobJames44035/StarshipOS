/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8139564 8203960
 * @summary defaultmethods=debug should have logging from each of the statements in the code
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver DefaultMethodsTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class DefaultMethodsTest {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:defaultmethods=debug",
                                                                             InnerClass.class.getName());
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("Slots that need filling:");
        output.shouldContain("requires default method processing");
        output.shouldContain("Looking for default methods for slot ");
        output.shouldContain("Creating defaults and overpasses...");
        output.shouldContain("for slot: ");
        output.shouldContain("Default method processing complete");
        output.shouldContain("overpass methods");
        output.shouldContain("default methods");
        output.shouldHaveExitValue(0);
    }

    interface TestInterface {
        default void doSomething() {
            System.out.println("Default TestInterface");
        }
    }

    public static class InnerClass implements TestInterface {
        // InnerClass implements TestInterface with a default method.
        // Loading of InnerClass will trigger default method processing.
        public static void main(String[] args) throws Exception {
            System.out.println("Inner Class");
        }
    }
}

