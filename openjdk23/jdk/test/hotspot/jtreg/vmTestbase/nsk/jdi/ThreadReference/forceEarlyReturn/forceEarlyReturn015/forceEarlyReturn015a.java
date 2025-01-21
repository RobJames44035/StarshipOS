/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jdi.ThreadReference.forceEarlyReturn.forceEarlyReturn015;

import nsk.share.jdi.*;

public class forceEarlyReturn015a extends AbstractJDIDebuggee {

    public static void main(String args[]) {
        new forceEarlyReturn015a().doTest(args);
    }

    public static final String COMMAND_START_NEW_THREAD = "startNewThread";

    public boolean parseCommand(String command) {
        if (super.parseCommand(command))
            return true;

        if (command.equals(COMMAND_START_NEW_THREAD)) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    log.display("Thread exit");
                }
            });

            thread.setName("forceEarlyReturn015a_NewThread");
            thread.start();

            return true;
        }

        return false;
    }
}
