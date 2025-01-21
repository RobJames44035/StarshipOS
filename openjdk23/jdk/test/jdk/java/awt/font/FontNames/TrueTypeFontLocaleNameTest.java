/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8043509
 * @summary Test locale famlly falls back to same language before English
 * @run main/othervm TrueTypeFontLocaleNameTest
 */

import java.awt.Font;
import java.util.Locale;

public class TrueTypeFontLocaleNameTest {

    public static void main(String[] args) {

        String os = System.getProperty("os.name", "");
        if (!os.toLowerCase().startsWith("win")) {
            return;
        }
        System.setProperty("user.language", "de");
        System.setProperty("user.country", "AT");
        Locale de_atLocale = Locale.of("de", "AT");
        Locale.setDefault(de_atLocale);

        String family = "Verdana";
        Font font = new Font(family, Font.BOLD, 12);
        if (!font.getFamily(Locale.ENGLISH).equals(family)) {
            System.out.println(family + " not found - skipping test.");
            return;
        }

        String atFontName = font.getFontName();
        Locale deGELocale = Locale.of("de", "GE");
        String deFontName = font.getFontName(deGELocale);
        System.out.println("Austrian font name: " + atFontName);
        System.out.println("German font name: " + deFontName);

        String deLangFullName = "Verdana Fett";
        // We expect "Fett" for "Bold" when the language is German.
        // This font does have that so these should both be equal and
        // say "Verdana Fett"
        if (!deFontName.equals(atFontName)) {
            throw new RuntimeException("Font names differ " +
                                       deFontName + " " + atFontName);
        }
        if (!deLangFullName.equals(deFontName)) {
            throw new RuntimeException("Font name is not " + deLangFullName +
                                       " instead got " + deFontName);
        }
    }
}
