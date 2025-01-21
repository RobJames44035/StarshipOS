/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.nio.file.Path;
import jdk.jpackage.test.AdditionalLauncher;
import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.LauncherAsServiceVerifier;
import jdk.jpackage.test.TKit;

/**
 * Test how services and desktop integration align together in the same package.
 * On Linux these features share common code in custom actions (common_utils.sh).
 * Test correctness of integration of this code.
 *
 * The test is not intended to be executed by SQE. It is for internal use only
 */

/*
 * @test
 * @summary jpackage with desktop integration and services on Linux
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @requires jpackage.test.SQETest == null
 * @build jdk.jpackage.test.*
 * @requires (os.family == "linux")
 * @compile ServiceAndDesktopTest.java
 * @run main/othervm/timeout=720 jdk.jpackage.test.Main
 *  --jpt-run=ServiceAndDesktopTest
 */

public class ServiceAndDesktopTest {

    @Test
    public static void test() {
        var pkg = new PackageTest()
                .configureHelloApp()
                .addBundleDesktopIntegrationVerifier(true)
                .addInitializer(cmd -> {
                    // Want a .desktop file for the main launcher
                    cmd.addArguments("--icon", GOLDEN_ICON.toString());
                });
        LauncherAsServiceVerifier.build().setLauncherName("foo").
                setExpectedValue("Fun").setAdditionalLauncherCallback(al -> {
                    // Don't want .desktop file for service launcher
                    al.setNoIcon();
                }).applyTo(pkg);
        pkg.run();
    }

    private final static Path GOLDEN_ICON = TKit.TEST_SRC_ROOT.resolve(Path.of(
            "resources", "icon" + TKit.ICON_SUFFIX));
}
