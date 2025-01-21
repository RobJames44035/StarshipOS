/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.dcmd;

import jdk.test.lib.process.OutputAnalyzer;

/**
 * Abstract base class for Diagnostic Command executors
 */
public abstract class CommandExecutor {

    /**
     * Execute a diagnostic command
     *
     * @param cmd The diagnostic command to execute
     * @return an {@link jdk.test.lib.process.OutputAnalyzer} encapsulating the output of the command
     * @throws CommandExecutorException if there is an exception on the "calling side" while trying to execute the
     *          Diagnostic Command. Exceptions thrown on the remote side are available as textual representations in
     *          stderr, regardless of the specific executor used.
     */
    public final OutputAnalyzer execute(String cmd) throws CommandExecutorException {
        return execute(cmd, false);
    }

    /**
     * Execute a diagnostic command
     *
     * @param cmd The diagnostic command to execute
     * @param silent Do not print the command output
     * @return an {@link jdk.test.lib.process.OutputAnalyzer} encapsulating the output of the command
     * @throws CommandExecutorException if there is an exception on the "calling side" while trying to execute the
     *          Diagnostic Command. Exceptions thrown on the remote side are available as textual representations in
     *          stderr, regardless of the specific executor used.
     */
    public final OutputAnalyzer execute(String cmd, boolean silent) throws CommandExecutorException {
        if (!silent) {
            System.out.printf("Running DCMD '%s' through '%s'%n", cmd, this.getClass().getSimpleName());
        }

        OutputAnalyzer oa = executeImpl(cmd);

        if (!silent) {
            System.out.println("---------------- stdout ----------------");
            System.out.println(oa.getStdout());
            System.out.println("---------------- stderr ----------------");
            System.out.println(oa.getStderr());
            System.out.println("----------------------------------------");
            System.out.println();
        }
        return oa;
    }

    protected abstract OutputAnalyzer executeImpl(String cmd) throws CommandExecutorException;
}
