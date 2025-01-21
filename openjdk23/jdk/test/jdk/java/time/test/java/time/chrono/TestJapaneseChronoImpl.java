/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.chrono;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;
import java.time.chrono.JapaneseDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test
public class TestJapaneseChronoImpl {

    /**
     * Range of years to check consistency with java.util.Calendar
     */
    @DataProvider(name="RangeVersusCalendar")
    Object[][] provider_rangeVersusCalendar() {
        return new Object[][] {
            {LocalDate.of(1873, 1, 1), LocalDate.of(2100, 1, 1)},
        };
    }

    //-----------------------------------------------------------------------
    // Verify  Japanese Calendar matches java.util.Calendar for range
    //-----------------------------------------------------------------------
    @Test(dataProvider="RangeVersusCalendar")
    public void test_JapaneseChrono_vsCalendar(LocalDate isoStartDate, LocalDate isoEndDate) {
        Locale locale = Locale.forLanguageTag("ja-JP-u-ca-japanese");
        assertEquals(locale.toString(), "ja_JP_#u-ca-japanese", "Unexpected locale");

        Calendar cal = java.util.Calendar.getInstance(locale);
        assertEquals(cal.getCalendarType(), "japanese", "Unexpected calendar type");

        JapaneseDate jDate = JapaneseChronology.INSTANCE.date(isoStartDate);

        // Convert to millis and set Japanese Calendar to that start date (at GMT)
        OffsetDateTime jodt = OffsetDateTime.of(isoStartDate, LocalTime.MIN, ZoneOffset.UTC);
        long millis = jodt.toInstant().toEpochMilli();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+00"));
        cal.setTimeInMillis(millis);

        while (jDate.isBefore(isoEndDate)) {
            assertEquals(jDate.get(ChronoField.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH), "Day mismatch in " + jDate + ";  cal: " + cal);
            assertEquals(jDate.get(ChronoField.MONTH_OF_YEAR), cal.get(Calendar.MONTH) + 1, "Month mismatch in " + jDate);
            assertEquals(jDate.get(ChronoField.YEAR_OF_ERA), cal.get(Calendar.YEAR), "Year mismatch in " + jDate);

            jDate = jDate.plus(1, ChronoUnit.DAYS);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    //-----------------------------------------------------------------------
    // Verify  Japanese Calendar matches java.util.Calendar for number of days
    // in years 1 and 2.
    //-----------------------------------------------------------------------
    @Test
    public void test_dayOfYearVsCalendar() {
        Locale locale = Locale.forLanguageTag("ja-JP-u-ca-japanese");
        Calendar cal = java.util.Calendar.getInstance(locale);

        for (JapaneseEra era : JapaneseEra.values()) {
            for (int year : new int[] {6, 7}) {
                JapaneseDate jd = JapaneseChronology.INSTANCE.dateYearDay(era, year, 1);
                OffsetDateTime jodt = OffsetDateTime.of(LocalDate.from(jd), LocalTime.MIN, ZoneOffset.UTC);
                long millis = jodt.toInstant().toEpochMilli();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+00"));
                cal.setTimeInMillis(millis);

                assertEquals(jd.get(ChronoField.DAY_OF_YEAR), cal.get(Calendar.DAY_OF_YEAR),
                        "different DAY_OF_YEAR values in " + era + ", year: " + year);
                assertEquals(jd.range(ChronoField.DAY_OF_YEAR).getMaximum(), cal.getActualMaximum(Calendar.DAY_OF_YEAR),
                        "different maximum for DAY_OF_YEAR in " + era + ", year: " + year);
                assertEquals(jd.range(ChronoField.DAY_OF_YEAR).getMinimum(), cal.getActualMinimum(Calendar.DAY_OF_YEAR),
                        "different minimum for DAY_OF_YEAR in " + era + ",  year: " + year);
            }
        }

    }

}
