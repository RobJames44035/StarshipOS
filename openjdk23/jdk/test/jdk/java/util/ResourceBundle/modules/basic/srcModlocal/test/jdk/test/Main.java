/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.MissingResourceException;

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
            System.out.println("locale = " + tag + ", value = " + value);
            if (!value.startsWith(tag + ':')) {
                System.out.println("ERROR: " + value + " expected: " + tag);
                errors++;
            }
        }

        // Make sure ResourceBundle.getBundle throws an UnsupportedOperationException with
        // a ResourceBundle.Control.
        try {
            ResourceBundle rb;
            rb = ResourceBundle.getBundle("jdk.test.resources.MyResources",
                                          Locale.ENGLISH,
                                          Control.getControl(Control.FORMAT_DEFAULT));
            System.out.println("ERROR: no UnsupportedOperationException thrown with a ResourceBundle.Control");
            errors++;
        } catch (UnsupportedOperationException e) {
            // OK
        }

        if (errors > 0) {
            throw new RuntimeException(errors + " errors");
        }
    }
}
