/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8186211
 * @summary Tests various ldc, ldc_w, ldc2_w instructions of CONSTANT_Dynamic.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile CondyUseLDC_W.jasm
 * @compile CondyBadLDC2_W.jasm
 * @compile CondyBadLDC.jasm
 * @run driver CondyLDCTest
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.compiler.InMemoryJavaCompiler;

public class CondyLDCTest {
    public static void main(String args[]) throws Throwable {
        // 1. Test a ldc_w instruction can be used with condy's which generate
        //    loadable constants of the following types: byte, char, short, float, integer, boolean.
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("-Xverify:all",
                                                                             "CondyUseLDC_W");
        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldNotContain("VerifyError");
        oa.shouldHaveExitValue(0);

        // 2. Test ldc2_w of a condy which returns a dynamically generated
        //    float constant, generates a VerifyError.
        pb = ProcessTools.createTestJavaProcessBuilder("-Xverify:all",
                                                              "CondyBadLDC2_W");
        oa = new OutputAnalyzer(pb.start());
        oa.shouldContain("java.lang.VerifyError: Illegal type at constant pool entry");
        oa.shouldContain("CondyBadLDC2_W.F()F @0: ldc2_w");
        oa.shouldHaveExitValue(1);

        // 3. Test a ldc of a condy which returns a dynamically generated
        //    double constant, generates a VerifyError.
        pb = ProcessTools.createTestJavaProcessBuilder("-Xverify:all",
                                                              "CondyBadLDC");
        oa = new OutputAnalyzer(pb.start());
        oa.shouldContain("java.lang.VerifyError: Illegal type at constant pool entry");
        oa.shouldContain("CondyBadLDC.D()D @0: ldc");
        oa.shouldHaveExitValue(1);
    }
}
