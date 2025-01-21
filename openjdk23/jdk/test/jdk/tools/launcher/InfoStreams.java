/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @library /test/lib
 * @build InfoStreams jdk.test.lib.process.ProcessTools
 * @run main InfoStreams
 * @summary Test that informational options use the correct streams
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class InfoStreams {

    public static OutputAnalyzer run(String ... opts) throws Exception {
        return ProcessTools.executeTestJava(opts).shouldHaveExitValue(0);
    }

    private static final String
        java_version = System.getProperty("java.version"),
        USAGE = "^Usage: java ",
        VERSION_ERR = "^(java|openjdk) version \"" + java_version + "\"",
        VERSION_OUT = "^(java|openjdk) " + java_version,
        FULLVERSION_ERR = "^(java|openjdk) full version \"" + java_version + ".*\"",
        FULLVERSION_OUT = "^(java|openjdk) " + java_version,
        NONSTD = ".*These extra options are subject to change";

    public static void main(String ... args) throws Exception {

        String classPath = System.getProperty("java.class.path");

        run("-help").stderrShouldMatch(USAGE).stdoutShouldNotMatch(USAGE);
        run("--help").stdoutShouldMatch(USAGE).stderrShouldNotMatch(USAGE);

        run("-version").stderrShouldMatch(VERSION_ERR)
                       .stdoutShouldNotMatch(VERSION_ERR)
                       .stdoutShouldNotMatch(VERSION_OUT);
        run("--version").stdoutShouldMatch(VERSION_OUT)
                        .stderrShouldNotMatch(VERSION_OUT)
                        .stderrShouldNotMatch(VERSION_ERR);

        run("-showversion", "--dry-run", "-cp", classPath, "InfoStreams")
            .stderrShouldMatch(VERSION_ERR)
            .stdoutShouldNotMatch(VERSION_ERR)
            .stdoutShouldNotMatch(VERSION_OUT);
        run("--show-version", "--dry-run", "-cp", classPath, "InfoStreams")
            .stdoutShouldMatch(VERSION_OUT)
            .stderrShouldNotMatch(VERSION_OUT)
            .stderrShouldNotMatch(VERSION_ERR);

        run("-fullversion").stderrShouldMatch(FULLVERSION_ERR)
                           .stdoutShouldNotMatch(FULLVERSION_ERR)
                           .stdoutShouldNotMatch(FULLVERSION_OUT);
        run("--full-version").stdoutShouldMatch(FULLVERSION_OUT)
                             .stderrShouldNotMatch(FULLVERSION_OUT)
                             .stderrShouldNotMatch(FULLVERSION_ERR);

        run("-X").stderrShouldMatch(NONSTD).stdoutShouldNotMatch(NONSTD);
        run("--help-extra").stdoutShouldMatch(NONSTD).stderrShouldNotMatch(NONSTD);
    }
}
