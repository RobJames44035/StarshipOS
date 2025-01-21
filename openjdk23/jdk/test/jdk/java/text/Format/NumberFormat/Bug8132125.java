/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8132125 8202537
 * @summary Checks Swiss' number elements
 * @modules jdk.localedata
 * @run junit Bug8132125
 */

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bug8132125 {

    // Ensure the CLDRConverter does not omit the Swiss number elements
    @Test
    public void swissNumElementsTest() {
        Locale deCH = Locale.of("de", "CH");
        NumberFormat nf = NumberFormat.getInstance(deCH);

        // "\u002E" as decimal separator, "\u2019" as grouping separator
        String expected = "54\u2019839\u2019483.142";
        String actual = nf.format(54839483.1415);
        assertEquals(expected, actual, "incorrect number elements for de_CH");
    }
}
