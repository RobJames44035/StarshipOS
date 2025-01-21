/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.format;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Test CharLiteralPrinterParser.
 */
@Test
public class TestCharLiteralPrinter extends AbstractTestPrinterParser {

    //-----------------------------------------------------------------------
    public void test_print_emptyCalendrical() throws Exception {
        buf.append("EXISTING");
        getFormatter('a').formatTo(EMPTY_DTA, buf);
        assertEquals(buf.toString(), "EXISTINGa");
    }

    public void test_print_dateTime() throws Exception {
        buf.append("EXISTING");
        getFormatter('a').formatTo(dta, buf);
        assertEquals(buf.toString(), "EXISTINGa");
    }

    public void test_print_emptyAppendable() throws Exception {
        getFormatter('a').formatTo(dta, buf);
        assertEquals(buf.toString(), "a");
    }

    //-----------------------------------------------------------------------
    public void test_toString() throws Exception {
        assertEquals(getFormatter('a').toString(), "'a'");
    }

    //-----------------------------------------------------------------------
    public void test_toString_apos() throws Exception {
        assertEquals(getFormatter('\'').toString(), "''");
    }

}
