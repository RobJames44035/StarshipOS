/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package compiler.c2.irTests;

import jdk.test.lib.Asserts;
import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8322077
 * @summary Test that Ideal transformations on the De Morgan's Law perform
            as expected for long.
 * @library /test/lib /
 * @run driver compiler.c2.irTests.DeMorganLawLongTests
 */
public class DeMorganLawLongTests {

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

        assertResult(0, 0, 0, 0);
        assertResult(a, b, c, d);
        assertResult(min, min, min, min);
        assertResult(max, max, max, max);
    }

    @DontCompile
    public void assertResult(long a, long b, long c, long d) {
        Asserts.assertEQ((~a | ~b) & (~c | ~d), test1(a, b, c, d));
        Asserts.assertEQ((~a & ~b) | (~c & ~d), test2(a, b, c, d));
        Asserts.assertEQ((~a | ~b) | (~c | ~d), test3(a, b, c, d));
        Asserts.assertEQ((~a & ~b) & (~c & ~d), test4(a, b, c, d));
    }

    // Checks (~a | ~b) & (~c | ~d)
    //     => ~(a & b) & ~(c & d)
    //     => ~((a & b) | (c & d))
    @Test
    @IR(counts = { IRNode.AND , "2",
                   IRNode.OR , "1",
                   IRNode.XOR, "1", })
    public long test1(long a, long b, long c, long d) {
        return (~a | ~b) & (~c | ~d);
    }

    // Checks (~a & ~b) | (~c & ~d)
    //     => ~(a | b) | ~(c | d)
    //     => ~((a | b) & (c | d))
    @Test
    @IR(counts = { IRNode.AND , "1",
                   IRNode.OR , "2",
                   IRNode.XOR, "1", })
    public long test2(long a, long b, long c, long d) {
        return (~a & ~b) | (~c & ~d);
    }

    // Checks (~a | ~b) | (~c | ~d)
    //     => ~(a & b) | ~(c & d)
    //     => ~((a & b) & (c & d))
    @Test
    @IR(failOn = { IRNode.OR })
    @IR(counts = { IRNode.AND , "3",
                   IRNode.XOR, "1", })
    public long test3(long a, long b, long c, long d) {
        return (~a | ~b) | (~c | ~d);
    }

    // Checks (~a & ~b) & (~c & ~d)
    //     => ~(a | b) & ~(c | d)
    //     => ~((a | b) | (c | d))
    @Test
    @IR(failOn = { IRNode.AND })
    @IR(counts = { IRNode.OR , "3",
                   IRNode.XOR, "1", })
    public long test4(long a, long b, long c, long d) {
        return (~a & ~b) & (~c & ~d);
    }
}
