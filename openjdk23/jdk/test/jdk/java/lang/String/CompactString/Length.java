/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.length.
 * @run testng/othervm -XX:+CompactStrings Length
 * @run testng/othervm -XX:-CompactStrings Length
 */

public class Length extends CompactString {

    @DataProvider
    public Object[][] provider() {
        return new Object[][] {

        new Object[] { STRING_EMPTY, 0 }, new Object[] { STRING_L1, 1 },
                new Object[] { STRING_L2, 2 },
                new Object[] { STRING_LLONG, 8 },
                new Object[] { STRING_U1, 1 }, new Object[] { STRING_U2, 2 },
                new Object[] { STRING_M12, 2 }, new Object[] { STRING_M11, 2 },
                new Object[] { STRING_UDUPLICATE, 10 },
                new Object[] { STRING_SUPPLEMENTARY, 6 }, };
    }

    @Test(dataProvider = "provider")
    public void testLength(String str, int expected) {
        map.get(str).forEach(
                (source, data) -> {
                    assertEquals(data.length(), expected, String.format(
                            "testing String(%s).length(), source : %s, ",
                            escapeNonASCIIs(data), source));
                });
    }
}
