/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/* @test
 * @bug 8138824
 * @summary Test expected equalsIgnoreCase behavior for some known asymmetric case mappings
 */

public class EqualsIgnoreCase {
    private static final String SMALL_I = "i";
    private static final String CAPITAL_I = "I";
    // Characters that do not map symmetrically between upper/lower case
    private static final String SMALL_DOTLESS_I = "\u0131";
    private static final String CAPITAL_I_WITH_DOT = "\u0130";
    private static final String LOWER_GREEK_THETA = "\u03D1";
    private static final String CAPITAL_GREEK_THETA = "\u03F4";

    public static void main(String[] args) {
        compareFuncs(SMALL_I, CAPITAL_I, true, true);
        compareFuncs(CAPITAL_I_WITH_DOT, SMALL_DOTLESS_I, true, false);
        compareFuncs(LOWER_GREEK_THETA, CAPITAL_GREEK_THETA, true, false);
    }

    /**
     * Compare the actual results of equalsIgnoreCase():
     *   toUpperCase(toLowerCase(eachChar))
     * to the behavior described in the equalsIgnoreCase() spec prior to 8138824:
     *   toUpperCase(eachChar)
     *   toLowerCase(eachChar)
     *
     * @param s1 A string
     * @param s2 Another string
     * @param expectEquals Expected result of equalsIgnoreCase()
     * @param expectTuTl Expected result of toUpperToLowerOriginals()
     */
    private static void compareFuncs(String s1, String s2, boolean expectEquals, boolean expectTuTl) {
        System.out.println(s1 + ", " + s2);
        boolean equalsResult = s1.equalsIgnoreCase(s2);
        System.out.println("equalsIgnoreCase:" + equalsResult);

        boolean tuTlResult = toUpperToLowerOriginals(s1, s2);
        System.out.println("tUtLO:" + tuTlResult);
        boolean failed = false;

        if (equalsResult != expectEquals) {
            System.out.println("Expected " + expectEquals + " from equalsIgnoreCase() but got " + equalsResult);
            failed = true;
        }
        if (tuTlResult != expectTuTl) {
            System.out.println("Expected " + expectTuTl + " from toUpperToLowerOriginals() but got " + tuTlResult);
            failed = true;
        }
        if (failed) { throw new RuntimeException("Test Failed"); }
    }

    /**
     * Apply toUpperCase() and toLowerCase() to corresponding chars of both
     * Strings.  Returns true if each pair of corresponding chars are either:
     *   1. == after both are converted to upper case
     * or
     *   2. == after both are converted to lower case
     * and the String lengths are equal.
     */
    private static boolean toUpperToLowerOriginals(String str1, String str2) {
        if (str1.length() != str2.length()) { return false; }
        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);

            char uc1 = Character.toUpperCase(c1);
            char uc2 = Character.toUpperCase(c2);
            boolean upperMatch = uc1 == uc2;

            char lc1 = Character.toLowerCase(c1);
            char lc2 = Character.toLowerCase(c2);
            boolean lowerMatch = lc1 == lc2;

            if (!(upperMatch || lowerMatch)) {
                return false;
            }
        }
        return true;
    }
}
