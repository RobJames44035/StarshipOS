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
 *                   compiler.intrinsics.bmi.TestTzcntI
 */

package compiler.intrinsics.bmi;

import jdk.test.whitebox.cpuinfo.CPUInfo;

public class TestTzcntI {

    public static void main(String args[]) throws Throwable {
        if (!CPUInfo.hasFeature("bmi1")) {
            System.out.println("INFO: CPU does not support bmi1 feature.");
        }

        BMITestRunner.runTests(TzcntIExpr.class, args,
                               "-XX:+IgnoreUnrecognizedVMOptions",
                               "-XX:+UseCountTrailingZerosInstruction");
    }

    public static class TzcntIExpr extends Expr.BitCountingIntExpr {

        public int intExpr(int src) {
            return Integer.numberOfTrailingZeros(src);
        }

    }

}
