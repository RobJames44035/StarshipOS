/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8141564
 * @summary vtables=trace should have logging from each of the statements in the code
 * @library /test/lib
 * @requires vm.debug
 * @requires vm.flagless
 * @compile ClassB.java
 *          p1/A.java
 *          p2/B.jcod
 *          p1/C.java
 *          p2/D.java
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver VtablesTest
 */

import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class VtablesTest {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:vtables=trace", "ClassB");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("copy vtable from ClassA to ClassB");
        output.shouldContain("Initializing: ClassB");
        output.shouldContain("adding ClassB.Method1()V");
        output.shouldContain("] overriding with ClassB.Method2()V");
        output.shouldContain("invokevirtual resolved method: caller-class:ClassB");
        output.shouldContain("invokevirtual selected method: receiver-class:ClassB");
        output.shouldContain("NOT overriding with p2.D.nooverride()V");
        output.shouldHaveExitValue(0);

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:vtables=trace", "p1/C");
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("transitive overriding superclass ");
        output.shouldHaveExitValue(0);
    }
}

