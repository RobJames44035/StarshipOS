/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.replace.
 * @run testng/othervm -XX:+CompactStrings Replace
 * @run testng/othervm -XX:-CompactStrings Replace
 */

public class Replace extends CompactString {

    @DataProvider
    public Object[][] provider() {
        return new Object[][] {

                new Object[] { STRING_L1, 'A', 'B', "B" },
                new Object[] { STRING_L1, 'A', 'A', "A" },
                new Object[] { STRING_L1, 'A', '\uFF21', "\uFF21" },
                new Object[] { STRING_L2, 'A', 'B', "BB" },
                new Object[] { STRING_L2, 'B', 'A', "AA" },
                new Object[] { STRING_L2, 'C', 'A', "AB" },
                new Object[] { STRING_L2, 'B', '\uFF21', "A\uFF21" },
                new Object[] { STRING_U1, '\uFF21', 'A', "A" },
                new Object[] { STRING_U1, '\uFF22', 'A', "\uFF21" },
                new Object[] { STRING_U2, '\uFF22', 'A', "\uFF21A" },
                new Object[] { STRING_M12, 'A', '\uFF21', "\uFF21\uFF21" },
                new Object[] { STRING_M11, '\uFF21', 'A', "AA" },
                new Object[] { STRING_UDUPLICATE, '\uFF21', 'A',
                        "A\uFF22A\uFF22A\uFF22A\uFF22A\uFF22" },
                new Object[] { STRING_MDUPLICATE1, '\uFF21', 'A', "AAAAAAAAAA" },
                new Object[] { STRING_MDUPLICATE1, 'A', '\uFF21',
                        "\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21" }, };
    }

    @Test(dataProvider = "provider")
    public void testReplace(String str, char oldChar, char newChar,
            String expected) {
        map.get(str)
                .forEach(
                        (source, data) -> {
                            assertEquals(
                                    data.replace(oldChar, newChar),
                                    expected,
                                    String.format(
                                            "testing String(%s).replace(%s, %s), source : %s, ",
                                            escapeNonASCIIs(data),
                                            escapeNonASCII(oldChar),
                                            escapeNonASCII(newChar), source));
                        });
    }

    @DataProvider
    public Object[][] provider2() {
        return new Object[][] {

                new Object[] { STRING_EMPTY, "", "ABC", "ABC" },
                new Object[] { STRING_EMPTY, "", "", "" },
                new Object[] { STRING_L1, "A", "B", "B" },
                new Object[] { STRING_L1, "A", "A", "A" },
                new Object[] { STRING_L2, "B", "\uFF21", "A\uFF21" },
                new Object[] { STRING_LLONG, "BCD", "\uFF21", "A\uFF21EFGH" },
                new Object[] { STRING_U1, "\uFF21", "A", "A" },
                new Object[] { STRING_U1, "\uFF21", "A\uFF21", "A\uFF21" },
                new Object[] { STRING_U2, "\uFF21", "A", "A\uFF22" },
                new Object[] { STRING_U2, "\uFF22", "A", "\uFF21A" },
                new Object[] { STRING_UDUPLICATE, "\uFF21\uFF22", "AB",
                        "ABABABABAB" },
                new Object[] { STRING_MDUPLICATE1, "\uFF21", "A", "AAAAAAAAAA" },
                new Object[] { STRING_MDUPLICATE1, "A", "\uFF21",
                        "\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21\uFF21" }, };
    }

    @Test(dataProvider = "provider2")
    public void testReplace(String str, CharSequence target,
            CharSequence replacement, String expected) {
        map.get(str)
                .forEach(
                        (source, data) -> {
                            assertEquals(
                                    data.replace(target, replacement),
                                    expected,
                                    String.format(
                                            "testing String(%s).replace(%s, %s), source : %s, ",
                                            escapeNonASCIIs(data),
                                            escapeNonASCIIs(target.toString()),
                                            escapeNonASCIIs(replacement
                                                    .toString()), source));
                        });
    }
}
