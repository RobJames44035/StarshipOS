/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 * @test
 * @bug 4965260 8008577 8287868 8174269
 * @modules jdk.localedata
 * @summary Verifies the language name of "nl" for supported locales
 * @run main Bug4965260
 */

import java.util.Locale;

public class Bug4965260  {

    // Define supported locales
    static Locale[] locales2Test = new Locale[] {
        Locale.GERMAN,
        Locale.of("es"),
        Locale.FRENCH,
        Locale.ITALIAN,
        Locale.of("sv")
    };

    static String[] expectedNames = new String[] {
        "Niederl\u00e4ndisch",
        "neerland\u00e9s",
        "n\u00e9erlandais",
        "olandese",
        "nederl\u00e4ndska"
    };

    public static void main(String[] args) throws Exception {
        Locale reservedLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.ENGLISH);
            if (locales2Test.length != expectedNames.length) {
                throw new Exception("\nData sizes does not match!\n");
            }

            StringBuffer message = new StringBuffer("");
            Locale dutch = Locale.of("nl", "BE");
            String current;
            for (int i = 0; i < locales2Test.length; i++) {
                Locale locale = locales2Test[i];
                current = dutch.getDisplayLanguage(locale);
                if (!current.equals(expectedNames[i])) {
                    message.append("[");
                    message.append(locale.getDisplayLanguage());
                    message.append("] ");
                    message.append("Language name is ");
                    message.append(current);
                    message.append(" should be ");
                    message.append(expectedNames[i]);
                    message.append("\n");
                }
            }

            if (message.length() >0) {
                throw new Exception("\n" + message.toString());
            }
        } finally {
            // restore the reserved locale
            Locale.setDefault(reservedLocale);
        }
    }
}
