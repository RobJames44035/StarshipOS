/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8208080
 * @summary Tests DateFormatSymbols provider implementations
 * @library provider
 * @build provider/module-info provider/foo.DateFormatSymbolsProviderImpl
 * @run main/othervm -Djava.locale.providers=SPI,CLDR DateFormatSymbolsProviderTests
 */

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.Map;

/**
 * Test DateFormatSymbolsProvider SPI with BCP47 U extensions
 */
public class DateFormatSymbolsProviderTests {
    private static final Map<Locale, String> data = Map.of(
        Locale.forLanguageTag("en-AA"),                 "foo",
        Locale.forLanguageTag("en-US-u-rg-aazzzz"),     "foo",
        Locale.forLanguageTag("en-US-u-ca-japanese"),   "bar"
    );

    public static void main(String... args) {
        data.forEach((l, e) -> {
            DateFormatSymbols dfs = DateFormatSymbols.getInstance(l);
            String[] months = dfs.getMonths();
            System.out.printf("January string for locale %s is %s.%n", l.toString(), months[0]);
            if (!months[0].equals(e)) {
                throw new RuntimeException("DateFormatSymbols provider is not called for" + l);
            }
        });
    }
}
