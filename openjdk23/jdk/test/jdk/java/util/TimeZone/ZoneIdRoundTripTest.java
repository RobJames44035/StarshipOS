/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.assertEquals;

/**
 * @test
 * @bug 8285844
 * @summary Checks round-trips between TimeZone and ZoneId are consistent
 * @run testng ZoneIdRoundTripTest
 */
@Test
public class ZoneIdRoundTripTest {

    @DataProvider
    private Object[][] testZoneIds() {
        return new Object[][] {
                {ZoneId.of("Z"), 0},
                {ZoneId.of("UT"), 0},
                {ZoneId.of("UTC"), 0},
                {ZoneId.of("GMT"), 0},
                {ZoneId.of("+00:01"), 60_000},
                {ZoneId.of("-00:01"), -60_000},
                {ZoneId.of("+00:00:01"), 1_000},
                {ZoneId.of("-00:00:01"), -1_000},
                {ZoneId.of("UT+00:00:01"), 1_000},
                {ZoneId.of("UT-00:00:01"), -1_000},
                {ZoneId.of("UTC+00:00:01"), 1_000},
                {ZoneId.of("UTC-00:00:01"), -1_000},
                {ZoneId.of("GMT+00:00:01"), 1_000},
                {ZoneId.of("GMT-00:00:01"), -1_000},
                {ZoneOffset.of("+00:00:01"), 1_000},
                {ZoneOffset.of("-00:00:01"), -1_000},
        };
    }

    @Test(dataProvider="testZoneIds")
    public void test_ZoneIdRoundTrip(ZoneId zid, int offset) {
        var tz = TimeZone.getTimeZone(zid);
        assertEquals(tz.getRawOffset(), offset);
        assertEquals(tz.toZoneId().normalized(), zid.normalized());
    }
}

