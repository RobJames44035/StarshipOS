/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  SAM types and method type inference
 * @author  Brian Goetz
 * @author  Maurizio Cimadamore
 * @run main LambdaConv03
 */

public class LambdaConv03 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface TU<T, U> {
      public T foo(U u);
    }

    public static <T, U> T exec(TU<T, U> lambda, U x) {
        return lambda.foo(x);
    }

    static {
        //Covariant returns:
        int i1 = exec((Integer x) -> { return x; }, 3);
        assertTrue(3 == i1);
        //Method resolution with boxing:
        int i2 = exec((Integer x) -> { return x; }, 3);
        assertTrue(3 == i2);
        //Runtime exception transparency:
        try {
            exec((Object x) -> { return x.hashCode(); }, null);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    {
        //Covariant returns:
        int i1 = exec((Integer x) -> { return x; }, 3);
        assertTrue(3 == i1);
        //Method resolution with boxing:
        int i2 = exec((Integer x) -> { return x; }, 3);
        assertTrue(3 == i2);
        //Runtime exception transparency:
        try {
            exec((Object x) -> { return x.hashCode(); }, null);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    public static void test1() {
        //Covariant returns:
        int i1 = exec((Integer x) -> { return x; }, 3);
        assertTrue(3 == i1);
        //Method resolution with boxing:
        int i2 = exec((Integer x) -> { return x; }, 3);
        assertTrue(3 == i2);
        //Runtime exception transparency:
        try {
            exec((Object x) -> { return x.hashCode(); }, null);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    public void test2() {
        //Covariant returns:
        int i1 = exec((Integer x) -> { return x; }, 3);
        assertTrue(3 == i1);
        //Method resolution with boxing:
        int i2 = exec((Integer x) -> { return x; }, 3);
        assertTrue(3 == i2);
        //Runtime exception transparency:
        try {
            exec((Object x) -> { return x.hashCode(); }, null);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    public static void main(String[] args) {
        test1();
        new LambdaConv03().test2();
        assertTrue(assertionCount == 12);
    }
}
