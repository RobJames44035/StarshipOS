/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 7046096
 * @summary SEGV IN C2 WITH 6U25
 *
 * @run main/othervm -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:+OptimizeStringConcat
 *    compiler.c2.Test7046096
 */

package compiler.c2;

public class Test7046096 {

    static int first = 1;

    String add(String str) {
        if (first != 0) {
            return str + "789";
        } else {
            return "null";
        }
    }

    String test(String str) {
        for (int i = 0; i < first; i++) {
            if (i > 1)
                return "bad";
        }
        return add(str + "456");
    }

    public static void main(String[] args) {
        Test7046096 t = new Test7046096();
        for (int i = 0; i < 11000; i++) {
            String str = t.test("123");
            if (!str.equals("123456789")) {
                System.out.println("FAILED: " + str + " != \"123456789\"");
                System.exit(97);
            }
        }
    }
}

