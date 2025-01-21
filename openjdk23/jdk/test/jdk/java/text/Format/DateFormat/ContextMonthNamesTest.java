/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7079560 8008577 8174269
 * @summary Unit test for context-sensitive month names
 * @modules jdk.localedata
 * @run main ContextMonthNamesTest
 */

import java.text.*;
import java.util.*;

public class ContextMonthNamesTest {
    static Locale CZECH = Locale.of("cs");
    static Date JAN30 = new GregorianCalendar(2012, Calendar.JANUARY, 30).getTime();

    static String[] PATTERNS = {
        "d. MMMM yyyy", // format
        "d. MMM yyyy",  // format (abbr)
        "MMMM",         // stand-alone
        "MMM",          // stand-alone (abbr)
        "d. LLLL yyyy", // force stand-alone
        "d. LLL yyyy",  // force stand-alone (abbr)
    };
    // NOTE: expected results are locale data dependent.
    static String[] EXPECTED = {
        "30. ledna 2012",
        "30. led 2012",
        "leden",
        "led",
        "30. leden 2012",
        "30. led 2012",
    };

    public static void main(String[] args) {
        SimpleDateFormat fmt = new SimpleDateFormat("", CZECH);
        for (int i = 0; i < PATTERNS.length; i++) {
            fmt.applyPattern(PATTERNS[i]);
            String str = fmt.format(JAN30);
            if (!EXPECTED[i].equals(str)) {
                throw new RuntimeException("bad result: got '" + str
                                           + "', expected '" + EXPECTED[i] + "'");
            }
        }
    }
}
