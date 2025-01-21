/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8003267
 * @summary Unit test for generic time zone names support. This test is locale
 *          data-dependent and assumes that both JRE and CLDR have the same
 *          geneic time zone names in English.
 * @modules java.base/sun.util.locale.provider
 * @comment Locale providers: default
 * @run main GenericTimeZoneNamesTest en-US
 * @comment Locale providers: CLDR
 * @run main/othervm -Djava.locale.providers=CLDR GenericTimeZoneNamesTest en-US
*/

import java.util.Locale;
import java.util.TimeZone;

import sun.util.locale.provider.TimeZoneNameUtility;

public class GenericTimeZoneNamesTest {
    private static final String[] PT = {
        "America/Los_Angeles", "US/Pacific", "PST"
    };

    private static int errors = 0;

    public static void main(String[] args) {
        for (String tag : args) {
            Locale locale = Locale.forLanguageTag(tag);
            for (String tzid : PT) {
                test(tzid, TimeZone.LONG, locale, "Pacific Time");
                test(tzid, TimeZone.SHORT, locale, "PT");
            }
        }

        if (errors != 0) {
            throw new RuntimeException("test failed");
        }
    }

    private static void test(String tzid, int style, Locale locale, String expected) {
        // No public API to get generic time zone names (JDK 8)
        String got = TimeZoneNameUtility.retrieveGenericDisplayName(tzid, style, locale);
        if (!expected.equals(got)) {
            System.err.printf("test: tzid=%s, locale=%s, style=%d, got=\"%s\", expected=\"%s\"%n",
                              tzid, locale, style, got, expected);
            errors++;
        }
    }
}
