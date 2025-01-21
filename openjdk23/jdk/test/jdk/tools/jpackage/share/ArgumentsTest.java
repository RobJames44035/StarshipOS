/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.nio.file.Path;
import java.util.List;
import jdk.jpackage.test.HelloApp;
import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.Annotations.BeforeEach;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.Annotations.Parameter;


/*
 * Tricky arguments used in the test require a bunch of levels of character
 * escaping for proper encoding them in a single string to be used as a value of
 * `--arguments` option. String with encoded arguments doesn't go through the
 * system to jpackage executable as is because OS is interpreting escape
 * characters. This is true for Windows at least.
 *
 * String mapping performed by the system corrupts the string and jpackage exits
 * with error. There is no problem with string corruption when jpackage is used
 * as tool provider. This is not jpackage issue, so just always run this test
 * with jpackage used as tool provider.
 * /

/*
 * @test
 * @summary jpackage create image with --arguments test
 * @library /test/jdk/tools/jpackage/helpers
 * @build jdk.jpackage.test.*
 * @compile ArgumentsTest.java
 * @run main/othervm/timeout=360 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=ArgumentsTest
 */
public class ArgumentsTest {

    @BeforeEach
    public static void useJPackageToolProvider() {
        JPackageCommand.useToolProviderByDefault();
    }

    @Test
    @Parameter("Goodbye")
    @Parameter("com.hello/com.hello.Hello")
    public static void testApp(String javaAppDesc) {
        JPackageCommand cmd = JPackageCommand.helloAppImage(javaAppDesc).addArguments(
                "--arguments", JPackageCommand.escapeAndJoin(TRICKY_ARGUMENTS));

        cmd.executeAndAssertImageCreated();

        if (!cmd.canRunLauncher("Not running the test")) {
            return;
        }

        Path launcherPath = cmd.appLauncherPath();
        HelloApp.assertApp(launcherPath)
                .addDefaultArguments(TRICKY_ARGUMENTS)
                .executeAndVerifyOutput();
    }

    private final static List<String> TRICKY_ARGUMENTS = List.of(
        "argument",
        "Some Arguments",
        "Value \"with\" quotes"
    );
}
