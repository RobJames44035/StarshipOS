/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.dcmd;

import jdk.test.lib.process.ProcessTools;

import java.util.Arrays;
import java.util.List;

/**
 * Executes Diagnostic Commands on the target VM (specified by pid) using the jcmd tool
 */
public class PidJcmdExecutor extends JcmdExecutor {
    protected final long pid;

    /**
     * Instantiates a new PidJcmdExecutor targeting the current VM
     */
    public PidJcmdExecutor() {
        super();
        try {
            pid = ProcessTools.getProcessId();
        } catch (Exception e) {
            throw new CommandExecutorException("Could not determine own pid", e);
        }
    }

    /**
     * Instantiates a new PidJcmdExecutor targeting the VM indicated by the given pid
     *
     * @param target Pid of the target VM
     */
    public PidJcmdExecutor(String target) {
        super();
        pid = Long.valueOf(target);
    }

    protected List<String> createCommandLine(String cmd) throws CommandExecutorException {
        return Arrays.asList(jcmdBinary, Long.toString(pid), cmd);
    }

}
