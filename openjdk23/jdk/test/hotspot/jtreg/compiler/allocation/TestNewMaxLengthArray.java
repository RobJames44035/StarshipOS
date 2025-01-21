/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8316414
 * @summary C2: large byte array clone triggers "failed: malformed control flow" assertion failure on linux-x86
 * @run main/othervm  -Xcomp -XX:CompileOnly=TestNewMaxLengthArray::createAndClone TestNewMaxLengthArray
 */

public class TestNewMaxLengthArray {

    // Maximum length of a byte array on a 32-bits platform using default object
    // alignment (8 bytes).
    static final int MAX_BYTE_ARRAY_LENGTH = 0x7ffffffc;

    public static byte[] createAndClone() {
        byte[] array = new byte[MAX_BYTE_ARRAY_LENGTH];
        return array.clone();
    }

    public static void main(String[] a) {
        try {
            createAndClone();
        } catch (OutOfMemoryError oome) {
        }
    }
}
