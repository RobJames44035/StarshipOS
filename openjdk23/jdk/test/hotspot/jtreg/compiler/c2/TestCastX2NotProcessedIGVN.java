/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.c2;

import compiler.lib.ir_framework.*;
import jdk.internal.misc.Unsafe;

/*
 * @test
 * @bug 8343068
 * @summary C2: CastX2P Ideal transformation not always applied
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 * @run driver compiler.c2.TestCastX2NotProcessedIGVN
 */

public class TestCastX2NotProcessedIGVN {
    private static final Unsafe UNSAFE = Unsafe.getUnsafe();
    static int size = 1024;
    static long base = UNSAFE.allocateMemory(4 * size);


    public static void main(String[] args) {
        // Cross-product: +-AlignVector and +-UseCompactObjectHeaders
        TestFramework.runWithFlags("--add-modules", "java.base", "--add-exports", "java.base/jdk.internal.misc=ALL-UNNAMED",
                                   "-XX:+UnlockExperimentalVMOptions", "-XX:-UseCompactObjectHeaders",
                                   "-XX:-AlignVector");
        TestFramework.runWithFlags("--add-modules", "java.base", "--add-exports", "java.base/jdk.internal.misc=ALL-UNNAMED",
                                   "-XX:+UnlockExperimentalVMOptions", "-XX:-UseCompactObjectHeaders",
                                   "-XX:+AlignVector");
        TestFramework.runWithFlags("--add-modules", "java.base", "--add-exports", "java.base/jdk.internal.misc=ALL-UNNAMED",
                                   "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCompactObjectHeaders",
                                   "-XX:-AlignVector");
        TestFramework.runWithFlags("--add-modules", "java.base", "--add-exports", "java.base/jdk.internal.misc=ALL-UNNAMED",
                                   "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCompactObjectHeaders",
                                   "-XX:+AlignVector");
    }

    @Test
    @IR(failOn = IRNode.ADD_L, counts = {IRNode.ADD_P, "1"})
    public static byte test1(long base) {
        int offset = 0;
        do {
            offset++;
        } while (offset < 100);
        long longOffset = ((long) offset) * 2;
        return UNSAFE.getByte(null, base + longOffset);
    }

    @Run(test = "test1")
    public static void test1Runner() {
        test1(base);
    }

    @Test
    @IR(counts = {IRNode.LOAD_VECTOR_I, "> 1"},
        applyIfOr = {"UseCompactObjectHeaders", "false", "AlignVector", "false"},
        applyIfPlatformOr = {"x64", "true", "aarch64", "true"})
    public static int test2(int stop, int[] array) {
        int v = 0;
        stop = Math.min(stop, Integer.MAX_VALUE / 4);
        for (int i = 0; i < stop; i++) {
            long offset = ((long)i) * 4;
            array[i] = UNSAFE.getInt(null, offset + base);
            // With AlignVector, we need 8-byte alignment of vector loads/stores.
            // UseCompactObjectHeaders=false                  UseCompactObjectHeaders=true
            // I_adr = base + 16 + 4*i  ->  i % 2 = 0         B_adr = base + 12 + 4*i  ->  i % 2 = 1
            // N_adr = base      + 4*i  ->  i % 2 = 0         N_adr = base      + 4*i  ->  i % 2 = 0
            // -> vectorize                                   -> no vectorization
        }
        return v;
    }

    @Run(test = "test2")
    public static void test2Runner() {
        test2(size, new int[size]);
    }
}
