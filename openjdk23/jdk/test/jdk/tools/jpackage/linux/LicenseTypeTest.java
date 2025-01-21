/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.PackageType;


/**
 * Test --linux-rpm-license-type parameter. Output of the test should be
 * licensetypetest-1.0-1.amd64.rpm package bundle. The output package
 * should provide the same functionality as the
 * default package.
 * License property of the package should be set to JP_LICENSE_TYPE.
 */


/*
 * @test
 * @summary jpackage with --linux-rpm-license-type
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @build LicenseTypeTest
 * @requires (os.family == "linux")
 * @run main/othervm/timeout=360 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=LicenseTypeTest
 */
public class LicenseTypeTest {

    @Test
    public static void test() {
        final String LICENSE_TYPE = "JP_LICENSE_TYPE";

        new PackageTest().forTypes(PackageType.LINUX_RPM).configureHelloApp()
                .addInitializer(cmd -> {
                    cmd.addArguments("--linux-rpm-license-type", LICENSE_TYPE);
                })
                .addBundlePropertyVerifier("License", LICENSE_TYPE)
                .run();
    }
}
