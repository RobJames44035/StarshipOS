/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/* @test
   @bug 8263481
   @summary Verifies calling setDefaultLocale(null) will reset
            to VM's default locale
   @run main TestNullLocale
 */

import javax.swing.JComponent;
import java.util.Locale;

public class TestNullLocale {
    public static void main(String[] args) {
        Locale defaultLocale = JComponent.getDefaultLocale();
        JComponent.setDefaultLocale(Locale.GERMAN);
        JComponent.setDefaultLocale(null);
        Locale currentLocale = JComponent.getDefaultLocale();
        if (defaultLocale != currentLocale) {
            System.out.println("currentLocale " + currentLocale);
            System.out.println("defaultLocale " + defaultLocale);
            throw new RuntimeException("locale not reset to default locale");
        }
    }
}


