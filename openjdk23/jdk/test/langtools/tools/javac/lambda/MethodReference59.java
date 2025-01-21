/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8004102
 * @summary Add support for array constructor references
 */
public class MethodReference59 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface ArrayFactory<X> {
        X make(int size);
    }

    public static void main(String[] args) {
        ArrayFactory<int[]> factory1 = int[]::new;
        int[] i1 = factory1.make(5);
        assertTrue(i1.length == 5);
        ArrayFactory<int[][]> factory2 = int[][]::new;
        int[][] i2 = factory2.make(5);
        assertTrue(i2.length == 5);
        assertTrue(assertionCount == 2);
    }
}
