/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.jpackage.test.TKit;
import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.PackageType;
import jdk.jpackage.test.Annotations.Test;

/**
 * Test --win-shortcut parameter. Output of the test should be
 * WinShortcutTest-1.0.exe installer. The output installer should provide the
 * same functionality as the default installer (see description of the default
 * installer in SimplePackageTest.java) plus install application shortcut on the
 * desktop.
 */

/*
 * @test
 * @summary jpackage with --win-shortcut
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @requires (os.family == "windows")
 * @compile WinShortcutTest.java
 * @run main/othervm/timeout=540 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=WinShortcutTest
 */

public class WinShortcutTest {
    @Test
    public static void test() {
        new PackageTest()
        .forTypes(PackageType.WINDOWS)
        .configureHelloApp()
        .addInitializer(cmd -> cmd.addArgument("--win-shortcut"))
        .run();
    }
}
