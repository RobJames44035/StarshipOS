/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/* @test
 * @bug 7130915 8289689
 * @summary Tests file path with nfc/nfd forms on MacOSX
 * @requires (os.family == "mac")
 * @library /test/lib ..
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 *        TestUtil MacPath
 * @run main MacPathTest
 * @run main/othervm -Djdk.nio.path.useNormalizationFormD=true MacPathTest
 */

import jdk.test.lib.process.ProcessTools;

public class MacPathTest {
    private static final String PROPERTY_NORMALIZE_FILE_PATHS =
        "jdk.nio.path.useNormalizationFormD";
    private static final boolean NORMALIZE_FILE_PATHS =
        Boolean.getBoolean(PROPERTY_NORMALIZE_FILE_PATHS);

    public static void main(String args[]) throws Exception {
        ProcessBuilder pb;
        if (NORMALIZE_FILE_PATHS) {
            String option = "-D" + PROPERTY_NORMALIZE_FILE_PATHS + "=true";
            pb = ProcessTools.createTestJavaProcessBuilder(option, MacPath.class.getName());
        } else {
            pb = ProcessTools.createTestJavaProcessBuilder(MacPath.class.getName());
        }
        pb.environment().put("LC_ALL", "en_US.UTF-8");
        ProcessTools.executeProcess(pb)
                    .outputTo(System.out)
                    .errorTo(System.err)
                    .shouldHaveExitValue(0);
    }
}
