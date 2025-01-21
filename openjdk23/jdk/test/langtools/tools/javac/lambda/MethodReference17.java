/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  basic test for constructor references
 * @author  Maurizio Cimadamore
 * @run main MethodReference17
 */

public class MethodReference17 {

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    static int assertionCount = 0;

    MethodReference17() {
        assertTrue(true);
    }

    interface SAM {
        MethodReference17 m();
    }

    static void test(SAM s) {
        s.m();
    }

    public static void main(String[] args) {
        SAM s = MethodReference17::new;
        s.m();
        test(MethodReference17::new);
        assertTrue(assertionCount == 2);
    }
}
