/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8048121
 * @summary javac complex method references: revamp and simplify
 *
 * Make sure that the method reference receiver is evaluated exactly once
 * even in this bridging case.
 */

 public class MethodRefSingleRefEvalBridge {

    interface SAM {
       int m();
    }

    class ZZ {
        // private to force bridging
        private int four() { return 4; }
    }

    static int count = 0;
    ZZ azz = new ZZ();

    static void assertEqual(int expected, int got) {
        if (got != expected)
            throw new AssertionError("Expected " + expected + " got " + got);
    }

    public static void main(String[] args) {
       new MethodRefSingleRefEvalBridge().test();
    }

    ZZ check() {
        count++;
        return azz;
    }

    void test() {
       count = 0;
       SAM s = check()::four;
       assertEqual(1, count);

       count = 0;
       assertEqual(4, s.m());
       assertEqual(0, count);
    }
}
