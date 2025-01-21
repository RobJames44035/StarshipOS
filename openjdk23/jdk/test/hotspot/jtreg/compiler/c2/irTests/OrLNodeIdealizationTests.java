/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package compiler.c2.irTests;

import jdk.test.lib.Asserts;
import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8322077
 * @summary Test that Ideal transformations of OrLNode* are being performed as expected.
 * @library /test/lib /
 * @run driver compiler.c2.irTests.OrLNodeIdealizationTests
 */
public class OrLNodeIdealizationTests {

    public static void main(String[] args) {
        TestFramework.run();
    }

    @Run(test = { "test1" })
    public void runMethod() {
        long a = RunInfo.getRandom().nextLong();
        long b = RunInfo.getRandom().nextLong();

        long min = Long.MIN_VALUE;
        long max = Long.MAX_VALUE;

        assertResult(0, 0);
        assertResult(a, b);
        assertResult(min, min);
        assertResult(max, max);
    }

    @DontCompile
    public void assertResult(long a, long b) {
        Asserts.assertEQ((~a) | (~b), test1(a, b));
    }

    // Checks (~a) | (~b) => ~(a & b)
    @Test
    @IR(failOn = { IRNode.OR })
    @IR(counts = { IRNode.AND, "1",
                   IRNode.XOR, "1" })
    public long test1(long a, long b) {
        return (~a) | (~b);
    }
}
