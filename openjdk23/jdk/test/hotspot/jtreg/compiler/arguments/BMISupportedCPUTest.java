/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.arguments;

import jdk.test.lib.process.ExitCode;
import jdk.test.lib.cli.CommandLineOptionTest;

/**
 * Test on bit manipulation related command line options,
 * that should be executed on CPU that supports all required
 * features.
 *
 * Note that this test intended to verify that VM could be launched with
 * specific options and that values of these options processed correctly.
 * In order to do that test launch a new VM with tested options, the same
 * flavor-specific flag as one that was used for parent VM (-client, -server,
 * -minimal, -graal) and '-version'.
 */
public class BMISupportedCPUTest extends BMICommandLineOptionTestBase {

    /**
     * Construct new test on {@code optionName} option.
     *
     * @param optionName Name of the option to be tested
     *                   without -XX:[+-] prefix.
     * @param warningMessage Message that can occur in VM output
     *                       if CPU on test box does not support
     *                       features required by the option.
     * @param cpuFeatures CPU features requires by the option.
     */
    public BMISupportedCPUTest(String optionName,
                               String warningMessage,
                               String... cpuFeatures) {
        super(optionName, warningMessage, cpuFeatures, null);
    }

    @Override
    public void runTestCases() throws Throwable {
        /*
          Verify that VM will successfully start up without warnings.
          VM will be launched with following flags:
          -XX:+<tested option> -version
        */
        String errorString = String.format("JVM should start with '-XX:+%s'"
                + " flag without any warnings", optionName);
        CommandLineOptionTest.verifySameJVMStartup(null,
                new String[] { warningMessage }, errorString, errorString,
                ExitCode.OK,
                CommandLineOptionTest.prepareBooleanFlag(optionName, true));

        /*
          Verify that VM will successfully start up without warnings.
          VM will be launched with following flags:
          -XX:-<tested option> -version
        */
        errorString = String.format("JVM should start with '-XX:-%s'"
                + " flag without any warnings", optionName);
        CommandLineOptionTest.verifySameJVMStartup(null,
                new String[] { warningMessage }, errorString,
                errorString, ExitCode.OK,
                CommandLineOptionTest.prepareBooleanFlag(optionName, false));

        /*
          Verify that on appropriate CPU option in on by default.
          VM will be launched with following flags:
          -version
        */
        CommandLineOptionTest.verifyOptionValueForSameVM(optionName, "true",
                String.format("Option '%s' is expected to have default value "
                    + "'true'", optionName));

        /*
          Verify that option could be explicitly turned off.
          VM will be launched with following flags:
          -XX:-<tested option> -version
        */
        CommandLineOptionTest.verifyOptionValueForSameVM(optionName, "false",
                String.format("Option '%s' is set to have value 'false'",
                    optionName),
                CommandLineOptionTest.prepareBooleanFlag(optionName, false));
    }
}

