/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package compiler.c2.irTests;

import jdk.test.lib.Asserts;
import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8303238
 * @summary Test that Ideal transformations of LShiftLNode* are being performed as expected.
 * @library /test/lib /
 * @run driver compiler.c2.irTests.LShiftLNodeIdealizationTests
 */
public class LShiftLNodeIdealizationTests {
    public static void main(String[] args) {
        TestFramework.run();
    }

    @Run(test = { "test3", "test4", "test5", "test6", "test7", "test8" })
    public void runMethod() {
        long a = RunInfo.getRandom().nextLong();
        long b = RunInfo.getRandom().nextLong();
        long c = RunInfo.getRandom().nextLong();
        long d = RunInfo.getRandom().nextLong();

        long min = Long.MIN_VALUE;
        long max = Long.MAX_VALUE;

        assertResult(0);
        assertResult(a);
        assertResult(b);
        assertResult(c);
        assertResult(d);
        assertResult(min);
        assertResult(max);
    }

    @DontCompile
    public void assertResult(long a) {
        Asserts.assertEQ((a >> 4L) << 8L, test3(a));
        Asserts.assertEQ((a >>> 4L) << 8L, test4(a));
        Asserts.assertEQ((a >> 8L) << 4L, test5(a));
        Asserts.assertEQ((a >>> 8L) << 4L, test6(a));
        Asserts.assertEQ(((a >> 4L) & 0xFFL) << 8L, test7(a));
        Asserts.assertEQ(((a >>> 4L) & 0xFFL) << 8L, test8(a));
    }

    @Test
    @IR(failOn = { IRNode.RSHIFT })
    @IR(counts = { IRNode.AND, "1", IRNode.LSHIFT, "1" })
    // Checks (x >> 4) << 8 => (x << 4) & -16
    public long test3(long x) {
        return (x >> 4L) << 8L;
    }

    @Test
    @IR(failOn = { IRNode.URSHIFT })
    @IR(counts = { IRNode.AND, "1", IRNode.LSHIFT, "1" })
    // Checks (x >>> 4) << 8 => (x << 4) & -16
    public long test4(long x) {
        return (x >>> 4L) << 8L;
    }

    @Test
    @IR(failOn = { IRNode.LSHIFT })
    @IR(counts = { IRNode.AND, "1", IRNode.RSHIFT, "1" })
    // Checks (x >> 8) << 4 => (x >> 4) & -16
    public long test5(long x) {
        return (x >> 8L) << 4L;
    }

    @Test
    @IR(failOn = { IRNode.LSHIFT })
    @IR(counts = { IRNode.AND, "1", IRNode.URSHIFT, "1" })
    // Checks (x >>> 8) << 4 => (x >>> 4) & -16
    public long test6(long x) {
        return (x >>> 8L) << 4L;
    }

    @Test
    @IR(failOn = { IRNode.RSHIFT })
    @IR(counts = { IRNode.AND, "1", IRNode.LSHIFT, "1" })
    // Checks ((x >> 4) & 0xFF) << 8 => (x << 4) & 0xFF00
    public long test7(long x) {
        return ((x >> 4L) & 0xFFL) << 8L;
    }

    @Test
    @IR(failOn = { IRNode.URSHIFT })
    @IR(counts = { IRNode.AND, "1", IRNode.LSHIFT, "1" })
    // Checks ((x >>> 4) & 0xFF) << 8 => (x << 4) & 0xFF00
    public long test8(long x) {
        return ((x >>> 4L) & 0xFFL) << 8L;
    }
}
