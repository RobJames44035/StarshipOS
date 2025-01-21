/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8008386
 * @summary (cs) Unmappable leading should be decoded to replacement.
 *          Tests for Shift_JIS and MS932 decoding
 * @modules jdk.charsets
 * @run main TestUnmappable
 */

import java.nio.charset.Charset;

public class TestUnmappable {
    public static void main(String args[]) throws Exception {

        // illegal leading character test
        byte[][] inputBytes = {
                               // Shift_JIS
                               {(byte)0xce, (byte)0xa0, (byte)0xce, (byte)0x7a},
                               // MS932
                               {(byte)0x3c, (byte)0x21, (byte)0x2d, (byte)0x2d,
                                (byte)0xe5, (byte)0xaf, (byte)0xbe, (byte)0xe5,
                                (byte)0xbf, (byte)0x9c, (byte)0x2d, (byte)0x2d,
                                (byte)0x3e, (byte)0xd,  (byte)0xa },
                               {(byte)0x81, (byte)0xad},
                               // PCK
                               {(byte)0xef, (byte)0x90},
                               {(byte)0x91, (byte)0xfd}
                              };

        String[] charsets = { "Shift_JIS", "MS932", "PCK" };
        String[] expectedStrings = {
                                    // Shift_JIS
                                    "0xce 0x3f 0xce 0x7a ",
                                    // MS932
                                    "0x3c 0x21 0x2d 0x2d 0xe5 0xaf 0xbe 0xe5 0xbf " +
                                    "0x3f 0x2d 0x2d 0x3e 0xd 0xa ",
                                    "0x3f 0xad ",
                                    // PCK
                                    "0x3f 0x3f ",
                                    "0x3f "};

        for (int i = 0; i < charsets.length; i++) {
            String ret = new String(inputBytes[i], charsets[i]);
            String bString = getByteString(ret.getBytes(Charset.forName(charsets[i])));
            if (expectedStrings[i].length() != bString.length()
               || ! expectedStrings[i].equals(bString)){
                throw new Exception("ByteToChar for " + charsets[i]
                    + " does not work correctly.\n" +
                    "Expected: " + expectedStrings[i] + "\n" +
                    "Received: " + bString);
            }
        }
    }

    private static String getByteString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append("0x" + Integer.toHexString((int)(bytes[i] & 0xFF)) + " ");
        }
        return sb.toString();
    }
}
