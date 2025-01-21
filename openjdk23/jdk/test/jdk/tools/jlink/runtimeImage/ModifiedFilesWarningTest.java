/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.nio.file.Path;

import jdk.test.lib.process.OutputAnalyzer;
import tests.Helper;

/*
 * @test
 * @summary Verify warnings are being produced when linking from the run-time
 *          image and files have been modified
 * @requires (vm.compMode != "Xcomp" & os.maxMemory >= 2g)
 * @library ../../lib /test/lib
 * @enablePreview
 * @modules java.base/jdk.internal.jimage
 *          jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jlink.plugin
 *          jdk.jlink/jdk.tools.jimage
 * @build tests.* jdk.test.lib.process.OutputAnalyzer
 *        jdk.test.lib.process.ProcessTools
 * @run main/othervm -Xmx1g ModifiedFilesWarningTest
 */
public class ModifiedFilesWarningTest extends ModifiedFilesTest {

    protected static final String IGNORE_MODIFIED_RUNTIME_OPT = "--ignore-modified-runtime";

    public static void main(String[] args) throws Exception {
        ModifiedFilesWarningTest test = new ModifiedFilesWarningTest();
        test.run();
    }

    @Override
    String initialImageName() {
        return "java-base-jlink-with-mod-warn";
    }

    @Override
    void testAndAssert(Path modifiedFile, Helper helper, Path initialImage) throws Exception {
        CapturingHandler handler = new CapturingHandler();
        jlinkUsingImage(new JlinkSpecBuilder()
                                .helper(helper)
                                .imagePath(initialImage)
                                .name("java-base-jlink-with-mod-warn-target")
                                .addModule("java.base")
                                .validatingModule("java.base")
                                .extraJlinkOpt(IGNORE_MODIFIED_RUNTIME_OPT) // only generate a warning
                                .build(), handler);
        OutputAnalyzer out = handler.analyzer();
        // verify we get the warning message
        out.stdoutShouldMatch("Warning: .* has been modified");
        out.stdoutShouldNotContain("java.lang.IllegalArgumentException");
        out.stdoutShouldNotContain("IOException");
    }
}
