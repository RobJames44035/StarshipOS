/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8321545
 * @summary Ensure value returned by overridden toString method is as expected
 * @run junit ToStringTest
 */

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToStringTest {

    // Check a normal expected value. There is no null locale test as
    // DecimalFormatSymbols will throw an exception when created with a null locale.
    @Test
    public void expectedValueTest() {
        String expectedStr =
                "DecimalFormat [locale: \"English (Canada)\", pattern: \"foo#00.00bar\"]\n";
        var d = new DecimalFormat("foo#00.00bar", new DecimalFormatSymbols(Locale.CANADA));
        assertEquals(expectedStr, d.toString());

        String expectedStr2 =
                "DecimalFormat [locale: \"English (Canada)\", pattern: \"#,##0.###\"]\n";
        var d2 = NumberFormat.getInstance(Locale.CANADA);
        assertEquals(expectedStr2, d2.toString());
    }
}
