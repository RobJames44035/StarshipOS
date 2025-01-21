/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */


/*
 * @test
 * @bug 8214975
 * @summary No hs-err file if fatal error is raised during dynamic initialization.
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @requires vm.flagless
 * @requires (vm.debug == true)
 * @requires os.family == "linux"
 * @run driver VeryEarlyAssertTest
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.Map;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class VeryEarlyAssertTest {

  public static void main(String[] args) throws Exception {


    ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-version");
    Map<String, String> env = pb.environment();
    env.put("HOTSPOT_FATAL_ERROR_DURING_DYNAMIC_INITIALIZATION", "1");

    OutputAnalyzer output_detail = new OutputAnalyzer(pb.start());

    // we should have crashed with an assert with a specific message:
    output_detail.shouldMatch("# A fatal error has been detected by the Java Runtime Environment:.*");
    output_detail.shouldMatch("#.*HOTSPOT_FATAL_ERROR_DURING_DYNAMIC_INITIALIZATION.*");

    // extract hs-err file
    File hs_err_file = HsErrFileUtils.openHsErrFileFromOutput(output_detail);

    // scan hs-err file: File should contain the same assertion message. Other than that,
    // do not expect too much: file will be littered with secondary errors. The test
    // should test that we get a hs-err file at all.
    // It is highly likely that we miss the END marker, too, since its likely we hit the
    // secondary error recursion limit.

    Pattern[] pattern = new Pattern[] {
            Pattern.compile(".*HOTSPOT_FATAL_ERROR_DURING_DYNAMIC_INITIALIZATION.*")
    };
    HsErrFileUtils.checkHsErrFileContent(hs_err_file, pattern, null, false /* check end marker */, true /* verbose */);

    System.out.println("OK.");

  }

}
