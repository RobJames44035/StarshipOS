/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  test recursion through SAM type
 * @author  Maurizio Cimadamore
 * @run main TargetType05
 */
public class TargetType05 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface Func<A,R> {
        R call(A a);
    }

    static Func<Integer, Integer> f;

    public static void main(String[] args) {
        f = i -> { return i == 1 ? 1 : f.call(i-1) * i; };
        assertTrue(f.call(5) == 120);
        assertTrue(assertionCount == 1);
    }
}
