/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8267239
 * @author Yi Yang
 * @summary apply RCE for % operations
 * @requires vm.compiler1.enabled
 * @library /test/lib
 * @run main/othervm -XX:TieredStopAtLevel=1 -XX:+TieredCompilation
 *                   -XX:CompileCommand=compileonly,*ArithmeticRemRCE.test*
 *                   compiler.c1.ArithmeticRemRCE
 */

package compiler.c1;

import jdk.test.lib.Asserts;

public class ArithmeticRemRCE {
    static int field = 1000;

    static void test1() {
        // seq should be loop invariant, so we can not put it into static fields
        int[] seq = new int[1000];
        for (int i = 0; i < seq.length; i++) {
            seq[i] = i;
        }

        for (int i = 0; i < 1024; i++) {
            int constVal = 10;
            Asserts.assertTrue(0 <= seq[i % 5] && seq[i % 5] <= 4);
            Asserts.assertTrue(0 <= seq[i % -5] && seq[i % -5] <= 4);

            Asserts.assertTrue(0 <= seq[i % constVal] && seq[i % constVal] <= 9);
            Asserts.assertTrue(0 <= seq[i % -constVal] && seq[i % -constVal] <= 9);

            Asserts.assertTrue(seq[i % 1] == 0);

            // will not apply RCE
            Asserts.assertTrue(0 <= seq[i % field] && seq[i % field] <= 999);
            Asserts.assertTrue(0 <= seq[i % -field] && seq[i % -field] <= 999);
        }
    }

    public static void main(String... args) throws Exception {
        for (int i = 0; i < 10_000; i++) {
            test1();
        }
    }
}
