/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8202758
 * @summary Ensure that if the JVM encounters a ClassLoader whose unnamedModule field is not set an InternalError results.
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile ClassLoaderNoUnnamedModule.java
 * @run driver ClassLoaderNoUnnamedModuleTest
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class ClassLoaderNoUnnamedModuleTest {
    public static void main(String args[]) throws Throwable {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                               "--add-modules=java.base",
                               "--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED",
                               "-XX:-CreateCoredumpOnCrash",
                               "ClassLoaderNoUnnamedModule");
        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldNotHaveExitValue(0);
        oa.shouldContain("Internal Error");
        oa.shouldContain("unnamed module");
        oa.shouldContain("null or not an instance of java.lang.Module");
    }
}
