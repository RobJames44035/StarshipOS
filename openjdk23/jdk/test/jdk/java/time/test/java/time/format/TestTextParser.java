/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.format;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParsePosition;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test TextPrinterParser.
 */
@Test
public class TestTextParser extends AbstractTestPrinterParser {

    //-----------------------------------------------------------------------
    @DataProvider(name="error")
    Object[][] data_error() {
        return new Object[][] {
            {DAY_OF_WEEK, TextStyle.FULL, "Monday", -1, IndexOutOfBoundsException.class},
            {DAY_OF_WEEK, TextStyle.FULL, "Monday", 7, IndexOutOfBoundsException.class},
        };
    }

    @Test(dataProvider="error")
    public void test_parse_error(TemporalField field, TextStyle style, String text, int pos, Class<?> expected) {
        try {
            getFormatter(field, style).parseUnresolved(text, new ParsePosition(pos));
        } catch (RuntimeException ex) {
            assertTrue(expected.isInstance(ex));
        }
    }

    //-----------------------------------------------------------------------
    public void test_parse_midStr() throws Exception {
        ParsePosition pos = new ParsePosition(3);
        assertEquals(getFormatter(DAY_OF_WEEK, TextStyle.FULL)
                     .parseUnresolved("XxxMondayXxx", pos)
                     .getLong(DAY_OF_WEEK), 1L);
        assertEquals(pos.getIndex(), 9);
    }

