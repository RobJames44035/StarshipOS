/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8323429
 * @summary Test min and max optimizations
 * @library /test/lib /
 * @run driver compiler.intrinsics.math.TestMinMaxOpt
 */

package compiler.intrinsics.math;

import compiler.lib.ir_framework.Argument;
import compiler.lib.ir_framework.Arguments;
import compiler.lib.ir_framework.Check;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.IRNode;
import compiler.lib.ir_framework.Test;
import compiler.lib.ir_framework.TestFramework;

public class TestMinMaxOpt {
    public static void main(String[] args) {
        TestFramework.run();
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    @IR(failOn = {IRNode.MIN_I})
    private static int testIntMin(int v) {
        return Math.min(v, v);
    }

    @Check(test = "testIntMin")
    public static void checkTestIntMin(int result) {
        if (result != 42) {
            throw new RuntimeException("Incorrect result: " + result);
        }
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    @IR(failOn = {IRNode.MAX_I})
    private static int testIntMax(int v) {
        return Math.max(v, v);
    }

    @Check(test = "testIntMax")
    public static void checkTestIntMax(int result) {
        if (result != 42) {
            throw new RuntimeException("Incorrect result: " + result);
        }
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    @IR(failOn = {IRNode.MIN_L})
    private static long testLongMin(long v) {
        return Math.min(v, v);
    }

    @Check(test = "testLongMin")
    public static void checkTestLongMin(long result) {
        if (result != 42) {
            throw new RuntimeException("Incorrect result: " + result);
        }
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    @IR(failOn = {IRNode.MAX_L})
    private static long testLongMax(long v) {
        return Math.max(v, v);
    }

    @Check(test = "testLongMax")
    public static void checkTestLongMax(long result) {
        if (result != 42) {
            throw new RuntimeException("Incorrect result: " + result);
        }
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    @IR(failOn = {IRNode.MIN_F})
    private static float testFloatMin(float v) {
        return Math.min(v, v);
    }

    @Check(test = "testFloatMin")
    public static void checkTestFloatMin(float result) {
        if (result != 42) {
            throw new RuntimeException("Incorrect result: " + result);
        }
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    @IR(failOn = {IRNode.MAX_F})
    private static float testFloatMax(float v) {
        return Math.max(v, v);
    }

    @Check(test = "testFloatMax")
    public static void checkTestFloatMax(float result) {
        if (result != 42) {
            throw new RuntimeException("Incorrect result: " + result);
        }
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    @IR(failOn = {IRNode.MIN_D})
    private static double testDoubleMin(double v) {
        return Math.min(v, v);
    }

    @Check(test = "testDoubleMin")
    public static void checkTestDoubleMin(double result) {
        if (result != 42) {
            throw new RuntimeException("Incorrect result: " + result);
        }
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    @IR(failOn = {IRNode.MAX_D})
    private static double testDoubleMax(double v) {
        return Math.max(v, v);
    }

    @Check(test = "testDoubleMax")
    public static void checkTestDoubleMax(double result) {
        if (result != 42) {
            throw new RuntimeException("Incorrect result: " + result);
        }
    }
}
