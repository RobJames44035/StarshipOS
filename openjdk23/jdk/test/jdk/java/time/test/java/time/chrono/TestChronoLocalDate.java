/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.chrono;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Test chrono local date.
 */
@Test
public class TestChronoLocalDate {
    // this class primarily tests whether the generics work OK

    //-----------------------------------------------------------------------
    public void test_date_comparator_checkGenerics_ISO() {
        List<ChronoLocalDate> dates = new ArrayList<>();
        ChronoLocalDate date = LocalDate.of(2013, 1, 1);

        // Insert dates in order, no duplicates
        dates.add(date.minus(10, ChronoUnit.YEARS));
        dates.add(date.minus(1, ChronoUnit.YEARS));
        dates.add(date.minus(1, ChronoUnit.MONTHS));
        dates.add(date.minus(1, ChronoUnit.WEEKS));
        dates.add(date.minus(1, ChronoUnit.DAYS));
        dates.add(date);
        dates.add(date.plus(1, ChronoUnit.DAYS));
        dates.add(date.plus(1, ChronoUnit.WEEKS));
        dates.add(date.plus(1, ChronoUnit.MONTHS));
        dates.add(date.plus(1, ChronoUnit.YEARS));
        dates.add(date.plus(10, ChronoUnit.YEARS));

        List<ChronoLocalDate> copy = new ArrayList<>(dates);
        Collections.shuffle(copy);
        Collections.sort(copy, ChronoLocalDate.timeLineOrder());
        assertEquals(copy, dates);
        assertTrue(ChronoLocalDate.timeLineOrder().compare(copy.get(0), copy.get(1)) < 0);
    }

    public void test_date_comparator_checkGenerics_LocalDate() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = LocalDate.of(2013, 1, 1);

        // Insert dates in order, no duplicates
        dates.add(date.minus(10, ChronoUnit.YEARS));
        dates.add(date.minus(1, ChronoUnit.YEARS));
        dates.add(date.minus(1, ChronoUnit.MONTHS));
        dates.add(date.minus(1, ChronoUnit.WEEKS));
        dates.add(date.minus(1, ChronoUnit.DAYS));
        dates.add(date);
        dates.add(date.plus(1, ChronoUnit.DAYS));
        dates.add(date.plus(1, ChronoUnit.WEEKS));
        dates.add(date.plus(1, ChronoUnit.MONTHS));
        dates.add(date.plus(1, ChronoUnit.YEARS));
        dates.add(date.plus(10, ChronoUnit.YEARS));

        List<LocalDate> copy = new ArrayList<>(dates);
        Collections.shuffle(copy);
        Collections.sort(copy, ChronoLocalDate.timeLineOrder());
        assertEquals(copy, dates);
        assertTrue(ChronoLocalDate.timeLineOrder().compare(copy.get(0), copy.get(1)) < 0);
    }

    //-----------------------------------------------------------------------
    public void test_date_checkGenerics_genericsMethod() {
        Chronology chrono = ThaiBuddhistChronology.INSTANCE;
        ChronoLocalDate date = chrono.dateNow();
        date = processOK(date);
        date = processClassOK(ThaiBuddhistDate.class);
        date = dateSupplier();

        date = processClassWeird(ThaiBuddhistDate.class);
    }

    public void test_date_checkGenerics_genericsMethod_concreteType() {
        ThaiBuddhistChronology chrono = ThaiBuddhistChronology.INSTANCE;
        ThaiBuddhistDate date = chrono.dateNow();
        date = ThaiBuddhistDate.now();
        date = processOK(date);
        date = processClassOK(ThaiBuddhistDate.class);
        date = dateSupplier();

        // date = processClassWeird(ThaiBuddhistDate.class);  // does not compile (correct)
    }

    public <D extends ChronoLocalDate> void test_date_checkGenerics_genericsMethod_withType() {
        Chronology chrono = ThaiBuddhistChronology.INSTANCE;
        @SuppressWarnings("unchecked")
        D date = (D) chrono.dateNow();
        date = processOK(date);
        // date = processClassOK(ThaiBuddhistDate.class);  // does not compile (correct)
        date = dateSupplier();

        // date = processWeird(date);  // does not compile (correct)
        // date = processClassWeird(ThaiBuddhistDate.class);  // does not compile (correct)
    }

    @SuppressWarnings("unchecked")
    private <D extends ChronoLocalDate> D dateSupplier() {
        return (D) ThaiBuddhistChronology.INSTANCE.dateNow();
    }

    // decent generics signatures that need to work
    @SuppressWarnings("unchecked")
    private <D extends ChronoLocalDate> D processOK(D date) {
        return (D) date.plus(1, ChronoUnit.DAYS);
    }
    private <D extends ChronoLocalDate> D processClassOK(Class<D> cls) {
        return null;
    }

    // weird generics signatures that shouldn't really work
    private <D extends ChronoLocalDate> ChronoLocalDate processClassWeird(Class<D> cls) {
        return null;
    }

    public void test_date_checkGenerics_chronoLocalDateTime1() {
        LocalDateTime now = LocalDateTime.now();
        Chronology chrono = ThaiBuddhistChronology.INSTANCE;
        ChronoLocalDateTime<?> ldt = chrono.localDateTime(now);
        ldt = processCLDT(ldt);
    }

    public void test_date_checkGenerics_chronoLocalDateTime2() {
        LocalDateTime now = LocalDateTime.now();
        Chronology chrono = ThaiBuddhistChronology.INSTANCE;
        ChronoLocalDateTime<? extends ChronoLocalDate> ldt = chrono.localDateTime(now);
        ldt = processCLDT(ldt);
    }

    private <D extends ChronoLocalDate> ChronoLocalDateTime<D> processCLDT(ChronoLocalDateTime<D> dt) {
        return dt;
    }
}
