/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6448234
 * @summary Make sure indexing of DAY_OF_WEEK is correct in JapaneseImperialCalendar.getDisplayName.
 */

import java.util.Calendar;
import java.util.Locale;
import static java.util.Calendar.*;

public class Bug6448234 {
    public static void main(String[] args) {
        Calendar jcal = Calendar.getInstance(Locale.of("ja", "JP", "JP"));
        Calendar gcal = Calendar.getInstance(Locale.US);

        for (int i = SUNDAY; i <= SATURDAY; i++) {
            jcal.set(DAY_OF_WEEK, i);
            gcal.set(DAY_OF_WEEK, i);

            // Test LONG
            String j = jcal.getDisplayName(DAY_OF_WEEK, LONG, Locale.US);
            String g = gcal.getDisplayName(DAY_OF_WEEK, LONG, Locale.US);
            if (!j.equals(g)) {
                throw new RuntimeException("Got " + j + ", expected " + g);
            }

            // Test SHORT
            j = jcal.getDisplayName(DAY_OF_WEEK, SHORT, Locale.US);
            g = gcal.getDisplayName(DAY_OF_WEEK, SHORT, Locale.US);
            if (!j.equals(g)) {
                throw new RuntimeException("Got " + j + ", expected " + g);
            }
        }
    }
}
