/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.temporal.serial;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_DAY;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
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

import java.io.IOException;
import java.time.temporal.ChronoField;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tck.java.time.AbstractTCKTest;

/**
 * Test serialization of ChronoField.
 */
@Test
public class TCKChronoFieldSerialization extends AbstractTCKTest {

    //-----------------------------------------------------------------------
    // List of Fields
    //-----------------------------------------------------------------------
    @DataProvider(name="fieldBased")
    Object[][] data_fieldBased() {
        return new Object[][] {
                {DAY_OF_WEEK},
                {ALIGNED_DAY_OF_WEEK_IN_MONTH},
                {ALIGNED_DAY_OF_WEEK_IN_YEAR},
                {DAY_OF_MONTH},
                {DAY_OF_YEAR},
                {EPOCH_DAY},
                {ALIGNED_WEEK_OF_MONTH},
                {ALIGNED_WEEK_OF_YEAR},
                {MONTH_OF_YEAR},
                {PROLEPTIC_MONTH},
                {YEAR_OF_ERA},
                {YEAR},
                {ERA},

                {AMPM_OF_DAY},
                {CLOCK_HOUR_OF_DAY},
                {HOUR_OF_DAY},
                {CLOCK_HOUR_OF_AMPM},
                {HOUR_OF_AMPM},
                {MINUTE_OF_DAY},
                {MINUTE_OF_HOUR},
                {SECOND_OF_DAY},
                {SECOND_OF_MINUTE},
                {MILLI_OF_DAY},
                {MILLI_OF_SECOND},
                {MICRO_OF_DAY},
                {MICRO_OF_SECOND},
                {NANO_OF_DAY},
                {NANO_OF_SECOND},
        };
    }

    @Test(dataProvider = "fieldBased")
    public void test_fieldSerializable(ChronoField field) throws IOException, ClassNotFoundException {
        assertSerializableSame(field);
    }

}
