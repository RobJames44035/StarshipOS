/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 7042153
 * @summary Bad folding of IfOps with unloaded constant arguments in C1
 *
 * @run main/othervm -Xcomp compiler.c1.Test7042153
 */

package compiler.c1;

public class Test7042153 {
    static public class Bar { }
    static public class Foo { }

    static volatile boolean z;
    public static void main(String [] args) {
        Class cx = Bar.class;
        Class cy = Foo.class;
        z = (cx == cy);
    }
}
