/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
* @test
* @summary Test intrinsics for Float.isInfinite.
* @requires vm.cpu.features ~= ".*avx512dq.*" | os.arch == "riscv64"
* @library /test/lib /
* @run driver compiler.intrinsics.TestFloatIsInfinite
*/

package compiler.intrinsics;
import compiler.lib.ir_framework.*;

public class TestFloatIsInfinite extends TestFloatClassCheck {
    public static void main(String args[]) {
        TestFramework.run(TestFloatIsInfinite.class);
    }

    @Test // needs to be run in (fast) debug mode
    @Warmup(10000)
    @IR(counts = {IRNode.IS_INFINITE_F, ">= 1"}) // At least one IsInfiniteF node is generated if intrinsic is used
    public void testIsInfinite() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            outputs[i] = Float.isInfinite(inputs[i]);
        }
        checkResult("isInfinite");
    }
}
