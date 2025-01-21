/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.format;

import static org.testng.Assert.assertEquals;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.testng.annotations.Test;

/**
 * Test PadPrinterDecorator.
 */
@Test
public class TestPadPrinterDecorator extends AbstractTestPrinterParser {

    //-----------------------------------------------------------------------
    public void test_print_emptyCalendrical() throws Exception {
        builder.padNext(3, '-').appendLiteral('Z');
        getFormatter().formatTo(EMPTY_DTA, buf);
        assertEquals(buf.toString(), "--Z");
    }

    public void test_print_fullDateTime() throws Exception {
        builder.padNext(3, '-').appendLiteral('Z');
        getFormatter().formatTo(LocalDate.of(2008, 12, 3), buf);
        assertEquals(buf.toString(), "--Z");
    }

    public void test_print_append() throws Exception {
        buf.append("EXISTING");
        builder.padNext(3, '-').appendLiteral('Z');
        getFormatter().formatTo(EMPTY_DTA, buf);
        assertEquals(buf.toString(), "EXISTING--Z");
    }

    //-----------------------------------------------------------------------
    public void test_print_noPadRequiredSingle() throws Exception {
        builder.padNext(1, '-').appendLiteral('Z');
        getFormatter().formatTo(EMPTY_DTA, buf);
        assertEquals(buf.toString(), "Z");
    }

    public void test_print_padRequiredSingle() throws Exception {
        builder.padNext(5, '-').appendLiteral('Z');
        getFormatter().formatTo(EMPTY_DTA, buf);
        assertEquals(buf.toString(), "----Z");
    }

    public void test_print_noPadRequiredMultiple() throws Exception {
        builder.padNext(4, '-').appendLiteral("WXYZ");
        getFormatter().formatTo(EMPTY_DTA, buf);
        assertEquals(buf.toString(), "WXYZ");
    }

    public void test_print_padRequiredMultiple() throws Exception {
        builder.padNext(5, '-').appendLiteral("WXYZ");
        getFormatter().formatTo(EMPTY_DTA, buf);
        assertEquals(buf.toString(), "-WXYZ");
    }

    @Test(expectedExceptions=DateTimeException.class)
    public void test_print_overPad() throws Exception {
        builder.padNext(3, '-').appendLiteral("WXYZ");
        getFormatter().formatTo(EMPTY_DTA, buf);
    }

    //-----------------------------------------------------------------------
    public void test_toString1() throws Exception {
        builder.padNext(5, ' ').appendLiteral('Y');
        assertEquals(getFormatter().toString(), "Pad('Y',5)");
    }

    public void test_toString2() throws Exception {
        builder.padNext(5, '-').appendLiteral('Y');
        assertEquals(getFormatter().toString(), "Pad('Y',5,'-')");
    }

}
