/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.c2.gvn;

import compiler.lib.ir_framework.*;

import java.util.Random;

/**
 * @test
 * @bug 8327381
 * @summary Refactor boolean node tautology transformations
 * @library /test/lib /
 * @run driver compiler.c2.gvn.TestBoolNodeGVN
 */
public class TestBoolNodeGVN {
    public static void main(String[] args) {
        TestFramework.run();
        testCorrectness();
    }

    /**
     * Test changing ((x & m) u<= m) or ((m & x) u<= m) to always true, same with ((x & m) u< m+1) and ((m & x) u< m+1)
     * The test is only applicable to x64, aarch64 and riscv64 for having <code>Integer.compareUnsigned</code>
     * intrinsified.
     */
    @Test
    @Arguments(values = {Argument.DEFAULT, Argument.DEFAULT})
    @IR(failOn = IRNode.CMP_U,
        phase = CompilePhase.AFTER_PARSING,
        applyIfPlatformOr = {"x64", "true", "aarch64", "true", "riscv64", "true"})
    public static boolean testShouldReplaceCpmUCase1(int x, int m) {
        return !(Integer.compareUnsigned((x & m), m) > 0); // assert in inversions to generates the pattern looking for
    }
    @Test
    @Arguments(values = {Argument.DEFAULT, Argument.DEFAULT})
    @IR(failOn = IRNode.CMP_U,
            phase = CompilePhase.AFTER_PARSING,
            applyIfPlatformOr = {"x64", "true", "aarch64", "true", "riscv64", "true"})
    public static boolean testShouldReplaceCpmUCase2(int x, int m) {
        return !(Integer.compareUnsigned((m & x), m) > 0);
    }

    @Test
    @Arguments(values = {Argument.DEFAULT, Argument.DEFAULT})
    @IR(failOn = IRNode.CMP_U,
            phase = CompilePhase.AFTER_PARSING,
            applyIfPlatformOr = {"x64", "true", "aarch64", "true", "riscv64", "true"})
    public static boolean testShouldReplaceCpmUCase3(int x, int m) {
        return Integer.compareUnsigned((x & m), m + 1) < 0;
    }

    @Test
    @Arguments(values = {Argument.DEFAULT, Argument.DEFAULT})
    @IR(failOn = IRNode.CMP_U,
            phase = CompilePhase.AFTER_PARSING,
            applyIfPlatformOr = {"x64", "true", "aarch64", "true", "riscv64", "true"})
    public static boolean testShouldReplaceCpmUCase4(int x, int m) {
        return Integer.compareUnsigned((m & x), m + 1) < 0;
    }

    @Test
    @Arguments(values = {Argument.DEFAULT, Argument.DEFAULT})
    @IR(counts = {IRNode.CMP_U, "1"},
        phase = CompilePhase.AFTER_PARSING,
        applyIfPlatformOr = {"x64", "true", "aarch64", "true", "riscv64", "true"})
    public static boolean testShouldHaveCpmUCase1(int x, int m) {
        return !(Integer.compareUnsigned((x & m), m - 1) > 0);
    }

    @Test
    @Arguments(values = {Argument.DEFAULT, Argument.DEFAULT})
    @IR(counts = {IRNode.CMP_U, "1"},
            phase = CompilePhase.AFTER_PARSING,
            applyIfPlatformOr = {"x64", "true", "aarch64", "true", "riscv64", "true"})
    public static boolean testShouldHaveCpmUCase2(int x, int m) {
        return !(Integer.compareUnsigned((m & x), m - 1) > 0);
    }

    @Test
    @Arguments(values = {Argument.DEFAULT, Argument.DEFAULT})
    @IR(counts = {IRNode.CMP_U, "1"},
            phase = CompilePhase.AFTER_PARSING,
            applyIfPlatformOr = {"x64", "true", "aarch64", "true", "riscv64", "true"})
    public static boolean testShouldHaveCpmUCase3(int x, int m) {
        return Integer.compareUnsigned((x & m), m + 2) < 0;
    }

    @Test
    @Arguments(values = {Argument.DEFAULT, Argument.DEFAULT})
    @IR(counts = {IRNode.CMP_U, "1"},
            phase = CompilePhase.AFTER_PARSING,
            applyIfPlatformOr = {"x64", "true", "aarch64", "true", "riscv64", "true"})
    public static boolean testShouldHaveCpmUCase4(int x, int m) {
        return Integer.compareUnsigned((m & x), m + 2) < 0;
    }

    private static void testCorrectness() {
        int[] values = {
                0, 1, 5, 8, 16, 42, 100, new Random().nextInt(0, Integer.MAX_VALUE), Integer.MAX_VALUE
        };

        for (int x : values) {
            for (int m : values) {
                if (!testShouldReplaceCpmUCase1(x, m) |
                    !testShouldReplaceCpmUCase2(x, m) |
                    !testShouldReplaceCpmUCase3(x, m) |
                    !testShouldReplaceCpmUCase4(x, m)) {
                    throw new RuntimeException("Bad result for x = " + x + " and m = " + m + ", expected always true");
                }
            }
        }
    }
}
