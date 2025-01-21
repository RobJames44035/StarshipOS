/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.format;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.SignStyle;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.time.temporal.MockFieldValue;

/**
 * Test SimpleNumberPrinterParser.
 */
@Test
public class TestNumberPrinter extends AbstractTestPrinterParser {

    //-----------------------------------------------------------------------
    @Test(expectedExceptions=DateTimeException.class)
    public void test_print_emptyCalendrical() throws Exception {
        getFormatter(DAY_OF_MONTH, 1, 2, SignStyle.NEVER).formatTo(EMPTY_DTA, buf);
    }

    public void test_print_append() throws Exception {
        buf.append("EXISTING");
        getFormatter(DAY_OF_MONTH, 1, 2, SignStyle.NEVER).formatTo(LocalDate.of(2012, 1, 3), buf);
        assertEquals(buf.toString(), "EXISTING3");
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="Pad")
    Object[][] provider_pad() {
        return new Object[][] {
            {1, 1, -10, null},
            {1, 1, -9, "9"},
            {1, 1, -1, "1"},
            {1, 1, 0, "0"},
            {1, 1, 3, "3"},
            {1, 1, 9, "9"},
            {1, 1, 10, null},

            {1, 2, -100, null},
            {1, 2, -99, "99"},
            {1, 2, -10, "10"},
            {1, 2, -9, "9"},
            {1, 2, -1, "1"},
            {1, 2, 0, "0"},
            {1, 2, 3, "3"},
            {1, 2, 9, "9"},
            {1, 2, 10, "10"},
            {1, 2, 99, "99"},
            {1, 2, 100, null},

            {2, 2, -100, null},
            {2, 2, -99, "99"},
            {2, 2, -10, "10"},
            {2, 2, -9, "09"},
            {2, 2, -1, "01"},
            {2, 2, 0, "00"},
            {2, 2, 3, "03"},
            {2, 2, 9, "09"},
            {2, 2, 10, "10"},
            {2, 2, 99, "99"},
            {2, 2, 100, null},

            {1, 3, -1000, null},
            {1, 3, -999, "999"},
            {1, 3, -100, "100"},
            {1, 3, -99, "99"},
            {1, 3, -10, "10"},
            {1, 3, -9, "9"},
            {1, 3, -1, "1"},
            {1, 3, 0, "0"},
            {1, 3, 3, "3"},
            {1, 3, 9, "9"},
            {1, 3, 10, "10"},
            {1, 3, 99, "99"},
            {1, 3, 100, "100"},
            {1, 3, 999, "999"},
            {1, 3, 1000, null},

            {2, 3, -1000, null},
            {2, 3, -999, "999"},
            {2, 3, -100, "100"},
            {2, 3, -99, "99"},
            {2, 3, -10, "10"},
            {2, 3, -9, "09"},
            {2, 3, -1, "01"},
            {2, 3, 0, "00"},
            {2, 3, 3, "03"},
            {2, 3, 9, "09"},
            {2, 3, 10, "10"},
            {2, 3, 99, "99"},
            {2, 3, 100, "100"},
            {2, 3, 999, "999"},
            {2, 3, 1000, null},

            {3, 3, -1000, null},
            {3, 3, -999, "999"},
            {3, 3, -100, "100"},
            {3, 3, -99, "099"},
            {3, 3, -10, "010"},
            {3, 3, -9, "009"},
            {3, 3, -1, "001"},
            {3, 3, 0, "000"},
            {3, 3, 3, "003"},
            {3, 3, 9, "009"},
            {3, 3, 10, "010"},
            {3, 3, 99, "099"},
            {3, 3, 100, "100"},
            {3, 3, 999, "999"},
            {3, 3, 1000, null},

            {1, 10, Integer.MAX_VALUE - 1, "2147483646"},
            {1, 10, Integer.MAX_VALUE, "2147483647"},
            {1, 10, Integer.MIN_VALUE + 1, "2147483647"},
            {1, 10, Integer.MIN_VALUE, "2147483648"},
       };
    }

