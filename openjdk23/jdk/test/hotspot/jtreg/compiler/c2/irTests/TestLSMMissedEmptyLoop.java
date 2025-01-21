/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8297724
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @run driver compiler.c2.irTests.TestLSMMissedEmptyLoop
 */

public class TestLSMMissedEmptyLoop {
    public static void main(String[] args) {
        TestFramework.runWithFlags("-XX:LoopMaxUnroll=0");
        TestFramework.runWithFlags("-XX:-UseCountedLoopSafepoints", "-XX:LoopMaxUnroll=0");
        TestFramework.run();
        TestFramework.runWithFlags("-XX:-UseCountedLoopSafepoints");
    }

    static double doubleField;

    @ForceInline
    public static void testHelper(int i, double d) {
        if (i != 42) {
            doubleField = d;
        }
    }

    @Test
    @IR(failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void test1() {
        double d = 1;
        for (int i = 0; i < 1000; i++) {
            d = d * 2;
        }
        int i = 0;
        for (i = 0; i < 42; i++) {
        }
        testHelper(i, d);
    }

    @Run(test = "test1")
    private void test1_runner() {
        testHelper(-42, 42);
        test1();
    }

    @Test
    @IR(applyIf = { "LoopStripMiningIter", "0" }, failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    @IR(applyIf = { "LoopStripMiningIter", "> 0" }, counts = {IRNode.COUNTED_LOOP, ">= 2" })
    public static void test2() {
        double d = 1;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 1000; i++) {
                d = d * 2;
            }
        }
        int i = 0;
        for (i = 0; i < 42; i++) {
        }
        testHelper(i, d);
    }

    @Run(test = "test2")
    private void test2_runner() {
        testHelper(-42, 42);
        test2();
    }

    @Test
    @IR(failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    public static void test3() {
        double d = 1;
        for (int i = 0; i < 1000; i++) {
            d = d * 2;
        }
        for (int i = 0; i < 1000; i++) {
            d = d * 2;
        }
        int i = 0;
        for (i = 0; i < 42; i++) {
        }
        testHelper(i, d);
    }

    @Run(test = "test3")
    private void test3_runner() {
        testHelper(-42, 42);
        test1();
    }

    @Test
    @IR(applyIf = { "LoopStripMiningIter", "0" }, failOn = {IRNode.COUNTED_LOOP, IRNode.LOOP })
    @IR(applyIf = { "LoopStripMiningIter", "> 0" }, counts = {IRNode.COUNTED_LOOP, ">= 3" })
    public static void test4() {
        double d = 1;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 1000; i++) {
                d = d * 2;
            }
            for (int i = 0; i < 1000; i++) {
                d = d * 2;
            }
        }
        int i = 0;
        for (i = 0; i < 42; i++) {
        }
        testHelper(i, d);
    }

    @Run(test = "test4")
    private void test4_runner() {
        testHelper(-42, 42);
        test4();
    }
}
