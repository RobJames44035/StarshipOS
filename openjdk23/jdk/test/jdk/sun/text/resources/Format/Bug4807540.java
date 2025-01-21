/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test %i%
 * @bug 4807540 8008577 8174269
 * @modules jdk.localedata
 * @summary updating dateformat for sl_SI
 * @run main Bug4807540
 */

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class Bug4807540 {

    public static void main(String[] args) {
        Locale si = Locale.of("sl", "si");

        String expected = "30. apr. 2008";
        DateFormat dfSi = DateFormat.getDateInstance (DateFormat.MEDIUM, si);

        String siString = new String (dfSi.format(new Date(108, Calendar.APRIL, 30)));

        if (expected.compareTo(siString) != 0) {
            throw new RuntimeException("Error: " + siString  + " should be " + expected);
        }
    }
}
