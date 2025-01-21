/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.format;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.format.TextStyle;

import org.testng.annotations.Test;

/**
 * Test DecimalStyle.
 */
@Test
public class TCKTextStyle {

    @Test
    public void test_standaloneNormal() {
        assertEquals(TextStyle.FULL, TextStyle.FULL_STANDALONE.asNormal());
        assertEquals(TextStyle.SHORT, TextStyle.SHORT.asNormal());
        assertEquals(TextStyle.NARROW, TextStyle.NARROW.asNormal());

        assertEquals(TextStyle.FULL_STANDALONE, TextStyle.FULL_STANDALONE.asStandalone());
        assertEquals(TextStyle.SHORT_STANDALONE, TextStyle.SHORT.asStandalone());
        assertEquals(TextStyle.NARROW_STANDALONE, TextStyle.NARROW.asStandalone());

        assertTrue(TextStyle.FULL_STANDALONE.isStandalone());
        assertTrue(TextStyle.SHORT_STANDALONE.isStandalone());
        assertTrue(TextStyle.NARROW_STANDALONE.isStandalone());

        assertTrue(!TextStyle.FULL.isStandalone());
        assertTrue(!TextStyle.SHORT.isStandalone());
        assertTrue(!TextStyle.NARROW.isStandalone());
    }

    //-----------------------------------------------------------------------
    // valueOf()
    //-----------------------------------------------------------------------
    @Test
    public void test_valueOf() {
        for (TextStyle style : TextStyle.values()) {
            assertEquals(TextStyle.valueOf(style.name()), style);
        }
    }

}
