/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test id=special
 * @bug 8226956
 * @summary Run invocation tests against C1 compiler
 * @requires vm.flagless
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 * @compile invokespecial/Checker.java invokespecial/ClassGenerator.java invokespecial/Generator.java
 *
 * @run driver/timeout=1800 invocationC1Tests special
 */

/*
 * @test id=virtual
 * @bug 8226956
 * @summary Run invocation tests against C1 compiler
 * @requires vm.flagless
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 * @compile invokevirtual/Checker.java invokevirtual/ClassGenerator.java invokevirtual/Generator.java
 *
 * @run driver/timeout=1800 invocationC1Tests virtual
 */

/*
 * @test id=interface
 * @bug 8226956
 * @summary Run invocation tests against C1 compiler
 * @requires vm.flagless
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 * @compile invokeinterface/Checker.java invokeinterface/ClassGenerator.java invokeinterface/Generator.java
 *
 * @run driver/timeout=1800 invocationC1Tests interface
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.compiler.InMemoryJavaCompiler;

public class invocationC1Tests {

    public static void runTest(String whichTests, String classFileVersion) throws Throwable {
        System.out.println("\nC1 invocation tests, Tests: " + whichTests +
                           ", class file version: " + classFileVersion);
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xmx128M",
            "-Xcomp", "-XX:TieredStopAtLevel=1",
            whichTests, "--classfile_version=" + classFileVersion);
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
                "\nAlso note that passing --dump to invoke*.Generator will" +
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
            case "special":
                runTest("invokespecial.Generator", String.valueOf(major_version));
                break;
            case "virtual":
                runTest("invokevirtual.Generator", String.valueOf(major_version));
                break;
            case "interface":
                runTest("invokeinterface.Generator", String.valueOf(major_version));
                break;
            default:
                throw new IllegalArgumentException("Unknown test name: " + testName);
        }
    }
}
