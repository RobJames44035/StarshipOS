/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4651568 8008577 8174269
 * @modules jdk.localedata
 * @summary Verifies the currency pattern for pt_BR locale
 * @run main Bug4651568
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Bug4651568 {

    public static void main (String argv[] )  {
        Locale reservedLocale = Locale.getDefault();
        try {
            String expectedCurrencyPattern = "\u00a4\u00a0#.##0,00";

            Locale locale = new Locale ("pt", "BR");
            Locale.setDefault(locale);

            DecimalFormat formatter =
                (DecimalFormat) NumberFormat.getCurrencyInstance(locale);

            if (formatter.toLocalizedPattern().equals(
                        expectedCurrencyPattern)) {
                System.out.println ("Passed.");
            } else {
                 System.out.println ("Failed Currency pattern." +
                        "  Expected:  " + expectedCurrencyPattern +
                        "  Received:  " + formatter.toLocalizedPattern() );
                 throw new RuntimeException();

            }
        } finally {
            // restore the reserved locale
            Locale.setDefault(reservedLocale);
        }
    }
}
