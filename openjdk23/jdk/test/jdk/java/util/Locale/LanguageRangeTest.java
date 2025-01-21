/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
/**
 * @test
 * @bug 8253321
 * @summary test LanguageRange class
 * @run testng LanguageRangeTest
 */

import static java.util.Locale.LanguageRange;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

@Test
public class LanguageRangeTest {

    @Test
    public void hashCodeTest() {
        var range1 = new LanguageRange("en-GB", 0);
        var range2 = new LanguageRange("en-GB", 0);
        assertEquals(range1, range2);
        range1.hashCode();
        assertEquals(range1, range2);
        range2.hashCode();
        assertEquals(range1, range2);
    }
}
