/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8074791
 * @modules jdk.localedata
 * @summary Make sure that Finnish month names are correct in formatted text.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import static java.text.DateFormat.LONG;
import static java.util.Calendar.JANUARY;

public class Bug8074791 {
    private static Locale FINNISH = Locale.of("fi");
    private static String JAN_FORMAT = "tammikuuta";
    private static String JAN_STANDALONE = "tammikuu";

    public static void main(String[] arg) {
        int errors = 0;

        DateFormat df = DateFormat.getDateInstance(LONG, FINNISH);
        Date jan20 = new GregorianCalendar(2015, JANUARY, 20).getTime();
        String str = df.format(jan20).toString();
        // Extract the month name (locale data dependent)
        String month = str.replaceAll(".+\\s([a-z]+)\\s\\d+$", "$1");
        if (!month.equals(JAN_FORMAT)) {
            errors++;
            System.err.println("wrong format month name: got '" + month
                               + "', expected '" + JAN_FORMAT + "'");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("LLLL", FINNISH); // stand-alone month name
        month = sdf.format(jan20);
        if (!month.equals(JAN_STANDALONE)) {
            errors++;
            System.err.println("wrong stand-alone month name: got '" + month
                               + "', expected '" + JAN_STANDALONE + "'");
        }

        if (errors > 0) {
            throw new RuntimeException();
        }
    }
}
