/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 8272131
 * @requires vm.compiler2.enabled
 * @summary ArrayCopy with negative index before infinite loop
 * @run main/othervm -Xbatch -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.arraycopy.TestIllegalArrayCopyBeforeInfiniteLoop::foo
 *                   compiler.arraycopy.TestIllegalArrayCopyBeforeInfiniteLoop
 */

package compiler.arraycopy;

import java.util.Arrays;

public class TestIllegalArrayCopyBeforeInfiniteLoop {
    private static char src[] = new char[10];
    private static int count = 0;
    private static final int iter = 20_000;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < iter; ++i) {
            foo();
        }
        if (count != iter) {
            throw new RuntimeException("test failed");
        }
    }

    static void foo() {
        try {
            Arrays.copyOfRange(src, -1, 128);
            do {
            } while (true);
        } catch (ArrayIndexOutOfBoundsException ex) {
            count++;
        }
    }
}
