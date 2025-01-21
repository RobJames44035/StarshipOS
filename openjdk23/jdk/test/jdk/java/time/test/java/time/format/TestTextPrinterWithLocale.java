/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @modules jdk.localedata
 */

package test.java.time.format;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.testng.Assert.assertEquals;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.util.Locale;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.time.temporal.MockFieldValue;

/**
 * Test TextPrinterParserWithLocale.
 */
@Test
public class TestTextPrinterWithLocale extends AbstractTestPrinterParser {
    static final Locale RUSSIAN = Locale.of("ru");
    static final Locale FINNISH = Locale.of("fi");

    //-----------------------------------------------------------------------
    @DataProvider(name="print_DayOfWeekData")
    Object[][] providerDayOfWeekData() {
        return new Object[][] {
            // Locale, pattern, expected text, input DayOfWeek
            {Locale.US, "e",  "1",  DayOfWeek.SUNDAY},
            {Locale.US, "ee", "01", DayOfWeek.SUNDAY},
            {Locale.US, "c",  "1",  DayOfWeek.SUNDAY},

            {Locale.UK, "e",  "1",  DayOfWeek.MONDAY},
            {Locale.UK, "ee", "01", DayOfWeek.MONDAY},
            {Locale.UK, "c",  "1",  DayOfWeek.MONDAY},
        };
    }

    // Test data is dependent on localized resources.
    @DataProvider(name="print_standalone")
    Object[][] provider_StandaloneNames() {
        return new Object[][] {
            // standalone names for 2013-01-01 (Tue)
            // Locale, TemporalField, TextStyle, expected text
            {RUSSIAN, MONTH_OF_YEAR, TextStyle.FULL_STANDALONE,  "\u044f\u043d\u0432\u0430\u0440\u044c"},
            {RUSSIAN, MONTH_OF_YEAR, TextStyle.SHORT_STANDALONE, "\u044f\u043d\u0432."},
            {FINNISH, DAY_OF_WEEK,   TextStyle.FULL_STANDALONE,  "tiistai"},
            {FINNISH, DAY_OF_WEEK,   TextStyle.SHORT_STANDALONE, "ti"},
        };
    }

    @Test(dataProvider="print_DayOfWeekData")
    public void test_formatDayOfWeek(Locale locale, String pattern, String expected, DayOfWeek dayOfWeek) {
        DateTimeFormatter formatter = getPatternFormatter(pattern).withLocale(locale);
        String text = formatter.format(dayOfWeek);
        assertEquals(text, expected);
    }

    @Test(dataProvider="print_standalone")
    public void test_standaloneNames(Locale locale, TemporalField field, TextStyle style, String expected) {
        getFormatter(field, style).withLocale(locale).formatTo(LocalDate.of(2013, 1, 1), buf);
        assertEquals(buf.toString(), expected);
    }

    //-----------------------------------------------------------------------
    public void test_print_french_long() throws Exception {
        getFormatter(MONTH_OF_YEAR, TextStyle.FULL).withLocale(Locale.FRENCH).formatTo(LocalDate.of(2012, 1, 1), buf);
        assertEquals(buf.toString(), "janvier");
    }

    public void test_print_french_short() throws Exception {
        getFormatter(MONTH_OF_YEAR, TextStyle.SHORT).withLocale(Locale.FRENCH).formatTo(LocalDate.of(2012, 1, 1), buf);
        assertEquals(buf.toString(), "janv.");
    }

    @DataProvider(name="print_JapaneseChronology")
    Object[][] provider_japaneseEra() {
       return new Object[][] {
            {ERA,           TextStyle.FULL, 2, "Heisei"}, // Note: CLDR doesn't define "wide" Japanese era names.
            {ERA,           TextStyle.SHORT, 2, "Heisei"},
            {ERA,           TextStyle.NARROW, 2, "H"},
       };
    };

    @Test(dataProvider="print_JapaneseChronology")
    public void test_formatJapaneseEra(TemporalField field, TextStyle style, int value, String expected) throws Exception {
        LocalDate ld = LocalDate.of(2013, 1, 31);
        getFormatter(field, style).withChronology(JapaneseChronology.INSTANCE).formatTo(ld, buf);
        assertEquals(buf.toString(), expected);
    }
}
