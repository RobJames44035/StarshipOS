/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 * @test
 * @bug 4396021
 * @summary Verify that a resource bundle can override its parent.
 * @build Bug4396021GeneralMessages Bug4396021SpecialMessages
 * @run main Bug4396021
 */

import java.util.ResourceBundle;

public class Bug4396021 {

    private static ResourceBundle bundle;

    public static void main(String[] args) throws Exception {
        bundle = ResourceBundle.getBundle("Bug4396021SpecialMessages");

        checkValue("special_key", "special_value");
        checkValue("general_key", "general_value");
    }

    private static void checkValue(String key, String expected) throws Exception {
        String result = bundle.getString(key);
        if (!result.equals(expected)) {
            throw new RuntimeException("Got wrong value from resource bundle"
                    + " - key: " + key + ", expected: " + expected
                    + ", got: " + result);
        }
    }
}
