/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestG1ConcMarkStepDurationMillis
 * @requires vm.gc.G1 & vm.opt.G1ConcMarkStepDurationMillis == null
 * @summary Tests argument processing for double type flag, G1ConcMarkStepDurationMillis
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.arguments.TestG1ConcMarkStepDurationMillis
 */

import jdk.test.lib.process.OutputAnalyzer;
import java.util.*;
import java.util.regex.*;

public class TestG1ConcMarkStepDurationMillis {

  static final int PASS                = 0;
  static final int FAIL_IMPROPER_VALUE = 1;
  static final int FAIL_OUT_RANGE      = 2;

  static final String DOUBLE_1       = "1.0";
  static final String DOUBLE_MAX     = "1.79e+308";

  static final String DOUBLE_NEG_EXP = "1.0e-30";
  static final String NEG_DOUBLE_1   = "-1.0";

  static final String DOUBLE_INF     = "1.79e+309";
  static final String NEG_DOUBLE_INF = "-1.79e+309";
  static final String DOUBLE_NAN     = "abe+309";
  static final String WRONG_DOUBLE_1 = "1.79e+308e";
  static final String WRONG_DOUBLE_2 = "1.79ee+308";

  public static void main(String args[]) throws Exception {
    // Pass cases
    runG1ConcMarkStepDurationMillisTest(DOUBLE_1,       PASS);
    runG1ConcMarkStepDurationMillisTest(DOUBLE_MAX,     PASS);

    // Fail cases: out of range
    runG1ConcMarkStepDurationMillisTest(DOUBLE_NEG_EXP, FAIL_OUT_RANGE);
    runG1ConcMarkStepDurationMillisTest(NEG_DOUBLE_1,   FAIL_OUT_RANGE);

    // Fail cases: not double
    runG1ConcMarkStepDurationMillisTest(DOUBLE_INF,     FAIL_IMPROPER_VALUE);
    runG1ConcMarkStepDurationMillisTest(NEG_DOUBLE_INF, FAIL_IMPROPER_VALUE);
    runG1ConcMarkStepDurationMillisTest(DOUBLE_NAN,     FAIL_IMPROPER_VALUE);
    runG1ConcMarkStepDurationMillisTest(WRONG_DOUBLE_1, FAIL_IMPROPER_VALUE);
    runG1ConcMarkStepDurationMillisTest(WRONG_DOUBLE_2, FAIL_IMPROPER_VALUE);
  }

  private static void runG1ConcMarkStepDurationMillisTest(String expectedValue, int expectedResult) throws Exception {
    List<String> vmOpts = new ArrayList<>();

    Collections.addAll(vmOpts, "-XX:+UseG1GC", "-XX:G1ConcMarkStepDurationMillis="+expectedValue, "-XX:+PrintFlagsFinal", "-version");

    OutputAnalyzer output = GCArguments.executeTestJava(vmOpts);

    output.shouldHaveExitValue(expectedResult == PASS ? 0 : 1);
    String stdout = output.getStdout();
    if (expectedResult == PASS) {
      checkG1ConcMarkStepDurationMillisConsistency(stdout, expectedValue);
    } else if (expectedResult == FAIL_IMPROPER_VALUE) {
      output.shouldContain("Improperly specified VM option");
    } else if (expectedResult == FAIL_OUT_RANGE) {
      output.shouldContain("outside the allowed range");
    }
  }

  private static void checkG1ConcMarkStepDurationMillisConsistency(String output, String expectedValue) {
    double actualValue = getDoubleValue("G1ConcMarkStepDurationMillis", output);

    if (Double.parseDouble(expectedValue) != actualValue) {
      throw new RuntimeException(
            "Actual G1ConcMarkStepDurationMillis(" + Double.toString(actualValue)
            + ") is not equal to expected value(" + expectedValue + ")");
    }
  }

  public static double getDoubleValue(String flag, String where) {
    Matcher m = Pattern.compile(flag + "\\s+:?=\\s+\\d+").matcher(where);
    if (!m.find()) {
      throw new RuntimeException("Could not find value for flag " + flag + " in output string");
    }
    String match = m.group();
    return Double.parseDouble(match.substring(match.lastIndexOf(" ") + 1, match.length()));
  }
}
