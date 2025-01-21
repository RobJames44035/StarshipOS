/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.testng.annotations.Test;

/**
 * Test system clock.
 */
@Test
public class TCKClock_System extends AbstractTCKTest {

    private static final ZoneId MOSCOW = ZoneId.of("Europe/Moscow");
    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    //-----------------------------------------------------------------------
    public void test_instant() {
        Clock system = Clock.systemUTC();
        assertEquals(system.getZone(), ZoneOffset.UTC);
        for (int i = 0; i < 10000; i++) {
            // assume can eventually get these within 10 milliseconds
            Instant instant = system.instant();
            long systemMillis = System.currentTimeMillis();
            if (systemMillis - instant.toEpochMilli() < 10) {
                return;  // success
            }
        }
        fail();
    }

    public void test_millis() {
        Clock system = Clock.systemUTC();
        assertEquals(system.getZone(), ZoneOffset.UTC);
        for (int i = 0; i < 10000; i++) {
            // assume can eventually get these within 10 milliseconds
            long instant = system.millis();
            long systemMillis = System.currentTimeMillis();
            if (systemMillis - instant < 10) {
                return;  // success
            }
        }
        fail();
    }

    //-------------------------------------------------------------------------
    public void test_systemUTC() {
        Clock test = Clock.systemUTC();
        assertEquals(test.getZone(), ZoneOffset.UTC);
        assertEquals(test, Clock.system(ZoneOffset.UTC));
    }

    public void test_systemDefaultZone() {
        Clock test = Clock.systemDefaultZone();
        assertEquals(test.getZone(), ZoneId.systemDefault());
        assertEquals(test, Clock.system(ZoneId.systemDefault()));
    }

    public void test_system_ZoneId() {
        Clock test = Clock.system(PARIS);
        assertEquals(test.getZone(), PARIS);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void test_zoneId_nullZoneId() {
        Clock.system(null);
    }

    //-------------------------------------------------------------------------
    public void test_withZone() {
        Clock test = Clock.system(PARIS);
        Clock changed = test.withZone(MOSCOW);
        assertEquals(test.getZone(), PARIS);
        assertEquals(changed.getZone(), MOSCOW);
    }

    public void test_withZone_equal() {
        Clock test = Clock.system(PARIS);
        Clock changed = test.withZone(PARIS);
        assertEquals(changed.getZone(), PARIS);
    }

    public void test_withZone_fromUTC() {
        Clock test = Clock.systemUTC();
        Clock changed = test.withZone(PARIS);
        assertEquals(changed.getZone(), PARIS);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void test_withZone_null() {
        Clock.systemUTC().withZone(null);
    }

    //-----------------------------------------------------------------------
    public void test_equals() {
        Clock a = Clock.systemUTC();
        Clock b = Clock.systemUTC();
        assertEquals(a.equals(a), true);
        assertEquals(a.equals(b), true);
        assertEquals(b.equals(a), true);
        assertEquals(b.equals(b), true);

        Clock c = Clock.system(PARIS);
        Clock d = Clock.system(PARIS);
        assertEquals(c.equals(c), true);
        assertEquals(c.equals(d), true);
        assertEquals(d.equals(c), true);
        assertEquals(d.equals(d), true);

        assertEquals(a.equals(c), false);
        assertEquals(c.equals(a), false);

        assertEquals(a.equals(null), false);
        assertEquals(a.equals("other type"), false);
        assertEquals(a.equals(Clock.fixed(Instant.now(), ZoneOffset.UTC)), false);
    }

    public void test_hashCode() {
        Clock a = Clock.system(ZoneOffset.UTC);
        Clock b = Clock.system(ZoneOffset.UTC);
        assertEquals(a.hashCode(), a.hashCode());
        assertEquals(a.hashCode(), b.hashCode());

        Clock c = Clock.system(PARIS);
        assertEquals(a.hashCode() == c.hashCode(), false);
    }

}
