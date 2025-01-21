/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8026766
 * @summary Confirm that LanguageRange.toString() returns an expected result.
 * @run junit LRToString
 */

import java.util.Locale.LanguageRange;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LRToString {

    /**
     * This test ensures that the output of LanguageRange.toString()
     * returns an expected result, that is, the weight is hidden if it is
     * equal to 1.0.
     */
    @ParameterizedTest
    @MethodSource("ranges")
    public void toStringTest(String range, double weight) {
        LanguageRange lr = new LanguageRange(range, weight);
        String expected = weight == 1.0
                ? range
                : range+";q="+weight;
        assertEquals(lr.toString(), expected);
    }

    private static Stream<Arguments> ranges() {
        return Stream.of(
                Arguments.of("ja", 1.0),
                Arguments.of("de", 0.5),
                Arguments.of("fr", 0.0)
        );
    }
}
