/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test
 * @bug 6916644
 * @summary C2 compiler crash on x86
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c2.Test6916644::test
 *      compiler.c2.Test6916644
 */

package compiler.c2;

public class Test6916644 {
    static int result;
    static int i1;
    static int i2;

    static public void test(double d) {
        result = (d <= 0.0D) ? i1 : i2;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            // use an alternating value so the test doesn't always go
            // the same direction.  Otherwise we won't transform it
            // into a cmove.
            test(i & 1);
        }
    }
}
