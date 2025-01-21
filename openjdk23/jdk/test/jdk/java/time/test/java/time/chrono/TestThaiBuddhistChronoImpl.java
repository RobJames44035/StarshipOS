/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.chrono;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test
public class TestThaiBuddhistChronoImpl {

    /**
     * Range of years to check consistency with java.util.Calendar
     */
    @DataProvider(name="RangeVersusCalendar")
    Object[][] provider_rangeVersusCalendar() {
        return new Object[][] {
            {LocalDate.of(1583, 1, 1), LocalDate.of(2100, 1, 1)},
        };
    }

    //-----------------------------------------------------------------------
    // Verify  ThaiBuddhist Calendar matches java.util.Calendar for range
    //-----------------------------------------------------------------------
    @Test(dataProvider="RangeVersusCalendar")
    public void test_ThaiBuddhistChrono_vsCalendar(LocalDate isoStartDate, LocalDate isoEndDate) {
        Locale locale = Locale.forLanguageTag("th-TH--u-ca-buddhist");
        assertEquals(locale.toString(), "th_TH", "Unexpected locale");
        Calendar cal = java.util.Calendar.getInstance(locale);
        assertEquals(cal.getCalendarType(), "buddhist", "Unexpected calendar type");

        ThaiBuddhistDate thaiDate = ThaiBuddhistChronology.INSTANCE.date(isoStartDate);

        cal.setTimeZone(TimeZone.getTimeZone("GMT+00"));
        cal.set(Calendar.YEAR, thaiDate.get(ChronoField.YEAR));
        cal.set(Calendar.MONTH, thaiDate.get(ChronoField.MONTH_OF_YEAR) - 1);
        cal.set(Calendar.DAY_OF_MONTH, thaiDate.get(ChronoField.DAY_OF_MONTH));

        while (thaiDate.isBefore(isoEndDate)) {
            assertEquals(thaiDate.get(ChronoField.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH), "Day mismatch in " + thaiDate + ";  cal: " + cal);
            assertEquals(thaiDate.get(ChronoField.MONTH_OF_YEAR), cal.get(Calendar.MONTH) + 1, "Month mismatch in " + thaiDate);
            assertEquals(thaiDate.get(ChronoField.YEAR_OF_ERA), cal.get(Calendar.YEAR), "Year mismatch in " + thaiDate);

            thaiDate = thaiDate.plus(1, ChronoUnit.DAYS);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private String calToString(Calendar cal) {
        return String.format("%04d-%02d-%02d",
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

}
