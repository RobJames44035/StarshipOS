/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  Compiler crash when local inner class nested inside lambda captures local variables from enclosing scope
 */
public class LambdaCapture06 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface SAM {
        void m(int n);
    }

    public static void main(String[] args) {
        int n = 5;
        SAM s = k -> {
            new Object() {
                void test() { int j = n; assertTrue(j == 5); }
            }.test();
        };
        s.m(42);
        assertTrue(assertionCount == 1);
    }
}


