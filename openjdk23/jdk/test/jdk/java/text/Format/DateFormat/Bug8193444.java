/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
/*
 * @test
 * @bug 8193444
 * @summary Checks SimpleDateFormat.format/parse for the AIOOB exception when
 *          formatting/parsing dates through a pattern string that contains a
 *          sequence of 256 or more non-ASCII unicode characters.
 * @run testng/othervm Bug8193444
 */
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;

public class Bug8193444 {

    private static final String NON_ASCII_CHAR = "\u263A";

    @DataProvider(name = "dateFormat")
    Object[][] dateFormatData() {
        return new Object[][]{
            // short_length (between 0 and 254)
            {250},
            // boundary
            {254},
            // long_length
            {257},};
    }

    @Test(dataProvider = "dateFormat")
    public void testDateFormatAndParse(int length)
            throws ParseException {

        String pattern = NON_ASCII_CHAR.repeat(length);

        DateFormat df = new SimpleDateFormat(pattern);
        // format() should not throw AIOOB exception
        String result = df.format(new Date());

        // Since the tested format patterns do not contain any character
        // representing date/time field, those characters are not interpreted,
        // they are simply copied into the output string during formatting
        assertEquals(result, pattern, "Failed to format the date using"
                + " pattern of length: " + length);

        // The format pattern used by this SimpleDateFormat
        // contains a sequence of non-ASCII characters, which does not
        // represent any date/time field. The same sequence is given
        // for parsing, just to check that the parsing does
        // not throw any AIOOB exception.
        // Although as per the parse() specification, the calendar's default
        // values of the date-time fields are used for any missing
        // date-time information, but checking that is not the intention of
        // this test.
        df.parse(pattern);
    }

}
