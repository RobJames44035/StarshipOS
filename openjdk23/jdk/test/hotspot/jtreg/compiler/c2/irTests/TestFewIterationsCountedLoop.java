/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8262721
 * @summary Add Tests to verify single iteration loops are properly optimized
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @run driver compiler.c2.irTests.TestFewIterationsCountedLoop
 */

public class TestFewIterationsCountedLoop {

    public static void main(String[] args) {
        TestFramework.runWithFlags("-XX:LoopUnrollLimit=0");
        TestFramework.run();
    }

    static volatile int barrier;
    static final Object object = new Object();

    @Test
    @IR(failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void singleIterationFor() {
        for (int i = 0; i < 1; i++) {
            barrier = 0x42; // something that can't be optimized out
        }
    }

    @Test
    @IR(failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void singleIterationWhile() {
        int i = 0;
        while (i < 1) {
            barrier = 0x42;
            i++;
        }
    }

    @Test
    @IR(failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    @Warmup(1) // So C2 can't rely on profile data
    public static void singleIterationDoWhile() {
        int i = 0;
        do {
            synchronized(object) {} // so loop head is not cloned by ciTypeFlow
            barrier = 0x42;
            i++;
        } while (i < 1);
    }

    @Test
    @IR(applyIf = { "LoopUnrollLimit", "0" }, counts = {IRNode.COUNTED_LOOP, "1" })
    @IR(applyIf = { "LoopUnrollLimit", "> 0" }, failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void twoIterationsFor() {
        for (int i = 0; i < 2; i++) {
            barrier = 0x42; // something that can't be optimized out
        }
    }

    @Test
    @IR(applyIf = { "LoopUnrollLimit", "0" }, counts = {IRNode.COUNTED_LOOP, "1" })
    @IR(applyIf = { "LoopUnrollLimit", "> 0" }, failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void twoIterationsWhile() {
        int i = 0;
        while (i < 2) {
            barrier = 0x42;
            i++;
        }
    }

    @Test
    @IR(applyIf = { "LoopUnrollLimit", "0" }, counts = {IRNode.COUNTED_LOOP, "1" })
    @IR(applyIf = { "LoopUnrollLimit", "> 0" }, failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void twoIterationsDoWhile() {
        int i = 0;
        do {
            synchronized(object) {} // so loop head is not cloned by ciTypeFlow
            barrier = 0x42;
            i++;
        } while (i < 2);
    }

    @Test
    @IR(applyIf = { "LoopUnrollLimit", "0" }, counts = {IRNode.COUNTED_LOOP, "1" })
    @IR(applyIf = { "LoopUnrollLimit", "> 0" }, failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void threadIterationsFor() {
        for (int i = 0; i < 2; i++) {
            barrier = 0x42; // something that can't be optimized out
        }
    }

    @Test
    @IR(applyIf = { "LoopUnrollLimit", "0" }, counts = {IRNode.COUNTED_LOOP, "1" })
    @IR(applyIf = { "LoopUnrollLimit", "> 0" }, failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void threeIterationsWhile() {
        int i = 0;
        while (i < 2) {
            barrier = 0x42;
            i++;
        }
    }

    @Test
    @IR(applyIf = { "LoopUnrollLimit", "0" }, counts = {IRNode.COUNTED_LOOP, "1" })
    @IR(applyIf = { "LoopUnrollLimit", "> 0" }, failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void threeIterationsDoWhile() {
        int i = 0;
        do {
            synchronized(object) {} // so loop head is not cloned by ciTypeFlow
            barrier = 0x42;
            i++;
        } while (i < 2);
    }
}
