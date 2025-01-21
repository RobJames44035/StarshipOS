/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug      4108737
 * @summary  java.util.Date doesn't fail if current TimeZone is changed
 */

import java.util.TimeZone;
import java.util.Date;

public class TZ {

    public static void main(String args[]) {
        TimeZone tz = TimeZone.getDefault();
        try {
            testMain();
        } finally {
            TimeZone.setDefault(tz);
        }
    }

    static void testMain() {
        String expectedResult = "Sat Feb 01 00:00:00 PST 1997";

        // load the java.util.Date class in the GMT timezone
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        new Date(); // load the class (to run static initializers)

        // use the class in different timezone
        TimeZone.setDefault(TimeZone.getTimeZone("PST"));
        @SuppressWarnings("deprecation")
        Date date = new Date(97, 1, 1);
        if (!date.toString().equals(expectedResult)) {
            throw new RuntimeException("Regression bug id #4108737 - Date fails if default time zone changed");
        }
    }
}
