/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
* @test
* @summary Test intrinsics for Float.isFinite.
* @requires os.arch == "riscv64"
* @library /test/lib /
* @run driver compiler.intrinsics.TestFloatIsFinite
*/

package compiler.intrinsics;
import compiler.lib.ir_framework.*;

public class TestFloatIsFinite extends TestFloatClassCheck {
    public static void main(String args[]) {
        TestFramework.run(TestFloatIsFinite.class);
    }

    @Test // needs to be run in (fast) debug mode
    @Warmup(10000)
    @IR(counts = {IRNode.IS_FINITE_F, ">= 1"}) // At least one IsFiniteF node is generated if intrinsic is used
    public void testIsFinite() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            outputs[i] = Float.isFinite(inputs[i]);
        }
        checkResult("isFinite");
    }
}
