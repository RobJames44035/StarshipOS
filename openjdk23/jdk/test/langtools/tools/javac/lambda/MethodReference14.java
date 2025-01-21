/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check casting a method reference to a SAM type does not result in a CCE
 * @author  Maurizio Cimadamore
 * @run main MethodReference14
 */

public class MethodReference14 {

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    static int assertionCount = 0;

    interface SAM {
        void m();
    }

    static void m() { assertTrue(true); }

    public static void main(String[] args) {
        SAM s = (SAM)MethodReference14::m;
        s.m();
        assertTrue(assertionCount == 1);
    }
}
