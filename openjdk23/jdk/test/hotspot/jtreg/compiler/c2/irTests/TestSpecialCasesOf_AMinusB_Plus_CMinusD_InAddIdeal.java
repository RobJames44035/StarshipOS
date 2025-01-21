/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.c2.irTests;

import jdk.test.lib.Asserts;
import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8278471
 * @summary Remove unreached rules in AddNode::IdealIL
 * @library /test/lib /
 * @run driver compiler.c2.irTests.TestSpecialCasesOf_AMinusB_Plus_CMinusD_InAddIdeal
 */
/* Test conversion from (a - b) + (b - c) to (a - c) and conversion
 * from (a - b) + (c - a) to (c - b) have really happened so we can
 * safely remove both. */
public class TestSpecialCasesOf_AMinusB_Plus_CMinusD_InAddIdeal {

    public static void main(String[] args) {
        TestFramework.run();
    }

    @Test
    @Arguments(values = {Argument.RANDOM_ONCE, Argument.RANDOM_ONCE, Argument.RANDOM_ONCE})
    @IR(failOn = {IRNode.ADD_I})
    @IR(counts = {IRNode.SUB_I, "1"})
    public int test1Int(int a, int b, int c) {
        return (a - b) + (b - c); // transformed to a - c rather than (a + b) - (b + c)
    }

    @Test
    @Arguments(values = {Argument.RANDOM_ONCE, Argument.RANDOM_ONCE, Argument.RANDOM_ONCE})
    @IR(failOn = {IRNode.ADD_L})
    @IR(counts = {IRNode.SUB_L, "1"})
    public long test1Long(long a, long b, long c) {
        return (a - b) + (b - c); // transformed to a - c rather than (a + b) - (b + c)
    }

    @Test
    @Arguments(values = {Argument.RANDOM_ONCE, Argument.RANDOM_ONCE, Argument.RANDOM_ONCE})
    @IR(failOn = {IRNode.ADD_I})
    @IR(counts = {IRNode.SUB_I, "1"})
    public int test2Int(int b, int a, int c) { // make sure inputs sorted
        return (a - b) + (c - a); // transformed to c - b rather than (a + c) - (b + a)
    }

    @Test
    @Arguments(values = {Argument.RANDOM_ONCE, Argument.RANDOM_ONCE, Argument.RANDOM_ONCE})
    @IR(failOn = {IRNode.ADD_L})
    @IR(counts = {IRNode.SUB_L, "1"})
    public long test2Long(long b, long a, long c) { // make sure inputs sorted
        return (a - b) + (c - a); // transformed to return c - b rather than (a + c) - (b + a)
    }
}
