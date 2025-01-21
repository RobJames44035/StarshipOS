/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6837011
 * @summary SIGSEGV in PhaseIdealLoop in 32bit jvm
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c2.Test6837011::main
 *      compiler.c2.Test6837011
 */

package compiler.c2;

public class Test6837011 {
    static boolean var_3 = true;

    public static void main(String[] args) {
        double var_5;
        char var_7 = 1;
        double var_11 = 0;

        do {
            var_11++;
            var_5 = (var_7 /= ( var_3 ? ~1L : 3 ) );
        } while (var_11 < 1);
    }
}
