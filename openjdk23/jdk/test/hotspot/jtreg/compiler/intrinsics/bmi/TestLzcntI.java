/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @key randomness
 * @bug 8031321
 * @summary Verify that results of computations are the same w/
 *          and w/o usage of intrinsic
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.intrinsics.bmi.TestLzcntI
 */

package compiler.intrinsics.bmi;

import jdk.test.whitebox.cpuinfo.CPUInfo;

public class TestLzcntI {

    public static void main(String args[]) throws Throwable {
        if (!CPUInfo.hasFeature("lzcnt")) {
            System.out.println("INFO: CPU does not support lzcnt feature.");
        }

        BMITestRunner.runTests(LzcntIExpr.class, args,
                               "-XX:+IgnoreUnrecognizedVMOptions",
                               "-XX:+UseCountLeadingZerosInstruction");
    }

    public static class LzcntIExpr extends Expr.BitCountingIntExpr {

        public int intExpr(int src) {
            return Integer.numberOfLeadingZeros(src);
        }

    }

}