    @Test(dataProvider="Pad")
    public void test_pad_NOT_NEGATIVE(int minPad, int maxPad, long value, String result) throws Exception {
        try {
            getFormatter(DAY_OF_MONTH, minPad, maxPad, SignStyle.NOT_NEGATIVE).formatTo(new MockFieldValue(DAY_OF_MONTH, value), buf);
            if (result == null || value < 0) {
                fail("Expected exception");
            }
            assertEquals(buf.toString(), result);
        } catch (DateTimeException ex) {
            if (result == null || value < 0) {
                assertEquals(ex.getMessage().contains(DAY_OF_MONTH.toString()), true);
            } else {
                throw ex;
            }
        }
    }

    @Test(dataProvider="Pad")
    public void test_pad_NEVER(int minPad, int maxPad, long value, String result) throws Exception {
        try {
            getFormatter(DAY_OF_MONTH, minPad, maxPad, SignStyle.NEVER).formatTo(new MockFieldValue(DAY_OF_MONTH, value), buf);
            if (result == null) {
                fail("Expected exception");
            }
            assertEquals(buf.toString(), result);
        } catch (DateTimeException ex) {
            if (result != null) {
                throw ex;
            }
            assertEquals(ex.getMessage().contains(DAY_OF_MONTH.toString()), true);
        }
    }

    @Test(dataProvider="Pad")
    public void test_pad_NORMAL(int minPad, int maxPad, long value, String result) throws Exception {
        try {
            getFormatter(DAY_OF_MONTH, minPad, maxPad, SignStyle.NORMAL).formatTo(new MockFieldValue(DAY_OF_MONTH, value), buf);
            if (result == null) {
                fail("Expected exception");
            }
            assertEquals(buf.toString(), (value < 0 ? "-" + result : result));
        } catch (DateTimeException ex) {
            if (result != null) {
                throw ex;
            }
            assertEquals(ex.getMessage().contains(DAY_OF_MONTH.toString()), true);
        }
    }

    @Test(dataProvider="Pad")
    public void test_pad_ALWAYS(int minPad, int maxPad, long value, String result) throws Exception {
        try {
            getFormatter(DAY_OF_MONTH, minPad, maxPad, SignStyle.ALWAYS).formatTo(new MockFieldValue(DAY_OF_MONTH, value), buf);
            if (result == null) {
                fail("Expected exception");
            }
            assertEquals(buf.toString(), (value < 0 ? "-" + result : "+" + result));
        } catch (DateTimeException ex) {
            if (result != null) {
                throw ex;
            }
            assertEquals(ex.getMessage().contains(DAY_OF_MONTH.toString()), true);
        }
    }

    @Test(dataProvider="Pad")
    public void test_pad_EXCEEDS_PAD(int minPad, int maxPad, long value, String result) throws Exception {
        try {
            getFormatter(DAY_OF_MONTH, minPad, maxPad, SignStyle.EXCEEDS_PAD).formatTo(new MockFieldValue(DAY_OF_MONTH, value), buf);
            if (result == null) {
                fail("Expected exception");
                return;  // unreachable
            }
            if (result.length() > minPad || value < 0) {
                result = (value < 0 ? "-" + result : "+" + result);
            }
            assertEquals(buf.toString(), result);
        } catch (DateTimeException ex) {
            if (result != null) {
                throw ex;
            }
            assertEquals(ex.getMessage().contains(DAY_OF_MONTH.toString()), true);
        }
    }

    //-----------------------------------------------------------------------
    public void test_toString1() throws Exception {
        assertEquals(getFormatter(HOUR_OF_DAY, 1, 19, SignStyle.NORMAL).toString(), "Value(HourOfDay)");
    }

    public void test_toString2() throws Exception {
        assertEquals(getFormatter(HOUR_OF_DAY, 2, 2, SignStyle.NOT_NEGATIVE).toString(), "Value(HourOfDay,2)");
    }

    public void test_toString3() throws Exception {
        assertEquals(getFormatter(HOUR_OF_DAY, 1, 2, SignStyle.NOT_NEGATIVE).toString(), "Value(HourOfDay,1,2,NOT_NEGATIVE)");
    }

}
