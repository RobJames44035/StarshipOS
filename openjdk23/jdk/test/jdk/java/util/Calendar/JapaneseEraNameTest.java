/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8202088 8207152 8217609 8219890
 * @summary Test the localized Japanese new era name (May 1st. 2019-)
 *      is retrieved no matter CLDR provider contains the name or not.
 * @modules jdk.localedata
 * @run testng/othervm JapaneseEraNameTest
 * @run testng/othervm -Djava.locale.providers=CLDR JapaneseEraNameTest
 */

import static java.util.Calendar.*;
import static java.util.Locale.*;
import java.util.Calendar;
import java.util.Locale;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

@Test
public class JapaneseEraNameTest {
    static final Calendar c = new Calendar.Builder()
            .setCalendarType("japanese")
            .setFields(ERA, 5, YEAR, 1, MONTH, MAY, DAY_OF_MONTH, 1)
            .build();

    @DataProvider(name="names")
    Object[][] names() {
        return new Object[][] {
            // type,    locale,  name
            { LONG,     JAPAN,   "\u4ee4\u548c" },
            { LONG,     US,      "Reiwa" },
            { LONG,     CHINA,   "\u4ee4\u548c" },
            { SHORT,    JAPAN,   "\u4ee4\u548c" },
            { SHORT,    US,      "Reiwa" },
            { SHORT,    CHINA,   "\u4ee4\u548c" },
        };
    }

    @Test(dataProvider="names")
    public void testJapaneseNewEraName(int type, Locale locale, String expected) {
        assertEquals(c.getDisplayName(ERA, type, locale), expected);
    }
}
