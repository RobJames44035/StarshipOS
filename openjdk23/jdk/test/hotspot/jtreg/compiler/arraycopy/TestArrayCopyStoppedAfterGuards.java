/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8075921
 * @summary control becomes top after arraycopy guards and confuses tighly coupled allocation logic
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,java.lang.System::arraycopy
 *      -XX:CompileCommand=compileonly,compiler.arraycopy.TestArrayCopyStoppedAfterGuards::test
 *      compiler.arraycopy.TestArrayCopyStoppedAfterGuards
 *
 */

package compiler.arraycopy;

public class TestArrayCopyStoppedAfterGuards {

    static void test() {
        Object src = new Object();
        int[] dst = new int[10];
        System.arraycopy(src, 0, dst, 0, 10);
    }

    static public void main(String[] args) {
        // warmup
        Object o = new Object();
        int[] src = new int[10];
        int[] dst = new int[10];
        System.arraycopy(src, 0, dst, 0, 10);

        try {
            test();
        } catch(ArrayStoreException ase) {}
    }
}
