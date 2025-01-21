/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;
import jdk.test.lib.Utils;
import java.util.Random;
import java.util.Objects;

/*
 * @test
 * @bug 8288022
 * @key randomness
 * @summary c2: Transform (CastLL (AddL into (AddL (CastLL when possible
 * @library /test/lib /
 * @run driver compiler.c2.irTests.TestPushAddThruCast
 */

public class TestPushAddThruCast {
    private static final Random RANDOM = Utils.getRandomInstance();

    public static void main(String[] args) {
        TestFramework.run();
    }

    final static int length = RANDOM.nextInt(Integer.MAX_VALUE);
    final static long llength = RANDOM.nextInt(Integer.MAX_VALUE);
    static int i;
    static long l;

    @Test
    @IR(counts = { IRNode.CAST_II, "1" })
    public static int test1() {
        int j = Objects.checkIndex(i, length);
        int k = Objects.checkIndex(i + 1, length);
        return j + k;
    }

    @Run(test = "test1")
    public static void test1_runner() {
        i = RANDOM.nextInt(length-1);
        int res = test1();
        if (res != i * 2 + 1) {
            throw new RuntimeException("incorrect result: " + res);
        }
    }

    @Test
    @IR(counts = { IRNode.CAST_LL, "1" })
    public static long test2() {
        long j = Objects.checkIndex(l, llength);
        long k = Objects.checkIndex(l + 1, llength);
        return j + k;
    }

    @Run(test = "test2")
    public static void test2_runner() {
        l = RANDOM.nextInt(((int)llength)-1);
        long res = test2();
        if (res != l * 2 + 1) {
            throw new RuntimeException("incorrect result: " + res);
        }
    }
}
