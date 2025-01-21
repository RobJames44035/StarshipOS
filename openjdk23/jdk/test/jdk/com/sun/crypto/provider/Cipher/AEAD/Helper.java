/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

public class Helper {

    public static byte[] generateBytes(int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (i % 256);
        }
        return bytes;
    }
}
