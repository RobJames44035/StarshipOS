/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8279888
 * @summary Local variable independently used by multiple loops can interfere with loop optimizations
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @run driver compiler.c2.irTests.TestDuplicateBackedge
 */

public class TestDuplicateBackedge {
    public static void main(String[] args) {
        TestFramework.runWithFlags("-XX:LoopMaxUnroll=1");
        TestFramework.runWithFlags("-XX:LoopMaxUnroll=1", "-XX:-DuplicateBackedge");
    }

    @Test
    @IR(applyIf = { "DuplicateBackedge", "true" }, counts = {IRNode.LOOP, "1", IRNode.COUNTED_LOOP, "1" })
    @IR(applyIf = { "DuplicateBackedge", "false" }, counts = { IRNode.LOOP, "1" })
    @IR(applyIf = { "DuplicateBackedge", "false" }, failOn = { IRNode.COUNTED_LOOP})
    public static float test() {
        float res = 1;
        for (int i = 1;;) {
            if (i % 10 == 0) {
                i = (i * 2) + 1;
                res /= 42;
            } else {
                i++;
                res *= 42;
            }
            if (i >= 1000) {
                break;
            }
        }
        return res;
    }

}
