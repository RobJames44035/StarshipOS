/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */


import java.util.Locale;
import java.util.MissingResourceException;
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
            System.out.println(rb.getBaseBundleName() + ": locale = " + tag + ", value = " + value);
            if (!value.startsWith(tag + ':')) {
                System.out.println("ERROR: " + value + " expected: " + tag);
                errors++;
            }

            // inaccessible bundles
            try {
                ResourceBundle.getBundle("jdk.test.internal.resources.Foo", locale);
                System.out.println("ERROR: jdk.test.internal.resources.Foo should not be accessible");
                errors++;
            } catch (MissingResourceException e) {
                e.printStackTrace();

                Throwable cause = e.getCause();
                System.out.println("Expected: " +
                    (cause != null ? cause.getMessage() : e.getMessage()));
            }
        }
        if (errors > 0) {
            throw new RuntimeException(errors + " errors");
        }
    }
}
