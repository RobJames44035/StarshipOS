/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import static org.testng.Assert.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.assertEquals;

/**
 * @test
 * @bug 8212970 8324065
 * @summary Test whether the savings are positive in time zones that have
 *      negative savings in the source TZ files.
 * @run testng NegativeDSTTest
 */
@Test
public class NegativeDSTTest {

    private static final TimeZone DUBLIN = TimeZone.getTimeZone("Europe/Dublin");
    private static final TimeZone PRAGUE = TimeZone.getTimeZone("Europe/Prague");
    private static final TimeZone WINDHOEK = TimeZone.getTimeZone("Africa/Windhoek");
    private static final TimeZone CASABLANCA = TimeZone.getTimeZone("Africa/Casablanca");
    private static final int ONE_HOUR = 3600_000;

    @DataProvider
    private Object[][] negativeDST () {
        return new Object[][] {
            // TimeZone, localDate, offset, isDaylightSavings
            // Europe/Dublin for the Rule "Eire"
            {DUBLIN, LocalDate.of(1970, 6, 23), ONE_HOUR, true},
            {DUBLIN, LocalDate.of(1971, 6, 23), ONE_HOUR, true},
            {DUBLIN, LocalDate.of(1971, 11, 1), 0, false},
            {DUBLIN, LocalDate.of(2019, 6, 23), ONE_HOUR, true},
            {DUBLIN, LocalDate.of(2019, 12, 23), 0, false},

            // Europe/Prague which contains fixed negative savings (not a named Rule)
            {PRAGUE, LocalDate.of(1946, 9, 30), 2 * ONE_HOUR, true},
            {PRAGUE, LocalDate.of(1946, 10, 10), ONE_HOUR, false},
            {PRAGUE, LocalDate.of(1946, 12, 3), 0, false},
            {PRAGUE, LocalDate.of(1947, 2, 25), ONE_HOUR, false},
            {PRAGUE, LocalDate.of(1947, 4, 30), 2 * ONE_HOUR, true},

            // Africa/Windhoek for the Rule "Namibia"
            {WINDHOEK, LocalDate.of(1994, 3, 23), ONE_HOUR, false},
            {WINDHOEK, LocalDate.of(2016, 9, 23), 2 * ONE_HOUR, true},

            // Africa/Casablanca for the Rule "Morocco" Defines negative DST till 2037 as of 2019a.
            {CASABLANCA, LocalDate.of(1939, 9, 13), ONE_HOUR, true},
            {CASABLANCA, LocalDate.of(1939, 11, 20), 0, false},
            {CASABLANCA, LocalDate.of(2018, 6, 18), ONE_HOUR, true},
            {CASABLANCA, LocalDate.of(2019, 1, 1), ONE_HOUR, true},
            {CASABLANCA, LocalDate.of(2019, 5, 6), 0, false},
            {CASABLANCA, LocalDate.of(2037, 10, 5), 0, false},
            {CASABLANCA, LocalDate.of(2037, 11, 16), ONE_HOUR, true},
            {CASABLANCA, LocalDate.of(2038, 9, 27), 0, false},
            {CASABLANCA, LocalDate.of(2038, 11, 1), ONE_HOUR, true},
            {CASABLANCA, LocalDate.of(2087, 3, 31), 0, false},
            {CASABLANCA, LocalDate.of(2087, 5, 12), ONE_HOUR, true},
        };
    }

    @Test(dataProvider="negativeDST")
    public void test_NegativeDST(TimeZone tz, LocalDate ld, int offset, boolean isDST) {
        Date d = Date.from(Instant.from(ZonedDateTime.of(ld, LocalTime.MIN, tz.toZoneId())));
        assertEquals(tz.getOffset(d.getTime()), offset);
        assertEquals(tz.inDaylightTime(d), isDST);
    }
}
