/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4022932
 * @summary NegativeArraySizeException should not be optimized away.
 * @author turnidge
 *
 * @compile IllegallyOptimizedException.java
 * @run main IllegallyOptimizedException
 */


public class IllegallyOptimizedException {
    static int i = 0;
    public static void main (String argv[]) {
        try{
            int m[] = new int[-2];
        }
        catch(NegativeArraySizeException n) { i = 1;}
        if (i != 1) {
            throw new Error();
        }
    }
}
