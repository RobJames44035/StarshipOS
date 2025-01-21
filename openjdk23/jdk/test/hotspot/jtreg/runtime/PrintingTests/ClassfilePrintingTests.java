/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Platform;

/*
 * @test
 * @summary Test various printing functions in classfile directory
 * @bug 8211821 8323685
 * @requires vm.flagless
 * @library /test/lib
 * @run driver ClassfilePrintingTests
 */

class SampleClass {
    public static void main(java.lang.String[] unused) {
        System.out.println("Hello from the sample class");
    }
}

public class ClassfilePrintingTests {
    private static void printStringTableStatsTest() throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintStringTableStatistics",
            "--version");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("Number of buckets");
        output.shouldHaveExitValue(0);
    }

    private static void printSystemDictionaryAtExitTest() throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+PrintSystemDictionaryAtExit",
            "SampleClass");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain(SampleClass.class.getName());
        output.shouldContain("jdk/internal/loader/ClassLoaders$AppClassLoader");
        output.shouldHaveExitValue(0);
    }

    public static void main(String... args) throws Exception {
        printStringTableStatsTest();
        if (Platform.isDebugBuild()) {
            printSystemDictionaryAtExitTest();
        }
    }
}
