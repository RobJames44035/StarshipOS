/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8268725
 * @summary Tests for the --enable-native-access option
 * @modules jdk.jshell
 * @run testng ToolEnableNativeAccessTest
 */

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ToolEnableNativeAccessTest extends ReplToolTesting {

    @Test
    public void testOptionDebug() {
        test(
                (a) -> assertCommand(a, "/debug b",
                        "RemoteVM Options: []\n"
                        + "Compiler options: []"),
                (a) -> assertCommand(a, "/env --enable-native-access",
                        "|  Setting new options and restoring state."),
                (a) -> assertCommandCheckOutput(a, "/debug b", s -> {
                    assertTrue(s.contains("RemoteVM Options: [--enable-native-access, ALL-UNNAMED]"));
                    assertTrue(s.contains("Compiler options: []"));
                })
        );
    }

    @Test
    public void testCommandLineFlag() {
        test(new String[] {"--enable-native-access"},
                (a) -> assertCommandCheckOutput(a, "/debug b", s -> {
                    assertTrue(s.contains("RemoteVM Options: [--enable-native-access, ALL-UNNAMED]"));
                    assertTrue(s.contains("Compiler options: []"));
                })
        );
    }

}
