/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * bug 8290529
 * @summary verify that x <u 1 is transformed to x == 0
 * @requires os.arch=="amd64" | os.arch=="x86_64" | os.arch=="riscv64"
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @run driver compiler.c2.irTests.CmpUWithZero
 */

public class CmpUWithZero {
    static volatile boolean field;

    public static void main(String[] args) {
        TestFramework.run();
    }

    @Test
    @IR(counts = { IRNode.CMP_I, "1" })
    @IR(failOn = { IRNode.CMP_U})
    public static void test(int x) {
        if (Integer.compareUnsigned(x, 1) < 0) {
            field = true;
        } else {
            field = false;
        }
    }

    @Run(test = "test")
    private void testRunner() {
        test(0);
        test(42);
    }

}
