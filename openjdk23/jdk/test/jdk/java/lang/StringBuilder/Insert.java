/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @test
 * @bug 4914802 8257511
 * @summary Test StringBuilder.insert sanity tests
 * @run testng/othervm -XX:-CompactStrings Insert
 * @run testng/othervm -XX:+CompactStrings Insert
 */
@Test
public class Insert {

    public void insertFalse() {
        // Caused an infinite loop before 4914802
        StringBuilder sb = new StringBuilder();
        assertEquals("false", sb.insert(0, false).toString());
    }

    public void insertOffset() {
        // 8254082 made the String variant cause an AIOOBE, fixed in 8257511
        assertEquals("efabc", new StringBuilder("abc").insert(0, "def",                      1, 3).toString());
        assertEquals("efabc", new StringBuilder("abc").insert(0, new StringBuilder("def"),   1, 3).toString());
        // insert(I[CII) and insert(ILjava/lang/CharSequence;II) are inconsistently specified
        assertEquals("efabc", new StringBuilder("abc").insert(0, new char[] {'d', 'e', 'f'}, 1, 2).toString());
    }

}
