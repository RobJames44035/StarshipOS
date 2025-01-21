/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.nio.file.Files;
import java.nio.file.Path;
import static jdk.internal.util.OperatingSystem.WINDOWS;
import jdk.jpackage.test.ApplicationLayout;
import jdk.jpackage.test.TKit;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.JavaTool;
import jdk.jpackage.test.Executor;

/**
 * Test --runtime-image parameter with runtime image containing symbolic links.
 * This test only for macOS and Linux.
 */

/*
 * @test
 * @summary jpackage with --runtime-image
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @requires (os.family != "windows")
 * @build jdk.jpackage.test.*
 * @compile RuntimeImageSymbolicLinksTest.java
 * @run main/othervm/timeout=1400 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=RuntimeImageSymbolicLinksTest
 */

public class RuntimeImageSymbolicLinksTest {

    @Test(ifNotOS = WINDOWS)
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

        // Add symbolic links to generated runtime image
        // Release file
        Path releaseLink = jlinkOutputDir.resolve("releaseLink");
        Path releaseTarget = Path.of("release");
        TKit.assertFileExists(jlinkOutputDir.resolve("release"));
        Files.createSymbolicLink(releaseLink, releaseTarget);
        // Legal directory
        Path legalLink = jlinkOutputDir.resolve("legalLink");
        Path legalTarget = Path.of("legal");
        TKit.assertDirectoryExists(jlinkOutputDir.resolve("legal"));
        Files.createSymbolicLink(legalLink, legalTarget);

        JPackageCommand cmd = JPackageCommand.helloAppImage()
            .setArgumentValue("--runtime-image", jlinkOutputDir.toString());

        cmd.executeAndAssertHelloAppImageCreated();

        ApplicationLayout appLayout = cmd.appLayout();
        Path runtimeDir = appLayout.runtimeHomeDirectory();

        // Make sure that links are exist
        releaseLink = runtimeDir.resolve("releaseLink");
        TKit.assertSymbolicLinkExists(releaseLink);
        legalLink = runtimeDir.resolve("legalLink");
        TKit.assertSymbolicLinkExists(legalLink);
    }

}
