/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.Annotations.Test;

/**
 * Simple platform specific packaging test. Output of the test should be
 * simplepackagetest*.* package bundle.
 *
 * Windows:
 *
 * The installer should not have license text. It should not have an option
 * to change the default installation directory.
 * Test application should be installed in %ProgramFiles%\SimplePackageTest directory.
 * Installer should install test app for all users (machine wide).
 * Installer should not create any shortcuts.
 */

/*
 * @test
 * @summary Simple jpackage command run
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @compile SimplePackageTest.java
 * @run main/othervm/timeout=360 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=SimplePackageTest
 */
public class SimplePackageTest {

    @Test
    public static void test() {
        new PackageTest()
        .configureHelloApp()
        .addBundleDesktopIntegrationVerifier(false)
        .run();
    }
}
