/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6807534
 * @summary check whether the default implementation of
 *    CurrencyNameProvider.getDisplayName(String, Locale) throws appropriate
 *    exceptions when necessary.
 * @run junit CNPGetDisplayName
 */

import java.util.Locale;
import java.util.spi.CurrencyNameProvider;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CNPGetDisplayName {

    static final CurrencyNameProvider cnp = new CurrencyNameProviderImpl();

    /**
     * Tests that the currency name provider throws a NullPointerException
     * under the expected circumstances.
     */
    @ParameterizedTest
    @MethodSource("nullArgProvider")
    public void NPETest(String currencyCode, Locale locale, String err) {
        assertThrows(NullPointerException.class,
                () -> cnp.getDisplayName(currencyCode, locale), err);
    }

    /**
     * Tests that the currency name provider throws a IllegalArgumentException
     * under the expected circumstances.
     */
    @ParameterizedTest
    @MethodSource("illegalArgProvider")
    public void IAETest(String currencyCode, Locale locale, String err) {
        assertThrows(IllegalArgumentException.class,
                () -> cnp.getDisplayName(currencyCode, locale), err);
    }

    private static Stream<Arguments> nullArgProvider() {
        return Stream.of(
                Arguments.of(null, Locale.US,
                        "NPE was not thrown with null currencyCode"),
                Arguments.of("USD", null,
                        "NPE was not thrown with null locale")
        );
    }

    private static Stream<Arguments> illegalArgProvider() {
        return Stream.of(
                Arguments.of("INVALID", Locale.US,
                        "IAE was not thrown with invalid currency code"),
                Arguments.of("inv", Locale.US,
                        "IAE was not thrown with invalid currency code"),
                Arguments.of("USD", Locale.JAPAN,
                        "IllegalArgumentException was not thrown with non-supported locale")
        );
    }

    static class CurrencyNameProviderImpl extends CurrencyNameProvider {
        // dummy implementation
        public String getSymbol(String currencyCode, Locale locale) {
            return "";
        }

        public Locale[] getAvailableLocales() {
            Locale[] avail = new Locale[1];
            avail[0] = Locale.US;
            return avail;
        }
    }
}
