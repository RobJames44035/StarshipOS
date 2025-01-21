/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8285394
 * @requires vm.compiler2.enabled
 * @summary Blackholes should work when hot inlined
 * @library /test/lib /
 * @run driver compiler.c2.irTests.blackhole.BlackholeHotInlineTest
 */

package compiler.c2.irTests.blackhole;

import compiler.lib.ir_framework.*;
import jdk.test.lib.Asserts;

public class BlackholeHotInlineTest {

    public static void main(String[] args) {
        TestFramework.runWithFlags(
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:CompileThreshold=100",
            "-XX:-TieredCompilation",
            "-XX:CompileCommand=blackhole,compiler.c2.irTests.blackhole.BlackholeHotInlineTest::blackhole",
            "-XX:CompileCommand=dontinline,compiler.c2.irTests.blackhole.BlackholeHotInlineTest::dontinline"
        );
    }

    static long x, y;

    /*
     * Negative test: check that dangling expression is eliminated
     */

    @Test
    @IR(failOn = IRNode.MUL_L)
    static void testNothing() {
        long r = x * y;
    }

    @Run(test = "testNothing")
    static void runNothing() {
        testNothing();
    }

    /*
     * Auxiliary test: check that dontinline method does not allow the elimination.
     */

    @Test
    @IR(counts = {IRNode.MUL_L, "1"})
    static void testDontline() {
        long r = x * y;
        dontinline(r);
    }

    static void dontinline(long x) {}

    @Run(test = "testDontline")
    static void runDontinline() {
        testDontline();
    }

    /*
     * Positive test: check that blackhole method does not allow the elimination either.
     */

    @Test
    @IR(counts = {IRNode.MUL_L, "1"})
    static void testBlackholed() {
        long r = x * y;
        blackhole(r);
    }

    static void blackhole(long x) {}

    @Run(test = "testBlackholed")
    static void runBlackholed() {
        testBlackholed();
    }

}
