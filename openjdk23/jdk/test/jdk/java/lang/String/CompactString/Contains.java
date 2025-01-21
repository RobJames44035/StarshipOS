/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.contains.
 * @run testng/othervm -XX:+CompactStrings Contains
 * @run testng/othervm -XX:-CompactStrings Contains
 */

public class Contains extends CompactString {

    @DataProvider
    public Object[][] provider() {
        return new Object[][] {

        new Object[] { STRING_EMPTY, "", true },
                new Object[] { STRING_EMPTY, "A", false },
                new Object[] { STRING_EMPTY, "\uFF21", false },
                new Object[] { STRING_L1, "", true },
                new Object[] { STRING_L1, "A", true },
                new Object[] { STRING_L1, "\uFF21", false },
                new Object[] { STRING_L2, "", true },
                new Object[] { STRING_L2, "A", true },
                new Object[] { STRING_L2, "AB", true },
                new Object[] { STRING_L2, "B", true },
                new Object[] { STRING_L2, "ABC", false },
                new Object[] { STRING_L2, "ab", false },
                new Object[] { STRING_L4, "ABCD", true },
                new Object[] { STRING_L4, "BC", true },
                new Object[] { STRING_LLONG, "ABCDEFGH", true },
                new Object[] { STRING_LLONG, "BCDEFGH", true },
                new Object[] { STRING_LLONG, "EF", true },
                new Object[] { STRING_U1, "", true },
                new Object[] { STRING_U1, "\uFF21", true },
                new Object[] { STRING_U1, "a", false },
                new Object[] { STRING_U1, "\uFF21B", false },
                new Object[] { STRING_U2, "", true },
                new Object[] { STRING_U2, "\uFF21\uFF22", true },
                new Object[] { STRING_U2, "a", false },
                new Object[] { STRING_U2, "\uFF21B", false },
                new Object[] { STRING_M12, "\uFF21A", true },
                new Object[] { STRING_M12, "\uFF21", true },
                new Object[] { STRING_M12, "A", true },
                new Object[] { STRING_M12, "A\uFF21", false },
                new Object[] { STRING_M11, "A\uFF21", true },
                new Object[] { STRING_M11, "Ab", false }, };
    }

    @Test(dataProvider = "provider")
    public void testContains(String str, String anotherString, boolean expected) {
        map.get(str)
                .forEach(
                        (source, data) -> {
                            assertEquals(
                                    data.contains(anotherString),
                                    expected,
                                    String.format(
                                            "testing String(%s).contains(%s), source : %s, ",
                                            escapeNonASCIIs(data),
                                            escapeNonASCIIs(anotherString),
                                            source));
                        });
    }
}
