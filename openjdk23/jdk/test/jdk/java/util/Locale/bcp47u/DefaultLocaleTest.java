/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.util.Locale;

/*
 * Test application that verifies default locales. Invoked from
 * SystemPropertyTests
 */
public class DefaultLocaleTest {
    public static void main(String... args) {
        String defLoc = Locale.getDefault().toString();
        String defFmtLoc = Locale.getDefault(Locale.Category.FORMAT).toString();
        String defDspLoc = Locale.getDefault(Locale.Category.DISPLAY).toString();

        if (!defLoc.equals(args[0]) ||
            !defFmtLoc.equals(args[1]) ||
            !defDspLoc.equals(args[2])) {
            System.err.println("Some default locale(s) don't match.\n" +
                "Default Locale expected: " + args[0] + ", result: " + defLoc + "\n" +
                "Default Format Locale expected: " + args[1] + ", result: " + defFmtLoc + "\n" +
                "Default Display Locale expected: " + args[2] + ", result: " + defDspLoc);
            System.exit(-1);
        }
    }
}
