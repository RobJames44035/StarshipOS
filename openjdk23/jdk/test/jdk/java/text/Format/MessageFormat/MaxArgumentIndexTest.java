/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8331446
 * @summary Enforce the MAX_ARGUMENT_INDEX(10,000) implementation limit for the
 *          ArgumentIndex element in the MessageFormat pattern syntax. This
 *          should be checked during construction/applyPattern/readObject and should effectively
 *          prevent parse/format from being invoked with values over the limit.
 * @run junit MaxArgumentIndexTest
 */

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaxArgumentIndexTest {

    // A MessageFormat pattern that contains an ArgumentIndex value
    // which violates this implementation's limit: MAX_ARGUMENT_INDEX(10,000)
    // As this check is exclusive, 10,000 will violate the limit
    private static final String VIOLATES_MAX_ARGUMENT_INDEX = "{10000}";

    // Check String constructor enforces the limit
    @Test
    public void constructorTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new MessageFormat(VIOLATES_MAX_ARGUMENT_INDEX));
    }

    // Check String, Locale constructor enforces the limit
    @ParameterizedTest
    @MethodSource
    public void constructorWithLocaleTest(Locale locale) {
        assertThrows(IllegalArgumentException.class,
                () -> new MessageFormat(VIOLATES_MAX_ARGUMENT_INDEX, locale));
    }

    // Provide some basic common locale values
    private static Stream<Locale> constructorWithLocaleTest() {
        return Stream.of(null, Locale.US, Locale.ROOT);
    }

    // Edge case: Test a locale dependent subformat (with null locale) with a
    // violating ArgumentIndex. In this instance, the violating ArgumentIndex
    // will be caught and IAE thrown instead of the NPE
    @Test
    public void localeDependentSubFormatTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new MessageFormat("{10000,number,short}", null));
        // For reference
        assertThrows(NullPointerException.class,
                () -> new MessageFormat("{999,number,short}", null));
    }

    // Check that the static format method enforces the limit
    @Test
    public void staticFormatTest() {
        assertThrows(IllegalArgumentException.class,
                () -> MessageFormat.format(VIOLATES_MAX_ARGUMENT_INDEX, new Object[]{1}));
    }

    // Check that applyPattern(String) enforces the limit
    @Test
    public void applyPatternTest() {
        MessageFormat mf = new MessageFormat("");
        assertThrows(IllegalArgumentException.class,
                () -> mf.applyPattern(VIOLATES_MAX_ARGUMENT_INDEX));
    }
}
