/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.dcmd;

import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.util.List;

/**
 * Base class for Diagnostic Command Executors using the jcmd tool
 */
public abstract class JcmdExecutor extends CommandExecutor {
    protected String jcmdBinary;

    protected abstract List<String> createCommandLine(String cmd) throws CommandExecutorException;

    protected JcmdExecutor() {
        jcmdBinary = JDKToolFinder.getJDKTool("jcmd");
    }

    protected OutputAnalyzer executeImpl(String cmd) throws CommandExecutorException {
        List<String> commandLine = createCommandLine(cmd);

        try {
            System.out.printf("Executing command '%s'%n", commandLine);
            OutputAnalyzer output = ProcessTools.executeProcess(new ProcessBuilder(commandLine));
            System.out.printf("Command returned with exit code %d%n", output.getExitValue());

            return output;
        } catch (Exception e) {
            String message = String.format("Caught exception while executing '%s'", commandLine);
            throw new CommandExecutorException(message, e);
        }
    }
}
