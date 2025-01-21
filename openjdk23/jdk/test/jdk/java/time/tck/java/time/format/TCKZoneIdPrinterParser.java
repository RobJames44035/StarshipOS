/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.format;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.text.ParsePosition;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Locale;
import java.util.Objects;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test DateTimeFormatterBuilder.appendZoneId().
 */
@Test
public class TCKZoneIdPrinterParser {

    private static final ZoneOffset OFFSET_UTC = ZoneOffset.UTC;
    private static final ZoneOffset OFFSET_P0123 = ZoneOffset.ofHoursMinutes(1, 23);
    private static final ZoneId EUROPE_PARIS = ZoneId.of("Europe/Paris");
    private static final ZoneId AMERICA_NEW_YORK = ZoneId.of("America/New_York");
    private static final LocalDateTime DT_2012_06_30_12_30_40 = LocalDateTime.of(2012, 6, 30, 12, 30, 40);

    private DateTimeFormatterBuilder builder;
    private ParsePosition pos;

    @BeforeMethod
    public void setUp() {
        builder = new DateTimeFormatterBuilder();
        pos = new ParsePosition(0);
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="print")
    Object[][] data_print() {
        return new Object[][] {
                {DT_2012_06_30_12_30_40, EUROPE_PARIS, "Europe/Paris"},
                {DT_2012_06_30_12_30_40, AMERICA_NEW_YORK, "America/New_York"},
                {DT_2012_06_30_12_30_40, OFFSET_UTC, "Z"},
                {DT_2012_06_30_12_30_40, OFFSET_P0123, "+01:23"},
        };
    }

    @Test(dataProvider="print")
    public void test_print(LocalDateTime ldt, ZoneId zone, String expected) {
        ZonedDateTime zdt = ldt.atZone(zone);
        builder.appendZoneId();
        String output = builder.toFormatter().format(zdt);
        assertEquals(output, expected);
    }

    @Test(dataProvider="print")
    public void test_print_pattern_VV(LocalDateTime ldt, ZoneId zone, String expected) {
        ZonedDateTime zdt = ldt.atZone(zone);
        builder.appendPattern("VV");
        String output = builder.toFormatter().format(zdt);
        assertEquals(output, expected);
    }

