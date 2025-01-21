/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.codePointCount.
 * @run testng/othervm -XX:+CompactStrings CodePointCount
 * @run testng/othervm -XX:-CompactStrings CodePointCount
 */

public class CodePointCount extends CompactString {

    @DataProvider
    public Object[][] provider() {
        return new Object[][] { new Object[] { STRING_EMPTY, 0, 0, 0 },
                new Object[] { STRING_L1, 0, 1, 1 },
                new Object[] { STRING_L1, 1, 1, 0 },
                new Object[] { STRING_L2, 0, 2, 2 },
                new Object[] { STRING_L2, 0, 1, 1 },
                new Object[] { STRING_L2, 1, 2, 1 },
                new Object[] { STRING_L4, 0, 4, 4 },
                new Object[] { STRING_L4, 0, 1, 1 },
                new Object[] { STRING_L4, 2, 4, 2 },
                new Object[] { STRING_LLONG, 0, 8, 8 },
                new Object[] { STRING_LLONG, 0, 5, 5 },
                new Object[] { STRING_LLONG, 4, 8, 4 },
                new Object[] { STRING_LLONG, 0, 7, 7 },
                new Object[] { STRING_U1, 0, 1, 1 },
                new Object[] { STRING_U2, 0, 2, 2 },
                new Object[] { STRING_U2, 0, 1, 1 },
                new Object[] { STRING_U2, 1, 2, 1 },
                new Object[] { STRING_M12, 0, 2, 2 },
                new Object[] { STRING_M12, 0, 1, 1 },
                new Object[] { STRING_M12, 1, 2, 1 },
                new Object[] { STRING_M11, 0, 2, 2 },
                new Object[] { STRING_M11, 0, 1, 1 },
                new Object[] { STRING_M11, 1, 2, 1 },
                new Object[] { STRING_SUPPLEMENTARY, 0, 1, 1 },
                new Object[] { STRING_SUPPLEMENTARY, 0, 2, 1 },
                new Object[] { STRING_SUPPLEMENTARY, 0, 3, 2 },
                new Object[] { STRING_SUPPLEMENTARY, 0, 5, 3 },
                new Object[] { STRING_SUPPLEMENTARY, 0, 6, 4 },
                new Object[] { STRING_SUPPLEMENTARY, 1, 4, 2 },
                new Object[] { STRING_SUPPLEMENTARY, 1, 6, 4 },
                new Object[] { STRING_SUPPLEMENTARY, 2, 4, 1 },};
    }

    @Test(dataProvider = "provider")
    public void testCodePointCount(String str, int beginIndex, int endIndex,
            int expected) {
        map.get(str)
                .forEach(
                        (source, data) -> {
                            assertEquals(
                                    data.codePointCount(beginIndex, endIndex),
                                    expected,
                                    String.format(
                                            "testing String(%s).codePointCount(%d, %d), source : %s, ",
                                            escapeNonASCIIs(data), beginIndex,
                                            endIndex, source));
                        });
    }
}
