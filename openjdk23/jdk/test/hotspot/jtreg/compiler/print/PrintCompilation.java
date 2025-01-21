/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Checks that -XX:PrintCompilation works
 * @library /test/lib
 * @run driver compiler.print.PrintCompilation
 */

package compiler.print;

import java.util.ArrayList;
import java.util.List;

import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class PrintCompilation {

    public static void main(String[] args) throws Exception {
        List<String> options = new ArrayList<String>();
        options.add("-XX:+PrintCompilation");
        options.add("-Xcomp");
        options.add("-XX:-Inline");
        options.add("-XX:CompileCommand=compileonly," + getTestClass() + "::*");
        options.add(getTestClass());

        OutputAnalyzer oa = ProcessTools.executeTestJava(options);

        oa.shouldHaveExitValue(0)
        .shouldContain(getTestMethod("method1"))
        .shouldContain(getTestMethod("method2"))
        .shouldContain(getTestMethod("method3"))
        .shouldNotContain(getTestMethod("notcalled"));
    }

    // Test class that is invoked by the sub process
    public static String getTestClass() {
        return TestMain.class.getName();
    }

    public static String getTestMethod(String method) {
        return getTestClass() + "::" + method;
    }

    public static class TestMain {
        public static void main(String[] args) {
            method1();
            method2();
            method3();
        }

        static void method1() { System.out.println("method1()"); }
        static void method2() {}
        static void method3() {}
        static void notcalled() {}
    }
}

