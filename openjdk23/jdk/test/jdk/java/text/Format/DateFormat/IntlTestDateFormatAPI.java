/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @summary test International Date Format API
 * @bug 8008577 8174269
 * @run junit IntlTestDateFormatAPI
 */
import java.text.*;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.fail;

public class IntlTestDateFormatAPI
{

    // Test that the equals method works correctly.
    @Test
    public void TestEquals()
    {
        // Create two objects at different system times
        DateFormat a = DateFormat.getInstance();
        Date start = Calendar.getInstance().getTime();
        while (start.equals(Calendar.getInstance().getTime())) ; // Wait for time to change
        DateFormat b = DateFormat.getInstance();

        if (!(a.equals(b)))
            fail("FAIL: DateFormat objects created at different times are unequal.");

        if (b instanceof SimpleDateFormat)
        {
            double ONE_YEAR = 365*24*60*60*1000.0;
            try {
//                ((SimpleDateFormat)b).setTwoDigitStartDate(start.getTime() + 50*ONE_YEAR);
//                if (a.equals(b))
//                    errln("FAIL: DateFormat objects with different two digit start dates are equal.");
            }
            catch (Exception e) {
                fail("FAIL: setTwoDigitStartDate failed.");
            }
        }
    }

    // This test checks various generic API methods in DateFormat to achieve 100% API coverage.
    @Test
    public void TestAPI()
    {
        System.out.println("DateFormat API test---"); System.out.println("");
        Locale.setDefault(Locale.ENGLISH);


        // ======= Test constructors

        System.out.println("Testing DateFormat constructors");

        DateFormat def = DateFormat.getInstance();
        DateFormat fr = DateFormat.getTimeInstance(DateFormat.FULL, Locale.FRENCH);
        DateFormat it = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ITALIAN);
        DateFormat de = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.GERMAN);

        // ======= Test equality

        System.out.println("Testing equality operator");

        if( fr.equals(it) ) {
            fail("ERROR: equals failed");
        }

        // ======= Test various format() methods

        System.out.println("Testing various format() methods");

        Date d = new Date((long)837039928046.0);

        StringBuffer res1 = new StringBuffer();
        StringBuffer res2 = new StringBuffer();
        String res3 = new String();
        FieldPosition pos1 = new FieldPosition(0);
        FieldPosition pos2 = new FieldPosition(0);

        res1 = fr.format(d, res1, pos1);
        System.out.println("" + d.getTime() + " formatted to " + res1);

        res2 = it.format(d, res2, pos2);
        System.out.println("" + d.getTime() + " formatted to " + res2);

        res3 = de.format(d);
        System.out.println("" + d.getTime() + " formatted to " + res3);

        // ======= Test parse()

        System.out.println("Testing parse()");

        String text = new String("02/03/76, 2:50 AM, CST");
        Object result1 = new Date();
        Date result2 = new Date();
        Date result3 = new Date();
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos01 = new ParsePosition(0);

        result1 = def.parseObject(text, pos);
        if (result1 == null) {
            fail("ERROR: parseObject() failed for " + text);
        }
        System.out.println(text + " parsed into " + ((Date)result1).getTime());

        try {
            result2 = def.parse(text);
        }
        catch (ParseException e) {
            fail("ERROR: parse() failed");
        }
        System.out.println(text + " parsed into " + result2.getTime());

        result3 = def.parse(text, pos01);
        if (result3 == null) {
            fail("ERROR: parse() failed for " + text);
        }
        System.out.println(text + " parsed into " + result3.getTime());


        // ======= Test getters and setters

        System.out.println("Testing getters and setters");

        final Locale[] locales = DateFormat.getAvailableLocales();
        long count = locales.length;
        System.out.println("Got " + count + " locales" );
        for(int i = 0; i < count; i++) {
            String name;
            name = locales[i].getDisplayName();
            System.out.println(name);
        }

        fr.setLenient(it.isLenient());
        if(fr.isLenient() != it.isLenient()) {
            fail("ERROR: setLenient() failed");
        }

        final Calendar cal = def.getCalendar();
        Calendar newCal = (Calendar) cal.clone();
        de.setCalendar(newCal);
        it.setCalendar(newCal);
        if( ! de.getCalendar().equals(it.getCalendar())) {
            fail("ERROR: set Calendar() failed");
        }

        final NumberFormat nf = def.getNumberFormat();
        NumberFormat newNf = (NumberFormat) nf.clone();
        de.setNumberFormat(newNf);
        it.setNumberFormat(newNf);
        if( ! de.getNumberFormat().equals(it.getNumberFormat())) {
            fail("ERROR: set NumberFormat() failed");
        }

        final TimeZone tz = def.getTimeZone();
        TimeZone newTz = (TimeZone) tz.clone();
        de.setTimeZone(newTz);
        it.setTimeZone(newTz);
        if( ! de.getTimeZone().equals(it.getTimeZone())) {
            fail("ERROR: set TimeZone() failed");
        }

        // ======= Test getStaticClassID()

//        logln("Testing instanceof()");

//        try {
//            DateFormat test = new SimpleDateFormat();

//            if (! (test instanceof SimpleDateFormat)) {
//                errln("ERROR: instanceof failed");
//            }
//        }
//        catch (Exception e) {
//            errln("ERROR: Couldn't create a DateFormat");
//        }
    }
}
