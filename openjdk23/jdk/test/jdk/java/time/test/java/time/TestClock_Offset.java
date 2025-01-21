/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;

import org.testng.annotations.Test;

/**
 * Test offset clock.
 */
@Test
public class TestClock_Offset {

    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");
    private static final Duration OFFSET = Duration.ofSeconds(2);

    //-------------------------------------------------------------------------
    public void test_withZone_same() {
        Clock test = Clock.offset(Clock.system(PARIS), OFFSET);
        Clock changed = test.withZone(PARIS);
        assertSame(test, changed);
    }

    //-----------------------------------------------------------------------
    public void test_toString() {
        Clock test = Clock.offset(Clock.systemUTC(), OFFSET);
        assertEquals(test.toString(), "OffsetClock[SystemClock[Z],PT2S]");
    }

}
