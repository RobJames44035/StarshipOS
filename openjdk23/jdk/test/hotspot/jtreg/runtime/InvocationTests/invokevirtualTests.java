/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test id=current-int
 * @bug 8224137
 * @summary Run invokevirtual invocation tests
 * @requires vm.flagless
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 * @compile invokevirtual/Checker.java invokevirtual/ClassGenerator.java invokevirtual/Generator.java
 *
 * @run driver/timeout=1800 invokevirtualTests current-int
 */

/*
 * @test id=current-comp
 * @bug 8224137
 * @summary Run invokevirtual invocation tests
 * @requires vm.flagless
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 * @compile invokevirtual/Checker.java invokevirtual/ClassGenerator.java invokevirtual/Generator.java
 *
 * @run driver/timeout=1800 invokevirtualTests current-comp
 */

/*
 * @test id=old-int
 * @bug 8224137
 * @summary Run invokevirtual invocation tests
 * @requires vm.flagless
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 * @compile invokevirtual/Checker.java invokevirtual/ClassGenerator.java invokevirtual/Generator.java
 *
 * @run driver/timeout=1800 invokevirtualTests old-int
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.compiler.InMemoryJavaCompiler;

public class invokevirtualTests {

    public static void runTest(String classFileVersion, String option) throws Throwable {
        System.out.println("\ninvokevirtual invocation tests, option: " + option +
                           ", class file version: " + classFileVersion);
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xmx128M", option,
            "invokevirtual.Generator", "--classfile_version=" + classFileVersion);
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        try {
            output.shouldContain("EXECUTION STATUS: PASSED");
            output.shouldHaveExitValue(0);
        } catch (Throwable e) {
            System.out.println(
                "\nNote that an entry such as 'B.m/C.m' in the failure chart means that" +
                " the test case failed because method B.m was invoked but the test " +
                "expected method C.m to be invoked. Similarly, a result such as 'AME/C.m'" +
                " means that an AbstractMethodError exception was thrown but the test" +
                " case expected method C.m to be invoked.");
            System.out.println(
                "\nAlso note that passing --dump to invokevirtual.Generator will" +
                " dump the generated classes (for debugging purposes).\n");

            throw e;
        }
    }

    public static void main(String args[]) throws Throwable {
        if (args.length < 1) {
            throw new IllegalArgumentException("Should provide the test name");
        }
        String testName = args[0];

        // Get current major class file version and test with it.
        byte klassbuf[] = InMemoryJavaCompiler.compile("blah", "public class blah { }");
        int major_version = klassbuf[6] << 8 | klassbuf[7];

        switch (testName) {
            case "current-int":
                runTest(String.valueOf(major_version), "-Xint");
                break;
            case "current-comp":
                runTest(String.valueOf(major_version), "-Xcomp");
                break;
            case "old-int":
                // Test old class file version.
                runTest("51", "-Xint"); // JDK-7
                break;
            default:
                throw new IllegalArgumentException("Unknown test name: " + testName);
        }
    }
}
