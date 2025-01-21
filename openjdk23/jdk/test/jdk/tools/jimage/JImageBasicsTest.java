/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Tests to verify jimage basic options, i.e. --version, -h
 * @library /test/lib
 * @modules jdk.jlink/jdk.tools.jimage
 * @build jdk.test.lib.Asserts
 * @run main JImageBasicsTest
 */

import static jdk.test.lib.Asserts.assertTrue;

public class JImageBasicsTest extends JImageCliTest {

    public void testVersion() {
        jimage("--version")
                .assertSuccess()
                .resultChecker(r -> {
                    assertTrue(r.output.contains(System.getProperty("java.version")), "Contains java version.");
                });
    }

    public void testFullVersion() {
        jimage("--full-version")
                .assertSuccess()
                .resultChecker(r -> {
                    assertTrue(r.output.contains(System.getProperty("java.version")), "Contains java version.");
                });
    }

    public void testHelp() {
        jimage("--help")
                .assertSuccess()
                .resultChecker(r -> verifyHelpOutput(r.output));
    }

    public void testShortHelp() {
        jimage("-h")
                .assertSuccess()
                .resultChecker(r -> verifyHelpOutput(r.output));
    }

    public void testUnknownAction() {
        jimage("unknown")
                .assertFailure()
                .assertShowsError();
    }

    public void testUnknownOption() {
        jimage("--unknown")
                .assertFailure()
                .assertShowsError();
    }

    private void verifyHelpOutput(String output) {
        assertTrue(output.startsWith("Usage: jimage"), "Usage is printed.");
        assertTrue(output.contains("extract"), "Option 'extract' is found.");
        assertTrue(output.contains("info"), "Option 'info' is found.");
        assertTrue(output.contains("list"), "Option 'list' is found.");
        assertTrue(output.contains("verify"), "Option 'verify' is found.");
    }

    public static void main(String[] args) throws Throwable {
        new JImageBasicsTest().runTests();
    }
}

