/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.valueOf.
 *          valueOf(char[] data) is not tested here.
 * @run testng/othervm -XX:+CompactStrings ValueOf
 * @run testng/othervm -XX:-CompactStrings ValueOf
 */

public class ValueOf {

    /*
     * Data provider for testValueOf
     *
     * @return input parameter for testValueOf
     */
    @DataProvider
    public Object[][] valueOfs() {
        return new Object[][] { { String.valueOf(true), "true" },
                { String.valueOf(false), "false" },
                { String.valueOf(1.0f), "1.0" },
                { String.valueOf(0.0f), "0.0" },
                { String.valueOf(Float.MAX_VALUE), "3.4028235E38" },
                { String.valueOf(Float.MIN_VALUE), "1.4E-45" },
                { String.valueOf(1.0d), "1.0" },
                { String.valueOf(0.0d), "0.0" },
                { String.valueOf(Double.MAX_VALUE), "1.7976931348623157E308" },
                { String.valueOf(Double.MIN_VALUE), "4.9E-324" },
                { String.valueOf(1), "1" }, { String.valueOf(0), "0" },
                { String.valueOf(Integer.MAX_VALUE), "2147483647" },
                { String.valueOf(Integer.MIN_VALUE), "-2147483648" },
                { String.valueOf(1L), "1" }, { String.valueOf(0L), "0" },
                { String.valueOf(Long.MAX_VALUE), "9223372036854775807" },
                { String.valueOf(Long.MIN_VALUE), "-9223372036854775808" } };
    }

    /*
     * test String.valueOf(xxx).
     *
     * @param res
     *            real result
     * @param expected
     *            expected result
     */
    @Test(dataProvider = "valueOfs")
    public void testValueOf(String res, String expected) {
        assertEquals(res, expected);
    }

}
