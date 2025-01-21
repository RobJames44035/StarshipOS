/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8305486
 * @summary Tests to exercise the split functionality added in the issue.
 * @run junit SplitWithDelimitersTest
 */

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SplitWithDelimitersTest {

    private static String[] dropOddIndexed(String[] a, int limit) {
        String[] r = new String[(a.length + 1) / 2];
        for (int i = 0; i < a.length; i += 2) {
            r[i / 2] = a[i];
        }
        int len = r.length;
        if (limit == 0) {
            /* Also drop trailing empty strings */
            for (; len > 0 && r[len - 1].isEmpty(); --len);  // empty body
        }
        return len < r.length ? Arrays.copyOf(r, len) : r;
    }

    static Arguments[] testSplit() {
        return new Arguments[] {
                arguments(new String[] {"b", "o", "", "o", ":::and::f", "o", "", "o", ""},
                        "boo:::and::foo", "o", 5),
                arguments(new String[] {"b", "o", "", "o", ":::and::f", "o", "o"},
                        "boo:::and::foo", "o", 4),
                arguments(new String[] {"b", "o", "", "o", ":::and::foo"},
                        "boo:::and::foo", "o", 3),
                arguments(new String[] {"b", "o", "o:::and::foo"},
                        "boo:::and::foo", "o", 2),
                arguments(new String[] {"boo:::and::foo"},
                        "boo:::and::foo", "o", 1),
                arguments(new String[] {"b", "o", "", "o", ":::and::f", "o", "", "o"},
                        "boo:::and::foo", "o", 0),
                arguments(new String[] {"b", "o", "", "o", ":::and::f", "o", "", "o", ""},
                        "boo:::and::foo", "o", -1),

                arguments(new String[] {"boo", ":::", "and", "::", "foo"},
                        "boo:::and::foo", ":+", 3),
                arguments(new String[] {"boo", ":::", "and::foo"},
                        "boo:::and::foo", ":+", 2),
                arguments(new String[] {"boo:::and::foo"},
                        "boo:::and::foo", ":+", 1),
                arguments(new String[] {"boo", ":::", "and", "::", "foo"},
                        "boo:::and::foo", ":+", 0),
                arguments(new String[] {"boo", ":::", "and", "::", "foo"},
                        "boo:::and::foo", ":+", -1),

                arguments(new String[] {"b", "", "b", "", ""},
                        "bb", "a*|b*", 3),
                arguments(new String[] {"b", "", "b"},
                        "bb", "a*|b*", 2),
                arguments(new String[] {"bb"},
                        "bb", "a*|b*", 1),
                arguments(new String[] {"b", "", "b"},
                        "bb", "a*|b*", 0),
                arguments(new String[] {"b", "", "b", "", ""},
                        "bb", "a*|b*", -1),

                arguments(new String[] {"", "bb", "", "", ""},
                        "bb", "b*|a*", 3),
                arguments(new String[] {"", "bb", ""},
                        "bb", "b*|a*", 2),
                arguments(new String[] {"bb"},
                        "bb", "b*|a*", 1),
                arguments(new String[] {"", "bb"},
                        "bb", "b*|a*", 0),
                arguments(new String[] {"", "bb", "", "", ""},
                        "bb", "b*|a*", -1),
        };
    }

    @ParameterizedTest
    @MethodSource
    void testSplit(String[] expected, String target, String regex, int limit) {
        String[] computedWith = target.splitWithDelimiters(regex, limit);
        assertArrayEquals(expected, computedWith);
        String[] patComputedWith = Pattern.compile(regex).splitWithDelimiters(target, limit);
        assertArrayEquals(computedWith, patComputedWith);

        String[] computedWithout = target.split(regex, limit);
        assertArrayEquals(dropOddIndexed(expected, limit), computedWithout);
        String[] patComputedWithout = Pattern.compile(regex).split(target, limit);
        assertArrayEquals(computedWithout, patComputedWithout);
    }

}
