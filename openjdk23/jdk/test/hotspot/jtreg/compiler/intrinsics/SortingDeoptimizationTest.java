/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8318306
 * @run main/othervm/timeout=200 -XX:+IgnoreUnrecognizedVMOptions -Xcomp -ea -esa -XX:CompileThreshold=100 -XX:+UnlockExperimentalVMOptions -server -XX:-TieredCompilation -XX:+DeoptimizeALot SortingDeoptimizationTest 1e-2 100 50
 * @summary Exercise Arrays.parallelSort when -XX:+DeoptimizeALot is enabled
 *
 */

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

public class SortingDeoptimizationTest {

    private static final PrintStream err = System.err;
    private static final PrintStream out = System.out;

    public static void main(String[] args) {
        int MAX = 2147483647; // 2^32 - 1
        float fraction = Float.parseFloat(args[0]);
        int size = (int) (fraction * MAX); // size is a fraction of the MAX size
        int iters = Integer.parseInt(args[1]); // number of iterations
        int max = args.length > 2 ? Integer.parseInt(args[2]) : -1 ; // max value for the array elements
        long seed = 0xC0FFEE;
        Random rand = new Random(seed);

        for (int i = 0; i < iters; i++) {
            boolean isSorted = runSort(size, max, rand);
            out.println("Iteration " + i + ": is sorted? -> "+ isSorted);
            if (!isSorted) fail("Array is not correctly sorted.");
        }
    }

    private static void fail(String message) {
        err.format("\n*** TEST FAILED ***\n\n%s\n\n", message);
        throw new RuntimeException("Test failed");
    }

    private static boolean runSort(int size, int max, Random rand) {
        int[] a = new int[size];
        for (int i = 0; i < a.length; i++) a[i] =  max > 0 ? rand.nextInt(max) : rand.nextInt();
        // call parallel sort
        Arrays.parallelSort(a);
        // check if sorted
        boolean isSorted = true;
        for (int i = 0; i < (a.length -1); i++) isSorted = isSorted && (a[i] <= a[i+1]);
        return isSorted;
    }
}
