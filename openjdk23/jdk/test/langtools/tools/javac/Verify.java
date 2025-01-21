/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4381996
 * @summary Java Bytecode Verification impossible [2]
 * @author gafter
 *
 * @compile Verify.java
 * @run main Verify
 */

public class Verify {
    public static void main(String[] args) {
        test(args.length > 200);
    }

    static int test(boolean b) {
        int i;
        try {
            if (b) return 1;
            i = 2;
        } finally {
            if (b) i = 3;
        }
        return i;
    }
}
