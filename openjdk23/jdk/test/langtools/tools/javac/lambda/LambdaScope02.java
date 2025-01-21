/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that Object members are accessible as expected
 * @author  Maurizio Cimadamore
 * @run main LambdaScope02
 */

public class LambdaScope02 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    @Override
    public String toString() {
        return "Callable1";
    }

    interface Callable {
        void call();
    }

    static void call(Callable c) { c.call(); }

    void test() {
        call(()-> { assertTrue(LambdaScope02.this.toString().equals("Callable1")); });
        call(()-> { assertTrue(toString().equals("Callable1")); });
    }

    public static void main(String[] args) {
        new LambdaScope02().test();
        assertTrue(assertionCount == 2);
    }
}
