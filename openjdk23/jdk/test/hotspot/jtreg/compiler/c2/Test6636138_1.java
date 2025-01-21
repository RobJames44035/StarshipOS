/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6636138
 * @summary SuperWord::co_locate_pack(Node_List* p) generates memory graph that leads to memory order violation.
 *
 * @run main/othervm -Xbatch
 *      -XX:CompileCommand=compileonly,compiler.c2.Test6636138_1::init
 *      compiler.c2.Test6636138_1
 */

package compiler.c2;

public class Test6636138_1 {

    public static void init(int src[], int [] dst, int[] ref) {
        // initialize the arrays
        for (int i =0; i<src.length; i++) {
            src[i] =  i;
            dst[i] = 2;      // yes, dst[i] needed(otherwise src[i] will be replaced with i)
            ref[i] = src[i]; // src[i] depends on the store src[i]
        }
    }

    public static void verify(int src[], int[] ref) {
        // check whether src and ref are equal
        for (int i = 0; i < src.length; i++) {
            if (src[i] != ref[i]) {
                System.out.println("Error: src and ref don't match at " + i);
                System.exit(97);
            }
        }
    }

    public static void test() {
        int[] src = new int[34];
        int[] dst = new int[34];
        int[] ref = new int[34];

        init(src, dst, ref);
        verify(src, ref);
    }

    public static void main(String[] args) {
        for (int i=0; i< 2000; i++) {
            test();
        }
    }
}
