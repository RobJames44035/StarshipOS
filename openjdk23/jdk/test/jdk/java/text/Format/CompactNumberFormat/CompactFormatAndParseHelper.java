/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import static org.testng.Assert.assertEquals;

class CompactFormatAndParseHelper {

    static void testFormat(NumberFormat cnf, Object number,
            String expected) {
        String result = cnf.format(number);
        assertEquals(result, expected, "Incorrect formatting of the number '"
                + number + "'");
    }

    static void testParse(NumberFormat cnf, String parseString,
            Number expected, ParsePosition position, Class<? extends Number> returnType) throws ParseException {

        Number number;
        if (position == null) {
            number = cnf.parse(parseString);
        } else {
            number = cnf.parse(parseString, position);
        }

        if (returnType != null) {
            assertEquals(number.getClass(), returnType,
                    "Incorrect return type for string '" + parseString + "'");
        }

        assertEquals(number, expected, "Incorrect parsing of the string '"
                + parseString + "'");
    }
}
