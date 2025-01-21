/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.HelloApp;
import jdk.jpackage.test.Annotations.Test;

/**
 * Tests generation of app image and then launches app by passing -psn_1_1
 * argument via command line and checks that -psn_1_1 is not passed to
 * application. Second test app image is generated -psn_2_2 and then app is
 * launched with -psn_1_1 and we should filter -psn_1_1 and keep -psn_2_2.
 * See JDK-8255947.
 */

/*
 * @test
 * @summary jpackage with -psn
 * @library /test/jdk/tools/jpackage/helpers
 * @build jdk.jpackage.test.*
 * @compile ArgumentsFilteringTest.java
 * @requires (os.family == "mac")
 * @run main/othervm/timeout=540 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=ArgumentsFilteringTest
 */
public class ArgumentsFilteringTest {

    @Test
    public void test1() {
        JPackageCommand cmd = JPackageCommand.helloAppImage();
        cmd.executeAndAssertHelloAppImageCreated();
        var appVerifier = HelloApp.assertMainLauncher(cmd);
        if (appVerifier != null) {
            appVerifier.execute("-psn_1_1");
            appVerifier.verifyOutput();
        }
    }

    @Test
    public void test2() {
        JPackageCommand cmd = JPackageCommand.helloAppImage()
                .addArguments("--arguments", "-psn_2_2");
        cmd.executeAndAssertHelloAppImageCreated();
        var appVerifier = HelloApp.assertMainLauncher(cmd);
        if (appVerifier != null) {
            appVerifier.execute("-psn_1_1");
            appVerifier.verifyOutput("-psn_2_2");
        }
    }
}
