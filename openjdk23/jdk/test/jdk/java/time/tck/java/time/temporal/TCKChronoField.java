/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.temporal;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_DAY;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.MICRO_OF_DAY;
import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.MILLI_OF_DAY;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.SECOND_OF_DAY;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.FOREVER;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MICROS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test
public class TCKChronoField {

    //-----------------------------------------------------------------------
    // getBaseUnit() and getRangeUnit()
    //-----------------------------------------------------------------------
    @DataProvider(name="fieldUnit")
    Object[][] data_fieldUnit() {
        return new Object[][] {
                {YEAR, YEARS, FOREVER},
                {MONTH_OF_YEAR, MONTHS, YEARS},
                {DAY_OF_MONTH, DAYS, MONTHS},
                {DAY_OF_WEEK, DAYS, WEEKS},
                {DAY_OF_YEAR, DAYS, YEARS},
                {HOUR_OF_DAY, HOURS, DAYS},
                {MINUTE_OF_DAY, MINUTES, DAYS},
                {MINUTE_OF_HOUR, MINUTES, HOURS},
                {SECOND_OF_DAY, SECONDS, DAYS},
                {SECOND_OF_MINUTE, SECONDS, MINUTES},
                {MILLI_OF_DAY, MILLIS, DAYS},
                {MILLI_OF_SECOND, MILLIS, SECONDS},
                {MICRO_OF_SECOND, MICROS, SECONDS},
                {MICRO_OF_DAY, MICROS, DAYS},
                {NANO_OF_SECOND, NANOS, SECONDS},
                {NANO_OF_DAY, NANOS, DAYS},

        };
    }

    @Test(dataProvider = "fieldUnit")
    public void test_getBaseUnit(ChronoField field, ChronoUnit baseUnit, ChronoUnit rangeUnit) {
        assertEquals(field.getBaseUnit(), baseUnit);
        assertEquals(field.getRangeUnit(), rangeUnit);
    }

    //-----------------------------------------------------------------------
    // isDateBased() and isTimeBased()
    //-----------------------------------------------------------------------
    @DataProvider(name="fieldBased")
    Object[][] data_fieldBased() {
        return new Object[][] {
                {DAY_OF_WEEK, true, false},
                {ALIGNED_DAY_OF_WEEK_IN_MONTH, true, false},
                {ALIGNED_DAY_OF_WEEK_IN_YEAR, true, false},
                {DAY_OF_MONTH, true, false},
                {DAY_OF_YEAR, true, false},
                {EPOCH_DAY, true, false},
                {ALIGNED_WEEK_OF_MONTH, true, false},
                {ALIGNED_WEEK_OF_YEAR, true, false},
                {MONTH_OF_YEAR, true, false},
                {PROLEPTIC_MONTH, true, false},
                {YEAR_OF_ERA, true, false},
                {YEAR, true, false},
                {ERA, true, false},

                {AMPM_OF_DAY, false, true},
                {CLOCK_HOUR_OF_DAY, false, true},
                {HOUR_OF_DAY, false, true},
                {CLOCK_HOUR_OF_AMPM, false, true},
                {HOUR_OF_AMPM, false, true},
                {MINUTE_OF_DAY, false, true},
                {MINUTE_OF_HOUR, false, true},
                {SECOND_OF_DAY, false, true},
                {SECOND_OF_MINUTE, false, true},
                {MILLI_OF_DAY, false, true},
                {MILLI_OF_SECOND, false, true},
                {MICRO_OF_DAY, false, true},
                {MICRO_OF_SECOND, false, true},
                {NANO_OF_DAY, false, true},
                {NANO_OF_SECOND, false, true},
        };
    }

    @Test(dataProvider = "fieldBased")
    public void test_isDateBased(ChronoField field, boolean isDateBased, boolean isTimeBased) {
        assertEquals(field.isDateBased(), isDateBased);
        assertEquals(field.isTimeBased(), isTimeBased);
    }

