/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.chrono;

import static java.time.temporal.ChronoField.ERA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ValueRange;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test
public class TCKThaiBuddhistEra {

    @DataProvider(name = "ThaiBuddhistEras")
    Object[][] data_of_eras() {
        return new Object[][] {
                    {ThaiBuddhistEra.BEFORE_BE, "BEFORE_BE", 0},
                    {ThaiBuddhistEra.BE, "BE", 1},
        };
    }


    //-----------------------------------------------------------------------
    // valueOf()
    //-----------------------------------------------------------------------
    @Test(dataProvider="ThaiBuddhistEras")
    public void test_valueOf(ThaiBuddhistEra era , String eraName, int eraValue) {
        assertEquals(era.getValue(), eraValue);
        assertEquals(ThaiBuddhistEra.of(eraValue), era);
        assertEquals(ThaiBuddhistEra.valueOf(eraName), era);
    }

    //-----------------------------------------------------------------------
    // values()
    //-----------------------------------------------------------------------
    @Test
    public void test_values() {
        List<Era> eraList = ThaiBuddhistChronology.INSTANCE.eras();
        ThaiBuddhistEra[] eras = ThaiBuddhistEra.values();
        assertEquals(eraList.size(), eras.length);
        for (ThaiBuddhistEra era : eras) {
            assertTrue(eraList.contains(era));
        }
    }

    //-----------------------------------------------------------------------
    // range()
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        for (ThaiBuddhistEra era : ThaiBuddhistEra.values()) {
            assertEquals(era.range(ERA), ValueRange.of(0, 1));
        }
    }

}
