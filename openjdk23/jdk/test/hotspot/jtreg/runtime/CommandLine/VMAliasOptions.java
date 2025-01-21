/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.cli.*;

/*
 * @test
 * @bug 8061611
 * @summary Test that various alias options correctly set the target options. See aliased_jvm_flags in arguments.cpp.
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @run driver VMAliasOptions
 */
public class VMAliasOptions {

    /**
     * each entry is {[0]: alias name, [1]: alias target, [2]: value to set
     * (true/false/n/string)}.
     */
    public static final String[][] ALIAS_OPTIONS = {
        {"CreateMinidumpOnCrash",   "CreateCoredumpOnCrash", "false" },
    };

    static void testAliases(String[][] optionInfo) throws Throwable {
        String aliasNames[]     = new String[optionInfo.length];
        String optionNames[]    = new String[optionInfo.length];
        String expectedValues[] = new String[optionInfo.length];
        for (int i = 0; i < optionInfo.length; i++) {
            aliasNames[i]     = optionInfo[i][0];
            optionNames[i]    = optionInfo[i][1];
            expectedValues[i] = optionInfo[i][2];
        }

        OutputAnalyzer output = CommandLineOptionTest.startVMWithOptions(aliasNames, expectedValues, "-XX:+PrintFlagsFinal");
        CommandLineOptionTest.verifyOptionValuesFromOutput(output, optionNames, expectedValues);
    }

    public static void main(String[] args) throws Throwable {
        testAliases(ALIAS_OPTIONS);
    }
}
