/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @library /java/text/testlib
 * @summary test French Collation
 * @modules jdk.localedata
 * @run junit FrenchTest
 */
import java.util.Locale;
import java.text.Collator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

// Quick dummy program for printing out test results
public class FrenchTest {

    private static final String[] tertiarySourceData = {
        "abc",
        "COTE",
        "p\u00EAche",
        "p\u00EAcher",
        "p\u00E9cher",
        "p\u00E9cher",
        "Hello"
    };

    private static final String[] tertiaryTargetData = {
        "ABC",
        "c\u00f4te",
        "p\u00E9ch\u00E9",
        "p\u00E9ch\u00E9",
        "p\u00EAche",
        "p\u00EAcher",
        "hellO"
    };

    private static final int[] tertiaryResults = {
        -1, -1, -1,  1,  1, -1,  1
    };

    private static final String[] testData = {
        "a",
        "A",
        "e",
        "E",
        "\u00e9",
        "\u00e8",
        "\u00ea",
        "\u00eb",
        "ea",
        "x"
    };

    @Test
    public void TestTertiary() {
        TestUtils.doCollatorTest(myCollation, Collator.TERTIARY,
               tertiarySourceData, tertiaryTargetData, tertiaryResults);

        for (int i = 0; i < testData.length-1; i++) {
            for (int j = i+1; j < testData.length; j++) {
                TestUtils.doCollatorTest(myCollation, testData[i], testData[j], -1);
            }
        }
    }

    private final Collator myCollation = Collator.getInstance(Locale.FRANCE);
}
