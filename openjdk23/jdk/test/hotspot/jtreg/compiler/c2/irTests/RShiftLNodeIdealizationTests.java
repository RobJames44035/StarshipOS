/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package compiler.c2.irTests;

import jdk.test.lib.Asserts;
import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8320330
 * @summary Test that RShiftLNode optimizations are being performed as expected.
 * @library /test/lib /
 * @run driver compiler.c2.irTests.RShiftLNodeIdealizationTests
 */
public class RShiftLNodeIdealizationTests {
    public static void main(String[] args) {
        TestFramework.run();
    }

    @Run(test = { "test1", "test2", "test3", "test4" })
    public void runMethod() {
        long a = RunInfo.getRandom().nextLong();
        long b = RunInfo.getRandom().nextLong();
        long c = RunInfo.getRandom().nextLong();
        long d = RunInfo.getRandom().nextLong();

        long min = Long.MIN_VALUE;
        long max = Long.MAX_VALUE;

        assertResult(a, 0);
        assertResult(a, b);
        assertResult(b, a);
        assertResult(c, d);
        assertResult(d, c);
        assertResult(min, max);
        assertResult(max, min);
        assertResult(min, min);
        assertResult(max, max);
    }

    @DontCompile
    public void assertResult(long x, long y) {
        Asserts.assertEQ((x >> y) >= 0 ? 0L : 1L, test1(x, y));
        Asserts.assertEQ(((x & 127) >> y) >= 0 ? 0L : 1L, test2(x, y));
        Asserts.assertEQ(((-(x & 127) - 1) >> y) >= 0 ? 0L : 1L, test3(x, y));
        Asserts.assertEQ((x >> 62) > 4 ? 0L : 1L, test4(x, y));
    }

    @Test
    @IR(counts = { IRNode.RSHIFT, "1" })
    public long test1(long x, long y) {
        return (x >> y) >= 0 ? 0 : 1;
    }

    @Test
    @IR(failOn = { IRNode.RSHIFT })
    public long test2(long x, long y) {
        return ((x & 127) >> y) >= 0 ? 0L : 1L;
    }

    @Test
    @IR(failOn = { IRNode.RSHIFT })
    public long test3(long x, long y) {
        return ((-(x & 127) - 1) >> y) >= 0 ? 0L : 1L;
    }

    @Test
    @IR(failOn = { IRNode.RSHIFT })
    public long test4(long x, long y) {
        return (x >> 62) > 4 ? 0L : 1L;
    }
}
