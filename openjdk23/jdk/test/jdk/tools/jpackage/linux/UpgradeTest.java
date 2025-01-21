/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.util.Map;
import java.nio.file.Path;
import jdk.jpackage.test.AdditionalLauncher;
import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.TKit;

/*
 * @test
 * @summary Linux upgrade testing
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @requires (os.family == "linux")
 * @compile UpgradeTest.java
 * @run main/othervm/timeout=360 jdk.jpackage.test.Main
 *  --jpt-run=UpgradeTest
 */
public class UpgradeTest {

    @Test
    public void testDesktopFiles() {
        // Create two packages with the same name but different versions.
        // The first will have `launcherA`, and `launcherB` additional launchers.
        // The second will have `launcherB`, and `launcherC` additional launchers.
        // Launchers are configured in a way to have correpsonding .desktop files.
        // These files will be installed in system directories.
        // After the upgrade `launcherA`-related files must be deleted and
        // `launcherB`-related files from the first package must be replaced with
        // the files from the second package.
        // Checks that correct files are installed in system directories
        // encapsulated in AdditionalLauncher class.

        var pkg = createPackageTest().disablePackageUninstaller();

        var alA = createAdditionalLauncher("launcherA");

        alA.applyTo(pkg);
        createAdditionalLauncher("launcherB").addRawProperties(Map.entry(
                "description", "Foo")).applyTo(pkg);

        var pkg2 = createPackageTest().addInitializer(cmd -> {
            cmd.addArguments("--app-version", "2.0");
        });

        alA.verifyRemovedInUpgrade(pkg2);
        createAdditionalLauncher("launcherB").addRawProperties(Map.entry(
                "description", "Bar")).applyTo(pkg2);
        createAdditionalLauncher("launcherC").applyTo(pkg2);

        new PackageTest.Group(pkg, pkg2).run();
    }

    private static PackageTest createPackageTest() {
        return new PackageTest().configureHelloApp().addInitializer(
                JPackageCommand::setInputToEmptyDirectory).addInitializer(
                        JPackageCommand::setFakeRuntime).
                addBundleDesktopIntegrationVerifier(true);
    }

    private static AdditionalLauncher createAdditionalLauncher(String name) {
        // Configure additionl launcher in a way to trigger jpackage create
        // corresponding .desktop file.
        return new AdditionalLauncher(name).setIcon(GOLDEN_ICON);
    }

    private final static Path GOLDEN_ICON = TKit.TEST_SRC_ROOT.resolve(Path.of(
            "resources", "icon" + TKit.ICON_SUFFIX));
}
