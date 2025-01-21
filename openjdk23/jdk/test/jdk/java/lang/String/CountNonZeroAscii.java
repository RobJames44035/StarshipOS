/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import jdk.internal.access.JavaLangAccess;
import jdk.internal.access.SharedSecrets;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/*
 * @test
 * @modules java.base/jdk.internal.access
 * @summary test latin1 String countNonZeroAscii
 * @run main/othervm -XX:+CompactStrings CountNonZeroAscii
 * @run main/othervm -XX:-CompactStrings CountNonZeroAscii
 */
public class CountNonZeroAscii {
    private static final JavaLangAccess JLA = SharedSecrets.getJavaLangAccess();

    public static void main(String [] args) {
        byte[] bytes = new byte[1000];

        Arrays.fill(bytes, (byte) 'A');
        String s = new String(bytes, StandardCharsets.ISO_8859_1);
        assertEquals(bytes.length, JLA.countNonZeroAscii(s));

        for (int i = 0; i < bytes.length; i++) {
            for (int j = Byte.MIN_VALUE; j <= 0; j++) {
                bytes[i] = (byte) j;
                s = new String(bytes, StandardCharsets.ISO_8859_1);
                assertEquals(i, JLA.countNonZeroAscii(s));
            }
            bytes[i] = (byte) 'A';
        }
    }

    static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }
}
