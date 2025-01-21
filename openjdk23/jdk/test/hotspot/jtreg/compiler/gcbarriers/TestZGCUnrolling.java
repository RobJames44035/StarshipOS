/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.gcbarriers;

import compiler.lib.ir_framework.*;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles;

/**
 * @test
 * @summary Test that the expanded size of ZGC barriers is taken into account in
 *          C2's loop unrolling heuristics so that over-unrolling is avoided.
 *          The tests use volatile memory accesses to prevent C2 from simply
 *          optimizing them away.
 * @library /test/lib /
 * @requires vm.gc.Z
 * @run driver compiler.gcbarriers.TestZGCUnrolling
 */

public class TestZGCUnrolling {

    static class Outer {
        Object f;
    }

    static final VarHandle fVarHandle;
    static {
        MethodHandles.Lookup l = MethodHandles.lookup();
        try {
            fVarHandle = l.findVarHandle(Outer.class, "f", Object.class);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        TestFramework.runWithFlags("-XX:+UseZGC", "-XX:LoopUnrollLimit=24");
    }

    @Test
    @IR(counts = {IRNode.STORE_P, "1"})
    public static void testNoUnrolling(Outer o, Object o1) {
        for (int i = 0; i < 64; i++) {
            fVarHandle.setVolatile(o, o1);
        }
    }

    @Run(test = {"testNoUnrolling"})
    void run() {
        testNoUnrolling(new Outer(), new Object());
    }
}
