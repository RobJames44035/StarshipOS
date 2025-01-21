/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8271003
 * @summary CLASSPATH env variable setting should not be truncated in a hs err log.
 * @requires vm.flagless
 * @requires vm.debug
 * @library /test/lib
 * @run driver ClassPathEnvVar
 */
import java.io.File;
import java.util.Map;

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class ClassPathEnvVar {
    private static final String pathSep = File.pathSeparator;
    private static final String sep = File.separator;
    private static final String classPathEnv = "CLASSPATH";
    private static final String endPath = "end-path";

    public static void main(String[] args) throws Exception {
        OutputAnalyzer output = runCrasher().shouldContain("CreateCoredumpOnCrash turned off, no core file dumped")
                                             .shouldNotHaveExitValue(0);

        checkErrorLog(output);

    }
    private static OutputAnalyzer runCrasher() throws Exception {
        ProcessBuilder pb =
            ProcessTools.createLimitedTestJavaProcessBuilder("-XX:-CreateCoredumpOnCrash",
                                                             "-XX:ErrorHandlerTest=14",
                                                             "-XX:+ErrorFileToStdout");

        // Obtain the CLASSPATH setting and expand it to more than 2000 chars.
        Map<String, String> envMap = pb.environment();
        String cp = envMap.get(classPathEnv);
        if (cp == null) {
            cp = "this" + sep + "is" + sep + "dummy" + sep + "path";
        }
        while (cp.length() < 2000) {
            cp += pathSep + cp;
        }
        cp += pathSep + endPath;
        envMap.put(classPathEnv, cp);

        return new OutputAnalyzer(pb.start());
    }

    private static void checkErrorLog(OutputAnalyzer output) throws Exception {
        String classPathLine = output.firstMatch("CLASSPATH=.*");

        if (classPathLine == null) {
            throw new RuntimeException("CLASSPATH setting not found in hs err log.");
        }

        // Check if the CLASSPATH line has been truncated.
        if (!classPathLine.endsWith(endPath)) {
            throw new RuntimeException("CLASSPATH was truncated in the hs err log.");
        }
    }
}
