/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8176847
 * @summary Make sure that style value of '3' throws IllegalArgumentException
 *          in Calendar.getDisplayName(s) methods.
 */

import java.util.Calendar;
import java.util.Locale;

public class Bug8176847 {
    public static void main(String[] args) {
        Calendar c = new Calendar.Builder().build();

        try {
            c.getDisplayName(Calendar.MONTH, 3, Locale.US);
            throw new RuntimeException("IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException iae) {
            // success
        }

        try {
            c.getDisplayNames(Calendar.MONTH, 3, Locale.US);
            throw new RuntimeException("IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException iae) {
            // success
        }
    }
}