    //-----------------------------------------------------------------------
    // isSupportedBy(TemporalAccessor temporal) and getFrom(TemporalAccessor temporal)
    //-----------------------------------------------------------------------
    @DataProvider(name="fieldAndAccessor")
    Object[][] data_fieldAndAccessor() {
        return new Object[][] {
                {YEAR, LocalDate.of(2000, 2, 29), true, 2000},
                {YEAR, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 2000},
                {MONTH_OF_YEAR, LocalDate.of(2000, 2, 29), true, 2},
                {MONTH_OF_YEAR, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 2},
                {DAY_OF_MONTH, LocalDate.of(2000, 2, 29), true, 29},
                {DAY_OF_MONTH, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 29},
                {DAY_OF_YEAR, LocalDate.of(2000, 2, 29), true, 60},
                {DAY_OF_YEAR, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 60},

                {HOUR_OF_DAY, LocalTime.of(5, 4, 3, 200), true, 5},
                {HOUR_OF_DAY, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 5},

                {MINUTE_OF_DAY, LocalTime.of(5, 4, 3, 200), true, 5*60 + 4},
                {MINUTE_OF_DAY, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 5*60 + 4},
                {MINUTE_OF_HOUR, LocalTime.of(5, 4, 3, 200), true, 4},
                {MINUTE_OF_HOUR, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 4},

                {SECOND_OF_DAY, LocalTime.of(5, 4, 3, 200), true, 5*3600 + 4*60 + 3},
                {SECOND_OF_DAY, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 5*3600 + 4*60 + 3},
                {SECOND_OF_MINUTE, LocalTime.of(5, 4, 3, 200), true, 3},
                {SECOND_OF_MINUTE, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 3},

                {NANO_OF_SECOND, LocalTime.of(5, 4, 3, 200), true, 200},
                {NANO_OF_SECOND, LocalDateTime.of(2000, 2, 29, 5, 4, 3, 200), true, 200},

                {YEAR, LocalTime.of(5, 4, 3, 200), false, -1},
                {MONTH_OF_YEAR, LocalTime.of(5, 4, 3, 200), false, -1},
                {DAY_OF_MONTH, LocalTime.of(5, 4, 3, 200), false, -1},
                {DAY_OF_YEAR, LocalTime.of(5, 4, 3, 200), false, -1},
                {HOUR_OF_DAY, LocalDate.of(2000, 2, 29), false, -1},
                {MINUTE_OF_DAY, LocalDate.of(2000, 2, 29), false, -1},
                {MINUTE_OF_HOUR, LocalDate.of(2000, 2, 29), false, -1},
                {SECOND_OF_DAY, LocalDate.of(2000, 2, 29), false, -1},
                {SECOND_OF_MINUTE, LocalDate.of(2000, 2, 29), false, -1},
                {NANO_OF_SECOND, LocalDate.of(2000, 2, 29), false, -1},
        };
    }

    @Test(dataProvider = "fieldAndAccessor")
    public void test_supportedAccessor(ChronoField field, TemporalAccessor accessor, boolean isSupported, long value) {
        assertEquals(field.isSupportedBy(accessor), isSupported);
        if (isSupported) {
            assertEquals(field.getFrom(accessor), value);
        }
    }

    //-----------------------------------------------------------------------
    // range() and rangeRefinedBy(TemporalAccessor temporal)
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        assertEquals(MONTH_OF_YEAR.range(), ValueRange.of(1, 12));
        assertEquals(MONTH_OF_YEAR.rangeRefinedBy(LocalDate.of(2000, 2, 29)), ValueRange.of(1, 12));

        assertEquals(DAY_OF_MONTH.range(), ValueRange.of(1, 28, 31));
        assertEquals(DAY_OF_MONTH.rangeRefinedBy(LocalDate.of(2000, 2, 29)), ValueRange.of(1, 29));
    }

    //-----------------------------------------------------------------------
    // valueOf()
    //-----------------------------------------------------------------------
    @Test
    public void test_valueOf() {
        for (ChronoField field : ChronoField.values()) {
            assertEquals(ChronoField.valueOf(field.name()), field);
        }
    }

    // verify the minimum and maximum values of ChronoField.INSTANT_SECONDS
    // matches the minimum and maximum supported epoch second by Instant.
    @Test
    public void testMinMaxInstantSeconds() {
        assertEquals(ChronoField.INSTANT_SECONDS.range().getMinimum(),
                Instant.MIN.getLong(ChronoField.INSTANT_SECONDS));
        assertEquals(ChronoField.INSTANT_SECONDS.range().getMaximum(),
                Instant.MAX.getLong(ChronoField.INSTANT_SECONDS));
    }
}
