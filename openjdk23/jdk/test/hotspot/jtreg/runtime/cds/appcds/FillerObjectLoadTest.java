/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * bug 8286066
 * @summary VM crash caused by unloaded FillerObject_klass
 * @library /test/lib
 * @requires vm.cds
 * @requires vm.flagless
 * @run driver FillerObjectLoadTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class FillerObjectLoadTest {
    public static void main(String... args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-XX:+IgnoreUnrecognizedVMOptions", "-XX:-UseCompressedClassPointers",
                "-XX:+UnlockExperimentalVMOptions", "-XX:+UseEpsilonGC", "-Xshare:dump",
                "-XX:SharedArchiveFile=" + TestCommon.getNewArchiveName());
        OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());
        analyzer.shouldHaveExitValue(0);

        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-XX:+IgnoreUnrecognizedVMOptions", "-XX:-UseCompressedClassPointers",
                "-XX:TLABSize=2048", "-Xshare:dump",
                "-XX:SharedArchiveFile=" + TestCommon.getNewArchiveName());
        analyzer = new OutputAnalyzer(pb.start());
        analyzer.shouldHaveExitValue(0);
    }
}
