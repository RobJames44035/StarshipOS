/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8306927 8307547
 * @modules jdk.localedata
 * @summary Tests Swedish collation involving 'v' and 'w'.
 * @run junit SwedishTest
 */

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SwedishTest {
    private static final String[] src = {"wb", "va", "vc"};
    private static final String[] standard = {"va", "vc", "wb"};
    private static final String[] traditional = {"va", "wb", "vc"};

    @ParameterizedTest
    @MethodSource("swedishData")
    public void testSwedishCollation(Locale l, String[] expected) {
        Arrays.sort(src, Collator.getInstance(l));
        assertArrayEquals(expected, src);
    }

    private static Stream<Arguments> swedishData() {
        return Stream.of(
            Arguments.of(Locale.forLanguageTag("sv"), standard),
            Arguments.of(Locale.forLanguageTag("sv-u-co-standard"), standard),
            Arguments.of(Locale.forLanguageTag("sv-u-co-STANDARD"), standard),
            Arguments.of(Locale.forLanguageTag("sv-u-co-traditio"), standard),
            Arguments.of(Locale.forLanguageTag("sv-u-co-TRADITIO"), standard),
            Arguments.of(Locale.forLanguageTag("sv-u-co-traditional"), standard),
            Arguments.of(Locale.forLanguageTag("sv-u-co-TRADITIONAL"), standard),
            // the new standard used to be called "reformed"
            Arguments.of(Locale.forLanguageTag("sv-u-co-reformed"), standard),
            Arguments.of(Locale.forLanguageTag("sv-u-co-REFORMED"), standard),

            Arguments.of(Locale.forLanguageTag("sv-u-co-trad"), traditional),
            Arguments.of(Locale.forLanguageTag("sv-u-co-TRAD"), traditional)
        );
    }
}
