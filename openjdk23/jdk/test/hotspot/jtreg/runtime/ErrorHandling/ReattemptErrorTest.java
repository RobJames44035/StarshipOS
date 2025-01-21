/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */


/*
 * @test
 * @summary Check secondary error handling
 * @library /test/lib
 * @requires vm.flagless
 * @requires vm.debug
 * @requires os.family != "windows"
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver ReattemptErrorTest
 */

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class ReattemptErrorTest {

    // 16 seconds for hs_err generation timeout = 4 seconds per step timeout
    public static final int ERROR_LOG_TIMEOUT = 16;

    public static void main(String[] args) throws Exception {

        // How this works:
        // The test will fault with SIGFPE (ErrorHandlerTest=15) and then, during error handling,
        // three pieces of reattempt logic are tested:
        // * First a step will fault with SIGSEGV. And then reattempts the step twice. With the first
        //   reattempt succeeding. And the second reattempt being skipped.
        // * Second a step will timeout, followed by a reattempt, the reattempt will be skipped.
        // * Third a step will use almost all stack space and then fault with SIGSEGV. After this the
        //   proceeding reattempt steps will be skipped because of low stack headroom.

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+UnlockDiagnosticVMOptions",
            "-Xmx100M",
            "-XX:-CreateCoredumpOnCrash",
            "-XX:ErrorHandlerTest=15",
            "-XX:TestCrashInErrorHandler=15",
            "-XX:ErrorLogTimeout=" + ERROR_LOG_TIMEOUT,
            "-version");

        OutputAnalyzer output_detail = new OutputAnalyzer(pb.start());

        // we should have crashed with a SIGFPE
        output_detail.shouldMatch("# A fatal error has been detected by the Java Runtime Environment:.*");
        output_detail.shouldMatch("#.+SIGFPE.*");

        // extract hs-err file
        File hs_err_file = HsErrFileUtils.openHsErrFileFromOutput(output_detail);

        ArrayList<Pattern> positivePatternlist = new ArrayList<>();
        ArrayList<Pattern> negativePatternlist = new ArrayList<>();

        // * First case
        //   * First step crashes
        positivePatternlist.add(Pattern.compile("Will crash now \\(TestCrashInErrorHandler=15\\)..."));
        positivePatternlist.add(Pattern.compile("\\[error occurred during error reporting \\(test reattempt secondary crash\\).*\\]"));
        //   * Second attempt succeeds
        positivePatternlist.add(Pattern.compile("test reattempt secondary crash. attempt 2"));
        //   * Third attempt is skipped
        negativePatternlist.add(Pattern.compile("test reattempt secondary crash. attempt 3"));

        // * Second case
        //   * First step timeouts
        positivePatternlist.add(Pattern.compile("test reattempt timeout"));
        positivePatternlist.add(Pattern.compile(".*timeout occurred during error reporting in step \"test reattempt timeout\".*"));
        //   * Second attempt is skipped because of previous timeout
        negativePatternlist.add(Pattern.compile("test reattempt secondary crash, attempt 2"));
        positivePatternlist.add(Pattern.compile(".*stop reattempt \\(test reattempt timeout, attempt 2\\) reason: Step time limit reached.*"));

        // * Third case
        //   * First step crashes after using almost all stack space
        positivePatternlist.add(Pattern.compile("test reattempt stack headroom"));
        positivePatternlist.add(Pattern.compile("\\[error occurred during error reporting \\(test reattempt stack headroom\\).*\\]"));
        //   * Second step is skip because of limited stack headroom
        negativePatternlist.add(Pattern.compile("test reattempt stack headroom, attempt 2"));
        positivePatternlist.add(Pattern.compile(".*stop reattempt \\(test reattempt stack headroom, attempt 2\\) reason: Stack headroom limit reached.*"));

        Pattern[] positivePatterns = positivePatternlist.toArray(new Pattern[] {});
        Pattern[] negativePatterns = negativePatternlist.toArray(new Pattern[] {});

        HsErrFileUtils.checkHsErrFileContent(hs_err_file, positivePatterns, negativePatterns, true, true);

        System.out.println("OK.");
    }
}
