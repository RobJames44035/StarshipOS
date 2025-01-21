/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import jdk.test.lib.process.OutputAnalyzer;
import tests.Helper;

/*
 * @test
 * @summary Test --add-options jlink plugin when linking from the run-time image
 * @requires (vm.compMode != "Xcomp" & os.maxMemory >= 2g)
 * @library ../../lib /test/lib
 * @enablePreview
 * @modules java.base/jdk.internal.jimage
 *          jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jlink.plugin
 *          jdk.jlink/jdk.tools.jimage
 * @build tests.* jdk.test.lib.process.OutputAnalyzer
 *        jdk.test.lib.process.ProcessTools
 * @run main/othervm -Xmx1g AddOptionsTest
 */
public class AddOptionsTest extends AbstractLinkableRuntimeTest {

    public static void main(String[] args) throws Exception {
        AddOptionsTest test = new AddOptionsTest();
        test.run();
    }

    @Override
    void runTest(Helper helper, boolean isLinkableRuntime) throws Exception {
        BaseJlinkSpecBuilder builder = new BaseJlinkSpecBuilder()
                .addExtraOption("--add-options")
                .addExtraOption("-Xlog:gc=info:stderr -XX:+UseParallelGC")
                .name("java-base-with-opts")
                .addModule("java.base")
                .validatingModule("java.base")
                .helper(helper);
        if (isLinkableRuntime) {
            builder.setLinkableRuntime();
        }
        Path finalImage = createJavaImageRuntimeLink(builder.build());
        verifyListModules(finalImage, List.of("java.base"));
        verifyParallelGCInUse(finalImage);
    }

    private void verifyParallelGCInUse(Path finalImage) throws Exception {
        OutputAnalyzer analyzer = runJavaCmd(finalImage, List.of("--version"));
        boolean foundMatch = false;
        try (Scanner lineScan = new Scanner(analyzer.getStderr())) {
            while (lineScan.hasNextLine()) {
                String line = lineScan.nextLine();
                if (line.endsWith("Using Parallel")) {
                    foundMatch = true;
                    break;
                }
            }
        }
        if (!foundMatch) {
            throw new AssertionError("Expected Parallel GC in place for jlinked image");
        }
    }

}
