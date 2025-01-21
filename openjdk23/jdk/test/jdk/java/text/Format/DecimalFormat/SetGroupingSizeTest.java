/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8212749
 * @summary test whether input value check for
 *          DecimalFormat.setGroupingSize(int) works correctly.
 * @run testng/othervm SetGroupingSizeTest
 */

import java.text.DecimalFormat;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class SetGroupingSizeTest {

    @DataProvider
    public static Object[][] validGroupingSizes() {
        return new Object[][] {
            { 0 },
            { Byte.MAX_VALUE },
        };
    }

    @DataProvider
    public static Object[][] invalidGroupingSizes() {
        return new Object[][] {
            { Byte.MIN_VALUE - 1 },
            { Byte.MIN_VALUE },
            { -1 },
            { Byte.MAX_VALUE + 1 },
            { Integer.MIN_VALUE },
            { Integer.MAX_VALUE },
        };
    }

    @Test(dataProvider = "validGroupingSizes")
    public void test_validGroupingSize(int newVal) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingSize(newVal);
        assertEquals(df.getGroupingSize(), newVal);
    }

    @Test(dataProvider = "invalidGroupingSizes",
        expectedExceptions = IllegalArgumentException.class)
    public void test_invalidGroupingSize(int newVal) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingSize(newVal);
    }
}
