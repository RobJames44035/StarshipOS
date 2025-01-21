/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.format;

import static java.time.temporal.ChronoField.YEAR;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.time.temporal.MockFieldValue;

/**
 * Test ReducedPrinterParser.
 */
@Test
public class TestReducedPrinter extends AbstractTestPrinterParser {

    private DateTimeFormatter getFormatter0(TemporalField field, int width, int baseValue) {
        return builder.appendValueReduced(field, width, width, baseValue).toFormatter(locale).withDecimalStyle(decimalStyle);
    }

    private DateTimeFormatter getFormatter0(TemporalField field, int minWidth, int maxWidth, int baseValue) {
        return builder.appendValueReduced(field, minWidth, maxWidth, baseValue).toFormatter(locale).withDecimalStyle(decimalStyle);
    }

    private DateTimeFormatter getFormatterBaseDate(TemporalField field, int minWidth, int maxWidth, int baseValue) {
        return builder.appendValueReduced(field, minWidth, maxWidth, LocalDate.of(baseValue, 1, 1)).toFormatter(locale).withDecimalStyle(decimalStyle);
    }

    //-----------------------------------------------------------------------
    @Test(expectedExceptions=DateTimeException.class)
    public void test_print_emptyCalendrical() throws Exception {
        getFormatter0(YEAR, 2, 2010).formatTo(EMPTY_DTA, buf);
    }

    //-----------------------------------------------------------------------
    public void test_print_append() throws Exception {
        buf.append("EXISTING");
        getFormatter0(YEAR, 2, 2010).formatTo(LocalDate.of(2012, 1, 1), buf);
        assertEquals(buf.toString(), "EXISTING12");
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="Pivot")
    Object[][] provider_pivot() {
        return new Object[][] {
            {1, 1, 2010, 2010, "0"},
            {1, 1, 2010, 2011, "1"},
            {1, 1, 2010, 2012, "2"},
            {1, 1, 2010, 2013, "3"},
            {1, 1, 2010, 2014, "4"},
            {1, 1, 2010, 2015, "5"},
            {1, 1, 2010, 2016, "6"},
            {1, 1, 2010, 2017, "7"},
            {1, 1, 2010, 2018, "8"},
            {1, 1, 2010, 2019, "9"},
            {1, 1, 2010, 2009, "9"},
            {1, 1, 2010, 2020, "0"},

            {2, 2, 2010, 2010, "10"},
            {2, 2, 2010, 2011, "11"},
            {2, 2, 2010, 2021, "21"},
            {2, 2, 2010, 2099, "99"},
            {2, 2, 2010, 2100, "00"},
            {2, 2, 2010, 2109, "09"},
            {2, 2, 2010, 2009, "09"},
            {2, 2, 2010, 2110, "10"},

            {2, 2, 2005, 2005, "05"},
            {2, 2, 2005, 2099, "99"},
            {2, 2, 2005, 2100, "00"},
            {2, 2, 2005, 2104, "04"},
            {2, 2, 2005, 2004, "04"},
            {2, 2, 2005, 2105, "05"},

            {3, 3, 2005, 2005, "005"},
            {3, 3, 2005, 2099, "099"},
            {3, 3, 2005, 2100, "100"},
            {3, 3, 2005, 2999, "999"},
            {3, 3, 2005, 3000, "000"},
            {3, 3, 2005, 3004, "004"},
            {3, 3, 2005, 2004, "004"},
            {3, 3, 2005, 3005, "005"},

            {9, 9, 2005, 2005, "000002005"},
            {9, 9, 2005, 2099, "000002099"},
            {9, 9, 2005, 2100, "000002100"},
            {9, 9, 2005, 999999999, "999999999"},
            {9, 9, 2005, 1000000000, "000000000"},
            {9, 9, 2005, 1000002004, "000002004"},
            {9, 9, 2005, 2004, "000002004"},
            {9, 9, 2005, 1000002005, "000002005"},

            {2, 2, -2005, -2005, "05"},
            {2, 2, -2005, -2000, "00"},
            {2, 2, -2005, -1999, "99"},
            {2, 2, -2005, -1904, "04"},
            {2, 2, -2005, -2006, "06"},
            {2, 2, -2005, -1905, "05"},

            {2, 4, 2005, 2099, "99"},
            {2, 4, 2005, 2100, "00"},
            {2, 4, 2005, 9999, "9999"},
            {2, 4, 2005, 1000000000, "00"},
            {2, 4, 2005, 1000002004, "2004"},
            {2, 4, 2005, 2004, "2004"},
            {2, 4, 2005, 2005, "05"},
            {2, 4, 2005, 2104, "04"},
            {2, 4, 2005, 2105, "2105"},
        };
    }

