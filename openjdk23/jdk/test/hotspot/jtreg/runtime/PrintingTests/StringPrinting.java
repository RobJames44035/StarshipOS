/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8325945
 * @summary Test abridged VM String printing
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI StringPrinting
 */

import jdk.test.whitebox.WhiteBox;

public class StringPrinting {

    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    static void checkEqual(String s1, String s2) {
        if (!s1.equals(s2)) {
            throw new RuntimeException("Different strings: " + s1 + " vs " + s2);
        }
    }

    static void checkEqual(int len1, int len2) {
        if (len1 != len2) {
            throw new RuntimeException("Different lengths: " + len1 + " vs " + len2);
        }
    }

    public static void main(String[] args) {
        // Modified string format is "xxx ... (N characters ommitted) ... xxx" (abridged)
        final String elipse = " ... ";
        final String text = " characters ommitted)";
        final String abridged = "\" (abridged) ";

        // Define a set of maxLengths for ease of inspection in the test outout
        // Note: maxLength must be >= 2
        int[] maxLengths = new int[] { 2, 3, 16, 256, 512 };
        for (int maxLength : maxLengths) {
            // Test string lengths around maxLength and "much" bigger
            // than maxLength
            int[] strLengths = new int[] { maxLength - 1,
                                           maxLength,
                                           maxLength + 1,
                                           2 * maxLength
            };
            for (int length : strLengths) {
                System.out.println("Testing string length " + length + " with maxLength " + maxLength);
                String s = "x".repeat(length);
                String r = WB.printString(s, maxLength);
                if (length <= maxLength) {
                    // Strip off the double-quotes that surround the string
                    if (r.charAt(0) == '\"' && r.charAt(r.length() - 1) == '\"') {
                        r = r.substring(1, r.length() - 1);
                    } else {
                        throw new RuntimeException("String was not quoted as expected: " + r);
                    }
                    checkEqual(s, r);
                } else {
                    // Strip off leading double-quote
                    if (r.charAt(0) == '\"') {
                        r = r.substring(1, r.length());
                    } else {
                        throw new RuntimeException("String was not quoted as expected: " + r);
                    }

                    // Strip off abridged
                    if (r.endsWith(abridged)) {
                        r = r.substring(0, r.length() - abridged.length());
                    } else {
                        throw new RuntimeException("String was not marked abridged as expected: " + r);
                    }

                    // Now extract the two "halves"
                    int elipseStart = r.indexOf(elipse);
                    String firstHalf = r.substring(0, elipseStart);
                    int secondElipseStart = r.lastIndexOf(elipse);
                    String secondHalf = r.substring(secondElipseStart + elipse.length(), r.length());

                    System.out.println("S1: >" + firstHalf + "<");
                    System.out.println("S2: >" + secondHalf + "<");

                    checkEqual(firstHalf.length(), maxLength / 2);
                    checkEqual(secondHalf.length(), maxLength /2);

                    // Now check number of characters ommitted
                    String tail = r.substring(r.indexOf("("), r.length());
                    int numberEnd = tail.indexOf(" ");
                    String nChars = tail.substring(1, numberEnd);
                    System.out.println("N: >" + nChars + "<");

                    // Now add all the bits back together to get the expected full length
                    int fullLength = maxLength / 2 + elipse.length() + 1 /* for ( */
                        + nChars.length() + text.length() + elipse.length() + maxLength / 2;
                    checkEqual(r.length(), fullLength);
                }
            }
        }
    }
}
