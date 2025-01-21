/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @key randomness
 * @bug 8031321
 * @summary Verify that results of computations are the same w/
 *          and w/o usage of BLSI instruction
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.intrinsics.bmi.TestBlsiL
 */

package compiler.intrinsics.bmi;

import jdk.test.whitebox.cpuinfo.CPUInfo;

public class TestBlsiL {

    public static void main(String args[]) throws Throwable {
        if (!CPUInfo.hasFeature("bmi1")) {
            System.out.println("INFO: CPU does not support bmi1 feature.");
        }

        BMITestRunner.runTests(BlsiLExpr.class, args,
                               "-XX:+IgnoreUnrecognizedVMOptions",
                               "-XX:+UseBMI1Instructions");
        BMITestRunner.runTests(BlsiLCommutativeExpr.class, args,
                               "-XX:+IgnoreUnrecognizedVMOptions",
                               "-XX:+UseBMI1Instructions");
    }

    public static class BlsiLExpr extends Expr.BMIUnaryLongExpr {

        public long longExpr(long src) {
            return -src & src;
        }

        public long longExpr(Expr.MemL src) {
            return -src.value & src.value;
        }

    }

    public static class BlsiLCommutativeExpr extends Expr.BMIUnaryLongExpr {

        public long longExpr(long src) {
            return src & -src;
        }

        public long longExpr(Expr.MemL src) {
            return src.value & -src.value;
        }

    }

}
