/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @bug 8189134
 * @summary Tests the system properties
 * @modules jdk.localedata
 * @build DefaultLocaleTest
 * @run testng/othervm SystemPropertyTests
 */

import static jdk.test.lib.process.ProcessTools.executeTestJava;
import static org.testng.Assert.assertTrue;

import java.util.Locale;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test Locale.getDefault() reflects the system property. Note that the
 * result may change depending on the CLDR releases.
 */
@Test
public class SystemPropertyTests {

    private static String LANGPROP = "-Duser.language=en";
    private static String SCPTPROP = "-Duser.script=";
    private static String CTRYPROP = "-Duser.country=US";

    @DataProvider(name="data")
    Object[][] data() {
        return new Object[][] {
            // system property, expected default, expected format, expected display
            {"-Duser.extensions=u-ca-japanese",
             "en_US_#u-ca-japanese",
             "en_US_#u-ca-japanese",
             "en_US_#u-ca-japanese",
            },

            {"-Duser.extensions=u-ca-japanese-nu-thai",
             "en_US_#u-ca-japanese-nu-thai",
             "en_US_#u-ca-japanese-nu-thai",
             "en_US_#u-ca-japanese-nu-thai",
            },

            {"-Duser.extensions=foo",
             "en_US",
             "en_US",
             "en_US",
            },

            {"-Duser.extensions.format=u-ca-japanese",
             "en_US",
             "en_US_#u-ca-japanese",
             "en_US",
            },

            {"-Duser.extensions.display=u-ca-japanese",
             "en_US",
             "en_US",
             "en_US_#u-ca-japanese",
            },
        };
    }

    @Test(dataProvider="data")
    public void runTest(String extprop, String defLoc,
                        String defFmtLoc, String defDspLoc) throws Exception {
        int exitValue = executeTestJava(LANGPROP, SCPTPROP, CTRYPROP,
                                    extprop, "DefaultLocaleTest", defLoc, defFmtLoc, defDspLoc)
                            .outputTo(System.out)
                            .errorTo(System.out)
                            .getExitValue();

        assertTrue(exitValue == 0);
    }
}
