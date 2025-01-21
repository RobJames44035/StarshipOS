/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.util.Arrays;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/*
 * @test
 * @summary Unit test for jinfo utility
 * @library /test/lib
 * @run main BasicJInfoTest
 */
public class BasicJInfoTest {

    private static ProcessBuilder processBuilder = new ProcessBuilder();

    public static void main(String[] args) throws Exception {
        testJinfoNoArgs();
        testJinfoFlags();
        testJinfoProps();
        testJinfoFlagInvalid();
    }

    private static void testJinfoNoArgs() throws Exception {
        OutputAnalyzer output = jinfo();
        output.shouldContain("-XX");
        output.shouldContain("test.jdk=");
        output.shouldHaveExitValue(0);
    }

    private static void testJinfoFlagInvalid() throws Exception {
        OutputAnalyzer output = jinfo("-flag");
        output.shouldHaveExitValue(1);
    }

    private static void testJinfoFlags() throws Exception {
        OutputAnalyzer output = jinfo("-flags");
        output.shouldContain("-XX");
        output.shouldHaveExitValue(0);
    }

    private static void testJinfoProps() throws Exception {
        OutputAnalyzer output = jinfo("-props");
        output.shouldContain("test.jdk=");
        output.shouldHaveExitValue(0);
    }

    private static OutputAnalyzer jinfo(String... toolArgs) throws Exception {
        JDKToolLauncher launcher = JDKToolLauncher.createUsingTestJDK("jinfo");
        launcher.addVMArgs(Utils.getTestJavaOpts());
        if (toolArgs != null) {
            for (String toolArg : toolArgs) {
                launcher.addToolArg(toolArg);
            }
        }
        launcher.addToolArg(Long.toString(ProcessTools.getProcessId()));

        processBuilder.command(launcher.getCommand());
        System.out.println(Arrays.toString(processBuilder.command().toArray()).replace(",", ""));
        OutputAnalyzer output = ProcessTools.executeProcess(processBuilder);
        System.out.println(output.getOutput());

        return output;
    }

}
