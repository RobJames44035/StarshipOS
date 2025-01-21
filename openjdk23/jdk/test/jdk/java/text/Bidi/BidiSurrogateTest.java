/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 4888843
 * @summary verify that surrogate pairs representing codepoints with R or AL directionality
 * and correctly recognized and reordered.
 */

import java.text.Bidi;

public class BidiSurrogateTest {
    private static final String RTLS = new String(Character.toChars(0x10800)); // surrogate code point with R directionality
    private static final String LTRS = new String(Character.toChars(0x107ff)); // surrogate code point with L directionality
    private static final String LRE = "\u202a";
    private static final String RLE = "\u202b";
    private static final String PDF = "\u202c";


    public static void main(String[] args) {
        new BidiSurrogateTest().test();
    }

    void test() {
        test0();
        test1();
    }

    void test0() {
        // test unpaired surrogates - should have L directionality
        testRequiresBidi("\ud800", false);           // unpaired lead surrogate
        testRequiresBidi("\udc00", false);           // unpaired trail surrogate
        testRequiresBidi("\udc00\ud800", false);     // out of order surrogates
        testRequiresBidi("a\udc00b\ud800c", false);  // out of order surrogates split
        testRequiresBidi(LTRS, false);               // supplementary with L
        testRequiresBidi(RTLS, true);                // supplementary with R
        testRequiresBidi("a" + RTLS + "b", true);    // R supplementary in LTR text
        testRequiresBidi(LTRS + RTLS, true);         // R supplementary in LTR supplementary text
        testRequiresBidi(LRE, false);                // LRE lone embedding
        testRequiresBidi(RLE, true);                 // RLE lone embedding
        testRequiresBidi(PDF, false);                // PDF lone pop embedding
    }

    void testRequiresBidi(String string, boolean requiresBidi) {
        char[] text = string.toCharArray();
        if (Bidi.requiresBidi(text, 0, text.length) != requiresBidi) {
            throw new RuntimeException("testRequiresBidi failed with '" + string + "', " + requiresBidi);
        }
    }

    void test1() {
        // test that strings with surrogate runs process surrogate directionality ok
        testBidi("This is a string with " + LTRS + " in it.", false);
        testBidi("This is a string with \ud800 in it.", false);
        testBidi("This is a string with \u0640 in it.", 22, 1);
        testBidi(RTLS, true);
        testBidi("This is a string with " + RTLS + RTLS + RTLS + " in it.", 22, 6);
    }

    void testBidi(String string, boolean directionIsRTL) {
        Bidi bidi = new Bidi(string, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        if (bidi.isMixed()) {
            throw new RuntimeException("bidi is mixed");
        }
        if (bidi.isRightToLeft() != directionIsRTL) {
            throw new RuntimeException("bidi is not " + (directionIsRTL ? "rtl" : "ltr"));
        }
    }

    void testBidi(String string, int rtlstart, int rtllength) {
        Bidi bidi = new Bidi(string, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        for (int i = 0; i < bidi.getRunCount(); ++i) {
            if ((bidi.getRunLevel(i) & 1) != 0) {
                if (bidi.getRunStart(i) != rtlstart ||
                    bidi.getRunLimit(i) != rtlstart + rtllength) {
                    throw new RuntimeException("first rtl run didn't match " + rtlstart + ", " + rtllength);
                }
                break;
            }
        }
    }
}
