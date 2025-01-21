/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.exceptions;

/**
 * @test
 * @key stress randomness
 * @bug 8263227
 * @summary Tests that users of return values from exception-throwing method
 *          calls are not duplicated in the call's exception path. The second
 *          run with a variable seed is added for test robustness.
 * @library /test/lib /
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -Xbatch -XX:+StressGCM -XX:StressSeed=0
 *                   -XX:+VerifyRegisterAllocator
 *                   -XX:CompileCommand=dontinline,java.lang.Integer::*
 *                   compiler.exceptions.TestSpilling
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -Xbatch -XX:+StressGCM
 *                   -XX:+VerifyRegisterAllocator
 *                   -XX:CompileCommand=dontinline,java.lang.Integer::*
 *                   compiler.exceptions.TestSpilling
 */

public class TestSpilling {

    public static void test() {
        int a = Integer.valueOf(42).intValue();
        // After global code motion, the logic below should only be placed in
        // the fall-through path of java.lang.Integer::intValue(). Otherwise,
        // live range splitting might create uses without reaching definitions
        // if 'a' is spilled.
        int b = (((a & 0x0000F000)) + 1);
        int c = a / b + ((a % b > 0) ? 1 : 0);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_000; i++) {
            test();
        }
    }

}
