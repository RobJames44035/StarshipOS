/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time;

import static org.testng.Assert.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.testng.annotations.Test;

/**
 * Test Clock.
 */
@Test
public class TCKClock {

    static class MockInstantClock extends Clock {
        final long millis;
        final ZoneId zone;
        MockInstantClock(long millis, ZoneId zone) {
            this.millis = millis;
            this.zone = zone;
        }
        @Override
        public long millis() {
            return millis;
        }
        @Override
        public Instant instant() {
            return Instant.ofEpochMilli(millis());
        }
        @Override
        public ZoneId getZone() {
            return zone;
        }
        @Override
        public Clock withZone(ZoneId timeZone) {
            return new MockInstantClock(millis, timeZone);
        }
        @Override
        public boolean equals(Object obj) {
            return false;
        }
        @Override
        public int hashCode() {
            return 0;
        }
        @Override
        public String toString() {
            return "Mock";
        }
    }

    private static final Instant INSTANT = Instant.ofEpochSecond(1873687, 357000000);
    private static final ZoneId ZONE = ZoneId.of("Europe/Paris");
    private static final Clock MOCK_INSTANT = new MockInstantClock(INSTANT.toEpochMilli(), ZONE);

    //-----------------------------------------------------------------------
    @Test
    public void test_mockInstantClock_get() {
        assertEquals(MOCK_INSTANT.instant(), INSTANT);
        assertEquals(MOCK_INSTANT.millis(), INSTANT.toEpochMilli());
        assertEquals(MOCK_INSTANT.getZone(), ZONE);
    }

    @Test
    public void test_mockInstantClock_withZone() {
        ZoneId london = ZoneId.of("Europe/London");
        Clock changed = MOCK_INSTANT.withZone(london);
        assertEquals(MOCK_INSTANT.instant(), INSTANT);
        assertEquals(MOCK_INSTANT.millis(), INSTANT.toEpochMilli());
        assertEquals(changed.getZone(), london);
    }

}
