/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.Annotations.Test;

/**
 * Name with space packaging test. Output of the test should be
 * "Name With Space-*.*" package bundle.
 *
 * macOS only:
 *
 * Test should generates basic pkg and dmg. Name of packages and application itself
 * should have name: "Name With Space". Package should be installed into "/Applications"
 * folder and verified that it can be installed and run.
 */

/*
 * @test
 * @summary jpackage test with name containing spaces
 * @library /test/jdk/tools/jpackage/helpers
 * @build jdk.jpackage.test.*
 * @compile NameWithSpaceTest.java
 * @requires (os.family == "mac")
 * @key jpackagePlatformPackage
 * @run main/othervm/timeout=360 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=NameWithSpaceTest
 */
public class NameWithSpaceTest {

    @Test
    public static void test() {
        new PackageTest()
        .configureHelloApp()
        .addBundleDesktopIntegrationVerifier(false)
        .addInitializer(cmd -> {
            cmd.setArgumentValue("--name", "Name With Space");
        })
        .run();
    }
}
