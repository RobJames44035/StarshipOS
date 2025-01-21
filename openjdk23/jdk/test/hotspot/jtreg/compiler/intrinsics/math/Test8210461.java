/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8210461
 * @summary Math cos instrinsic returns incorrect result for large value
 *
 * @run main/othervm compiler.intrinsics.math.Test8210461
 */

package compiler.intrinsics.math;

import java.util.Arrays;

public class Test8210461 {
    private static final double[] testCases = new double[] {
        1647100.0d,
        16471000.0d,
        164710000.0d
    };

    public static void main(String[] args) {
        Arrays.stream(testCases).forEach(Test8210461::test);
    }

    private static void test(double arg) {
        double strictResult = StrictMath.cos(arg);
        double mathResult = Math.cos(arg);
        if (Math.abs(strictResult - mathResult) > Math.ulp(strictResult))
            throw new AssertionError(mathResult + " while expecting " + strictResult);
    }
}
