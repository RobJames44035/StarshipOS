/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4851640
 * @summary Make sure not to set UNSET fields to COMPUTED after time calculation.
 */

import java.util.GregorianCalendar;
import static java.util.Calendar.*;

public class Bug4851640 {

    public static void main(String args[]) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.clear();
        cal.set(YEAR, 2003);
        long t = cal.getTime().getTime();

        // For the time calculation, the MONTH and DAY_OF_MONTH fields
        // (with the default values) have been used for determining
        // the date.  However, both the MONTH and DAY_OF_MONTH fields
        // should be kept UNSET after the time calculation.
        if (cal.isSet(MONTH) || cal.isSet(DAY_OF_MONTH)) {
            throw new RuntimeException("After getTime(): MONTH field=" + cal.isSet(MONTH)
                                       + ", DAY_OF_MONTH field=" + cal.isSet(DAY_OF_MONTH));
        }

        // After calling get() for any field, all field values are
        // recalculated and their field states are set to
        // COMPUTED. isSet() must return true.
        int y = cal.get(YEAR);
        if (!(cal.isSet(MONTH) && cal.isSet(DAY_OF_MONTH))) {
            throw new RuntimeException("After get(): MONTH field=" + cal.isSet(MONTH)
                                       + ", DAY_OF_MONTH field=" + cal.isSet(DAY_OF_MONTH));
        }
    }
}
