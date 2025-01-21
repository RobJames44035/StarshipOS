/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8332827
 * @summary [REDO] C2: crash in compiled code because of dependency on removed range check CastIIs
 * @requires os.arch != "riscv64" | vm.cpu.features ~= ".*rvv.*"
 * @library /test/lib /
 * @run driver TestVectorizationNegativeScale
 *
 */

import compiler.lib.ir_framework.*;

import java.util.Arrays;

public class TestVectorizationNegativeScale {
    public static void main(String[] args) {
        TestFramework.run();
    }

    static byte[] array = new byte[1000];

    @Test
    @IR(counts = { IRNode.STORE_VECTOR , ">= 1"})
    private static void test1(byte[] array, int start) {
        for (int i = start; i < array.length; i++) {
            array[array.length - i - 1] = 0x42;
        }
    }

    @Run(test = "test1")
    private static void test1Runner() {
        Arrays.fill(array, (byte)0);
        test1(array, 0);
        for (int j = 0; j < array.length; j++) {
            if (array[j] != 0x42) {
                throw new RuntimeException("For index " + j + ": " + array[j]);
            }
        }
    }
}
