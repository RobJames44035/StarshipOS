/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8186211
 * @summary Test CONSTANT_Dynamic where the BSM is invoked via a REF_newInvokeSpecial.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile CondyNewInvokeSpecial.jasm
 * @run driver CondyNewInvokeSpecialTest
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.compiler.InMemoryJavaCompiler;

public class CondyNewInvokeSpecialTest {
    public static void main(String args[]) throws Throwable {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("-Xverify:all",
                                                                             "CondyNewInvokeSpecial");
        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldContain("In CondyNewInvokeSpecial <init> method");
        oa.shouldHaveExitValue(0);
    }
}
