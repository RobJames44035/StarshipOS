/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.intern.
 * @run testng/othervm -XX:+CompactStrings Intern
 * @run testng/othervm -XX:-CompactStrings Intern
 */

public class Intern extends CompactString {

    @DataProvider
    public Object[][] provider() {
        return new Object[][] {

                new Object[] { STRING_EMPTY, "" },
                new Object[] { STRING_L1, "A" },
                new Object[] { STRING_LLONG, "ABCDEFGH" },
                new Object[] { STRING_U1, "\uFF21" },
                new Object[] { STRING_U2, "\uFF21\uFF22" },
                new Object[] { STRING_M12, "\uFF21A" },
                new Object[] { STRING_M11, "A\uFF21" },
                new Object[] { STRING_MDUPLICATE1,
                        "\uFF21A\uFF21A\uFF21A\uFF21A\uFF21A" }, };
    }

    @Test(dataProvider = "provider")
    public void testIntern(String str, String expected) {
        map.get(str).forEach(
                (source, data) -> {
                    assertTrue(data.intern() == expected, String.format(
                            "testing String(%s).intern(), source : %s, ",
                            escapeNonASCIIs(data), source));
                });
    }
}
