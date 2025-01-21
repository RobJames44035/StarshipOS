/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.chrono;

import static java.time.temporal.ChronoField.ERA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.chrono.IsoEra;
import java.time.temporal.ValueRange;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test
public class TCKIsoEra {

    @DataProvider(name = "IsoEras")
    Object[][] data_of_eras() {
        return new Object[][] {
                    {IsoEra.BCE, "BCE", 0},
                    {IsoEra.CE, "CE", 1},
        };
    }

    @Test(dataProvider="IsoEras")
    public void test_valueOf(IsoEra era , String eraName, int eraValue) {
        assertEquals(era.getValue(), eraValue);
        assertEquals(IsoEra.of(eraValue), era);
        assertEquals(IsoEra.valueOf(eraName), era);
    }

    //-----------------------------------------------------------------------
    // values()
    //-----------------------------------------------------------------------
    @Test
    public void test_values() {
        List<Era> eraList = IsoChronology.INSTANCE.eras();
        IsoEra[] eras = IsoEra.values();
        assertEquals(eraList.size(), eras.length);
        for (IsoEra era : eras) {
            assertTrue(eraList.contains(era));
        }
    }

    //-----------------------------------------------------------------------
    // range()
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        for (IsoEra era : IsoEra.values()) {
            assertEquals(era.range(ERA), ValueRange.of(0, 1));
        }
    }

}
