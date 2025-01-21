/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @run main StringConstructor
 * @bug 4103117 4331084 4488017 4490929 6255285 6268365 8074460 8078672 8233760
 * @summary Tests the BigDecimal string constructor (use -Dseed=X to set PRNG seed).
 * @key randomness
 */

import java.math.*;
import java.util.Random;
import jdk.test.lib.RandomFactory;

public class StringConstructor {

    public static void main(String[] args) throws Exception {
        constructWithError("");
        constructWithError("+");
        constructWithError("-");
        constructWithError("+e");
        constructWithError("-e");
        constructWithError("e+");
        constructWithError("1.-0");
        constructWithError(".-123");
        constructWithError("-");
        constructWithError("--1.1");
        constructWithError("-+1.1");
        constructWithError("+-1.1");
        constructWithError("1-.1");
        constructWithError("1+.1");
        constructWithError("1.111+1");
        constructWithError("1.111-1");
        constructWithError("11.e+");
        constructWithError("11.e-");
        constructWithError("11.e+-");
        constructWithError("11.e-+");
        constructWithError("11.e-+1");
        constructWithError("11.e+-1");

        // Range checks
        constructWithError("1e"+Integer.MIN_VALUE);
        constructWithError("10e"+Integer.MIN_VALUE);
        constructWithError("0.01e"+Integer.MIN_VALUE);
        constructWithError("1e"+((long)Integer.MIN_VALUE-1));

        leadingExponentZeroTest();
        nonAsciiZeroTest();

        /* These BigDecimals produce a string with an exponent > Integer.MAX_VALUE */
        roundtripWithAbnormalExponent(BigDecimal.valueOf(10, Integer.MIN_VALUE));
        roundtripWithAbnormalExponent(BigDecimal.valueOf(Long.MIN_VALUE, Integer.MIN_VALUE));
        roundtripWithAbnormalExponent(new BigDecimal(new BigInteger("1" + "0".repeat(100)), Integer.MIN_VALUE));

        /* These Strings have an exponent > Integer.MAX_VALUE */
        roundtripWithAbnormalExponent("1.0E+2147483649");
        roundtripWithAbnormalExponent("-9.223372036854775808E+2147483666");
        roundtripWithAbnormalExponent("1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000E+2147483748");

        // Roundtrip tests
        Random random = RandomFactory.getRandom();
        for (int i=0; i<100; i++) {
            int size = random.nextInt(100) + 1;
            BigInteger bi = new BigInteger(size, random);
            if (random.nextBoolean())
                bi = bi.negate();
            int decimalLength = bi.toString().length();
            int scale = random.nextInt(decimalLength);
            BigDecimal bd = new BigDecimal(bi, scale);
            String bdString = bd.toString();
            // System.err.println("bi" + bi.toString() + "\tscale " + scale);
            // System.err.println("bd string: " + bdString);
            BigDecimal bdDoppel = new BigDecimal(bdString);
            if (!bd.equals(bdDoppel)) {
                System.err.println("bd string: scale: " + bd.scale() +
                                   "\t" + bdString);
                System.err.println("bd doppel: scale: " + bdDoppel.scale() +
                                   "\t" + bdDoppel.toString());
                throw new RuntimeException("String constructor failure.");
            }
        }
    }

    private static void roundtripWithAbnormalExponent(BigDecimal bd) {
        if (!bd.equals(new BigDecimal(bd.toString()))) {
            throw new RuntimeException("Abnormal exponent roundtrip failure");
        }
    }

    private static void roundtripWithAbnormalExponent(String s) {
        if (!s.equals(new BigDecimal(s).toString())) {
            throw new RuntimeException("Abnormal exponent roundtrip failure");
        }
    }

    /*
     * Verify precision is set properly if the significand has
     * non-ASCII leading zeros.
     */
    private static void nonAsciiZeroTest() {
        String values[] = {
            "00004e5",
            "\u0660\u0660\u0660\u06604e5",
        };

        BigDecimal expected = new BigDecimal("4e5");

        for(String s : values) {
            BigDecimal tmp = new BigDecimal(s);
            // System.err.println("Testing " + s);
            if (! expected.equals(tmp) || tmp.precision() != 1) {
                System.err.println("Bad conversion of " + s + "got " +
                                   tmp + "precision = " + tmp.precision());
                throw new RuntimeException("String constructor failure.");
            }
        }

    }

    private static void leadingExponentZeroTest() {
        BigDecimal twelve = new BigDecimal("12");
        BigDecimal onePointTwo = new BigDecimal("1.2");

        String start = "1.2e0";
        String end = "1";
        String middle = "";

        // Test with more excess zeros than the largest number of
        // decimal digits needed to represent a long
        int limit  = ((int)Math.log10(Long.MAX_VALUE)) + 6;
        for(int i = 0; i < limit; i++, middle += "0") {
            String t1 = start + middle;
            String t2 = t1 + end;

            // System.out.println(i + "\t" + t1 + "\t" + t2);
            testString(t1, onePointTwo);
            testString(t2, twelve);
        }
    }

    private static void testString(String s, BigDecimal expected) {
        testString0(s, expected);
        testString0(switchZero(s), expected);
    }

    private static void testString0(String s, BigDecimal expected) {
        if (!expected.equals(new BigDecimal(s)))
            throw new RuntimeException(s + " is not equal to " + expected);
    }

    private static String switchZero(String s) {
        return s.replace('0', '\u0660'); // Arabic-Indic zero
    }

    private static void constructWithError(String badString) {
        try {
            BigDecimal d = new BigDecimal(badString);
            throw new RuntimeException(badString + " accepted");
        } catch(NumberFormatException e) {
        }
    }
}
