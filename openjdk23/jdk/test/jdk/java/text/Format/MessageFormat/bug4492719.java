/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 *
 * @bug 4492719
 * @summary Confirm that Message.parse() interprets time zone which uses "GMT+/-" format correctly and doesn't throw ParseException.
 * @run junit/othervm bug4492719
 */

import java.util.*;
import java.text.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.fail;

public class bug4492719 {

    // MessageFormat.parse() should be able to interpret a time zone
    // that uses "GMT+/-".
    @Test
    public void testParse() throws Exception {
        Locale savedLocale = Locale.getDefault();
        TimeZone savedTimeZone = TimeZone.getDefault();
        MessageFormat mf;
        boolean err =false;

        String[] formats = {
                "short", "medium", "long", "full"
        };
        String[] timezones = {
                "America/Los_Angeles", "GMT", "GMT+09:00", "GMT-8:00",
                "GMT+123", "GMT-1234", "GMT+2", "GMT-13"
        };
        String text;

        Locale.setDefault(Locale.US);

        try {
            for (int i = 0; i < timezones.length; i++) {
                TimeZone.setDefault(TimeZone.getTimeZone(timezones[i]));

                for (int j = 0; j < formats.length; j++) {
                    mf = new MessageFormat("{0,time," + formats[j] + "} - time");
                    text = MessageFormat.format("{0,time," + formats[j] + "} - time",
                            new Object [] { new Date(123456789012L)});
                    Object[] objs = mf.parse(text);
                }
            }
        } catch (ParseException e) {
            err = true;
            System.err.println("Invalid ParseException occurred : " +
                    e.getMessage());
            System.err.println("    TimeZone=" + TimeZone.getDefault());
        }
        finally {
            Locale.setDefault(savedLocale);
            TimeZone.setDefault(savedTimeZone);
            if (err) {
                throw new Exception("MessageFormat.parse(\"GMT format\") failed.");
            }
        }
    }
}
