/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.trim.
 * @run testng/othervm -XX:+CompactStrings Trim
 * @run testng/othervm -XX:-CompactStrings Trim
 */

public class Trim {

    /*
     * Data provider for testTrim
     *
     * @return input parameter for testTrim
     */
    @DataProvider
    public Object[][] trims() {
        return new Object[][] {
                { " \t \t".trim(), "" },
                { "\t \t ".trim(), "" },
                { "\t A B C\t ".trim(), "A B C" },
                { " \t A B C \t".trim(), "A B C" },
                { "\t \uFF21 \uFF22 \uFF23\t ".trim(), "\uFF21 \uFF22 \uFF23" },
                { " \t \uFF21 \uFF22 \uFF23 \t".trim(), "\uFF21 \uFF22 \uFF23" },
                { " \t \uFF41 \uFF42 \uFF43 \t".trim(), "\uFF41 \uFF42 \uFF43" },
                { " \t A\uFF21 B\uFF22 C\uFF23 \t".trim(),
                        "A\uFF21 B\uFF22 C\uFF23" } };
    }

    /*
     * test trim().
     *
     * @param res
     *            real result
     * @param expected
     *            expected result
     */
    @Test(dataProvider = "trims")
    public void testTrim(String res, String expected) {
        assertEquals(res, expected);
    }

}
