
/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Launches a new Java process that uses a communication pipe to interact
 * with the test.
 */

public class TestProcessLauncher {

    protected static final Path USER_DIR = FileSystems.getDefault().getPath(System.getProperty("user.dir", "."));
    protected static final Path TEST_CLASSES_DIR = FileSystems.getDefault().getPath(System.getProperty("test.classes"));

    protected final String className;
    private final ArgumentHandler argHandler;

    private IOPipe pipe;

    public TestProcessLauncher(String className, ArgumentHandler argHandler) {
        this.className = className;
        this.argHandler = argHandler;
    }

    public TestProcessLauncher(String className) {
        this(className, new ArgumentHandler(new String[] {"-transport.address=dynamic"}));
    }

    public Process launch() {

        String java = argHandler.getLaunchExecPath();

        Log log = new Log(System.out, argHandler);
        Binder binder = new Binder(argHandler, log);
        binder.prepareForPipeConnection(argHandler);

        pipe = IOPipe.startDebuggerPipe(binder);

        String cmd = prepareLaunch(java, argHandler.getPipePort());

        Debugee debuggee = binder.startLocalDebugee(cmd);
        debuggee.redirectOutput(log);

        String line = pipe.readln();
        if (!"ready".equals(line)) {
            System.out.println("Wrong reply received:" + line);
        }
        return debuggee.getProcess();
    }

    public void quit() {
        if (pipe != null) {
            if (pipe.isConnected()) {
                pipe.println("quit");
            } else {
                System.out.println("WARNING: IOPipe is not connected");
            }
        }
    }

    protected String prepareLaunch(String javaExec, String pipePort) {
        return  javaExec + " " + className + " -pipe.port=" + pipePort;
    }

}