    //-----------------------------------------------------------------------
    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_print_pattern_V1rejected() {
        builder.appendPattern("V");
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_print_pattern_V3rejected() {
        builder.appendPattern("VVV");
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_print_pattern_V4rejected() {
        builder.appendPattern("VVVV");
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_print_pattern_V5rejected() {
        builder.appendPattern("VVVVV");
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="parseSuccess")
    Object[][] data_parseSuccess() {
        return new Object[][] {
                {"Z", 1, -1, ZoneId.of("Z"), true},
                {"UTC", 3, -1, ZoneId.of("UTC"), false},
                {"UT", 2, -1, ZoneId.of("UT"), false},
                {"GMT", 3, -1, ZoneId.of("GMT"), false},
                {"GMT0", 4, -1, ZoneId.of("GMT0"), false},

                {"+00:00", 6, -1, ZoneOffset.UTC, true},
                {"UTC+00:00", 9, -1, ZoneId.of("UTC"), false},
                {"UT+00:00", 8, -1, ZoneId.of("UT"), false},
                {"GMT+00:00", 9, -1, ZoneId.of("GMT"), false},
                {"-00:00", 6, -1, ZoneOffset.UTC, true},
                {"UTC-00:00", 9, -1, ZoneId.of("UTC"), false},
                {"UT-00:00", 8, -1, ZoneId.of("UT"), false},
                {"GMT-00:00", 9, -1, ZoneId.of("GMT"), false},

                {"+01:30", 6, -1, ZoneOffset.ofHoursMinutes(1, 30), true},
                {"UTC+01:30", 9, -1, ZoneId.of("UTC+01:30"), false},
                {"UT+02:30", 8, -1, ZoneId.of("UT+02:30"), false},
                {"GMT+03:30", 9, -1, ZoneId.of("GMT+03:30"), false},
                {"-01:30", 6, -1, ZoneOffset.ofHoursMinutes(-1, -30), true},
                {"UTC-01:30", 9, -1, ZoneId.of("UTC-01:30"), false},
                {"UT-02:30", 8, -1, ZoneId.of("UT-02:30"), false},
                {"GMT-03:30", 9, -1, ZoneId.of("GMT-03:30"), false},

                // fallback to UTC
                {"UTC-01:WW", 3, -1, ZoneId.of("UTC"), false},
                {"UT-02:WW", 2, -1, ZoneId.of("UT"), false},
                {"GMT-03:WW", 3, -1, ZoneId.of("GMT"), false},
                {"Z0", 1, -1, ZoneOffset.UTC, true},
                {"UTC1", 3, -1, ZoneId.of("UTC"), false},

                // Z not parsed as zero
                {"UTCZ", 3, -1, ZoneId.of("UTC"), false},
                {"UTZ", 2, -1, ZoneId.of("UT"), false},
                {"GMTZ", 3, -1, ZoneId.of("GMT"), false},

                // 0 not parsed
                {"UTC0", 3, -1, ZoneId.of("UTC"), false},
                {"UT0", 2, -1, ZoneId.of("UT"), false},

                // fail to parse
                {"", 0, 0, null, false},
                {"A", 0, 0, null, false},
                {"UZ", 0, 0, null, false},
                {"GMA", 0, 0, null, false},
                {"0", 0, 0, null, false},
                {"+", 0, 0, null, false},
                {"-", 0, 0, null, false},

                // zone IDs
                {"Europe/London", 13, -1, ZoneId.of("Europe/London"), false},
                {"America/New_York", 16, -1, ZoneId.of("America/New_York"), false},
                {"America/Bogusville", 0, 0, null, false},
        };
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneId_parseSuccess_plain(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.appendZoneId();
        test(text, expectedIndex, expectedErrorIndex, expected, isZoneOffset);
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneId_parseSuccess_prefix(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.appendZoneId();
        pos.setIndex(3);
        test("XXX" + text,
             expectedIndex + 3,
             expectedErrorIndex >= 0 ? expectedErrorIndex + 3 : expectedErrorIndex,
             expected, isZoneOffset);
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneId_parseSuccess_suffix(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.appendZoneId();
        test(text + "XXX", expectedIndex, expectedErrorIndex, expected, isZoneOffset);
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneId_parseSuccess_caseSensitive(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.parseCaseSensitive().appendZoneId();

        if (text.matches("[^A-Z]*[A-Z].*")) {  // if input has letters
            String lcText = text.toLowerCase(Locale.ENGLISH);
            TemporalAccessor parsed = builder.toFormatter().parseUnresolved(lcText, pos);
            assertEquals(pos.getErrorIndex() >= 0, true);
            assertEquals(pos.getIndex(), 0);
            assertEquals(parsed, null);
        } else {
            test(text.toLowerCase(Locale.ENGLISH), expectedIndex, expectedErrorIndex, expected, isZoneOffset);
        }
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneId_parseSuccess_caseInsensitive(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.parseCaseInsensitive().appendZoneId();
        test(text.toLowerCase(Locale.ENGLISH), expectedIndex, expectedErrorIndex, expected, isZoneOffset);
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneOrOffsetId_parseSuccess_plain(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.appendZoneOrOffsetId();
        test(text, expectedIndex, expectedErrorIndex, expected, isZoneOffset);
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneOrOffsetId_parseSuccess_prefix(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.appendZoneOrOffsetId();
        pos.setIndex(3);
        test("XXX" + text,
             expectedIndex + 3,
             expectedErrorIndex >= 0 ? expectedErrorIndex + 3 : expectedErrorIndex,
             expected, isZoneOffset);
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneOrOffsetId_parseSuccess_suffix(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.appendZoneOrOffsetId();
        test(text + "XXX", expectedIndex, expectedErrorIndex, expected, isZoneOffset);
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneOrOffsetId_parseSuccess_caseSensitive(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.parseCaseSensitive().appendZoneOrOffsetId();
        if (text.matches("[^A-Z]*[A-Z].*")) {  // if input has letters
            String lcText = text.toLowerCase(Locale.ENGLISH);
            TemporalAccessor parsed = builder.toFormatter().parseUnresolved(lcText, pos);
            assertEquals(pos.getErrorIndex() >= 0, true);
            assertEquals(pos.getIndex(), 0);
            assertEquals(parsed, null);
        } else {
            test(text.toLowerCase(Locale.ENGLISH), expectedIndex, expectedErrorIndex, expected, isZoneOffset);
        }
    }

    @Test(dataProvider="parseSuccess")
    public void test_ZoneOrOffsetIdparseSuccess_caseInsensitive(
        String text, int expectedIndex, int expectedErrorIndex, ZoneId expected, boolean isZoneOffset)
    {
        builder.parseCaseInsensitive().appendZoneOrOffsetId();
        test(text.toLowerCase(Locale.ENGLISH), expectedIndex, expectedErrorIndex, expected, isZoneOffset);
    }

    private void test(String text, int expectedIndex, int expectedErrorIndex, ZoneId expected,
                      boolean isZoneOffset) {
        TemporalAccessor parsed = builder.toFormatter().parseUnresolved(text, pos);
        assertEquals(pos.getErrorIndex(), expectedErrorIndex, "Incorrect error index parsing: " + text);
        assertEquals(pos.getIndex(), expectedIndex, "Incorrect index parsing: " + text);
        if (expected != null) {
            assertEquals(parsed.query(TemporalQueries.zoneId()),
                         expected,
                         "Incorrect zoneId parsing: " + text);
            assertEquals(parsed.query(TemporalQueries.offset()),
                         isZoneOffset ? expected : null,
                         "Incorrect offset parsing: " + text);
            assertEquals(parsed.query(TemporalQueries.zone()),
                         expected,
                         "Incorrect zone parsing: " + text);
        } else {
            assertEquals(parsed, null);
        }
    }

}
