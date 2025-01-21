/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
   @bug 5016049
   @summary ensure euc-jp-linux charset decoder recovery for unmappable input
 */

import java.io.*;

public class EucJpLinuxDecoderRecoveryTest {
    public static void main(String[] args) throws Exception {
        byte[] encoded = {
                // EUC_JP_LINUX mappable JIS X 0208 range
                (byte)0xa6, (byte)0xc5,
                // EUC_JP_LINUX Unmappable (JIS X 0212 range)
                (byte)0x8f, (byte)0xa2, (byte)0xb7,
                // EUC_JP_LINUX mappable JIS X 0208 range
                (byte)0xa6, (byte)0xc7 };

        char[] decodedChars = new char[3];
        char[] expectedChars =
                        {
                        '\u03B5',  // mapped
                        '\ufffd',  // unmapped
                        '\u03B7'   // mapped
                        };

        ByteArrayInputStream bais = new ByteArrayInputStream(encoded);
        InputStreamReader isr = new InputStreamReader(bais, "EUC_JP_LINUX");
        int n = 0;   // number of chars decoded

        try {
            n = isr.read(decodedChars);
        } catch (Exception ex) {
            throw new Error("euc-jp-linux decoding broken");
        }

        // check number of decoded chars is what is expected
        if (n != expectedChars.length)
            throw new Error("Unexpected number of chars decoded");

        // Compare actual decoded with expected

        for (int i = 0; i < n; i++) {
            if (expectedChars[i] != decodedChars[i])
                throw new Error("euc-jp-linux decoding incorrect");
        }
    }
}
