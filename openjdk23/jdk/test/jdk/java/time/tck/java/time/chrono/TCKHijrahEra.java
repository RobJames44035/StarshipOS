/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.chrono;

import static java.time.temporal.ChronoField.ERA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.chrono.Era;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahEra;
import java.time.temporal.ValueRange;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test
public class TCKHijrahEra {

    @DataProvider(name = "HijrahEras")
    Object[][] data_of_eras() {
        return new Object[][] {
                    {HijrahEra.AH, "AH", 1},
       };
    }

    @Test(dataProvider="HijrahEras")
    public void test_valueOf(HijrahEra era , String eraName, int eraValue) {
        assertEquals(era.getValue(), eraValue);

        assertEquals(HijrahChronology.INSTANCE.eraOf(eraValue), era);
        assertEquals(HijrahEra.of(eraValue), era);
        assertEquals(HijrahEra.valueOf(eraName), era);
    }

    //-----------------------------------------------------------------------
    // values()
    //-----------------------------------------------------------------------
    @Test
    public void test_values() {
        List<Era> eraList = HijrahChronology.INSTANCE.eras();
        HijrahEra[] eras = HijrahEra.values();
        assertEquals(eraList.size(), eras.length);
        for (HijrahEra era : eras) {
            assertTrue(eraList.contains(era));
        }
    }

    //-----------------------------------------------------------------------
    // range()
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        for (HijrahEra era : HijrahEra.values()) {
            assertEquals(era.range(ERA), ValueRange.of(1, 1));
        }
    }

}
