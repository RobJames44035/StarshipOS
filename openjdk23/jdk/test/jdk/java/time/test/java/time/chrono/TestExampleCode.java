/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.chrono;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test case verify that the example code in the package-info.java compiles
 * and runs.
 */
public class TestExampleCode {

    @Test
    public void test_chronoPackageExample() {
        // Print the Thai Buddhist date
        ChronoLocalDate now1 = Chronology.of("ThaiBuddhist").dateNow();
        int day = now1.get(ChronoField.DAY_OF_MONTH);
        int dow = now1.get(ChronoField.DAY_OF_WEEK);
        int month = now1.get(ChronoField.MONTH_OF_YEAR);
        int year = now1.get(ChronoField.YEAR);
        System.out.printf("  Today is %s %s %d-%s-%d%n", now1.getChronology().getId(),
                dow, day, month, year);

        // Enumerate the list of available calendars and print today for each
        Set<Chronology> chronos = Chronology.getAvailableChronologies();
        for (Chronology chrono : chronos) {
            ChronoLocalDate date = chrono.dateNow();
            System.out.printf("   %20s: %s%n", chrono.getId(), date.toString());
        }

        // Print today's date and the last day of the year for the Thai Buddhist Calendar.
        ChronoLocalDate first = now1
                .with(ChronoField.DAY_OF_MONTH, 1)
                .with(ChronoField.MONTH_OF_YEAR, 1);
        ChronoLocalDate last = first
                .plus(1, ChronoUnit.YEARS)
                .minus(1, ChronoUnit.DAYS);
        System.out.printf("  %s: 1st of year: %s; end of year: %s%n", last.getChronology().getId(),
                first, last);
    }

    //-----------------------------------------------------------------------
    // Data provider for Hijrah Type names
    //-----------------------------------------------------------------------
    @DataProvider(name = "HijrahTypeNames")
    Object[][] data_of_ummalqura() {
        return new Object[][]{
            { "Hijrah-umalqura", "islamic-umalqura"},
        };
    }

    @Test(dataProvider= "HijrahTypeNames")
    public void test_HijrahTypeViaLocale(String calendarId, String calendarType) {
        Locale.Builder builder = new Locale.Builder();
        builder.setLanguage("en").setRegion("US");
        builder.setUnicodeLocaleKeyword("ca", calendarType);
        Locale locale = builder.build();
        Chronology chrono = Chronology.ofLocale(locale);
        System.out.printf(" Locale language tag: %s, Chronology ID: %s, type: %s%n",
                locale.toLanguageTag(), chrono, chrono.getCalendarType());
        Chronology expected = Chronology.of(calendarId);
        assertEquals(chrono, expected, "Expected chronology not found");
    }

    @Test
    public void test_calendarPackageExample() {

        // Enumerate the list of available calendars and print today for each
        Set<Chronology> chronos = Chronology.getAvailableChronologies();
        for (Chronology chrono : chronos) {
            ChronoLocalDate date = chrono.dateNow();
            System.out.printf("   %20s: %s%n", chrono.getId(), date.toString());
        }

        // Print the Thai Buddhist date
        ThaiBuddhistDate now1 = ThaiBuddhistDate.now();
        int day = now1.get(ChronoField.DAY_OF_MONTH);
        int dow = now1.get(ChronoField.DAY_OF_WEEK);
        int month = now1.get(ChronoField.MONTH_OF_YEAR);
        int year = now1.get(ChronoField.YEAR);
        System.out.printf("  Today is %s %s %d-%s-%d%n", now1.getChronology().getId(),
                dow, day, month, year);

        // Print today's date and the last day of the year for the Thai Buddhist Calendar.
        ThaiBuddhistDate first = now1
                .with(ChronoField.DAY_OF_MONTH, 1)
                .with(ChronoField.MONTH_OF_YEAR, 1);
        ThaiBuddhistDate last = first
                .plus(1, ChronoUnit.YEARS)
                .minus(1, ChronoUnit.DAYS);
        System.out.printf("  %s: 1st of year: %s; end of year: %s%n", last.getChronology().getId(),
                first, last);
    }

    void HijrahExample1() {
        HijrahDate hd2 = HijrahChronology.INSTANCE.date(1200, 1, 1);

        ChronoLocalDateTime<HijrahDate> hdt = hd2.atTime(LocalTime.MIDNIGHT);
        ChronoZonedDateTime<HijrahDate> zhdt = hdt.atZone(ZoneId.of("GMT"));
        HijrahDate hd3 = zhdt.toLocalDate();
        ChronoLocalDateTime<HijrahDate> hdt2 = zhdt.toLocalDateTime();
        HijrahDate hd4 = hdt2.toLocalDate();

        HijrahDate hd5 = next(hd2);
    }

    void test_unknownChronologyWithDateTime() {
        ChronoLocalDate date = LocalDate.now();
        ChronoLocalDateTime<?> cldt = date.atTime(LocalTime.NOON);
        ChronoLocalDate ld = cldt.toLocalDate();
        ChronoLocalDateTime<?> noonTomorrow = tomorrowNoon(ld);
    }

    @Test
    public void test_library() {
        HijrahDate date = HijrahDate.now();
        HijrahDate next = next(date);
        ChronoLocalDateTime<HijrahDate> noonTomorrow = tomorrowNoon(date);
        HijrahDate hd3 = noonTomorrow.toLocalDate();
        System.out.printf("  now: %s, noon tomorrow: %s%n", date, noonTomorrow);
    }

    /**
     * Simple function based on a date, returning a ChronoDate of the same type.
     * @param <D> a parameterized ChronoLocalDate
     * @param date a specific date extending ChronoLocalDate
     * @return a new date in the same chronology.
     */
    @SuppressWarnings("unchecked")
    private <D extends ChronoLocalDate> D next(D date) {
        return (D) date.plus(1, ChronoUnit.DAYS);
    }

    /**
     * Simple function based on a date, returning a ChronoLocalDateTime of the
     * same chronology.
     * @param <D> a parameterized ChronoLocalDate
     * @param date a specific date extending ChronoLocalDate
     * @return a {@code ChronoLocalDateTime<D>} using the change chronology.
     */
    @SuppressWarnings("unchecked")
    private <D extends ChronoLocalDate> ChronoLocalDateTime<D> tomorrowNoon(D date) {
        return (ChronoLocalDateTime<D>) date.plus(1, ChronoUnit.DAYS).atTime(LocalTime.of(12, 0));
    }
}
