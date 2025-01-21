/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.chrono;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test assertions that must be true for all built-in chronologies.
 */
@Test
public class TCKChronoLocalDateTime {

    //-----------------------------------------------------------------------
    // regular data factory for names and descriptions of available calendars
    //-----------------------------------------------------------------------
    @DataProvider(name = "calendars")
    Chronology[][] data_of_calendars() {
        return new Chronology[][]{
                    {HijrahChronology.INSTANCE},
                    {IsoChronology.INSTANCE},
                    {JapaneseChronology.INSTANCE},
                    {MinguoChronology.INSTANCE},
                    {ThaiBuddhistChronology.INSTANCE}};
    }

    @Test(dataProvider="calendars")
    public void test_badWithAdjusterChrono(Chronology chrono) {
        LocalDate refDate = LocalDate.of(2013, 1, 1);
        ChronoLocalDateTime<?> cdt = chrono.date(refDate).atTime(LocalTime.NOON);
        for (Chronology[] clist : data_of_calendars()) {
            Chronology chrono2 = clist[0];
            ChronoLocalDateTime<?> cdt2 = chrono2.date(refDate).atTime(LocalTime.NOON);
            TemporalAdjuster adjuster = new FixedAdjuster(cdt2);
            if (chrono != chrono2) {
                try {
                    cdt.with(adjuster);
                    Assert.fail("WithAdjuster should have thrown a ClassCastException, "
                            + "required: " + cdt + ", supplied: " + cdt2);
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoLocalDateTime<?> result = cdt.with(adjuster);
                assertEquals(result, cdt2, "WithAdjuster failed to replace date");
            }
        }
    }

    @Test(dataProvider="calendars")
    public void test_badPlusAdjusterChrono(Chronology chrono) {
        LocalDate refDate = LocalDate.of(2013, 1, 1);
        ChronoLocalDateTime<?> cdt = chrono.date(refDate).atTime(LocalTime.NOON);
        for (Chronology[] clist : data_of_calendars()) {
            Chronology chrono2 = clist[0];
            ChronoLocalDateTime<?> cdt2 = chrono2.date(refDate).atTime(LocalTime.NOON);
            TemporalAmount adjuster = new FixedAdjuster(cdt2);
            if (chrono != chrono2) {
                try {
                    cdt.plus(adjuster);
                    Assert.fail("WithAdjuster should have thrown a ClassCastException, "
                            + "required: " + cdt + ", supplied: " + cdt2);
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoLocalDateTime<?> result = cdt.plus(adjuster);
                assertEquals(result, cdt2, "WithAdjuster failed to replace date time");
            }
        }
    }

    @Test(dataProvider="calendars")
    public void test_badMinusAdjusterChrono(Chronology chrono) {
        LocalDate refDate = LocalDate.of(2013, 1, 1);
        ChronoLocalDateTime<?> cdt = chrono.date(refDate).atTime(LocalTime.NOON);
        for (Chronology[] clist : data_of_calendars()) {
            Chronology chrono2 = clist[0];
            ChronoLocalDateTime<?> cdt2 = chrono2.date(refDate).atTime(LocalTime.NOON);
            TemporalAmount adjuster = new FixedAdjuster(cdt2);
            if (chrono != chrono2) {
                try {
                    cdt.minus(adjuster);
                    Assert.fail("WithAdjuster should have thrown a ClassCastException, "
                            + "required: " + cdt + ", supplied: " + cdt2);
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoLocalDateTime<?> result = cdt.minus(adjuster);
                assertEquals(result, cdt2, "WithAdjuster failed to replace date");
            }
        }
    }

    @Test(dataProvider="calendars")
    public void test_badPlusTemporalUnitChrono(Chronology chrono) {
        LocalDate refDate = LocalDate.of(2013, 1, 1);
        ChronoLocalDateTime<?> cdt = chrono.date(refDate).atTime(LocalTime.NOON);
        for (Chronology[] clist : data_of_calendars()) {
            Chronology chrono2 = clist[0];
            ChronoLocalDateTime<?> cdt2 = chrono2.date(refDate).atTime(LocalTime.NOON);
            TemporalUnit adjuster = new FixedTemporalUnit(cdt2);
            if (chrono != chrono2) {
                try {
                    cdt.plus(1, adjuster);
                    Assert.fail("TemporalUnit.doPlus plus should have thrown a ClassCastException" + cdt
                            + ", can not be cast to " + cdt2);
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoLocalDateTime<?> result = cdt.plus(1, adjuster);
                assertEquals(result, cdt2, "WithAdjuster failed to replace date");
            }
        }
    }

    @Test(dataProvider="calendars")
    public void test_badMinusTemporalUnitChrono(Chronology chrono) {
        LocalDate refDate = LocalDate.of(2013, 1, 1);
        ChronoLocalDateTime<?> cdt = chrono.date(refDate).atTime(LocalTime.NOON);
        for (Chronology[] clist : data_of_calendars()) {
            Chronology chrono2 = clist[0];
            ChronoLocalDateTime<?> cdt2 = chrono2.date(refDate).atTime(LocalTime.NOON);
            TemporalUnit adjuster = new FixedTemporalUnit(cdt2);
            if (chrono != chrono2) {
                try {
                    cdt.minus(1, adjuster);
                    Assert.fail("TemporalUnit.doPlus minus should have thrown a ClassCastException" + cdt.getClass()
                            + ", can not be cast to " + cdt2.getClass());
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoLocalDateTime<?> result = cdt.minus(1, adjuster);
                assertEquals(result, cdt2, "WithAdjuster failed to replace date");
            }
        }
    }

    @Test(dataProvider="calendars")
    public void test_badTemporalFieldChrono(Chronology chrono) {
        LocalDate refDate = LocalDate.of(2013, 1, 1);
        ChronoLocalDateTime<?> cdt = chrono.date(refDate).atTime(LocalTime.NOON);
        for (Chronology[] clist : data_of_calendars()) {
            Chronology chrono2 = clist[0];
            ChronoLocalDateTime<?> cdt2 = chrono2.date(refDate).atTime(LocalTime.NOON);
            TemporalField adjuster = new FixedTemporalField(cdt2);
            if (chrono != chrono2) {
                try {
                    cdt.with(adjuster, 1);
                    Assert.fail("TemporalField doWith() should have thrown a ClassCastException" + cdt.getClass()
                            + ", can not be cast to " + cdt2.getClass());
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoLocalDateTime<?> result = cdt.with(adjuster, 1);
                assertEquals(result, cdt2, "TemporalField doWith() failed to replace date");
            }
        }
    }

    //-----------------------------------------------------------------------
    // isBefore, isAfter, isEqual
    //-----------------------------------------------------------------------
    @Test(dataProvider="calendars")
    public void test_datetime_comparisons(Chronology chrono) {
        List<ChronoLocalDateTime<?>> dates = new ArrayList<>();

        ChronoLocalDateTime<?> date = chrono.date(LocalDate.of(2013, 1, 1)).atTime(LocalTime.MIN);

        // Insert dates in order, no duplicates
        dates.add(date.minus(1, ChronoUnit.YEARS));
        dates.add(date.minus(1, ChronoUnit.MONTHS));
        dates.add(date.minus(1, ChronoUnit.WEEKS));
        dates.add(date.minus(1, ChronoUnit.DAYS));
        dates.add(date.minus(1, ChronoUnit.HOURS));
        dates.add(date.minus(1, ChronoUnit.MINUTES));
        dates.add(date.minus(1, ChronoUnit.SECONDS));
        dates.add(date.minus(1, ChronoUnit.NANOS));
        dates.add(date);
        dates.add(date.plus(1, ChronoUnit.NANOS));
        dates.add(date.plus(1, ChronoUnit.SECONDS));
        dates.add(date.plus(1, ChronoUnit.MINUTES));
        dates.add(date.plus(1, ChronoUnit.HOURS));
        dates.add(date.plus(1, ChronoUnit.DAYS));
        dates.add(date.plus(1, ChronoUnit.WEEKS));
        dates.add(date.plus(1, ChronoUnit.MONTHS));
        dates.add(date.plus(1, ChronoUnit.YEARS));

        // Check these dates against the corresponding dates for every calendar
        for (Chronology[] clist : data_of_calendars()) {
            List<ChronoLocalDateTime<?>> otherDates = new ArrayList<>();
            Chronology chrono2 = clist[0];
            for (ChronoLocalDateTime<?> d : dates) {
                otherDates.add(chrono2.date(d).atTime(d.toLocalTime()));
            }

            // Now compare  the sequence of original dates with the sequence of converted dates
            for (int i = 0; i < dates.size(); i++) {
                ChronoLocalDateTime<?> a = dates.get(i);
                for (int j = 0; j < otherDates.size(); j++) {
                    ChronoLocalDateTime<?> b = otherDates.get(j);
                    int cmp = ChronoLocalDateTime.timeLineOrder().compare(a, b);
                    if (i < j) {
                        assertTrue(cmp < 0, a + " compare " + b);
                        assertEquals(a.isBefore(b), true, a + " isBefore " + b);
                        assertEquals(a.isAfter(b), false, a + " isAfter " + b);
                        assertEquals(a.isEqual(b), false, a + " isEqual " + b);
                    } else if (i > j) {
                        assertTrue(cmp > 0, a + " compare " + b);
                        assertEquals(a.isBefore(b), false, a + " isBefore " + b);
                        assertEquals(a.isAfter(b), true, a + " isAfter " + b);
                        assertEquals(a.isEqual(b), false, a + " isEqual " + b);
                    } else {
                        assertTrue(cmp == 0, a + " compare " + b);
                        assertEquals(a.isBefore(b), false, a + " isBefore " + b);
                        assertEquals(a.isAfter(b), false, a + " isAfter " + b);
                        assertEquals(a.isEqual(b), true, a + " isEqual " + b);
                    }
                }
            }
        }
    }

    //-----------------------------------------------------------------------
    @Test(dataProvider="calendars")
    public void test_from_TemporalAccessor(Chronology chrono) {
        LocalDateTime refDateTime = LocalDateTime.of(2013, 1, 1, 12, 30);
        ChronoLocalDateTime<?> dateTime = chrono.localDateTime(refDateTime);
        ChronoLocalDateTime<?> test1 = ChronoLocalDateTime.from(dateTime);
        assertEquals(test1, dateTime);
        ChronoLocalDateTime<?> test2 = ChronoLocalDateTime.from(dateTime.atZone(ZoneOffset.UTC));
        assertEquals(test2, dateTime);
    }

    @Test(expectedExceptions = DateTimeException.class)
    public void test_from_TemporalAccessor_dateOnly() {
        ChronoLocalDateTime.from(LocalDate.of(2013, 1, 1));
    }

    @Test(expectedExceptions = DateTimeException.class)
    public void test_from_TemporalAccessor_timeOnly() {
        ChronoLocalDateTime.from(LocalTime.of(12, 30));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void test_from_TemporalAccessor_null() {
        ChronoLocalDateTime.from(null);
    }

    //-----------------------------------------------------------------------
    @Test(dataProvider="calendars")
    public void test_getChronology(Chronology chrono) {
        ChronoLocalDateTime<?> test = chrono.localDateTime(LocalDateTime.of(2010, 6, 30, 11, 30));
        assertEquals(test.getChronology(), chrono);
    }

    //-----------------------------------------------------------------------
    /**
     * FixedAdjusted returns a fixed Temporal in all adjustments.
     * Construct an adjuster with the Temporal that should be returned from adjust.
     */
    static class FixedAdjuster implements TemporalAdjuster, TemporalAmount {
        private Temporal datetime;

        FixedAdjuster(Temporal datetime) {
            this.datetime = datetime;
        }

        @Override
        public Temporal adjustInto(Temporal ignore) {
            return datetime;
        }

        @Override
        public Temporal addTo(Temporal ignore) {
            return datetime;
        }

        @Override
        public Temporal subtractFrom(Temporal ignore) {
            return datetime;
        }

        @Override
        public long get(TemporalUnit unit) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<TemporalUnit> getUnits() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    /**
     * FixedTemporalUnit returns a fixed Temporal in all adjustments.
     * Construct an FixedTemporalUnit with the Temporal that should be returned from addTo.
     */
    static class FixedTemporalUnit implements TemporalUnit {
        private Temporal temporal;

        FixedTemporalUnit(Temporal temporal) {
            this.temporal = temporal;
        }

        @Override
        public Duration getDuration() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isDurationEstimated() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isDateBased() {
            return false;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public boolean isSupportedBy(Temporal temporal) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R extends Temporal> R addTo(R temporal, long amount) {
            return (R) this.temporal;
        }

        @Override
        public long between(Temporal temporal1, Temporal temporal2) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String toString() {
            return "FixedTemporalUnit";
        }
    }

    /**
     * FixedTemporalField returns a fixed Temporal in all adjustments.
     * Construct an FixedTemporalField with the Temporal that should be returned from adjustInto.
     */
    static class FixedTemporalField implements TemporalField {
        private Temporal temporal;
        FixedTemporalField(Temporal temporal) {
            this.temporal = temporal;
        }

        @Override
        public TemporalUnit getBaseUnit() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public TemporalUnit getRangeUnit() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ValueRange range() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isDateBased() {
            return false;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporal) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            return (R) this.temporal;
        }

        @Override
        public String toString() {
            return "FixedTemporalField";
        }
    }
}
