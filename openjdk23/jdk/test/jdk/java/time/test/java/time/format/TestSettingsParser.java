/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.format;

import static org.testng.Assert.assertEquals;

import java.text.ParsePosition;

import org.testng.annotations.Test;

/**
 * Test SettingsParser.
 */
@Test
public class TestSettingsParser extends AbstractTestPrinterParser {

    //-----------------------------------------------------------------------
    public void test_print_sensitive() throws Exception {
        setCaseSensitive(true);
        getFormatter().formatTo(dta, buf);
        assertEquals(buf.toString(), "");
    }

    public void test_print_strict() throws Exception {
        setStrict(true);
        getFormatter().formatTo(dta, buf);
        assertEquals(buf.toString(), "");
    }

    /*
    public void test_print_nulls() throws Exception {
        setCaseSensitive(true);
        getFormatter().formatTo(null, null);
    }
    */

    //-----------------------------------------------------------------------
    public void test_parse_changeStyle_sensitive() throws Exception {
        setCaseSensitive(true);
        ParsePosition pos = new ParsePosition(0);
        getFormatter().parseUnresolved("a", pos);
        assertEquals(pos.getIndex(), 0);
    }

    public void test_parse_changeStyle_insensitive() throws Exception {
        setCaseSensitive(false);
        ParsePosition pos = new ParsePosition(0);
        getFormatter().parseUnresolved("a", pos);
        assertEquals(pos.getIndex(), 0);
    }

    public void test_parse_changeStyle_strict() throws Exception {
        setStrict(true);
        ParsePosition pos = new ParsePosition(0);
        getFormatter().parseUnresolved("a", pos);
        assertEquals(pos.getIndex(), 0);
    }

    public void test_parse_changeStyle_lenient() throws Exception {
        setStrict(false);
        ParsePosition pos = new ParsePosition(0);
        getFormatter().parseUnresolved("a", pos);
        assertEquals(pos.getIndex(), 0);
    }

    //-----------------------------------------------------------------------
    public void test_toString_sensitive() throws Exception {
        setCaseSensitive(true);
        assertEquals(getFormatter().toString(), "ParseCaseSensitive(true)");
    }

    public void test_toString_insensitive() throws Exception {
        setCaseSensitive(false);
        assertEquals(getFormatter().toString(), "ParseCaseSensitive(false)");
    }

    public void test_toString_strict() throws Exception {
        setStrict(true);
        assertEquals(getFormatter().toString(), "ParseStrict(true)");
    }

    public void test_toString_lenient() throws Exception {
        setStrict(false);
        assertEquals(getFormatter().toString(), "ParseStrict(false)");
    }

}
