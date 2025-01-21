/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8139107 8174269
 * @summary Test that date parsing with DateTimeFormatter pattern
 *   that contains timezone field doesn't trigger NPE. All supported
 *   locales are tested.
 * @run testng Bug8139107
 */
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.testng.annotations.Test;

public class Bug8139107 {

    @Test
    public void testSupportedLocales() {
        for (Locale loc:Locale.getAvailableLocales()) {
            testLocale(loc);
        }
    }

    //Test one locale
    void testLocale(Locale tl) {
        System.out.println("Locale:" + tl);
        DateTimeFormatter inputDateTimeFormat = DateTimeFormatter
                .ofPattern(pattern)
                .withLocale(tl);
        System.out.println("Parse result: " + inputDateTimeFormat.parse(inputDate));
    }

    // Input date time string with short time zone name
    static final String inputDate = "06-10-2015 18:58:04 MSK";
    // Pattern with time zone field
    static final String pattern = "dd-MM-yyyy HH:mm:ss z";
}

