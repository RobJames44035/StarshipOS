/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
    @test
    @summary test that getBundle handles change in default locale correctly
    @bug 4318520
*/

/*
 *
 */

import java.util.ResourceBundle;
import java.util.Locale;

public class Test4318520 {

    public static void main(String[] args) {
        Locale reservedLocale = Locale.getDefault();
        try {
            test(Locale.GERMAN);
            test(Locale.ENGLISH);
        } finally {
            // restore the reserved locale
            Locale.setDefault(reservedLocale);
        }
    }

    private static void test(Locale locale) {
        Locale.setDefault(locale);
        ResourceBundle myResources =
                ResourceBundle.getBundle("Test4318520RB", Locale.FRENCH);
        String actualLocale = myResources.getString("name");
        if (!actualLocale.equals(locale.toString())) {
            System.out.println("expected: " + locale + ", got: " + actualLocale);
            throw new RuntimeException();
        }
    }
}
