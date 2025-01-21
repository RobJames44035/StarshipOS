/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.util.Arrays;

import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.JDKToolLauncher;

/**
 * Helper class for starting jcmd process.
 * <pre>
 * - jcmd will send diagnostic requests to the current java process:
 *      jcmd pid_to_current_process PerfCounter.print
 * - jcmd will be run without sending request to any JVM
 *      jcmd -h
 * </pre>
 */
public final class JcmdBase {

    private static ProcessBuilder processBuilder = new ProcessBuilder();

    private JcmdBase() {
        // Private constructor to prevent class instantiation
    }

    /**
     * Sends the diagnostic command request to the current process
     *
     * @see #jcmd(boolean, String[], String[])
     */
    public final static OutputAnalyzer jcmd(String... jcmdArgs)
            throws Exception {
        return jcmd(true, null, jcmdArgs);
    }

    /**
     * Sends the diagnostic command request to the current process.
     * jcmd will be run with specified {@code vmArgs}.
     *
     * @see #jcmd(boolean, String[], String[])
     */
    public final static OutputAnalyzer jcmd(String[] vmArgs,
            String[] jcmdArgs) throws Exception {
        return jcmd(true, vmArgs, jcmdArgs);
    }

    /**
     * Runs jcmd without sending request to any JVM
     *
     * @see #jcmd(boolean, String[], String[])
     */
    public final static OutputAnalyzer jcmdNoPid(String[] vmArgs,
            String[] jcmdArgs) throws Exception {
        return jcmd(false, vmArgs, jcmdArgs);
    }

    /**
     * If {@code requestToCurrentProcess} is {@code true}
     * sends a diagnostic command request to the current process.
     * If {@code requestToCurrentProcess} is {@code false}
     * runs jcmd without sending request to any JVM.
     *
     * @param requestToCurrentProcess
     *            Defines if jcmd will send request to the current process
     * @param vmArgs
     *            jcmd will be run with VM arguments specified,
     *            e.g. -XX:+UsePerfData
     * @param jcmdArgs
     *            jcmd will be run with option or command and its arguments
     *            specified, e.g. VM.flags
     * @return The output from {@link OutputAnalyzer} object
     * @throws Exception
     */
    private static final OutputAnalyzer jcmd(boolean requestToCurrentProcess,
            String[] vmArgs, String[] jcmdArgs) throws Exception {
        JDKToolLauncher launcher = JDKToolLauncher.createUsingTestJDK("jcmd");
        launcher.addVMArgs(Utils.getTestJavaOpts());
        if (vmArgs != null) {
            for (String vmArg : vmArgs) {
                launcher.addVMArg(vmArg);
            }
        }
        if (requestToCurrentProcess) {
            launcher.addToolArg(Long.toString(ProcessTools.getProcessId()));
        }
        if (jcmdArgs != null) {
            for (String toolArg : jcmdArgs) {
                launcher.addToolArg(toolArg);
            }
        }
        processBuilder.command(launcher.getCommand());
        System.out.println(Arrays.toString(processBuilder.command().toArray()).replace(",", ""));
        OutputAnalyzer output = ProcessTools.executeProcess(processBuilder);
        System.out.println(output.getOutput());

        return output;
    }

}
