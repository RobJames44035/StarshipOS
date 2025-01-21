/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import jdk.internal.math.MathUtilsChecker;

/*
 * @test
 *
 * @modules java.base/jdk.internal.math
 * @library java.base
 * @build java.base/jdk.internal.math.*
 * @run main MathUtilsTest
 */
public class MathUtilsTest {

    public static void main(String[] args) {
        MathUtilsChecker.test();
    }

}
