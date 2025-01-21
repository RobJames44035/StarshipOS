/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test MaxMetaspaceSizeTest
 * @requires vm.bits == 64 & vm.opt.final.UseCompressedOops == true
 * @requires vm.flagless
 * @bug 8087291
 * @library /test/lib
 * @run driver MaxMetaspaceSizeTest
 */

public class MaxMetaspaceSizeTest {
    public static void main(String... args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-Xshare:off",
            "-Xmx1g",
            "-XX:MaxMetaspaceSize=4K",
            "-XX:+UseCompressedClassPointers",
            "-XX:CompressedClassSpaceSize=1g",
            "--version");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        // -Xshare:off --version loads hundreds of classes and will hit either one of
        // "OutOfMemoryError: Metaspace" or
        // "OutOfMemoryError: Compressed class space"
        output.shouldMatch("OutOfMemoryError.*(Compressed class space|Metaspace)");
        output.shouldNotHaveExitValue(0);
    }
}
