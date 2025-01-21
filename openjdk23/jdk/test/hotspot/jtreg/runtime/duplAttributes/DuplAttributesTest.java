/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8040292
 * @library /test/lib
 * @summary Throw exceptions when duplicate attributes are detected.
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @compile DuplAttributes.jcod
 * @run driver DuplAttributesTest
 */

import java.io.File;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class DuplAttributesTest {

    static final String testsrc = System.getProperty("test.src");

    public static void runTest(String test, String result) throws Throwable {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(test);
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("java.lang.ClassFormatError: Multiple " + result);
        output.shouldNotHaveExitValue(0);
    }

    public static void main(String args[]) throws Throwable {
        System.out.println("Regression test for bug 8040292");

        runTest("ClassInvisAnnotsDup", "RuntimeInvisibleAnnotations");
        runTest("ClassVisAnnotsDup", "RuntimeVisibleAnnotations");
        runTest("SrcDbgExtDup", "SourceDebugExtension");

        runTest("FieldInvisAnnotsDup", "RuntimeInvisibleAnnotations");
        runTest("FieldVisAnnotsDup", "RuntimeVisibleAnnotations");

        runTest("AnnotationDefaultDup", "AnnotationDefault");
        runTest("MethInvisAnnotsDup", "RuntimeInvisibleAnnotations");
        runTest("MethVisAnnotsDup", "RuntimeVisibleAnnotations");
        runTest("MethInvisParamAnnotsDup", "RuntimeInvisibleParameterAnnotations");
        runTest("MethVisParamAnnotsDup", "RuntimeVisibleParameterAnnotations");
    }
}

