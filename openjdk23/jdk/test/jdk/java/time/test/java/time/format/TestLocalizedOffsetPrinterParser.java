/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8154520
 * @summary This test verifies that the localized text for "GMT" from CLDR is
 * applied/recognized during printing/parsing timestamps. For example, the
 * localized text for "GMT" on some particular locale may be "UTC", and the
 * resulting formatted string should have UTC+<offset> (instead of GMT+<offset>).
 * Since the test relies on CLDR data, the "expected" text in the test data may
 * require to be modified in accordance with changes to CLDR, if any.
 * @modules jdk.localedata
 */

package test.java.time.format;

import static org.testng.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test DateTimeFormatterBuilder.appendOffset().
 */
@Test
public class TestLocalizedOffsetPrinterParser {

    private static final LocalDateTime DT_2012_06_30_12_30_40 = LocalDateTime.of(2012, 6, 30, 12, 30, 40);

    private static final Locale LOCALE_GA = Locale.of("ga");

    @DataProvider(name="print_localized_custom_locale")
    Object[][] data_print_localized_custom_locale() {
        return new Object[][] {
                {TextStyle.FULL, DT_2012_06_30_12_30_40, ZoneOffset.UTC, LOCALE_GA, "MAG"},
                {TextStyle.SHORT, DT_2012_06_30_12_30_40, ZoneOffset.ofHours(1), LOCALE_GA, "MAG+1"},
                {TextStyle.FULL, DT_2012_06_30_12_30_40, ZoneOffset.ofHours(-1), LOCALE_GA, "MAG-01:00"}
        };
    }

    @Test(dataProvider="print_localized_custom_locale")
    public void test_print_localized_custom_locale(TextStyle style, LocalDateTime ldt, ZoneOffset offset, Locale locale, String expected) {

        Objects.requireNonNull(locale, "Locale must not be null");

        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);
        ZonedDateTime zdt = ldt.atZone(offset);

        DateTimeFormatter f = new DateTimeFormatterBuilder().appendLocalizedOffset(style).toFormatter(locale);
        assertEquals(f.format(odt), expected);
        assertEquals(f.format(zdt), expected);
        assertEquals(f.parse(expected, ZoneOffset::from), offset);

        if (style == TextStyle.FULL) {
            f = new DateTimeFormatterBuilder().appendPattern("ZZZZ").toFormatter(locale);
            assertEquals(f.format(odt), expected);
            assertEquals(f.format(zdt), expected);
            assertEquals(f.parse(expected, ZoneOffset::from), offset);

            f = new DateTimeFormatterBuilder().appendPattern("OOOO").toFormatter(locale);
            assertEquals(f.format(odt), expected);
            assertEquals(f.format(zdt), expected);
            assertEquals(f.parse(expected, ZoneOffset::from), offset);
        }

        if (style == TextStyle.SHORT) {
            f = new DateTimeFormatterBuilder().appendPattern("O").toFormatter(locale);
            assertEquals(f.format(odt), expected);
            assertEquals(f.format(zdt), expected);
            assertEquals(f.parse(expected, ZoneOffset::from), offset);
        }

    }

}
