/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test TestCompileCommand
 * @bug 8069389
 * @summary Regression tests of -XX:CompileCommand
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @requires vm.flagless
 * @run driver compiler.oracle.TestCompileCommand
 */

package compiler.oracle;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestCompileCommand {

    private static final String[][] ARGUMENTS = {
        {
            "-XX:CompileCommand=print,*01234567890123456789012345678901234567890123456789.*0123456789012345678901234567890123456789",
            "-version"
        }
    };

    private static final String[][] OUTPUTS = {
        {
            "print *01234567890123456789012345678901234567890123456789.*0123456789012345678901234567890123456789"
        }
    };

    private static void verifyValidOption(String[] arguments, String[] expected_outputs) throws Exception {
        ProcessBuilder pb;
        OutputAnalyzer out;

        pb = ProcessTools.createLimitedTestJavaProcessBuilder(arguments);
        out = new OutputAnalyzer(pb.start());

        for (String expected_output : expected_outputs) {
            out.shouldContain(expected_output);
        }

        out.shouldNotContain("CompileCommand: An error occurred during parsing");
        out.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {

        if (ARGUMENTS.length != OUTPUTS.length) {
            throw new RuntimeException("Test is set up incorrectly: length of arguments and expected outputs for type (1) options does not match.");
        }

        // Check if type (1) options are parsed correctly
        for (int i = 0; i < ARGUMENTS.length; i++) {
            verifyValidOption(ARGUMENTS[i], OUTPUTS[i]);
        }
    }
}
