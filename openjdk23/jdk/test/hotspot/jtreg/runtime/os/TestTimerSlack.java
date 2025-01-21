/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary Check that timer slack options work
 * @requires os.family == "linux"
 * @requires vm.flagless
 * @library /test/lib
 * @run driver TestTimerSlack
 */
public class TestTimerSlack {

    public static void main(String[] args) throws Exception {
        int defaultSlack;

        // Check the timer slack value is not printed by default
        {
            ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:os+thread",
                                                      "TestTimerSlack$TestMain");

            OutputAnalyzer output = new OutputAnalyzer(pb.start());
            output.shouldHaveExitValue(0);
            output.shouldNotContain("timer slack:");
        }

        // Check the timer slack value is not printed when explicitly disabled
        {
            ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:os+thread",
                                                      "-XX:+UnlockExperimentalVMOptions",
                                                      "-XX:TimerSlack=-1",
                                                      "TestTimerSlack$TestMain");

            OutputAnalyzer output = new OutputAnalyzer(pb.start());
            output.shouldHaveExitValue(0);
            output.shouldNotContain("timer slack:");
        }

        // Check the timer slack value is good when system-wide default is requested
        {
            ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:os+thread",
                                                      "-XX:+UnlockExperimentalVMOptions",
                                                      "-XX:TimerSlack=0",
                                                      "TestTimerSlack$TestMain");

            OutputAnalyzer output = new OutputAnalyzer(pb.start());
            output.shouldHaveExitValue(0);
            output.shouldContain("timer slack:");

            defaultSlack = parseSlackValue(output);

            if (defaultSlack == 0) {
                fail(output, "Default slack value (" + defaultSlack + ") is unexpected");
            }
        }

        // Check the timer slack value is accepted by all threads
        for (int slack : new int[] {1, 10, 100, 1000, 10000, 100000, 1000000}) {
            ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:os+thread",
                                                      "-XX:+UnlockExperimentalVMOptions",
                                                      "-XX:TimerSlack=" + slack,
                                                      "TestTimerSlack$TestMain");

            OutputAnalyzer output = new OutputAnalyzer(pb.start());
            output.shouldHaveExitValue(0);
            output.shouldContain("timer slack:");

            int actualSlack = parseSlackValue(output);
            if (actualSlack != slack) {
                fail(output, "Actual slack value (" + actualSlack + ") is not the requested one (" + slack + ")");
            }
        }
    }

    static final Pattern EXTRACT_PATTERN = Pattern.compile("(.*)timer slack: ([0-9]+)ns(.*)");

    public static int parseSlackValue(OutputAnalyzer output) {
        Integer value = null;
        for (String s : output.asLines()) {
            Matcher m = EXTRACT_PATTERN.matcher(s);
            if (m.matches()) {
                Integer parsedValue = Integer.parseInt(m.group(2));
                if (value == null) {
                    value = parsedValue;
                } else if (!value.equals(parsedValue)) {
                    fail(output, "Multiple timer slack values detected");
                }
            }
        }
        if (value == null) {
            fail(output, "No timer slack values detected");
        }
        return value;
    }

    private static void fail(OutputAnalyzer output, String msg) {
        output.reportDiagnosticSummary();
        throw new IllegalStateException(msg);
    }

    public static class TestMain {
        static final int THREADS = 8;

        public static void main(String... args) throws Exception {
            Thread[] ts = new Thread[THREADS];
            for (int c = 0; c < THREADS; c++) {
                ts[c] = new Thread();
                ts[c].start();
            }

            for (int c = 0; c < THREADS; c++) {
                ts[c].join();
            }
        }
    }

}
