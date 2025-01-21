/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.chrono;

import static java.time.temporal.ChronoField.EPOCH_DAY;

import java.io.Serializable;
import java.time.chrono.AbstractChronology;
import java.time.chrono.Era;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * The Coptic calendar system.
 * <p>
 * This chronology defines the rules of the Coptic calendar system.
 * This calendar system is primarily used in Christian Egypt.
 * Dates are aligned such that {@code 0001AM-01-01 (Coptic)} is {@code 0284-08-29 (ISO)}.
 * <p>
 * The fields are defined as follows:
 * <p><ul>
 * <li>era - There are two eras, the current 'Era of the Martyrs' (AM) and the previous era (ERA_ERA_BEFORE_AM).
 * <li>year-of-era - The year-of-era for the current era increases uniformly from the epoch at year one.
 *  For the previous era the year increases from one as time goes backwards.
 * <li>proleptic-year - The proleptic year is the same as the year-of-era for the
 *  current era. For the previous era, years have zero, then negative values.
 * <li>month-of-year - There are 13 months in a Coptic year, numbered from 1 to 13.
 * <li>day-of-month - There are 30 days in each of the first 12 Coptic months, numbered 1 to 30.
 *  The 13th month has 5 days, or 6 in a leap year, numbered 1 to 5 or 1 to 6.
 * <li>day-of-year - There are 365 days in a standard Coptic year and 366 in a leap year.
 *  The days are numbered from 1 to 365 or 1 to 366.
 * <li>leap-year - Leap years occur every 4 years.
 * </ul><p>
 *
 * <h4>Implementation notes</h4>
 * This class is immutable and thread-safe.
 */
public final class CopticChronology extends AbstractChronology implements Serializable {

    /**
     * Singleton instance of the Coptic chronology.
     */
    public static final CopticChronology INSTANCE = new CopticChronology();

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 7291205177830286973L;
    /**
     * Range of months.
     */
    static final ValueRange MOY_RANGE = ValueRange.of(1, 13);
    /**
     * Range of days.
     */
    static final ValueRange DOM_RANGE = ValueRange.of(1, 5, 30);
    /**
     * Range of days.
     */
    static final ValueRange DOM_RANGE_NONLEAP = ValueRange.of(1, 5);
    /**
     * Range of days.
     */
    static final ValueRange DOM_RANGE_LEAP = ValueRange.of(1, 6);

    /**
     * Public Constructor to be instantiated by the ServiceLoader
     */
    public CopticChronology() {
    }

    /**
     * Resolve singleton.
     *
     * @return the singleton instance, not null
     */
    private Object readResolve() {
        return INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the ID of the chronology - 'Coptic'.
     * <p>
     * The ID uniquely identifies the {@code Chronology}.
     * It can be used to lookup the {@code Chronology} using {@link #of(String)}.
     *
     * @return the chronology ID - 'Coptic'
     * @see #getCalendarType()
     */
    @Override
    public String getId() {
        return "Coptic";
    }

    /**
     * Gets the calendar type of the underlying calendar system - 'coptic'.
     * <p>
     * The calendar type is an identifier defined by the
     * <em>Unicode Locale Data Markup Language (LDML)</em> specification.
     * It can be used to lookup the {@code Chronology} using {@link #of(String)}.
     * It can also be used as part of a locale, accessible via
     * {@link Locale#getUnicodeLocaleType(String)} with the key 'ca'.
     *
     * @return the calendar system type - 'coptic'
     * @see #getId()
     */
    @Override
    public String getCalendarType() {
        return "coptic";
    }

    //-----------------------------------------------------------------------
    @Override
    public CopticDate date(int prolepticYear, int month, int dayOfMonth) {
        return new CopticDate(prolepticYear, month, dayOfMonth);
    }

    @Override
    public CopticDate dateYearDay(int prolepticYear, int dayOfYear) {
        return new CopticDate(prolepticYear, (dayOfYear - 1) / 30 + 1, (dayOfYear - 1) % 30 + 1);
    }

    @Override
    public CopticDate dateEpochDay(long epochDay) {
        return CopticDate.ofEpochDay(epochDay);
    }

    @Override
    public CopticDate date(TemporalAccessor dateTime) {
        if (dateTime instanceof CopticDate) {
            return (CopticDate) dateTime;
        }
        return CopticDate.ofEpochDay(dateTime.getLong(EPOCH_DAY));
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified year is a leap year.
     * <p>
     * A Coptic proleptic-year is leap if the remainder after division by four equals three.
     * This method does not validate the year passed in, and only has a
     * well-defined result for years in the supported range.
     *
     * @param prolepticYear  the proleptic-year to check, not validated for range
     * @return true if the year is a leap year
     */
    @Override
    public boolean isLeapYear(long prolepticYear) {
        return Math.floorMod(prolepticYear, 4) == 3;
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        if (era instanceof CopticEra == false) {
            throw new ClassCastException("Era must be CopticEra");
        }
        return (era == CopticEra.AM ? yearOfEra : 1 - yearOfEra);
    }

    @Override
    public Era eraOf(int eraValue) {
        return CopticEra.of(eraValue);
    }

    @Override
    public List<Era> eras() {
        return Arrays.<Era>asList(CopticEra.values());
    }

    //-----------------------------------------------------------------------
    @Override
    public ValueRange range(ChronoField field) {
        switch (field) {
            case DAY_OF_MONTH: return ValueRange.of(1, 5, 30);
            case ALIGNED_WEEK_OF_MONTH: return ValueRange.of(1, 1, 5);
            case MONTH_OF_YEAR: return ValueRange.of(1, 13);
            case PROLEPTIC_MONTH: return ValueRange.of(-1000, 1000);  // TODO
            case YEAR_OF_ERA: return ValueRange.of(1, 999, 1000);  // TODO
            case YEAR: return ValueRange.of(-1000, 1000);  // TODO
        }
        return field.range();
    }

}
