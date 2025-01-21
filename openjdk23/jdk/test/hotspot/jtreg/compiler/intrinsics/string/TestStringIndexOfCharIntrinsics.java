/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8301491
 * @summary Check for correct return value when calling indexOfChar intrinsics with negative value.
 * @library /test/lib
 *
 * @run main/othervm -XX:CompileCommand=quiet
 *                   -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.intrinsics.string.TestStringIndexOfCharIntrinsics::testIndexOfChar*
 *                   -XX:CompileCommand=inline,java.lang.String*::indexOf*
 *                   -XX:PerBytecodeTrapLimit=20000
 *                   -XX:PerMethodTrapLimit=20000
 *                   compiler.intrinsics.string.TestStringIndexOfCharIntrinsics
 */

package compiler.intrinsics.string;

import jdk.test.lib.Asserts;

public class TestStringIndexOfCharIntrinsics {

    static byte byArr[] = new byte[500];

    public static void main(String[] args) {
        for (int j = 0; j < byArr.length; j++) {
            byArr[j] = (byte)j;
        }
        // Test value for aarch64
        byArr[24] = 0x7;
        byArr[23] = -0x80;
        // Warmup
        for (int i = 0; i < 10000; i++) {
            testIndexOfCharArg(i);
            testIndexOfCharConst();
        }
        Asserts.assertEquals(testIndexOfCharConst() , -1, "must be -1 (character not found)");
        Asserts.assertEquals(testIndexOfCharArg(-2147483641) , -1, "must be -1 (character not found)");
    }

    static int testIndexOfCharConst() {
        String s = new String(byArr);
        return s.indexOf(-2147483641);
    }

    static int testIndexOfCharArg(int ch) {
        String s = new String(byArr);
        return s.indexOf(ch);
    }
}
