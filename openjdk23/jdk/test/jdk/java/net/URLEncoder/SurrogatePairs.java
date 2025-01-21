/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4396708
 * @summary Test URL encoder and decoder on a string that contains
 * surrogate pairs.
 * @run junit SurrogatePairs
 */

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Surrogate pairs are two character Unicode sequences where the first
 * character lies in the range [d800, dbff] and the second character lies
 * in the range [dc00, dfff]. They are used as an escaping mechanism to add
 * 1M more characters to Unicode.
 */
public class SurrogatePairs {

    public static String[][] arguments() {
        return new String[][] {
                {"\uD800\uDC00", "%F0%90%80%80"},
                {"\uD800\uDFFF", "%F0%90%8F%BF"},
                {"\uDBFF\uDC00", "%F4%8F%B0%80"},
                {"\uDBFF\uDFFF", "%F4%8F%BF%BF"},
                {"1\uDBFF\uDC00", "1%F4%8F%B0%80"},
                {"@\uDBFF\uDC00", "%40%F4%8F%B0%80"},
                {"\uDBFF\uDC001", "%F4%8F%B0%801"},
                {"\uDBFF\uDC00@", "%F4%8F%B0%80%40"},
                {"\u0101\uDBFF\uDC00", "%C4%81%F4%8F%B0%80"},
                {"\u0101\u0101\u0101\u0101\u0101\u0101\uDBFF\uDC00\u0101", "%C4%81%C4%81%C4%81%C4%81%C4%81%C4%81%F4%8F%B0%80%C4%81"},
                {"\u0101\u0101\u0101\u0101\u0101\u0101\u0101\uDBFF\uDC00\u0101", "%C4%81%C4%81%C4%81%C4%81%C4%81%C4%81%C4%81%F4%8F%B0%80%C4%81"},
                {"\u0101\u0101\u0101\u0101\u0101\u0101\u0101\u0101\uDBFF\uDC00\u0101", "%C4%81%C4%81%C4%81%C4%81%C4%81%C4%81%C4%81%C4%81%F4%8F%B0%80%C4%81"},
                {"\uDBFF\uDC00\u0101", "%F4%8F%B0%80%C4%81"},
                {"\uDE0A\uD83D", "%3F%3F"},
                {"1\uDE0A\uD83D", "1%3F%3F"},
                {"@\uDE0A\uD83D", "%40%3F%3F"},
                {"1@1\uDE0A\uD800\uDC00 \uD83D", "1%401%3F%F0%90%80%80+%3F"}
        };
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void test(String str, String correctEncoding) {
        String encoded = URLEncoder.encode(str, UTF_8);
        assertEquals(correctEncoding, encoded, () ->
                "str=%s, expected=%s, actual=%s"
                        .formatted(escape(str), escape(correctEncoding), escape(encoded)));

        // Map unmappable characters to '?'
        String cleanStr = new String(str.getBytes(UTF_8), UTF_8);
        String decoded = URLDecoder.decode(encoded, UTF_8);
        assertEquals(cleanStr, decoded, () ->
                "expected=%s, actual=%s".formatted(escape(str), escape(decoded)));
    }

    private static String escape(String s) {
        return s.chars().mapToObj(c -> String.format("\\u%04x", c))
                .collect(Collectors.joining());
    }
}
