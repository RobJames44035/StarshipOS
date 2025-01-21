/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.jpackage.test.TKit;
import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.PackageType;
import jdk.jpackage.test.Annotations.Test;

/**
 * Test --win-menu and --win-menu-group parameters.
 * Output of the test should be WinMenuGroupTest-1.0.exe installer.
 * The output installer should provide the
 * same functionality as the default installer (see description of the default
 * installer in SimplePackageTest.java) plus
 * it should create a shortcut for application launcher in Windows Menu in
 * "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\WinMenuGroupTest_MenuGroup" folder.
 */

/*
 * @test
 * @summary jpackage with --win-menu and --win-menu-group
 * @library /test/jdk/tools/jpackage/helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @requires (os.family == "windows")
 * @compile WinMenuGroupTest.java
 * @run main/othervm/timeout=540 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=WinMenuGroupTest
 */

public class WinMenuGroupTest {
    @Test
    public static void test() {
        new PackageTest()
        .forTypes(PackageType.WINDOWS)
        .configureHelloApp()
        .addInitializer(cmd -> cmd.addArguments(
                "--win-menu", "--win-menu-group", "WinMenuGroupTest_MenuGroup"))
        .run();
    }
}
