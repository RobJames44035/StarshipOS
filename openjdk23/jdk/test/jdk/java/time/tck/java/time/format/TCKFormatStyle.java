/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.format;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.Temporal;
import java.util.Locale;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test
public class TCKFormatStyle {

    private static final ZoneId ZONEID_PARIS = ZoneId.of("Europe/Paris");
    private static final ZoneId OFFSET_PTWO = ZoneOffset.of("+02:00");

    //-----------------------------------------------------------------------
    // valueOf()
    //-----------------------------------------------------------------------
    @Test
    public void test_valueOf() {
        for (FormatStyle style : FormatStyle.values()) {
            assertEquals(FormatStyle.valueOf(style.name()), style);
        }
    }

    @DataProvider(name="formatStyle")
    Object[][] data_formatStyle() {
        return new Object[][] {
                {ZonedDateTime.of(LocalDateTime.of(2001, 10, 2, 1, 2, 3), ZONEID_PARIS), FormatStyle.FULL, "Tuesday, October 2, 2001, 1:02:03\u202fAM Central European Summer Time Europe/Paris"},
                {ZonedDateTime.of(LocalDateTime.of(2001, 10, 2, 1, 2, 3), ZONEID_PARIS), FormatStyle.LONG, "October 2, 2001, 1:02:03\u202fAM CEST Europe/Paris"},
                {ZonedDateTime.of(LocalDateTime.of(2001, 10, 2, 1, 2, 3), ZONEID_PARIS), FormatStyle.MEDIUM, "Oct 2, 2001, 1:02:03\u202fAM Europe/Paris"},
                {ZonedDateTime.of(LocalDateTime.of(2001, 10, 2, 1, 2, 3), ZONEID_PARIS), FormatStyle.SHORT, "10/2/01, 1:02\u202fAM Europe/Paris"},

                {ZonedDateTime.of(LocalDateTime.of(2001, 10, 2, 1, 2, 3), OFFSET_PTWO), FormatStyle.FULL, "Tuesday, October 2, 2001, 1:02:03\u202fAM +02:00 +02:00"},
                {ZonedDateTime.of(LocalDateTime.of(2001, 10, 2, 1, 2, 3), OFFSET_PTWO), FormatStyle.LONG, "October 2, 2001, 1:02:03\u202fAM +02:00 +02:00"},
                {ZonedDateTime.of(LocalDateTime.of(2001, 10, 2, 1, 2, 3), OFFSET_PTWO), FormatStyle.MEDIUM, "Oct 2, 2001, 1:02:03\u202fAM +02:00"},
                {ZonedDateTime.of(LocalDateTime.of(2001, 10, 2, 1, 2, 3), OFFSET_PTWO), FormatStyle.SHORT, "10/2/01, 1:02\u202fAM +02:00"},
        };
    }

    @Test(dataProvider = "formatStyle")
    public void test_formatStyle(Temporal temporal, FormatStyle style, String formattedStr) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = builder.appendLocalized(style, style).appendLiteral(" ").appendZoneOrOffsetId().toFormatter();
        formatter = formatter.withLocale(Locale.US);
        assertEquals(formatter.format(temporal), formattedStr);
    }
}
