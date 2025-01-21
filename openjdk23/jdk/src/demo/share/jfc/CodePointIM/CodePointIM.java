/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */



import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class CodePointIM {

    // Actually, the main method is not required for InputMethod.
    // The following method is added just to tell users that their use is
    // not correct and encourage their reading README.txt.
    public static void main(String[] args) {
        try {
            ResourceBundle resource = ResourceBundle.getBundle(
                    "resources.codepoint",
                    Locale.getDefault());
            System.err.println(resource.getString("warning"));
        } catch (MissingResourceException e) {
            System.err.println(e.toString());
        }

        System.exit(1);
    }
}
