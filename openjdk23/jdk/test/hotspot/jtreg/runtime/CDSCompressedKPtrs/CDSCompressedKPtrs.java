/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @requires vm.cds
 * @requires vm.bits == 64
 * @requires vm.flagless
 * @bug 8003424
 * @summary Testing UseCompressedClassPointers with CDS
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver CDSCompressedKPtrs
 */

import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jtreg.SkippedException;

public class CDSCompressedKPtrs {
  public static void main(String[] args) throws Exception {
    ProcessBuilder pb;
    pb = ProcessTools.createLimitedTestJavaProcessBuilder(
      "-XX:+UseCompressedClassPointers", "-XX:+UseCompressedOops",
      "-XX:+UnlockDiagnosticVMOptions", "-XX:SharedArchiveFile=./CDSCompressedKPtrs.jsa", "-Xshare:dump", "-Xlog:cds");
    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    try {
      output.shouldContain("Loading classes to share");
      output.shouldHaveExitValue(0);

      pb = ProcessTools.createLimitedTestJavaProcessBuilder(
        "-XX:+UseCompressedClassPointers", "-XX:+UseCompressedOops",
        "-XX:+UnlockDiagnosticVMOptions", "-XX:SharedArchiveFile=./CDSCompressedKPtrs.jsa", "-Xshare:on", "-version");
      output = new OutputAnalyzer(pb.start());
      output.shouldContain("sharing");
      output.shouldHaveExitValue(0);

    } catch (RuntimeException e) {
      output.shouldContain("Unable to use shared archive");
      output.shouldHaveExitValue(1);
      throw new SkippedException("CDS was turned off");
    }
  }
}
