/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * utility class
 */

public class TestUtility {

    private static final String DIGITS = "0123456789abcdef";

    private TestUtility() {

    }

    public static String hexDump(byte[] bytes) {

        StringBuilder buf = new StringBuilder(bytes.length * 2);
        int i;

        buf.append("    "); // four spaces
        for (i = 0; i < bytes.length; i++) {
            buf.append(DIGITS.charAt(bytes[i] >> 4 & 0x0f));
            buf.append(DIGITS.charAt(bytes[i] & 0x0f));
            if ((i + 1) % 32 == 0) {
                if (i + 1 != bytes.length) {
                    buf.append("\n    "); // line after four words
                }
            } else if ((i + 1) % 4 == 0) {
                buf.append(' '); // space between words
            }
        }
        return buf.toString();
    }

    public static String hexDump(byte[] bytes, int index) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        int i;

        buf.append("    "); // four spaces
        buf.append(DIGITS.charAt(bytes[index] >> 4 & 0x0f));
        buf.append(DIGITS.charAt(bytes[index] & 0x0f));
        return buf.toString();
    }

    public static boolean equalsBlock(byte[] b1, byte[] b2) {

        if (b1.length != b2.length) {
            return false;
        }

        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean equalsBlock(byte[] b1, byte[] b2, int len) {

        for (int i = 0; i < len; i++) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }

        return true;
    }

}
