/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4512215 4818420 4819436 8310923
 * @summary Test currencies without minor units.
 * @run junit NoMinorUnitCurrenciesTest
 */

import java.util.Currency;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoMinorUnitCurrenciesTest {

    /**
     * Spot check some minor undefined currencies and ensure their default fraction
     * digits are not 2.
     */
    @ParameterizedTest
    @MethodSource("minorUndefined")
    public void checkFractionDigits(String currencyCode, int digits) {
        Currency currency = Currency.getInstance(currencyCode);
        assertEquals(currency.getCurrencyCode(), currencyCode);
        assertEquals(currency.getDefaultFractionDigits(), digits, String.format(
                "[%s] expected: %s; got: %s", currencyCode, digits, currency.getDefaultFractionDigits()));
    }

    // Currencies from the minorUndefined key of CurrencyData.properties
    // (These are currencies without minor units)
    private static Stream<Arguments> minorUndefined() {
        return Stream.of(
                Arguments.of("XBD", -1),
                Arguments.of("XAG", -1),
                Arguments.of("XAU", -1),
                Arguments.of("XBA", -1),
                Arguments.of("XBB", -1)
        );
    }
}
