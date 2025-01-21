/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.nio.file.Path;
import java.util.function.Predicate;

import jdk.test.lib.process.OutputAnalyzer;
import tests.Helper;


/*
 * @test
 * @summary Verify that jlink with an empty module path, but trying to use
 *          --keep-packaged-modules fails as expected.
 * @requires (vm.compMode != "Xcomp" & os.maxMemory >= 2g)
 * @library ../../lib /test/lib
 * @enablePreview
 * @modules java.base/jdk.internal.jimage
 *          jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jlink.plugin
 *          jdk.jlink/jdk.tools.jimage
 * @build tests.* jdk.test.lib.process.OutputAnalyzer
 *        jdk.test.lib.process.ProcessTools
 * @run main/othervm -Xmx1g KeepPackagedModulesFailTest
 */
public class KeepPackagedModulesFailTest extends AbstractLinkableRuntimeTest {

    public static void main(String[] args) throws Exception {
        KeepPackagedModulesFailTest test = new KeepPackagedModulesFailTest();
        test.run();
    }

    @Override
    void runTest(Helper helper, boolean isLinkableRuntime) throws Exception {
        // create a base image for linking from the run-time image
        BaseJlinkSpecBuilder builder = new BaseJlinkSpecBuilder()
                .helper(helper)
                .name("jlink-fail")
                .addModule("java.base")
                .validatingModule("java.base");
        if (isLinkableRuntime) {
            builder.setLinkableRuntime();
        }
        Path baseImage = createRuntimeLinkImage(builder.build());

        CapturingHandler handler = new CapturingHandler();
        Predicate<OutputAnalyzer> exitFailPred = new Predicate<>() {

            @Override
            public boolean test(OutputAnalyzer t) {
                return t.getExitValue() != 0; // expect failure
            }
        };
        // Attempt a jlink using the run-time image and also using option
        // --keep-packaged-modules, which should fail.
        jlinkUsingImage(new JlinkSpecBuilder()
                                .helper(helper)
                                .imagePath(baseImage)
                                .name("java-base-jlink-keep-packaged-target")
                                .addModule("java.base")
                                .extraJlinkOpt("--keep-packaged-modules=foo")
                                .validatingModule("java.base")
                                .build(), handler, exitFailPred);
        OutputAnalyzer analyzer = handler.analyzer();
        if (analyzer.getExitValue() == 0) {
            throw new AssertionError("Expected jlink to have failed!");
        }
        analyzer.stdoutShouldContain("Error");
        analyzer.stdoutShouldContain("--keep-packaged-modules");
    }

}
