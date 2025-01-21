/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
 * @bug 4913702
 * @summary validates canEncode(char c) method for sun.nio.cs.Shift_JIS
 * @modules jdk.charsets
 */


import java.nio.*;
import java.nio.charset.*;

public class SJISCanEncode {
    private Charset cs;
    private CharsetEncoder encoder;

    private void canEncodeTest(char inputChar,
                               boolean expectedResult)
                               throws Exception {
        String msg = "err: Shift_JIS canEncode() return value ";

        if (encoder.canEncode(inputChar) != expectedResult) {
            throw new Exception(msg + !(expectedResult) +
                ": "  + Integer.toHexString((int)inputChar));
        }
    }

    public static void main(String[] args) throws Exception {
        SJISCanEncode test = new SJISCanEncode();
        test.cs = Charset.forName("SJIS");
        test.encoder = test.cs.newEncoder();

        // us-ascii (mappable by Shift_JIS)
        test.canEncodeTest('\u0001', true);

        // Halfwidth Katakana
        test.canEncodeTest('\uFF01', true);

        // CJK ideograph
        test.canEncodeTest('\u4E9C', true);

        //Hiragana
        test.canEncodeTest('\u3041', true);
        // fullwidth Katakana
        test.canEncodeTest('\u30A1', true);

        // U+0080 should be unmappable
        // U+4000 is a BMP character not covered by Shift_JISe

        test.canEncodeTest('\u0080', false);
        test.canEncodeTest('\u4000', false);
    }
}
