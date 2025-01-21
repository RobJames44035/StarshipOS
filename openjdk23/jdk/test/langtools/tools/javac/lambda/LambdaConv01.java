/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  basic test for lambda conversion
 * @author  Brian Goetz
 * @author  Maurizio Cimadamore
 * @run main LambdaConv01
 */

public class LambdaConv01 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface IntToInt {
      public int foo(int x);
    }

    interface IntToVoid {
      public void foo(int x);
    }

    interface VoidToInt {
      public int foo();
    }

    interface TU<T, U> {
      public T foo(U u);
    }

    public static <T, U> T exec(TU<T, U> lambda, U x) {
        return lambda.foo(x);
    }

    static {
        //Assignment conversion:
        VoidToInt f1 = ()-> 3;
        assertTrue(3 == f1.foo());
        //Covariant returns:
        TU<Number, Integer> f2 = (Integer x) -> x;
        assertTrue(3 == f2.foo(3).intValue());
        //Method resolution with boxing:
        int res = LambdaConv01.<Integer,Integer>exec((Integer x) -> x, 3);
        assertTrue(3 == res);
        //Runtime exception transparency:
        try {
            LambdaConv01.<Integer,Object>exec((Object x) -> x.hashCode(), null);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    {
        //Assignment conversion:
        VoidToInt f1 = ()-> 3;
        assertTrue(3 == f1.foo());
        //Covariant returns:
        TU<Number, Integer> f2 = (Integer x) -> x;
        assertTrue(3 == f2.foo(3).intValue());
        //Method resolution with boxing:
        int res = LambdaConv01.<Integer,Integer>exec((Integer x) -> x, 3);
        assertTrue(3 == res);
        //Runtime exception transparency:
        try {
            LambdaConv01.<Integer,Object>exec((Object x) -> x.hashCode(), null);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    public static void test1() {
        //Assignment conversion:
        VoidToInt f1 = ()-> 3;
        assertTrue(3 == f1.foo());
        //Covariant returns:
        TU<Number, Integer> f2 = (Integer x) -> x;
        assertTrue(3 == f2.foo(3).intValue());
        //Method resolution with boxing:
        int res = LambdaConv01.<Integer,Integer>exec((Integer x) -> x, 3);
        assertTrue(3 == res);
        //Runtime exception transparency:
        try {
            LambdaConv01.<Integer,Object>exec((Object x) -> x.hashCode(), null);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    public void test2() {
        //Assignment conversion:
        VoidToInt f1 = ()-> 3;
        assertTrue(3 == f1.foo());
        //Covariant returns:
        TU<Number, Integer> f2 = (Integer x) -> x;
        assertTrue(3 == f2.foo(3).intValue());
        //Method resolution with boxing:
        int res = LambdaConv01.<Integer,Integer>exec((Integer x) -> x, 3);
        assertTrue(3 == res);
        //Runtime exception transparency:
        try {
            LambdaConv01.<Integer,Object>exec((Object x) -> x.hashCode(), null);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    public static void main(String[] args) {
        test1();
        new LambdaConv01().test2();
        assertTrue(assertionCount == 16);
    }
}
