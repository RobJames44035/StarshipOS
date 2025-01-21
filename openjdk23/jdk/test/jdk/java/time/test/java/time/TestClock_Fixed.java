/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.testng.annotations.Test;

/**
 * Test fixed clock.
 */
@Test
public class TestClock_Fixed {

    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");
    private static final Instant INSTANT = LocalDateTime.of(2008, 6, 30, 11, 30, 10, 500).atZone(ZoneOffset.ofHours(2)).toInstant();

    //-------------------------------------------------------------------------
    public void test_withZone_same() {
        Clock test = Clock.fixed(INSTANT, PARIS);
        Clock changed = test.withZone(PARIS);
        assertSame(test, changed);
    }

    //-----------------------------------------------------------------------
    public void test_toString() {
        Clock test = Clock.fixed(INSTANT, PARIS);
        assertEquals(test.toString(), "FixedClock[2008-06-30T09:30:10.000000500Z,Europe/Paris]");
    }

}
