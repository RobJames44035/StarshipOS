/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8318761 8329118
 * @summary Test MessageFormatPattern ability to recognize and produce
 *          appropriate FormatType and FormatStyle for CompactNumberFormat.
 * @run junit CompactSubFormats
 */

import java.text.CompactNumberFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This test expects an US locale, as this locale provides distinct instances
// for different styles.
public class CompactSubFormats {

    // Ensure the built-in FormatType and FormatStyles for cnFmt are as expected
    @Test
    public void applyPatternTest() {
        var mFmt = new MessageFormat(
                "{0,number,compact_short}{1,number,compact_long}", Locale.US);
        var compactShort = NumberFormat.getCompactNumberInstance(
                mFmt.getLocale(), NumberFormat.Style.SHORT);
        var compactLong = NumberFormat.getCompactNumberInstance(
                mFmt.getLocale(), NumberFormat.Style.LONG);
        assertEquals(mFmt.getFormatsByArgumentIndex()[0], compactShort);
        assertEquals(mFmt.getFormatsByArgumentIndex()[1], compactLong);
    }

    // Ensure that only 'compact_short' and 'compact_long' are recognized as
    // compact number modifiers. All other compact_XX should be interpreted as
    // a subformatPattern for a DecimalFormat
    @Test
    public void recognizedCompactStylesTest() {
        // An exception won't be thrown since 'compact_regular' will be interpreted as a
        // subformatPattern.
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        assertEquals(new DecimalFormat("compact_regular", symbols),
                new MessageFormat("{0,number,compact_regular}", Locale.US).getFormatsByArgumentIndex()[0]);
    }

    // SHORT and LONG CompactNumberFormats should produce correct patterns
    @Test
    public void toPatternTest() {
        var mFmt = new MessageFormat("{0}{1}", Locale.US);
        mFmt.setFormatByArgumentIndex(0, NumberFormat.getCompactNumberInstance(
                mFmt.getLocale(), NumberFormat.Style.SHORT));
        mFmt.setFormatByArgumentIndex(1, NumberFormat.getCompactNumberInstance(
                mFmt.getLocale(), NumberFormat.Style.LONG));
        assertEquals("{0,number,compact_short}{1,number,compact_long}", mFmt.toPattern());
    }

    // A custom cnFmt cannot be recognized, thus does not produce any built-in pattern
    @Test
    public void badToPatternTest() {
        var mFmt = new MessageFormat("{0}", Locale.US);
        // Non-recognizable compactNumberFormat
        mFmt.setFormatByArgumentIndex(0, new CompactNumberFormat("",
                        DecimalFormatSymbols.getInstance(Locale.US), new String[]{""}));
        // Default behavior of unrecognizable Formats is a FormatElement
        // in the form of { ArgumentIndex }
        assertEquals("{0}", mFmt.toPattern());
    }
}
