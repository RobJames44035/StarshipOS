/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.nio.file.Files;
import java.nio.file.Path;
import jdk.jpackage.test.TKit;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.JavaTool;
import jdk.jpackage.test.Executor;

/*
 * @test
 * @summary jpackage with --runtime-image
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @compile RuntimeImageTest.java
 * @run main/othervm/timeout=1400 jdk.jpackage.test.Main
 *  --jpt-run=RuntimeImageTest
 */

public class RuntimeImageTest {

    @Test
    public static void test() throws Exception {
        final Path workDir = TKit.createTempDirectory("runtime").resolve("data");
        final Path jlinkOutputDir = workDir.resolve("temp.runtime");
        Files.createDirectories(jlinkOutputDir.getParent());

        new Executor()
        .setToolProvider(JavaTool.JLINK)
        .dumpOutput()
        .addArguments(
                "--output", jlinkOutputDir.toString(),
                "--add-modules", "java.desktop",
                "--strip-debug",
                "--no-header-files",
                "--no-man-pages",
                "--strip-native-commands")
        .execute();

        JPackageCommand cmd = JPackageCommand.helloAppImage()
            .setArgumentValue("--runtime-image", jlinkOutputDir.toString());

        cmd.executeAndAssertHelloAppImageCreated();
    }

}
