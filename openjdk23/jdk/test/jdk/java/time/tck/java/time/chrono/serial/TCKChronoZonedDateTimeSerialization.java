/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.chrono.serial;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.ThaiBuddhistChronology;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

/**
 * Test assertions that must be true for all built-in chronologies.
 */
@Test
public class TCKChronoZonedDateTimeSerialization extends AbstractTCKTest {

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
                    {ThaiBuddhistChronology.INSTANCE},
        };
    }

    //-----------------------------------------------------------------------
    // Test Serialization of ISO via chrono API
    //-----------------------------------------------------------------------
    @Test( dataProvider="calendars")
    public void test_ChronoZonedDateTimeSerialization(Chronology chrono) throws Exception {
        ZonedDateTime ref = LocalDate.of(2013, 1, 5).atTime(12, 1, 2, 3).atZone(ZoneId.of("GMT+01:23"));
        ChronoZonedDateTime<?> original = chrono.date(ref).atTime(ref.toLocalTime()).atZone(ref.getZone());
        assertSerializable(original);
    }

}