    @Test(dataProvider="Pivot")
    public void test_pivot(int minWidth, int maxWidth, int baseValue, int value, String result) throws Exception {
        try {
            getFormatter0(YEAR, minWidth, maxWidth, baseValue).formatTo(new MockFieldValue(YEAR, value), buf);
            if (result == null) {
                fail("Expected exception");
            }
            assertEquals(buf.toString(), result);
        } catch (DateTimeException ex) {
            if (result == null || value < 0) {
                assertEquals(ex.getMessage().contains(YEAR.toString()), true);
            } else {
                throw ex;
            }
        }
    }

    @Test(dataProvider="Pivot")
    public void test_pivot_baseDate(int minWidth, int maxWidth, int baseValue, int value, String result) throws Exception {
        try {
            getFormatterBaseDate(YEAR, minWidth, maxWidth, baseValue).formatTo(new MockFieldValue(YEAR, value), buf);
            if (result == null) {
                fail("Expected exception");
            }
            assertEquals(buf.toString(), result);
        } catch (DateTimeException ex) {
            if (result == null || value < 0) {
                assertEquals(ex.getMessage().contains(YEAR.toString()), true);
            } else {
                throw ex;
            }
        }
    }

    //-----------------------------------------------------------------------
    public void test_minguoChrono_fixedWidth() throws Exception {
        // ISO 2021 is Minguo 110
        DateTimeFormatter f = getFormatterBaseDate(YEAR, 2, 2, 2021);
        MinguoDate date = MinguoDate.of(109, 6, 30);
        assertEquals(f.format(date), "09");
        date = MinguoDate.of(110, 6, 30);
        assertEquals(f.format(date), "10");
        date = MinguoDate.of(199, 6, 30);
        assertEquals(f.format(date), "99");
        date = MinguoDate.of(200, 6, 30);
        assertEquals(f.format(date), "00");
        date = MinguoDate.of(209, 6, 30);
        assertEquals(f.format(date), "09");
        date = MinguoDate.of(210, 6, 30);
        assertEquals(f.format(date), "10");
    }

    public void test_minguoChrono_extendedWidth() throws Exception {
        // ISO 2021 is Minguo 110
        DateTimeFormatter f = getFormatterBaseDate(YEAR, 2, 4, 2021);
        MinguoDate date = MinguoDate.of(109, 6, 30);
        assertEquals(f.format(date), "109");
        date = MinguoDate.of(110, 6, 30);
        assertEquals(f.format(date), "10");
        date = MinguoDate.of(199, 6, 30);
        assertEquals(f.format(date), "99");
        date = MinguoDate.of(200, 6, 30);
        assertEquals(f.format(date), "00");
        date = MinguoDate.of(209, 6, 30);
        assertEquals(f.format(date), "09");
        date = MinguoDate.of(210, 6, 30);
        assertEquals(f.format(date), "210");
    }

    //-----------------------------------------------------------------------
    public void test_toString() throws Exception {
        assertEquals(getFormatter0(YEAR, 2, 2, 2005).toString(), "ReducedValue(Year,2,2,2005)");
    }

    //-----------------------------------------------------------------------
    // Cases and values in adjacent parsing mode
    //-----------------------------------------------------------------------
    @DataProvider(name="PrintAdjacent")
    Object[][] provider_printAdjacent() {
        return new Object[][] {
            // general
            {"yyMMdd", "990703",   1999, 7, 3},
            {"yyMMdd", "990703",   2099, 7, 3},
            {"yyMMdd", "200703",   2020, 7, 3},
            {"ddMMyy", "030714",   2014, 7, 3},
            {"ddMMyy", "030720",   2020, 7, 3},
            {"ddMMy",  "03072001", 2001, 7, 3},
        };
    }

    @Test(dataProvider="PrintAdjacent")
    public void test_printAdjacent(String pattern, String text, int year, int month, int day) {
        builder = new DateTimeFormatterBuilder();
        builder.appendPattern(pattern);
        DateTimeFormatter dtf = builder.toFormatter();

        LocalDate ld = LocalDate.of(year, month, day);
        String actual = dtf.format(ld);
        assertEquals(actual, text, "formatter output: " + dtf);
    }

}
