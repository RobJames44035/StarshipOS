/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4106263
 * @summary Tests on the bug 4106263 - CollationKey became non-final extendable class.
 *          The implementation of CollationKey is moved to the new private class,
 *          RuleBasedCollationKey. This test basically tests on the two features:
 *          1. Existing code using CollationKey works (backward compatiblility)
 *          2. CollationKey can be extended by its subclass.
 * @modules jdk.localedata
 */


public class CollationKeyTest {

    public static void main(String[] args) {
        CollationKeyTestImpl ck = new CollationKeyTestImpl("Testing the CollationKey");
        ck.run();
    }
}
