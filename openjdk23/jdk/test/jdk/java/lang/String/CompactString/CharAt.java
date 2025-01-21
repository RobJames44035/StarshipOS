/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.util.stream.IntStream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8077559
 * @summary Tests Compact String. This one is for String.charAt.
 * @run testng/othervm -XX:+CompactStrings CharAt
 * @run testng/othervm -XX:-CompactStrings CharAt
 */

public class CharAt extends CompactString {

    @DataProvider
    public Object[][] provider() {
        return new Object[][] {
                new Object[] { STRING_L1, new char[] { 'A' } },
                new Object[] { STRING_L2, new char[] { 'A', 'B' } },
                new Object[] { STRING_L4, new char[] { 'A', 'B', 'C', 'D' } },
                new Object[] { STRING_LLONG,
                        new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' } },
                new Object[] { STRING_U1, new char[] { '\uFF21' } },
                new Object[] { STRING_U2, new char[] { '\uFF21', '\uFF22' } },
                new Object[] { STRING_M12, new char[] { '\uFF21', 'A' } },
                new Object[] { STRING_M11, new char[] { 'A', '\uFF21' } }, };
    }

    @Test(dataProvider = "provider")
    public void testCharAt(String str, char[] expected) {
        map.get(str)
                .forEach(
                        (source, data) -> {
                            IntStream
                                    .range(0, str.length())
                                    .forEach(
                                            i -> assertEquals(
                                                    str.charAt(i),
                                                    expected[i],
                                                    String.format(
                                                            "testing String(%s).charAt(%d), source : %s, ",
                                                            escapeNonASCIIs(data),
                                                            i, source)));
                        });
    }
}
