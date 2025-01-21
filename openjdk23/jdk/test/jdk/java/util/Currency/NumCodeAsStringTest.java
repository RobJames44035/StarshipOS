/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8154295
 * @summary Check getNumericCodeAsString() method which returns numeric code as a 3 digit String.
 * @run junit NumCodeAsStringTest
 */

import java.util.Currency;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumCodeAsStringTest {

    /**
     * Ensure getNumericCodeAsString() returns the correct 3-digit numeric code
     * for the associated currency Code.
     */
    @ParameterizedTest
    @MethodSource("codeProvider")
    public void checkNumCodeTest(String currCode, String expectedNumCode) {
        String actualNumCode = Currency.getInstance(currCode).getNumericCodeAsString();
        assertEquals(expectedNumCode, actualNumCode, String.format(
                "Expected: %s, but got: %s, for %s", expectedNumCode, actualNumCode, currCode));
    }

    private static Stream<Arguments> codeProvider() {
        return Stream.of(
                Arguments.of("AFA", "004"),
                Arguments.of("AUD", "036"),
                Arguments.of("USD", "840")
        );
    }
}
