/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
/*
 * @test
 * @modules jdk.localedata
 * @bug 8303440 8317979 8322647 8174269
 * @summary Test parsing "UTC-XX:XX" text works correctly
 */
package test.java.time.format;

import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.TemporalQueries;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class TestUTCParse {

    @DataProvider
    public Object[][] utcZoneIdStrings() {
        return new Object[][] {
            {"UTC"},
            {"UTC+01:30"},
            {"UTC-01:30"},
        };
    }

    @Test(dataProvider = "utcZoneIdStrings")
    public void testUTCOffsetRoundTrip(String zidString) {
        var fmt = new DateTimeFormatterBuilder()
                .appendZoneText(TextStyle.NARROW)
                .toFormatter();
        var zid = ZoneId.of(zidString);
        assertEquals(fmt.parse(zidString).query(TemporalQueries.zoneId()), zid);
    }
}
