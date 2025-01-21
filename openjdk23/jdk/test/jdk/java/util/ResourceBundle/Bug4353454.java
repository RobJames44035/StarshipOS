/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 * @test
 * @bug 4353454
 * @summary Test if the second getBundle call finds a bundle in the default Locale search path.
 */

import java.util.ResourceBundle;
import java.util.Locale;

public class Bug4353454 {

    public static void main(String[] args) {
        Locale l = Locale.getDefault();
        try {
            Locale.setDefault(Locale.US);
            test();
            test();
        } finally {
            Locale.setDefault(l);
        }
    }

    private static void test() {
        ResourceBundle myResources = ResourceBundle.getBundle("RB4353454", Locale.of(""));
        if (!"Got it!".equals(myResources.getString("text"))) {
            throw new RuntimeException("returned wrong resource for key 'text': "
                                       + myResources.getString("text"));
        }
    }
}
