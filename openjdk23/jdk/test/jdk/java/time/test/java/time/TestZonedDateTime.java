/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;

import org.testng.annotations.Test;

/**
 * Test ZonedDateTime.
 *
 * @bug 8211990
 */
@Test
public class TestZonedDateTime extends AbstractTest {

    @Test
    public void test_immutable() {
        assertImmutable(ZonedDateTime.class);
    }

    @Test
    public void test_duration() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        ZoneId sanJose = ZoneId.of("America/Los_Angeles");

        ZonedDateTime end = ZonedDateTime.of(LocalDateTime.MAX, sanJose);
        ZonedDateTime start = end.withZoneSameLocal(tokyo);

        assertEquals(Duration.between(start, end), Duration.ofHours(17));
    }
}
