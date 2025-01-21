/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.internal.util.function.ThrowingConsumer;
import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.PackageType;


/**
 * Test --about-url parameter in Linux installers. Output of the test should be
 * appabouturltest_1.0-1_amd64.deb or appabouturltest-1.0-1.amd64.rpm package
 * bundle. The output package should provide the same functionality as the
 * default package.
 *
 * deb:
 * Homepage property of the package should be set to http://foo.com value.
 *
 * rpm:
 * URL property of the package should be set to http://foo.com value.
 */

/*
 * @test
 * @summary jpackage with --about-url
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @build AppAboutUrlTest
 * @requires (os.family == "linux")
 * @requires (jpackage.test.SQETest == null)
 * @run main/othervm/timeout=360 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=AppAboutUrlTest
 */

/*
 * @test
 * @summary jpackage with --about-url
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @build AppAboutUrlTest
 * @requires (os.family == "linux")
 * @requires (jpackage.test.SQETest != null)
 * @run main/othervm/timeout=360 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=AppAboutUrlTest.test
 */
public class AppAboutUrlTest {

    @Test
    public static void test() {
        final String ABOUT_URL = "http://foo.com";

        runTest(cmd -> {
            cmd.addArguments("--about-url", ABOUT_URL);
        }, ABOUT_URL, ABOUT_URL);
    }

    @Test
    public static void testDefaults() {
        runTest(JPackageCommand::setFakeRuntime, "", "(none)");
    }

    private static void runTest(ThrowingConsumer<JPackageCommand> initializer,
            String expectedDebHomepage, String expectedRpmUrl) {
        new PackageTest()
                .forTypes(PackageType.LINUX)
                .configureHelloApp()
                .addInitializer(initializer)
                .forTypes(PackageType.LINUX_DEB)
                .addBundlePropertyVerifier("Homepage", expectedDebHomepage)
                .forTypes(PackageType.LINUX_RPM)
                .addBundlePropertyVerifier("URL", expectedRpmUrl)
                .run();
    }
}
