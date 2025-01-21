/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4018937
 * @summary Confirm that DecimalFormat.parse() parses BigDecimal and BigInteger
 *          string values as expected. Specifically, ensure a ParseException is
 *          not thrown as well as the parsed value being numerically correct.
 *          Tests large String values with combinations of multipliers and exponents.
 * @run junit BigDecimalCompatibilityTest
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class BigDecimalCompatibilityTest {

    private static DecimalFormat df = new DecimalFormat();
    // Save JVM default Locale
    private static final Locale savedLocale = Locale.getDefault();

    // ---- Used for the test data (start) ----

    // Both ArrayList composed of Arguments(String longString, int multiplier)
    private static final ArrayList<Arguments> bigIntegers = new ArrayList<Arguments>();
    private static final ArrayList<Arguments> bigDecimals = new ArrayList<Arguments>();

    // Long string data to generate combinations of test values
    private static final String[] inputData = {
            "0".repeat(400),
            "1234567890".repeat(40)};

    // Variety of exponents to test parse() against
    private static final String[] exponents = {
            "E-100", "E100", "E-900", "E900", ""
    };

    // Variety of multipliers that DecimalFormat can apply
    private static final int[] multipliers = {
            -1, 1, -100, 100, -9999, 9999
    };
    // ---- Used for the test data (end) ----

    // Set JVM default Locale to US and populate the test arrayLists
    @BeforeAll
    static void initAll() {
        Locale.setDefault(Locale.US);
        buildTestData();
    }

    /*
     * Uses inputData and exponents to build long string
     * decimal and integer values and populate bigDecimals and bigIntegers
     * accordingly. Attaches a multiplier value as well to the test data.
     */
    private static void buildTestData() {
        for (String longString1 : inputData) {
            for (String longString2 : inputData) {
                String bigInteger = longString1 + longString2;
                for (int multiplier : multipliers) {
                    bigIntegers.add(Arguments.of(bigInteger, multiplier));
                    bigIntegers.add(Arguments.of('-' + bigInteger, multiplier));
                }
                for (String longString3 : inputData) {
                    for (String longString4 : inputData) {
                        for (String exponent : exponents) {
                            String bigDecimal = longString1 + longString2 + '.'
                                    + longString3 + longString4 + exponent;
                            for (int multiplier : multipliers) {
                                bigDecimals.add(Arguments.of(bigDecimal, multiplier));
                                bigDecimals.add(Arguments.of('-' + bigDecimal, multiplier));
                            }
                        }
                    }
                }
            }
        }
    }

    // Restore JVM default Locale
    @AfterAll
    static void tearDownAll() {
        Locale.setDefault(savedLocale);
    }

    // Tests strings with length 1600+. See test() for specific details.
    @ParameterizedTest
    @MethodSource("bigDecimalProvider")
    public void bigDecimalParseTest(String longString, int multiplier) {
        test(longString, multiplier);
    }

    // Returns 960 arrangements of bigDecimal string values and multipliers
    // In the form of (String, int).
    private static Stream<Arguments> bigDecimalProvider() {
        return bigDecimals.stream();
    }

    // Tests strings with length 800+. See test() for specific details.
    @ParameterizedTest
    @MethodSource("bigIntegerProvider")
    public void bigIntegerParseTest(String longString, int multiplier) {
        test(longString, multiplier);
    }

    // Returns 48 arrangements of bigInteger string values and multipliers
    // In the form of (String, int).
    private static Stream<Arguments> bigIntegerProvider() {
        return bigIntegers.stream();
    }

    /*
     * Tests that parsing a large BigDecimal/BigInteger string value
     * will not throw a ParseException with setParseBigDecimal as true.
     * Parses with a variety of multiplier values. Then ensures that the parsed
     * value is the expected number.
     */
    private static void test(String longString, int multiplier) {
        // Reset DecimalFormat for a clean test
        df = new DecimalFormat();
        df.setParseBigDecimal(true);
        // wide enough to support the long string test data
        df.setMaximumFractionDigits(Integer.MAX_VALUE);
        df.setMultiplier(multiplier);

        // Check parse and returned value. This was originally intended to ensure
        // a ParseException is not thrown
        Number parsedValue = assertDoesNotThrow(()-> df.parse(longString),
                "Should not throw an Exception");
        BigDecimal expectedValue = getExpected(longString, multiplier);
        assertEquals(expectedValue, parsedValue, "With multiplier: " + multiplier);
    }

    // Utility to get a numerically correct value of a long string.
    // Dependent on BigDecimal implementation
    private static BigDecimal getExpected(String longString, int multiplier) {
        BigDecimal expected = new BigDecimal(longString);
        try {
            expected = expected.divide(new BigDecimal(multiplier));
        }
        catch (ArithmeticException e) {
            expected = expected.divide(new BigDecimal(multiplier), RoundingMode.HALF_EVEN);
        }
        return expected;
    }
}
