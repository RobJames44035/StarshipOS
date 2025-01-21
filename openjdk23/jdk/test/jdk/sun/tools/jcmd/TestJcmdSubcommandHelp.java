/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8332124
 * @summary Test to verify jcmd accepts the "-help", "--help" and "-h" suboptions as a command argument
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm TestJcmdSubcommandHelp
 *
 */

import jdk.test.lib.process.OutputAnalyzer;


public class TestJcmdSubcommandHelp {

    private static final String HELP_ONE_DASH = "-help";
    private static final String HELP_TWO_DASH = "--help";
    private static final String SINGLE_H = "-h";
    private static final String CMD = "VM.metaspace";
    private static final String ILLEGAL = "IllegalArgumentException: Unknown argument";

    public static void main(String[] args) throws Exception {

        // Sanity check with empty input
        OutputAnalyzer output = JcmdBase.jcmd();
        output.shouldContain("The following commands are available:");

        // Sanity check with existing usage for "help <cmd>"
        output = JcmdBase.jcmd("help", CMD);
        String expectedOutput = output.getOutput();
        output.shouldNotContain("Unknown diagnostic command");

        testExpectedUsage(HELP_ONE_DASH, expectedOutput);
        testExpectedUsage(HELP_TWO_DASH, expectedOutput);
        testExpectedUsage(SINGLE_H, expectedOutput);

        testIgnoreAdditionalArgs(HELP_ONE_DASH, expectedOutput);
        testIgnoreAdditionalArgs(HELP_TWO_DASH, expectedOutput);
        testIgnoreAdditionalArgs(SINGLE_H, expectedOutput);

        testIgnoreTrailingSpaces(HELP_ONE_DASH, expectedOutput);
        testIgnoreTrailingSpaces(HELP_TWO_DASH, expectedOutput);
        testIgnoreTrailingSpaces(SINGLE_H, expectedOutput);

        testSimilarCommand(HELP_ONE_DASH + "less", ILLEGAL);
        testSimilarCommand(HELP_TWO_DASH + "me", ILLEGAL);
        testSimilarCommand(SINGLE_H + "ello", ILLEGAL);
    }

    private static void testExpectedUsage(String helpOption, String expectedOutput) throws Exception {
        verifyOutput(new String[] {CMD, helpOption}, expectedOutput,
                "Expected jcmd to accept '%s' suboption as a command argument and issue the same help output.".formatted(helpOption));
    }

    private static void testIgnoreAdditionalArgs(String helpOption, String expectedOutput) throws Exception {
        verifyOutput(new String[] {CMD, helpOption, "basic"}, expectedOutput,
                "Expected jcmd to accept '%s' suboption with additional arguments after help.".formatted(helpOption));
    }

    private static void testIgnoreTrailingSpaces(String helpOption, String expectedOutput) throws Exception {
        verifyOutput(new String[] {CMD, "%s    ".formatted(helpOption)}, expectedOutput,
                "Expected jcmd to accept '%s' suboption with trailing spaces".formatted(helpOption));
    }

    private static void testSimilarCommand(String helpOption, String expectedOutput) throws Exception {
        verifyOutputContains(new String[] {CMD, helpOption}, expectedOutput,
                "Expected jcmd to NOT accept '%s' suboption with trailing content".formatted(helpOption));
    }

    private static void verifyOutputContains(String[] args, String expectedOutput, String errorMessage) throws Exception {
        OutputAnalyzer output = JcmdBase.jcmd(args);
        String issuedOutput = output.getOutput();
        if (!issuedOutput.contains(expectedOutput)) {
            printDifferingOutputs(expectedOutput, issuedOutput);
            throw new Exception(errorMessage);
        }
    }

    private static void verifyOutput(String[] args, String expectedOutput, String errorMessage) throws Exception {
        OutputAnalyzer output = JcmdBase.jcmd(args);
        String issuedOutput = output.getOutput();
        if (!expectedOutput.equals(issuedOutput)) {
            printDifferingOutputs(expectedOutput, issuedOutput);
            throw new Exception(errorMessage);
        }
    }

    private static void printDifferingOutputs(String expectedOutput, String issuedOutput) {
        System.out.println("Expected output: ");
        System.out.println(expectedOutput);
        System.out.println("Issued output: ");
        System.out.println(issuedOutput);
    }
}
