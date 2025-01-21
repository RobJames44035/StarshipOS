/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time;

import java.time.YearMonth;

import org.testng.annotations.Test;

/**
 * Test YearMonth.
 */
@Test
public class TestYearMonth extends AbstractTest {

    //-----------------------------------------------------------------------
    @Test
    public void test_immutable() {
        assertImmutable(YearMonth.class);
    }

}
