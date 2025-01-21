/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.dcmd;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Executes Diagnostic Commands on the target VM (specified by pid) using the jcmd tool and its ability to read
 *          Diagnostic Commands from a file.
 */
public class FileJcmdExecutor extends PidJcmdExecutor {

    /**
     * Instantiates a new FileJcmdExecutor targeting the current VM
     */
    public FileJcmdExecutor() {
        super();
    }

    /**
     * Instantiates a new FileJcmdExecutor targeting the VM indicated by the given pid
     *
     * @param target Pid of the target VM
     */
    public FileJcmdExecutor(String target) {
        super(target);
    }

    protected List<String> createCommandLine(String cmd) throws CommandExecutorException {
        File cmdFile = createTempFile();
        writeCommandToTemporaryFile(cmd, cmdFile);

        return Arrays.asList(jcmdBinary, Long.toString(pid),
                "-f", cmdFile.getAbsolutePath());
    }

    private void writeCommandToTemporaryFile(String cmd, File cmdFile) {
        try (PrintWriter pw = new PrintWriter(cmdFile)) {
            pw.println(cmd);
        } catch (IOException e) {
            String message = "Could not write to file: " + cmdFile.getAbsolutePath();
            throw new CommandExecutorException(message, e);
        }
    }

    private File createTempFile() {
        try {
            File cmdFile = File.createTempFile("input", "jcmd");
            cmdFile.deleteOnExit();
            return cmdFile;
        } catch (IOException e) {
            throw new CommandExecutorException("Could not create temporary file", e);
        }
    }

}
