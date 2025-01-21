/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8080774 8174269
 * @modules jdk.localedata
 * @run testng LocaleDateFormats
 * @summary This file contains tests for JRE locales date formats
 */

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LocaleDateFormats {

    @Test(dataProvider = "dateFormats")
    public void testDateFormat(Locale loc, int style, int year, int month, int date, String expectedString) {
        Calendar cal = Calendar.getInstance(loc);
        cal.set(year, month-1, date);
        // Create date formatter based on requested style and test locale
        DateFormat df = DateFormat.getDateInstance(style, loc);
        // Test the date format
        assertEquals(df.format(cal.getTime()), expectedString);
    }

    @DataProvider(name = "dateFormats" )
    private Object[][] dateFormats() {
        return new Object[][] {
            //8080774
            //Locale, Format type, year, month, date, expected result
            {localeEnSG, DateFormat.SHORT, 2015, 5, 6, "6/5/15"},
            {localeEnSG, DateFormat.MEDIUM, 2015, 5, 6, "6 May 2015"},
            {localeEnSG, DateFormat.LONG, 2015, 5, 6, "6 May 2015"},
            {localeEnSG, DateFormat.FULL, 2015, 5, 6, "Wednesday, 6 May 2015"}
        };
    }
    // en_SG Locale instance
    private static final Locale localeEnSG = Locale.of("en", "SG");
}
