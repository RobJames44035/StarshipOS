/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package process;

import nsk.share.jdi.ArgumentHandler;
import nsk.share.jpda.IOPipe;

/**
 * A simple process that connects to a pipe and waits for command "quit" to
 * be received.
 *
 * Usage: java TestJavaProcess -pipe.port <PIPE_PORT_NUMBER>
 */

public class TestJavaProcess {

    static final int PASSED = 0;
    static final int FAILED = 2;

    public static void main(String argv[]) {

        log("Test Java process started!");

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        pipe.println("ready");
        log("Waiting for the quit command from the test ...");
        String cmd = pipe.readln();
        int exitCode = PASSED;
        if ("quit".equals(cmd)) {
            log("'quit' received");
        } else {
            log("Invalid command received " + cmd);
            exitCode = FAILED;
        }
        System.exit(exitCode);
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
