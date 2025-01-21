/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
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
public class TCKClock_Fixed extends AbstractTCKTest {

    private static final ZoneId MOSCOW = ZoneId.of("Europe/Moscow");
    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");
    private static final Instant INSTANT = LocalDateTime.of(2008, 6, 30, 11, 30, 10, 500).atZone(ZoneOffset.ofHours(2)).toInstant();

    //-------------------------------------------------------------------------
    public void test_fixed_InstantZoneId() {
        Clock test = Clock.fixed(INSTANT, PARIS);
        assertEquals(test.instant(), INSTANT);
        assertEquals(test.getZone(), PARIS);
   assertEquals(test.instant().getEpochSecond()*1000, test.millis());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void test_fixed_InstantZoneId_nullInstant() {
        Clock.fixed(null, PARIS);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void test_fixed_InstantZoneId_nullZoneId() {
        Clock.fixed(INSTANT, null);
    }

    //-------------------------------------------------------------------------
    public void test_withZone() {
        Clock test = Clock.fixed(INSTANT, PARIS);
        Clock changed = test.withZone(MOSCOW);
        assertEquals(test.getZone(), PARIS);
        assertEquals(changed.getZone(), MOSCOW);
    }

    public void test_withZone_equal() {
        Clock test = Clock.fixed(INSTANT, PARIS);
        Clock changed = test.withZone(PARIS);
        assertEquals(changed.getZone(), PARIS);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void test_withZone_null() {
        Clock.fixed(INSTANT, PARIS).withZone(null);
    }

    //-----------------------------------------------------------------------
    public void test_equals() {
        Clock a = Clock.fixed(INSTANT, ZoneOffset.UTC);
        Clock b = Clock.fixed(INSTANT, ZoneOffset.UTC);
        assertEquals(a.equals(a), true);
        assertEquals(a.equals(b), true);
        assertEquals(b.equals(a), true);
        assertEquals(b.equals(b), true);

        Clock c = Clock.fixed(INSTANT, PARIS);
        assertEquals(a.equals(c), false);

        Clock d = Clock.fixed(INSTANT.minusNanos(1), ZoneOffset.UTC);
        assertEquals(a.equals(d), false);

        assertEquals(a.equals(null), false);
        assertEquals(a.equals("other type"), false);
        assertEquals(a.equals(Clock.systemUTC()), false);
    }

    public void test_hashCode() {
        Clock a = Clock.fixed(INSTANT, ZoneOffset.UTC);
        Clock b = Clock.fixed(INSTANT, ZoneOffset.UTC);
        assertEquals(a.hashCode(), a.hashCode());
        assertEquals(a.hashCode(), b.hashCode());

        Clock c = Clock.fixed(INSTANT, PARIS);
        assertEquals(a.hashCode() == c.hashCode(), false);

        Clock d = Clock.fixed(INSTANT.minusNanos(1), ZoneOffset.UTC);
        assertEquals(a.hashCode() == d.hashCode(), false);
    }
}
