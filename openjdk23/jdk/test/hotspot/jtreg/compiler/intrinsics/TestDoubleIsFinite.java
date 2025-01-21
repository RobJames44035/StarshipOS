/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
* @test
* @summary Test intrinsic for Double.isFinite.
* @requires os.arch == "riscv64"
* @library /test/lib /
* @run driver compiler.intrinsics.TestDoubleIsFinite
*/

package compiler.intrinsics;
import compiler.lib.ir_framework.*;

public class TestDoubleIsFinite extends TestDoubleClassCheck {
    public static void main(String args[]) {
        TestFramework.run(TestDoubleIsFinite.class);
    }

    @Test // needs to be run in (fast) debug mode
    @Warmup(10000)
    @IR(counts = {IRNode.IS_FINITE_D, ">= 1"}) // At least one IsFiniteD node is generated if intrinsic is used
    public void testIsFinite() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            outputs[i] = Double.isFinite(inputs[i]);
        }
        checkResult("isFinite");
    }
}
