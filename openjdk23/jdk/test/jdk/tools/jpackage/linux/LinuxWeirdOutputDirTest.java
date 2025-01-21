/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.Annotations.Parameter;
import jdk.jpackage.test.JPackageCommand;

/*
 * @test
 * @summary jpackage with values of --dest parameter breaking jpackage launcher
 * @requires (os.family == "linux")
 * @bug 8268974
 * @library /test/jdk/tools/jpackage/helpers
 * @build jdk.jpackage.test.*
 * @compile LinuxWeirdOutputDirTest.java
 * @run main/othervm/timeout=540 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=LinuxWeirdOutputDirTest
 */
public class LinuxWeirdOutputDirTest {

    @Test
    @Parameter("bin")
    @Parameter("lib")
    public void test(String outputPath) {
        JPackageCommand cmd = JPackageCommand.helloAppImage();
        cmd.setArgumentValue("--dest", cmd.outputDir().resolve(outputPath));
        cmd.executeAndAssertHelloAppImageCreated();
    }
}
