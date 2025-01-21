/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8282227 8174269
 * @modules jdk.localedata
 * @summary Checks Norwegian locale fallback retrieves resource bundles correctly.
 * @run main NorwegianFallbackTest nb
 * @run main NorwegianFallbackTest nn
 * @run main NorwegianFallbackTest no
 */

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;
import static java.util.Calendar.SUNDAY;

public class NorwegianFallbackTest {

    private final static String SUN_ROOT = DateFormatSymbols.getInstance(Locale.ROOT).getShortWeekdays()[SUNDAY];
    private final static List<Locale> TEST_LOCS = List.of(
            Locale.forLanguageTag("nb"),
            Locale.forLanguageTag("nn"),
            Locale.forLanguageTag("no")
    );

    public static void main(String... args) {
        // Dummy instance
        var startup_loc = Locale.forLanguageTag(args[0]);
        DateFormatSymbols.getInstance(startup_loc);

        TEST_LOCS.stream()
            .peek(l -> System.out.print("Testing locale: " + l + ", (startup locale: " + startup_loc + ")... "))
            .map(l -> DateFormatSymbols.getInstance(l).getShortWeekdays()[SUNDAY])
            .forEach(sun -> {
                if (sun.equals(SUN_ROOT)) {
                    throw new RuntimeException("Norwegian fallback failed");
                } else {
                    System.out.println("Got " + "\"" + sun + "\" for Sunday short name");
                }
            });
    }
}
