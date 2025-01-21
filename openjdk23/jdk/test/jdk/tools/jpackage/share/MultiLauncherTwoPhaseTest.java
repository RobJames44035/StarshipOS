/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.nio.file.Path;
import java.io.IOException;
import jdk.jpackage.test.AdditionalLauncher;
import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.PackageType;
import jdk.jpackage.test.TKit;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.JPackageCommand;

/**
 * Test multiple launchers in two phases. First test creates app image and then
 * creates installer from this image. Output of the test should be
 * MultiLauncherTwoPhaseTest*.* installer. The output installer should be basic
 * installer with 3 launcher MultiLauncherTwoPhaseTest, bar and foo. On Windows
 * we should have start menu integration under MultiLauncherTwoPhaseTest and
 * desktop shortcuts for all 3 launchers. Linux should also create shortcuts for
 * all launchers.
 */

/*
 * @test
 * @summary Multiple launchers in two phases
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @compile MultiLauncherTwoPhaseTest.java
 * @run main/othervm/timeout=360 -Xmx512m
 *  jdk.jpackage.test.Main
 *  --jpt-run=MultiLauncherTwoPhaseTest
 */

public class MultiLauncherTwoPhaseTest {

    @Test
    public static void test() throws IOException {
        Path appimageOutput = TKit.createTempDirectory("appimage");

        JPackageCommand appImageCmd = JPackageCommand.helloAppImage()
                .setArgumentValue("--dest", appimageOutput);

        AdditionalLauncher launcher1 = new AdditionalLauncher("bar");
        launcher1.setDefaultArguments().applyTo(appImageCmd);

        AdditionalLauncher launcher2 = new AdditionalLauncher("foo");
        launcher2.applyTo(appImageCmd);

        PackageTest packageTest = new PackageTest()
                .addRunOnceInitializer(() -> appImageCmd.execute())
                .addBundleDesktopIntegrationVerifier(true)
                .addInitializer(cmd -> {
                    cmd.addArguments("--app-image", appImageCmd.outputBundle());
                    cmd.removeArgumentWithValue("--input");
                })
                .forTypes(PackageType.WINDOWS)
                .addInitializer(cmd -> {
                    cmd.addArguments("--win-shortcut", "--win-menu",
                            "--win-menu-group", "MultiLauncherTwoPhaseTest");
                })
                .forTypes(PackageType.LINUX)
                .addInitializer(cmd -> {
                    cmd.addArguments("--linux-shortcut");
                });

        packageTest.run();
    }
}
