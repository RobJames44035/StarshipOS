/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.format;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test localized behavior of formatter.
 */
@Test
public class TCKLocalizedPrinterParser {

    private DateTimeFormatterBuilder builder;
    private ParsePosition pos;

    @BeforeMethod
    public void setUp() {
        builder = new DateTimeFormatterBuilder();
        pos = new ParsePosition(0);
    }

    //-----------------------------------------------------------------------
    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_parse_negativePosition() {
        builder.appendLocalized(null, null);
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="date")
    Object[][] data_date() {
        return new Object[][] {
                {LocalDate.of(2012, 6, 30), FormatStyle.SHORT, DateFormat.SHORT, Locale.UK},
                {LocalDate.of(2012, 6, 30), FormatStyle.SHORT, DateFormat.SHORT, Locale.US},
                {LocalDate.of(2012, 6, 30), FormatStyle.SHORT, DateFormat.SHORT, Locale.FRANCE},
                {LocalDate.of(2012, 6, 30), FormatStyle.SHORT, DateFormat.SHORT, Locale.JAPAN},

                {LocalDate.of(2012, 6, 30), FormatStyle.MEDIUM, DateFormat.MEDIUM, Locale.UK},
                {LocalDate.of(2012, 6, 30), FormatStyle.MEDIUM, DateFormat.MEDIUM, Locale.US},
                {LocalDate.of(2012, 6, 30), FormatStyle.MEDIUM, DateFormat.MEDIUM, Locale.FRANCE},
                {LocalDate.of(2012, 6, 30), FormatStyle.MEDIUM, DateFormat.MEDIUM, Locale.JAPAN},

                {LocalDate.of(2012, 6, 30), FormatStyle.LONG, DateFormat.LONG, Locale.UK},
                {LocalDate.of(2012, 6, 30), FormatStyle.LONG, DateFormat.LONG, Locale.US},
                {LocalDate.of(2012, 6, 30), FormatStyle.LONG, DateFormat.LONG, Locale.FRANCE},
                {LocalDate.of(2012, 6, 30), FormatStyle.LONG, DateFormat.LONG, Locale.JAPAN},

                {LocalDate.of(2012, 6, 30), FormatStyle.FULL, DateFormat.FULL, Locale.UK},
                {LocalDate.of(2012, 6, 30), FormatStyle.FULL, DateFormat.FULL, Locale.US},
                {LocalDate.of(2012, 6, 30), FormatStyle.FULL, DateFormat.FULL, Locale.FRANCE},
                {LocalDate.of(2012, 6, 30), FormatStyle.FULL, DateFormat.FULL, Locale.JAPAN},
        };
    }

    @SuppressWarnings("deprecation")
    @Test(dataProvider="date")
    public void test_date_print(LocalDate date, FormatStyle dateStyle, int dateStyleOld, Locale locale) {
        DateFormat old = DateFormat.getDateInstance(dateStyleOld, locale);
        Date oldDate = new Date(date.getYear() - 1900, date.getMonthValue() - 1, date.getDayOfMonth());
        String text = old.format(oldDate);

        DateTimeFormatter f = builder.appendLocalized(dateStyle, null).toFormatter(locale);
        String formatted = f.format(date);
        assertEquals(formatted, text);
    }

    @SuppressWarnings("deprecation")
    @Test(dataProvider="date")
    public void test_date_parse(LocalDate date, FormatStyle dateStyle, int dateStyleOld, Locale locale) {
        DateFormat old = DateFormat.getDateInstance(dateStyleOld, locale);
        Date oldDate = new Date(date.getYear() - 1900, date.getMonthValue() - 1, date.getDayOfMonth());
        String text = old.format(oldDate);

        DateTimeFormatter f = builder.appendLocalized(dateStyle, null).toFormatter(locale);
        TemporalAccessor parsed = f.parse(text, pos);
        assertEquals(pos.getIndex(), text.length());
        assertEquals(pos.getErrorIndex(), -1);
        assertEquals(LocalDate.from(parsed), date);
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="time")
    Object[][] data_time() {
        return new Object[][] {
                {LocalTime.of(11, 30), FormatStyle.SHORT, DateFormat.SHORT, Locale.UK},
                {LocalTime.of(11, 30), FormatStyle.SHORT, DateFormat.SHORT, Locale.US},
                {LocalTime.of(11, 30), FormatStyle.SHORT, DateFormat.SHORT, Locale.FRANCE},
                {LocalTime.of(11, 30), FormatStyle.SHORT, DateFormat.SHORT, Locale.JAPAN},

                {LocalTime.of(11, 30), FormatStyle.MEDIUM, DateFormat.MEDIUM, Locale.UK},
                {LocalTime.of(11, 30), FormatStyle.MEDIUM, DateFormat.MEDIUM, Locale.US},
                {LocalTime.of(11, 30), FormatStyle.MEDIUM, DateFormat.MEDIUM, Locale.FRANCE},
                {LocalTime.of(11, 30), FormatStyle.MEDIUM, DateFormat.MEDIUM, Locale.JAPAN},

                // these localized patterns include "z" which isn't available from LocalTime
//                {LocalTime.of(11, 30), FormatStyle.LONG, DateFormat.LONG, Locale.UK},
//                {LocalTime.of(11, 30), FormatStyle.LONG, DateFormat.LONG, Locale.US},
//                {LocalTime.of(11, 30), FormatStyle.LONG, DateFormat.LONG, Locale.FRANCE},
//                {LocalTime.of(11, 30), FormatStyle.LONG, DateFormat.LONG, Locale.JAPAN},
//
//                {LocalTime.of(11, 30), FormatStyle.FULL, DateFormat.FULL, Locale.UK},
//                {LocalTime.of(11, 30), FormatStyle.FULL, DateFormat.FULL, Locale.US},
//                {LocalTime.of(11, 30), FormatStyle.FULL, DateFormat.FULL, Locale.FRANCE},
//                {LocalTime.of(11, 30), FormatStyle.FULL, DateFormat.FULL, Locale.JAPAN},
        };
    }

    @SuppressWarnings("deprecation")
    @Test(dataProvider="time")
    public void test_time_print(LocalTime time, FormatStyle timeStyle, int timeStyleOld, Locale locale) {
        DateFormat old = DateFormat.getTimeInstance(timeStyleOld, locale);
        Date oldDate = new Date(1970 - 1900, 0, 0, time.getHour(), time.getMinute(), time.getSecond());
        String text = old.format(oldDate);

        DateTimeFormatter f = builder.appendLocalized(null, timeStyle).toFormatter(locale);
        String formatted = f.format(time);
        assertEquals(formatted, text);
    }

    @SuppressWarnings("deprecation")
    @Test(dataProvider="time")
    public void test_time_parse(LocalTime time, FormatStyle timeStyle, int timeStyleOld, Locale locale) {
        DateFormat old = DateFormat.getTimeInstance(timeStyleOld, locale);
        Date oldDate = new Date(1970 - 1900, 0, 0, time.getHour(), time.getMinute(), time.getSecond());
        String text = old.format(oldDate);

        DateTimeFormatter f = builder.appendLocalized(null, timeStyle).toFormatter(locale);
        TemporalAccessor parsed = f.parse(text, pos);
        assertEquals(pos.getIndex(), text.length());
        assertEquals(pos.getErrorIndex(), -1);
        assertEquals(LocalTime.from(parsed), time);
    }

}
