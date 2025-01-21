/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.format;

import static java.time.temporal.ChronoField.YEAR;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.text.ParsePosition;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test StringLiteralPrinterParser.
 */
@Test
public class TestStringLiteralParser extends AbstractTestPrinterParser {

    @DataProvider(name="success")
    Object[][] data_success() {
        return new Object[][] {
            // match
            {"hello", true, "hello", 0, 5},
            {"hello", true, "helloOTHER", 0, 5},
            {"hello", true, "OTHERhelloOTHER", 5, 10},
            {"hello", true, "OTHERhello", 5, 10},

            // no match
            {"hello", true, "", 0, 0},
            {"hello", true, "a", 1, 1},
            {"hello", true, "HELLO", 0, 0},
            {"hello", true, "hlloo", 0, 0},
            {"hello", true, "OTHERhllooOTHER", 5, 5},
            {"hello", true, "OTHERhlloo", 5, 5},
            {"hello", true, "h", 0, 0},
            {"hello", true, "OTHERh", 5, 5},

            // case insensitive
            {"hello", false, "hello", 0, 5},
            {"hello", false, "HELLO", 0, 5},
            {"hello", false, "HelLo", 0, 5},
            {"hello", false, "HelLO", 0, 5},
        };
    }

    @Test(dataProvider="success")
    public void test_parse_success(String s, boolean caseSensitive, String text, int pos, int expectedPos) {
        setCaseSensitive(caseSensitive);
        ParsePosition ppos = new ParsePosition(pos);
        TemporalAccessor parsed = getFormatter(s).parseUnresolved(text, ppos);
        if (ppos.getErrorIndex() != -1) {
            assertEquals(ppos.getIndex(), expectedPos);
        } else {
            assertEquals(ppos.getIndex(), expectedPos);
            assertEquals(parsed.isSupported(YEAR), false);
            assertEquals(parsed.query(TemporalQueries.chronology()), null);
            assertEquals(parsed.query(TemporalQueries.zoneId()), null);
        }
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="error")
    Object[][] data_error() {
        return new Object[][] {
            {"hello", "hello", -1, IndexOutOfBoundsException.class},
            {"hello", "hello", 6, IndexOutOfBoundsException.class},
        };
    }

    @Test(dataProvider="error")
    public void test_parse_error(String s, String text, int pos, Class<?> expected) {
        try {
            ParsePosition ppos = new ParsePosition(pos);
            getFormatter(s).parseUnresolved(text, ppos);
            fail();
        } catch (RuntimeException ex) {
            assertTrue(expected.isInstance(ex));
        }
    }
}
