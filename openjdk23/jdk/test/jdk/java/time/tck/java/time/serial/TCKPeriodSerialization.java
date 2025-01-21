/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.serial;

import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

import java.time.Period;

/**
 * Test serialization of Period.
 */
@Test
public class TCKPeriodSerialization extends AbstractTCKTest {

    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        assertSerializable(Period.ZERO);
        assertSerializable(Period.ofDays(1));
        assertSerializable(Period.of(1, 2, 3));
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(Period.class);
    }

}
