/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.arguments;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Platform;

/*
 * @test
 * @bug 8015107
 * @summary Tests that VM prints a warning when -XX:CompressedClassSpaceSize
 *          is used together with -XX:-UseCompressedClassPointers
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @requires vm.opt.CompressedClassSpaceSize == null & vm.opt.UseCompressedClassPointers == null
 * @run driver gc.arguments.TestCompressedClassFlags
 */
public class TestCompressedClassFlags {
    public static void main(String[] args) throws Exception {
        if (Platform.is64bit()) {
            OutputAnalyzer output = GCArguments.executeTestJava(
                "-XX:CompressedClassSpaceSize=1g",
                "-XX:-UseCompressedClassPointers",
                "-version");
            output.shouldContain("warning");
            output.shouldNotContain("error");
            output.shouldHaveExitValue(0);
        }
    }
}
