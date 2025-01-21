/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that most specific method is selected as expected
 * @author  Maurizio Cimadamore
 * @run main MethodReference03
 */

public class MethodReference03 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface SAM {
       void m(Integer i);
    }

    static void m(Number i) {}
    static void m(Integer d) { assertTrue(true); }

    public static void main(String[] args) {
        SAM s = MethodReference03::m;
        s.m(1);
        assertTrue(assertionCount == 1);
    }
}
