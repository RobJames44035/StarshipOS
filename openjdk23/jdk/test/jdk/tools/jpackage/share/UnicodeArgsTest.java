/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */


import java.util.stream.Collectors;
import jdk.jpackage.test.TKit;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.Annotations.Parameter;
import jdk.jpackage.test.HelloApp;
import jdk.jpackage.test.JPackageCommand;

/*
 * @test
 * @summary test how app launcher handles unicode command line arguments
 * @library /test/jdk/tools/jpackage/helpers
 * @build jdk.jpackage.test.*
 * @compile UnicodeArgsTest.java
 * @requires (os.family == "windows")
 * @run main/othervm/timeout=720 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=UnicodeArgsTest
 */

public final class UnicodeArgsTest {

    @Parameter("true")
    @Parameter("false")
    @Test
    public void test8246042(boolean onCommandLine) {
        final String testString;

        String encoding = System.getProperty("native.encoding");
        switch (encoding) {
        default:
            testString = new String(Character.toChars(0x00E9));
            break;

        case "MS932":
        case "SJIS":
            testString = new String(Character.toChars(0x3042));
            break;
        }

        TKit.trace(String.format("Test string code points: %s", testString
                .codePoints()
                .mapToObj(codePoint -> String.format("0x%04x", codePoint))
                .collect(Collectors.joining(",", "[", "]"))));

        JPackageCommand cmd = JPackageCommand.helloAppImage().useToolProvider(true);
        if (!onCommandLine) {
            cmd.addArguments("--arguments", testString);
        }
        cmd.executeAndAssertImageCreated();

        if (onCommandLine) {
            HelloApp.executeLauncherAndVerifyOutput(cmd, testString);
        } else {
            HelloApp.executeLauncherAndVerifyOutput(cmd);
        }
    }
}
