/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6866651
 * @summary delay dead node elimination in set_req_X to prevent killing the current node when it is in use
 *
 * @run main compiler.c2.Test6866651
 */

package compiler.c2;

public class Test6866651 {

    static int sum() {
        int s = 0;
        for (int x = 1, y = 0; x != 0; x++, y--) {
            s ^= y;
        }
        return s;
    }

    public static void main(final String[] args) {
        for (int k = 0; k < 2; k++) {
            System.err.println(String.valueOf(sum()));
        }
    }
}
