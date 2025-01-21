/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8311023
 * @summary Crash encountered while converting the types of non-escaped object to instance types.
 *
 * @run main/othervm
 *      -XX:-TieredCompilation -Xbatch compiler.escapeAnalysis.TestEAVectorizedHashCode
 */

package compiler.escapeAnalysis;

import java.util.Arrays;

public class TestEAVectorizedHashCode {
    public static int micro() {
        int[] a = { 10, 20, 30, 40, 50, 60};
        return Arrays.hashCode(a);
    }

    public static void main(String [] args) {
        int res = 0;
        for (int i = 0; i < 10000; i++) {
            res += micro();
        }
        System.out.println("PASS:" +  res);
    }
}
