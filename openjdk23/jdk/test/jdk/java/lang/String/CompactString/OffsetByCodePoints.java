/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.offsetByCodePoints.
 * @run testng/othervm -XX:+CompactStrings OffsetByCodePoints
 * @run testng/othervm -XX:-CompactStrings OffsetByCodePoints
 */

public class OffsetByCodePoints extends CompactString {

    @DataProvider
    public Object[][] provider() {
        return new Object[][] {

        new Object[] { STRING_SUPPLEMENTARY, 0, 1, 2 },
                new Object[] { STRING_SUPPLEMENTARY, 0, 3, 5 },
                new Object[] { STRING_SUPPLEMENTARY, 1, 1, 2 },
                new Object[] { STRING_SUPPLEMENTARY, 1, 3, 5 },
                new Object[] { STRING_SUPPLEMENTARY, 2, 1, 4 },
                new Object[] { STRING_SUPPLEMENTARY, 2, 2, 5 },
                new Object[] { STRING_SUPPLEMENTARY, 2, 3, 6 },
                new Object[] { STRING_SUPPLEMENTARY, 3, 1, 4 },
                new Object[] { STRING_SUPPLEMENTARY, 3, 2, 5 },
                new Object[] { STRING_SUPPLEMENTARY, 3, 3, 6 }, };
    }

    @Test(dataProvider = "provider")
    public void testOffsetByCodePoints(String str, int index,
            int codePointOffset, int expected) {
        map.get(str)
                .forEach(
                        (source, data) -> {
                            assertEquals(
                                    data.offsetByCodePoints(index,
                                            codePointOffset),
                                    expected,
                                    String.format(
                                            "testing String(%s).offsetByCodePoints(%d, %d), source : %s, ",
                                            escapeNonASCIIs(data), index,
                                            codePointOffset, source));
                        });
    }
}
