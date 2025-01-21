/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.dcmd;

import java.util.Arrays;
import java.util.List;

/**
 * Executes Diagnostic Commands on the target VM (specified by main class) using the jcmd tool
 */
public class MainClassJcmdExecutor extends JcmdExecutor {
    private final String mainClass;

    /**
     * Instantiates a new MainClassJcmdExecutor targeting the current VM
     */
    public MainClassJcmdExecutor() {
        super();
        mainClass = System.getProperty("sun.java.command").split(" ")[0];
    }

    /**
     * Instantiates a new MainClassJcmdExecutor targeting the VM indicated by the given main class
     *
     * @param target Main class of the target VM
     */
    public MainClassJcmdExecutor(String target) {
        super();
        mainClass = target;
    }

    protected List<String> createCommandLine(String cmd) throws CommandExecutorException {
        return Arrays.asList(jcmdBinary, mainClass, cmd);
    }

}
