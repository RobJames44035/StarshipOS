/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/**
 * @test
 * @bug 6758234
 * @summary if (k cond (a ? : b: c)) returns reversed answer if k is constant and b and c are longs
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c1.Test6758234::main
 *      compiler.c1.Test6758234
 */

package compiler.c1;

public class Test6758234 {
    static int x = 0;
    static int y = 1;

    public static void main(String[] args) {
        if (1 != ((x < y) ? 1L : 0)) {
            throw new InternalError();
        }
   }
}
