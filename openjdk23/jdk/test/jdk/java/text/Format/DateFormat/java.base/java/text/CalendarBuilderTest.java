/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package java.text;

/**
 * Test validated that CalendarBuilder.toString does not throw an ArrayIndexOutOfBoundException.
 */

import java.util.Calendar;

public class CalendarBuilderTest {
    public static void testCalendarBuilderToString() {
        CalendarBuilder calendarBuilder = new CalendarBuilder();
        calendarBuilder.set(Calendar.YEAR, 2020);
        calendarBuilder.toString();

    }
}
