/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) throws Exception {
        int errors = 0;

        for (String loctag : args) {
            Locale locale = Locale.forLanguageTag(loctag);
            if (locale.equals(Locale.ROOT)) {
                continue;
            }
            ResourceBundle rb = ResourceBundle.getBundle("jdk.test.resources.MyResources",
                                                         locale);
            String tag = locale.toLanguageTag(); // normalized
            String value = rb.getString("key");
            System.out.println("locale = " + locale + ", value = " + value);
            if (!value.startsWith(tag + ':')) {
                errors++;
            }
        }
        if (errors > 0) {
            throw new RuntimeException(errors + " errors");
        }
    }
}