    public void test_parse_remainderIgnored() throws Exception {
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(DAY_OF_WEEK, TextStyle.SHORT)
                     .parseUnresolved("Wednesday", pos)
                     .getLong(DAY_OF_WEEK), 3L);
        assertEquals(pos.getIndex(), 3);
    }

    //-----------------------------------------------------------------------
    public void test_parse_noMatch1() throws Exception {
        ParsePosition pos = new ParsePosition(0);
        TemporalAccessor parsed =
            getFormatter(DAY_OF_WEEK, TextStyle.FULL).parseUnresolved("Munday", pos);
        assertEquals(pos.getErrorIndex(), 0);
        assertEquals(parsed, null);
    }

    public void test_parse_noMatch2() throws Exception {
        ParsePosition pos = new ParsePosition(3);
        TemporalAccessor parsed =
            getFormatter(DAY_OF_WEEK, TextStyle.FULL).parseUnresolved("Monday", pos);
        assertEquals(pos.getErrorIndex(), 3);
        assertEquals(parsed, null);
    }

    public void test_parse_noMatch_atEnd() throws Exception {
        ParsePosition pos = new ParsePosition(6);
        TemporalAccessor parsed =
            getFormatter(DAY_OF_WEEK, TextStyle.FULL).parseUnresolved("Monday", pos);
        assertEquals(pos.getErrorIndex(), 6);
        assertEquals(parsed, null);
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="parseText")
    Object[][] provider_text() {
        return new Object[][] {
            {DAY_OF_WEEK, TextStyle.FULL, 1, "Monday"},
            {DAY_OF_WEEK, TextStyle.FULL, 2, "Tuesday"},
            {DAY_OF_WEEK, TextStyle.FULL, 3, "Wednesday"},
            {DAY_OF_WEEK, TextStyle.FULL, 4, "Thursday"},
            {DAY_OF_WEEK, TextStyle.FULL, 5, "Friday"},
            {DAY_OF_WEEK, TextStyle.FULL, 6, "Saturday"},
            {DAY_OF_WEEK, TextStyle.FULL, 7, "Sunday"},

            {DAY_OF_WEEK, TextStyle.SHORT, 1, "Mon"},
            {DAY_OF_WEEK, TextStyle.SHORT, 2, "Tue"},
            {DAY_OF_WEEK, TextStyle.SHORT, 3, "Wed"},
            {DAY_OF_WEEK, TextStyle.SHORT, 4, "Thu"},
            {DAY_OF_WEEK, TextStyle.SHORT, 5, "Fri"},
            {DAY_OF_WEEK, TextStyle.SHORT, 6, "Sat"},
            {DAY_OF_WEEK, TextStyle.SHORT, 7, "Sun"},

            {MONTH_OF_YEAR, TextStyle.FULL, 1, "January"},
            {MONTH_OF_YEAR, TextStyle.FULL, 12, "December"},

            {MONTH_OF_YEAR, TextStyle.SHORT, 1, "Jan"},
            {MONTH_OF_YEAR, TextStyle.SHORT, 12, "Dec"},

            {QUARTER_OF_YEAR, TextStyle.FULL, 1, "1st quarter"},
            {QUARTER_OF_YEAR, TextStyle.FULL, 2, "2nd quarter"},
            {QUARTER_OF_YEAR, TextStyle.FULL, 3, "3rd quarter"},
            {QUARTER_OF_YEAR, TextStyle.FULL, 4, "4th quarter"},

            {QUARTER_OF_YEAR, TextStyle.SHORT, 1, "Q1"},
            {QUARTER_OF_YEAR, TextStyle.SHORT, 2, "Q2"},
            {QUARTER_OF_YEAR, TextStyle.SHORT, 3, "Q3"},
            {QUARTER_OF_YEAR, TextStyle.SHORT, 4, "Q4"},

            {QUARTER_OF_YEAR, TextStyle.NARROW, 1, "1"},
            {QUARTER_OF_YEAR, TextStyle.NARROW, 2, "2"},
            {QUARTER_OF_YEAR, TextStyle.NARROW, 3, "3"},
            {QUARTER_OF_YEAR, TextStyle.NARROW, 4, "4"},
       };
    }

    @DataProvider(name="parseNumber")
    Object[][] provider_number() {
        return new Object[][] {
            {DAY_OF_MONTH, TextStyle.FULL, 1, "1"},
            {DAY_OF_MONTH, TextStyle.FULL, 2, "2"},
            {DAY_OF_MONTH, TextStyle.FULL, 30, "30"},
            {DAY_OF_MONTH, TextStyle.FULL, 31, "31"},

            {DAY_OF_MONTH, TextStyle.SHORT, 1, "1"},
            {DAY_OF_MONTH, TextStyle.SHORT, 2, "2"},
            {DAY_OF_MONTH, TextStyle.SHORT, 30, "30"},
            {DAY_OF_MONTH, TextStyle.SHORT, 31, "31"},
       };
    }

    @DataProvider(name="parseDayOfWeekText")
    Object[][] providerDayOfWeekData() {
        return new Object[][] {
            // Locale, pattern, input text, expected DayOfWeek
            {Locale.US, "e",  "1",  DayOfWeek.SUNDAY},
            {Locale.US, "ee", "01", DayOfWeek.SUNDAY},
            {Locale.US, "c",  "1",  DayOfWeek.SUNDAY},
        };
    }


    @Test(dataProvider="parseText")
    public void test_parseText(TemporalField field, TextStyle style, int value, String input) throws Exception {
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(field, style).parseUnresolved(input, pos).getLong(field), (long) value);
        assertEquals(pos.getIndex(), input.length());
    }

    @Test(dataProvider="parseNumber")
    public void test_parseNumber(TemporalField field, TextStyle style, int value, String input) throws Exception {
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(field, style).parseUnresolved(input, pos).getLong(field), (long) value);
        assertEquals(pos.getIndex(), input.length());
    }

    @Test(dataProvider="parseDayOfWeekText")
    public void test_parseDayOfWeekText(Locale locale, String pattern, String input, DayOfWeek expected) {
        DateTimeFormatter formatter = getPatternFormatter(pattern).withLocale(locale);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(DayOfWeek.from(formatter.parse(input, pos)), expected);
        assertEquals(pos.getIndex(), input.length());
    }

    //-----------------------------------------------------------------------
    @Test(dataProvider="parseText")
    public void test_parse_strict_caseSensitive_parseUpper(TemporalField field, TextStyle style, int value, String input) throws Exception {
        if (input.equals(input.toUpperCase(Locale.ROOT))) {
            // Skip if the given input is all upper case (e.g., "Q1")
            return;
        }
        setCaseSensitive(true);
        ParsePosition pos = new ParsePosition(0);
        getFormatter(field, style).parseUnresolved(input.toUpperCase(Locale.ROOT), pos);
        assertEquals(pos.getErrorIndex(), 0);
    }

    @Test(dataProvider="parseText")
    public void test_parse_strict_caseInsensitive_parseUpper(TemporalField field, TextStyle style, int value, String input) throws Exception {
        setCaseSensitive(false);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(field, style).parseUnresolved(input.toUpperCase(Locale.ROOT), pos).getLong(field), (long) value);
        assertEquals(pos.getIndex(), input.length());
    }

    //-----------------------------------------------------------------------
    @Test(dataProvider="parseText")
    public void test_parse_strict_caseSensitive_parseLower(TemporalField field, TextStyle style, int value, String input) throws Exception {
        if (input.equals(input.toLowerCase(Locale.ROOT))) {
            // Skip if the given input is all lower case (e.g., "1st quarter")
            return;
        }
        setCaseSensitive(true);
        ParsePosition pos = new ParsePosition(0);
        getFormatter(field, style).parseUnresolved(input.toLowerCase(Locale.ROOT), pos);
        assertEquals(pos.getErrorIndex(), 0);
    }

    @Test(dataProvider="parseText")
    public void test_parse_strict_caseInsensitive_parseLower(TemporalField field, TextStyle style, int value, String input) throws Exception {
        setCaseSensitive(false);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(field, style).parseUnresolved(input.toLowerCase(Locale.ROOT), pos).getLong(field), (long) value);
        assertEquals(pos.getIndex(), input.length());
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void test_parse_full_strict_full_match() throws Exception {
        setStrict(true);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.FULL).parseUnresolved("January", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 7);
    }

    public void test_parse_full_strict_short_noMatch() throws Exception {
        setStrict(true);
        ParsePosition pos = new ParsePosition(0);
        getFormatter(MONTH_OF_YEAR, TextStyle.FULL).parseUnresolved("Janua", pos);
        assertEquals(pos.getErrorIndex(), 0);
    }

    public void test_parse_full_strict_number_noMatch() throws Exception {
        setStrict(true);
        ParsePosition pos = new ParsePosition(0);
        getFormatter(MONTH_OF_YEAR, TextStyle.FULL).parseUnresolved("1", pos);
        assertEquals(pos.getErrorIndex(), 0);
    }

    //-----------------------------------------------------------------------
    public void test_parse_short_strict_full_match() throws Exception {
        setStrict(true);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.SHORT).parseUnresolved("January", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 3);
    }

    public void test_parse_short_strict_short_match() throws Exception {
        setStrict(true);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.SHORT).parseUnresolved("Janua", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 3);
    }

    public void test_parse_short_strict_number_noMatch() throws Exception {
        setStrict(true);
        ParsePosition pos = new ParsePosition(0);
        getFormatter(MONTH_OF_YEAR, TextStyle.SHORT).parseUnresolved("1", pos);
        assertEquals(pos.getErrorIndex(), 0);
    }

    //-----------------------------------------------------------------------
    public void test_parse_full_lenient_full_match() throws Exception {
        setStrict(false);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.FULL).parseUnresolved("January.", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 7);
    }

    public void test_parse_full_lenient_short_match() throws Exception {
        setStrict(false);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.FULL).parseUnresolved("Janua", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 3);
    }

    public void test_parse_full_lenient_number_match() throws Exception {
        setStrict(false);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.FULL).parseUnresolved("1", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 1);
    }

    //-----------------------------------------------------------------------
    public void test_parse_short_lenient_full_match() throws Exception {
        setStrict(false);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.SHORT).parseUnresolved("January", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 7);
    }

    public void test_parse_short_lenient_short_match() throws Exception {
        setStrict(false);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.SHORT).parseUnresolved("Janua", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 3);
    }

    public void test_parse_short_lenient_number_match() throws Exception {
        setStrict(false);
        ParsePosition pos = new ParsePosition(0);
        assertEquals(getFormatter(MONTH_OF_YEAR, TextStyle.SHORT).parseUnresolved("1", pos).getLong(MONTH_OF_YEAR), 1L);
        assertEquals(pos.getIndex(), 1);
    }

}
