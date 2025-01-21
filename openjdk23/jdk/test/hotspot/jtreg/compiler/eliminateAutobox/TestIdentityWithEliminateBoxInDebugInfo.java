/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8261137
 * @requires vm.flavor == "server"
 * @summary Verify that box object identity matches after deoptimization when it is eliminated.
 * @library /test/lib
 *
 * @run main/othervm -Xbatch compiler.eliminateAutobox.TestIdentityWithEliminateBoxInDebugInfo
 */

package compiler.eliminateAutobox;

import jdk.test.lib.Asserts;

public class TestIdentityWithEliminateBoxInDebugInfo {
    interface TestF {
        void apply(boolean condition);
    }

    public static void helper(TestF f) {
        // warmup
        for (int i = 0; i < 100000; i++) {
            f.apply(true);
        }
        // deoptimize
        f.apply(false);
    }

    public static void runTest() throws Exception {
        helper((c) -> {
            Integer a = Integer.valueOf(42);
            Integer b = Integer.valueOf(-42);
            if (!c) {
                Asserts.assertTrue(a == Integer.valueOf(42));
                Asserts.assertTrue(b == Integer.valueOf(-42));
            }
        });

        helper((c) -> {
            long highBitsOnly = 2L << 40;
            Long a = Long.valueOf(42L);
            Long b = Long.valueOf(-42L);
            Long h = Long.valueOf(highBitsOnly);
            if (!c) {
                Asserts.assertTrue(a == Long.valueOf(42L));
                Asserts.assertTrue(b == Long.valueOf(-42L));
                Asserts.assertFalse(h == Long.valueOf(highBitsOnly));
            }
        });

        helper((c) -> {
            Character a = Character.valueOf('a');
            Character b = Character.valueOf('Z');
            if (!c) {
                Asserts.assertTrue(a == Character.valueOf('a'));
                Asserts.assertTrue(b == Character.valueOf('Z'));
            }
        });

        helper((c) -> {
            Short a = Short.valueOf((short)42);
            Short b = Short.valueOf((short)-42);
            if (!c) {
                Asserts.assertTrue(a == Short.valueOf((short)42));
                Asserts.assertTrue(b == Short.valueOf((short)-42));
            }
        });

        helper((c) -> {
            Byte a = Byte.valueOf((byte)42);
            Byte b = Byte.valueOf((byte)-42);
            if (!c) {
                Asserts.assertTrue(a == Byte.valueOf((byte)42));
                Asserts.assertTrue(b == Byte.valueOf((byte)-42));
            }
        });

        helper((c) -> {
            Boolean a = Boolean.valueOf(true);
            Boolean b = Boolean.valueOf(false);
            if (!c) {
                Asserts.assertTrue(a == Boolean.valueOf(true));
                Asserts.assertTrue(b == Boolean.valueOf(false));
            }
        });
    }

    public static void main(String[] args) throws Exception {
        runTest();
    }
}
