/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @library /java/text/testlib
 * @summary test Supplementary Character Collation
 * @run junit SurrogatesTest
 */

import java.text.Collator;
import java.text.RuleBasedCollator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

// Quick dummy program for printing out test results
public class SurrogatesTest {

    /*
     * Data for TestPrimary()
     */
    private static final String[] primarySourceData = {
        "A\ud800\udc04BCD"
    };

    private static final String[] primaryTargetData = {
        "A\ud800\udc05BCD"
    };

    private static final int[] primaryResults = {
         0
    };

    /*
     * Data for TestTertiary()
     */
    private static final String[] tertiarySourceData = {
        "ABCD",
        "ABCD",
        "A\ud800\udc00CD",
        "WXYZ",
        "WXYZ",
        "AFEM",
        "FGM",
        "BB",
        "BB"
    };

    private static final String[] tertiaryTargetData = {
        "A\ud800\udc00CD",
        "AB\ud800\udc00D",
        "A\ud800\udc01CD",
        "W\ud800\udc0aYZ",
        "W\ud800\udc0bYZ",
        "A\ud800\udc08M",
        "\ud800\udc08M",
        "\ud800\udc04\ud800\udc02",
        "\ud800\udc04\ud800\udc05"
    };

    private static final int[] tertiaryResults = {
        -1,  1,  1,  1, -1, -1, -1, -1,  1
    };

    @Test
    public void TestPrimary() {
        TestUtils.doCollatorTest(myCollation, Collator.PRIMARY,
               primarySourceData, primaryTargetData, primaryResults);
    }

    @Test
    public void TestTertiary() {
        TestUtils.doCollatorTest(myCollation, Collator.TERTIARY,
               tertiarySourceData, tertiaryTargetData, tertiaryResults);
    }

    private Collator getCollator() {
        RuleBasedCollator base = (RuleBasedCollator)Collator.getInstance();
        String rule = base.getRules();
        try {
            return new RuleBasedCollator(rule
                                     + "&B < \ud800\udc01 < \ud800\udc00"
                                     + ", \ud800\udc02, \ud800\udc03"
                                     + "; \ud800\udc04, \ud800\udc05"
                                     + "< \ud800\udc06 < \ud800\udc07"
                                     + "&FE < \ud800\udc08"
                                     + "&PE, \ud800\udc09"
                                     + "&Z < \ud800\udc0a < \ud800\udc0b < \ud800\udc0c"
                                     + "&\ud800\udc0a < x, X"
                                     + "&A < \ud800\udc04\ud800\udc05");
        } catch (Exception e) {
            fail("Failed to create new RulebasedCollator object");
            return null;
        }
    }

    private Collator myCollation = getCollator();
}
