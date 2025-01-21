/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import jdk.internal.math.DoubleToDecimalChecker;
import jdk.test.lib.RandomFactory;

/*
 * @test
 * @bug 4511638
 * @key randomness
 *
 * @modules java.base/jdk.internal.math
 * @library /test/lib
 * @library java.base
 * @build jdk.test.lib.RandomFactory
 * @build java.base/jdk.internal.math.*
 * @run main DoubleToDecimalTest 100_000
 */
public class DoubleToDecimalTest {

    private static final int RANDOM_COUNT = 100_000;

    public static void main(String[] args) {
        if (args.length == 0) {
            DoubleToDecimalChecker.test(RANDOM_COUNT, RandomFactory.getRandom());
        } else {
            try {
                int count = Integer.parseInt(args[0].replace("_", ""));
                DoubleToDecimalChecker.test(count, RandomFactory.getRandom());
            } catch (NumberFormatException ignored) {
                DoubleToDecimalChecker.test(RANDOM_COUNT, RandomFactory.getRandom());
            }
        }
    }

}
