/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.testng.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test
public class TestIsoChronoImpl {

    @DataProvider(name = "RangeVersusCalendar")
    Object[][] provider_rangeVersusCalendar() {
        return new Object[][]{
            {LocalDate.of(1583, 1, 1), LocalDate.of(2100, 1, 1)},
        };
    }

    //-----------------------------------------------------------------------
    // Verify  ISO Calendar matches java.util.Calendar for range
    //-----------------------------------------------------------------------
    @Test(dataProvider = "RangeVersusCalendar")
    public void test_IsoChrono_vsCalendar(LocalDate isoStartDate, LocalDate isoEndDate) {
        GregorianCalendar cal = new GregorianCalendar();
        assertEquals(cal.getCalendarType(), "gregory", "Unexpected calendar type");
        LocalDate isoDate = IsoChronology.INSTANCE.date(isoStartDate);

        cal.setTimeZone(TimeZone.getTimeZone("GMT+00"));
        cal.set(Calendar.YEAR, isoDate.get(YEAR));
        cal.set(Calendar.MONTH, isoDate.get(MONTH_OF_YEAR) - 1);
        cal.set(Calendar.DAY_OF_MONTH, isoDate.get(DAY_OF_MONTH));

        while (isoDate.isBefore(isoEndDate)) {
            assertEquals(isoDate.get(DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH), "Day mismatch in " + isoDate + ";  cal: " + cal);
            assertEquals(isoDate.get(MONTH_OF_YEAR), cal.get(Calendar.MONTH) + 1, "Month mismatch in " + isoDate);
            assertEquals(isoDate.get(YEAR_OF_ERA), cal.get(Calendar.YEAR), "Year mismatch in " + isoDate);

            isoDate = isoDate.plus(1, ChronoUnit.DAYS);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    //-----------------------------------------------------------------------
    // Verify  ISO Calendar matches java.util.Calendar
    // DayOfWeek, WeekOfMonth, WeekOfYear for range
    //-----------------------------------------------------------------------
    @Test(dataProvider = "RangeVersusCalendar")
    public void test_DayOfWeek_IsoChronology_vsCalendar(LocalDate isoStartDate, LocalDate isoEndDate) {
        GregorianCalendar cal = new GregorianCalendar();
        assertEquals(cal.getCalendarType(), "gregory", "Unexpected calendar type");
        LocalDate isoDate = IsoChronology.INSTANCE.date(isoStartDate);

        for (DayOfWeek firstDayOfWeek : DayOfWeek.values()) {
            for (int minDays = 1; minDays <= 7; minDays++) {
                WeekFields weekDef = WeekFields.of(firstDayOfWeek, minDays);
                cal.setFirstDayOfWeek(Math.floorMod(firstDayOfWeek.getValue(), 7) + 1);
                cal.setMinimalDaysInFirstWeek(minDays);

                cal.setTimeZone(TimeZone.getTimeZone("GMT+00"));
                cal.set(Calendar.YEAR, isoDate.get(YEAR));
                cal.set(Calendar.MONTH, isoDate.get(MONTH_OF_YEAR) - 1);
                cal.set(Calendar.DAY_OF_MONTH, isoDate.get(DAY_OF_MONTH));

                // For every date in the range
                while (isoDate.isBefore(isoEndDate)) {
                    assertEquals(isoDate.get(DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH), "Day mismatch in " + isoDate + ";  cal: " + cal);
                    assertEquals(isoDate.get(MONTH_OF_YEAR), cal.get(Calendar.MONTH) + 1, "Month mismatch in " + isoDate);
                    assertEquals(isoDate.get(YEAR_OF_ERA), cal.get(Calendar.YEAR), "Year mismatch in " + isoDate);

                    int jdow = Math.floorMod(cal.get(Calendar.DAY_OF_WEEK) - 2, 7) + 1;
                    int dow = isoDate.get(weekDef.dayOfWeek());
                    assertEquals(jdow, dow, "Calendar DayOfWeek does not match ISO DayOfWeek");

                    int jweekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
                    int isoWeekOfMonth = isoDate.get(weekDef.weekOfMonth());
                    assertEquals(jweekOfMonth, isoWeekOfMonth, "Calendar WeekOfMonth does not match ISO WeekOfMonth");

                    int jweekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
                    int weekOfYear = isoDate.get(weekDef.weekOfWeekBasedYear());
                    assertEquals(jweekOfYear, weekOfYear,  "GregorianCalendar WeekOfYear does not match WeekOfWeekBasedYear");

                    int jWeekYear = cal.getWeekYear();
                    int weekBasedYear = isoDate.get(weekDef.weekBasedYear());
                    assertEquals(jWeekYear, weekBasedYear,  "GregorianCalendar getWeekYear does not match YearOfWeekBasedYear");

                    int jweeksInWeekyear = cal.getWeeksInWeekYear();
                    int weeksInWeekBasedYear = (int)isoDate.range(weekDef.weekOfWeekBasedYear()).getMaximum();
                    assertEquals(jweeksInWeekyear, weeksInWeekBasedYear, "length of weekBasedYear");

                    isoDate = isoDate.plus(1, ChronoUnit.DAYS);
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }
    }

    /**
     * Return the ISO Day of Week from a java.util.Calendr DAY_OF_WEEK.
     * @param the java.util.Calendar day of week (1=Sunday, 7=Saturday)
     * @return the ISO DayOfWeek
     */
    private DayOfWeek toISOfromCalendarDOW(int i) {
        return DayOfWeek.of(Math.floorMod(i - 2, 7) + 1);
    }
}
