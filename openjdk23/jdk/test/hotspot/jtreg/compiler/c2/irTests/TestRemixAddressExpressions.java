/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8278784
 * @summary C2: Refactor PhaseIdealLoop::remix_address_expressions() so it operates on longs
 * @library /test/lib /
 * @run driver compiler.c2.irTests.TestRemixAddressExpressions
 */

public class TestRemixAddressExpressions {
    public static void main(String[] args) {
        TestFramework.run();
    }

    @Test
    @IR(counts = { IRNode.ADD_I, "1", IRNode.LSHIFT_I, "2" })
    @Arguments(values = {Argument.RANDOM_EACH, Argument.RANDOM_EACH})
    public static float invPlusVarLshiftInt(int inv, int scale) {
        float res = 0;
        for (int i = 1; i < 100; i *= 11) {
            res += (i + inv) << scale;
        }
        return res;
    }

    @Test
    @IR(counts = { IRNode.ADD_L, "1", IRNode.LSHIFT_L, "2" })
    @Arguments(values = {Argument.RANDOM_EACH, Argument.RANDOM_EACH})
    public static float invPlusVarLshiftLong(long inv, int scale) {
        float res = 0;
        for (long i = 1; i < 100; i *= 11) {
            res += (i + inv) << scale;
        }
        return res;
    }

    @Test
    @IR(counts = { IRNode.ADD_I, "1", IRNode.SUB_I, "1", IRNode.LSHIFT_I, "2" })
    @Arguments(values = {Argument.RANDOM_EACH, Argument.RANDOM_EACH})
    public static float invMinusVarLshiftInt(int inv, int scale) {
        float res = 0;
        for (int i = 1; i < 100; i *= 11) {
            res += (inv - i) << scale;
        }
        return res;
    }

    @Test
    @IR(counts = { IRNode.ADD_L, "1", IRNode.SUB_L, "1", IRNode.LSHIFT_L, "2" })
    @Arguments(values = {Argument.RANDOM_EACH, Argument.RANDOM_EACH})
    public static float invMinusVarLshiftLong(long inv, int scale) {
        float res = 0;
        for (long i = 1; i < 100; i *= 11) {
            res += (inv - i) << scale;
        }
        return res;
    }

    @Test
    @IR(counts = { IRNode.ADD_I, "1", IRNode.SUB_I, "1", IRNode.LSHIFT_I, "2" })
    @Arguments(values = {Argument.RANDOM_EACH, Argument.RANDOM_EACH})
    public static float varMinusInvLshiftInt(int inv, int scale) {
        float res = 0;
        for (int i = 1; i < 100; i *= 11) {
            res += (i - inv) << scale;
        }
        return res;
    }

    @Test
    @IR(counts = { IRNode.ADD_L, "1", IRNode.SUB_L, "1", IRNode.LSHIFT_L, "2" })
    @Arguments(values = {Argument.RANDOM_EACH, Argument.RANDOM_EACH})
    public static float varMinusInvLshiftLong(long inv, int scale) {
        float res = 0;
        for (long i = 1; i < 100; i *= 11) {
            res += (i - inv) << scale;
        }
        return res;
    }
}
