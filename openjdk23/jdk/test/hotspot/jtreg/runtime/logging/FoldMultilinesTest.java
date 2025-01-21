/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8271186 8273471
 * @library /test/lib
 * @run driver FoldMultilinesTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class FoldMultilinesTest {

    private static String EXCEPTION_LOG_FILE = "exceptions.log";
    private static String XLOG_BASE = "-Xlog:exceptions=info:";
    private static String EXCEPTION_MESSAGE = "line 1\nline 2\\nstring";
    private static String FOLDED_EXCEPTION_MESSAGE = "line 1\\nline 2\\\\nstring";
    private static Pattern NEWLINE_LOG_PATTERN = Pattern.compile("line 1\\R\\[\\s+\\] line 2\\\\nstring", Pattern.MULTILINE);

    private static String getLog(String out, OutputAnalyzer output) throws Exception {
        return switch (out) {
            case "" -> output.getStdout();
            case "stdout" -> output.getStdout();
            case "stderr" -> output.getStderr();
            default -> Files.readString(Path.of(EXCEPTION_LOG_FILE));
        };
    }

    private static void analyzeFoldMultilinesOn(ProcessBuilder pb, String out) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        if (!getLog(out, output).contains(FOLDED_EXCEPTION_MESSAGE)) {
            throw new RuntimeException(out + ": foldmultilines=true did not work.");
        }
    }

    private static void analyzeFoldMultilinesOff(ProcessBuilder pb, String out) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        if (!NEWLINE_LOG_PATTERN.matcher(getLog(out, output)).find()) {
            throw new RuntimeException(out + ": foldmultilines=false did not work.");
        }
    }

    private static void analyzeFoldMultilinesInvalid(ProcessBuilder pb) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("Invalid option: foldmultilines must be 'true' or 'false'.");
        output.shouldNotHaveExitValue(0);
    }

    private static void test(String out) throws Exception {
        String Xlog;
        ProcessBuilder pb;

        Xlog = XLOG_BASE + out +  "::foldmultilines=true";
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(Xlog, InternalClass.class.getName());
        analyzeFoldMultilinesOn(pb, out);

        Xlog = XLOG_BASE + out + "::foldmultilines=false";
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(Xlog, InternalClass.class.getName());
        analyzeFoldMultilinesOff(pb, out);

        Xlog = XLOG_BASE + out + "::foldmultilines=invalid";
        pb = ProcessTools.createLimitedTestJavaProcessBuilder(Xlog, InternalClass.class.getName());
        analyzeFoldMultilinesInvalid(pb);
    }

    public static void main(String[] args) throws Exception {
        // -Xlog:exceptions=info:file=exceptions.log::foldmultilines=[true|false|invalid]
        test("file=" + EXCEPTION_LOG_FILE);

        // -Xlog:exceptions=info:stdout::foldmultilines=[true|false|invalid]
        test("stdout");

        // -Xlog:exceptions=info:stderr::foldmultilines=[true|false|invalid]
        test("stderr");

        // -Xlog:exceptions=info:::foldmultilines=[true|false|invalid]
        test("");
    }

    public static class InternalClass {
        public static void main(String[] args) {
            try {
                throw new RuntimeException(EXCEPTION_MESSAGE);
            } catch (Exception e) {
                // Do nothing to return exit code 0
            }
        }
    }

}
