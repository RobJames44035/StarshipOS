/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6196102
 * @summary Integer seems to be greater than Integer.MAX_VALUE
 *
 * @run main compiler.c2.Test6196102
 */

package compiler.c2;

public class Test6196102 {
    static public void main(String[] args) {
        int i1 = 0;
        int i2 = Integer.MAX_VALUE;

        while (i1 >= 0) {
            i1++;
            if (i1 > i2) {
                System.out.println("E R R O R: " + i1);
                System.exit(97);
            }
        }
    }
}

