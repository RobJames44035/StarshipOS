/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4736959
 * @summary Make sure to parse "PM" (only) and produce the correct value.
 */

import java.text.*;
import java.util.*;

@SuppressWarnings("deprecation")
public class Bug4736959 {
    /**
     * 4736959: JSpinner won't work for AM/PM field
     */
    public static void main(String[] args) {
        SimpleDateFormat f = new SimpleDateFormat("a", Locale.US);

        Date d1 = f.parse("AM", new ParsePosition(0));
        System.out.println("d1: " + d1);
        if (d1.getHours() != 0) {
            throw new RuntimeException("Parsing \"AM\": expected 0 (midnight), got " +
                                       d1.getHours());
        }
        Date d2 = f.parse("PM", new ParsePosition(0));
        System.out.println("d2: " + d2);
        if (d2.getHours() != 12) {
            throw new RuntimeException("Parsing \"PM\": expected 12 (noon), got " +
                                       d2.getHours());
        }
    }
}
