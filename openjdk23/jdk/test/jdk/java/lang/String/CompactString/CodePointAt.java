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
 * @summary Tests Compact String. This one is for String.codePointAt.
 * @run testng/othervm -XX:+CompactStrings CodePointAt
 * @run testng/othervm -XX:-CompactStrings CodePointAt
 */

public class CodePointAt extends CompactString {

    @DataProvider
    public Object[][] provider() {
        return new Object[][] {

                new Object[] { STRING_L1, new int[] { 'A' } },
                new Object[] { STRING_L2, new int[] { 'A', 'B' } },
                new Object[] { STRING_L4, new int[] { 'A', 'B', 'C', 'D' } },
                new Object[] { STRING_LLONG,
                        new int[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' } },
                new Object[] { STRING_U1, new int[] { '\uFF21' } },
                new Object[] { STRING_U2, new int[] { '\uFF21', '\uFF22' } },
                new Object[] { STRING_M12, new int[] { '\uFF21', 'A' } },
                new Object[] { STRING_M11, new int[] { 'A', '\uFF21' } },
                new Object[] {
                        STRING_SUPPLEMENTARY,
                        new int[] { Character.toCodePoint('\uD801', '\uDC00'),
                                '\uDC00',
                                Character.toCodePoint('\uD801', '\uDC01'),
                                '\uDC01', '\uFF21', 'A' }, } };
    }

    @Test(dataProvider = "provider")
    public void testCodePointAt(String str, int[] expected) {
        map.get(str)
                .forEach(
                        (source, data) -> {
                            IntStream
                                    .range(0, str.length())
                                    .forEach(
                                            i -> assertEquals(
                                                    str.codePointAt(i),
                                                    expected[i],
                                                    String.format(
                                                            "testing String(%s).codePointAt(%d), source : %s, ",
                                                            escapeNonASCIIs(data),
                                                            i, source)));
                        });
    }
}
