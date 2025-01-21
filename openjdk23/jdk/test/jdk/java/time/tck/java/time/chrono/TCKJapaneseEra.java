/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.chrono;

import static java.time.temporal.ChronoField.ERA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.time.chrono.Era;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for JapaneseEra
 * @bug 8068278
 */
@Test
public class TCKJapaneseEra {

    @DataProvider(name = "JapaneseEras")
    Object[][] data_of_eras() {
        return new Object[][] {
                    {JapaneseEra.REIWA, "Reiwa", 3},
                    {JapaneseEra.HEISEI, "Heisei", 2},
                    {JapaneseEra.SHOWA, "Showa", 1},
                    {JapaneseEra.TAISHO, "Taisho", 0},
                    {JapaneseEra.MEIJI, "Meiji", -1},
        };
    }

    @DataProvider(name = "InvalidJapaneseEras")
    Object[][] data_of_invalid_eras() {
        return new Object[][] {
                {-2},
                {-3},
                {4},
                {Integer.MIN_VALUE},
                {Integer.MAX_VALUE},
        };
    }

    //-----------------------------------------------------------------------
    // JapaneseEra value test
    //-----------------------------------------------------------------------
    @Test(dataProvider="JapaneseEras")
    public void test_valueOf(JapaneseEra era , String eraName, int eraValue) {
        assertEquals(era.getValue(), eraValue);
        assertEquals(JapaneseEra.of(eraValue), era);
        assertEquals(JapaneseEra.valueOf(eraName), era);
    }

    //-----------------------------------------------------------------------
    // values()
    //-----------------------------------------------------------------------
    @Test
    public void test_values() {
        List<Era> eraList = JapaneseChronology.INSTANCE.eras();
        JapaneseEra[] eras = JapaneseEra.values();
        assertEquals(eraList.size(), eras.length);
        for (JapaneseEra era : eras) {
            assertTrue(eraList.contains(era));
        }
    }

    //-----------------------------------------------------------------------
    // range()
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        // eras may be added after release
        for (JapaneseEra era : JapaneseEra.values()) {
            assertEquals(era.range(ERA).getMinimum(), -1);
            assertEquals(era.range(ERA).getLargestMinimum(), -1);
            assertEquals(era.range(ERA).getSmallestMaximum(), era.range(ERA).getMaximum());
            assertEquals(era.range(ERA).getMaximum() >= 2, true);
        }
    }

    //-----------------------------------------------------------------------
    // JapaneseChronology.INSTANCE.eraOf invalid era test
    //-----------------------------------------------------------------------
    @Test(dataProvider="InvalidJapaneseEras", expectedExceptions=java.time.DateTimeException.class)
    public void test_outofrange(int era) {
        JapaneseChronology.INSTANCE.eraOf(era);
    }
}
