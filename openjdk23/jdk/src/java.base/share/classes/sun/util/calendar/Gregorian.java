/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.util.calendar;

import java.util.TimeZone;

/**
 * Gregorian calendar implementation.
 *
 * @author Masayoshi Okutsu
 * @since 1.5
 */

public final class Gregorian extends BaseCalendar {

    static final class Date extends BaseCalendar.Date {
        Date() {
            super();
        }

        Date(TimeZone zone) {
            super(zone);
        }

        @Override
        public int getNormalizedYear() {
            return getYear();
        }

        @Override
        public void setNormalizedYear(int normalizedYear) {
            setYear(normalizedYear);
        }
    }

    Gregorian() {
    }

    @Override
    public String getName() {
        return "gregorian";
    }

    @Override
    public Date getCalendarDate() {
        return getCalendarDate(System.currentTimeMillis(), newCalendarDate());
    }

    @Override
    public Date getCalendarDate(long millis) {
        return getCalendarDate(millis, newCalendarDate());
    }

    @Override
    public Date getCalendarDate(long millis, CalendarDate date) {
        return (Date) super.getCalendarDate(millis, date);
    }

    @Override
    public Date getCalendarDate(long millis, TimeZone zone) {
        return getCalendarDate(millis, newCalendarDate(zone));
    }

    @Override
    public Date newCalendarDate() {
        return new Date();
    }

    @Override
    public Date newCalendarDate(TimeZone zone) {
        return new Date(zone);
    }
}
