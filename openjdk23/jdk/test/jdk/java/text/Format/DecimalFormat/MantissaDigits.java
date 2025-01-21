/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8159023
 * @summary Confirm behavior of mantissa for scientific notation in Decimal Format
 * @run junit MantissaDigits
 */

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MantissaDigits {
    private static final double[] NUMBERS = {
            1.1, 12.1, 123.1, 1234.1, 12345.1, 123456.1,
            -1.1, -12.1, -123.1, -1234.1, -12345.1, -123456.1,
            1, 12, 123, 1234, 12345, 123456, 1234567,
            -1, -12, -123, -1234, -12345, -123456, -1234567,
            1.1234, 1.1111, 1.412, 222.333, -771.2222
            };
    private static final DecimalFormatSymbols DFS = new DecimalFormatSymbols(Locale.US);
    private static final String ERRMSG = "%s formatted with %s gives %s, and " +
            "significant digit count was %s, but the formula provided %s%n";
    // Hard coded as 1, since all test patterns only have 1 exponent digit
    private static final int EXPONENTDIGITS = 1;

    @ParameterizedTest
    @MethodSource("patterns")
    public void testMantissaDefinition(String pattern, int minDigits, int maxDigits) {
        DecimalFormat df = new DecimalFormat(pattern, DFS);
        for (double number : NUMBERS) {
            // Count the significant digits in the pre-formatted number
            int originalNumDigits = (int) String.valueOf(number).chars()
                    .filter(Character::isDigit).count();

            if (wholeNumber(number)) {
                // Trailing 0 should not be counted
                originalNumDigits--;
            }

            // Format the number, then grab the significant
            // digits inside the mantissa
            String formattedNum = df.format(number);
            int mantissaDigits = (int) formattedNum.chars()
                    .filter(Character::isDigit).count() - EXPONENTDIGITS;

            // Test the new definition of the Mantissa
            Integer calculatedDigits = Math
                    .min(Math.max(minDigits, originalNumDigits), maxDigits);
            assertEquals(mantissaDigits, calculatedDigits, String.format(ERRMSG,
                    number, pattern, formattedNum, mantissaDigits, calculatedDigits));
        }
    }

    private static Boolean wholeNumber(double number) {
        return (int) number == number;
    }

    private static Stream<Arguments> patterns() {
        return Stream.of(
                Arguments.of("#0.0##E0", 2, 5),
                Arguments.of("#00.00##E0", 4, 7),
                Arguments.of("#0.000##E0", 4, 7),
                Arguments.of("#00.000##E0", 5, 8),
                Arguments.of("#000.0##E0", 4, 7),
                Arguments.of("#000.00##E0", 5, 8),
                Arguments.of("#000.000##E0", 6, 9),
                Arguments.of("000.000E0", 6, 6),
                Arguments.of("#.##E0", 0, 3),
                Arguments.of("######.######E0", 0, 12),
                Arguments.of("####00.00######E0", 4, 14)
        );
    }
}
