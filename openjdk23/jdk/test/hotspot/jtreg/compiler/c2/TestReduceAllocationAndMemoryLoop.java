/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8322854
 * @summary Check that the RAM optimization works when there is a memory loop.
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @run main/othervm -XX:CompileCommand=compileonly,*TestReduceAllocationAndMemoryLoop*::test*
 *                   -XX:-TieredCompilation -Xbatch
 *                   compiler.c2.TestReduceAllocationAndMemoryLoop
 */

package compiler.c2;

public class TestReduceAllocationAndMemoryLoop {
    public static void main(String[] args) throws Exception {
        // Warmup
        for (int i = 0; i < 50_000; ++i) {
            test(false, 10);
        }

        // Trigger deoptimization
        MyClass obj = test(false, 11);
        if (obj.val != 42) {
            throw new RuntimeException("Test failed, val = " + obj.val);
        }
    }

    static class MyClass {
        final int val;

        public MyClass(int val) {
            this.val = val;
        }
    }

    public static MyClass test(boolean alwaysFalse, int limit) {
        for (int i = 0; ; ++i) {
            MyClass obj = new MyClass(42);
            if (alwaysFalse || i > 10) {
                return obj;
            }
            if (i == limit) {
              return null;
            }
        }
    }
}
