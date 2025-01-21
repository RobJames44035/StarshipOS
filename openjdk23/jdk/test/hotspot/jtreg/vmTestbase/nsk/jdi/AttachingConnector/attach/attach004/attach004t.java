/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jdi.AttachingConnector.attach.attach004;

import java.io.*;
import java.util.*;
import nsk.share.*;
import nsk.share.jdi.ArgumentHandler;
import nsk.share.jpda.SocketIOPipe;

public class attach004t {

    static String testWorkDir;

    static String[] parseArgs(String[] args) {
        ArrayList<String> standardArgs = new ArrayList<String>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-testWorkDir") && (i < args.length - 1)) {
                testWorkDir = args[i + 1];

                if (testWorkDir.endsWith(File.separator)) {
                    testWorkDir = testWorkDir.substring(0, testWorkDir.length() - 1);
                }

                i++;
            } else
                standardArgs.add(args[i]);
        }

        return standardArgs.toArray(new String[] {});
    }

    static int readPortNumber(Log log) {
        try {
            String fileName = testWorkDir + File.separator + attach004.tempFileName;
            File file = new File(fileName);

            while (!file.exists()) {
                log.display("File '" + file + "' doesn't exists, sleep some");
                Thread.sleep(1000);
            }

            LineNumberReader reader = null;

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                while (fileInputStream.available() == 0) {
                    log.display("File '" + file + "' is empty, sleep some");
                    Thread.sleep(1000);
                }

                reader = new LineNumberReader(new InputStreamReader(fileInputStream));
                String portString = reader.readLine();

                log.display("Port number was read: " + portString);

                return Integer.parseInt(portString);
            } finally {
                if (reader != null)
                    reader.close();
            }

        } catch (Exception e) {
            log.complain("Unexpected exception: " + e);
            e.printStackTrace(log.getOutStream());
            throw new Failure("Unexpected exception: " + e);
        }
    }

    public static void main(String args[]) throws InterruptedException {
        ArgumentHandler argHandler = new ArgumentHandler(parseArgs(args));

        if (testWorkDir == null) {
            throw new TestBug("'testWorkDir' parameter wasn't specified");
        }

        Log log = argHandler.createDebugeeLog();

        log.display("attach004t was started");

        SocketIOPipe pipe = null;
        try {
            int portNumber = readPortNumber(log);
            pipe = SocketIOPipe.createClientIOPipe(log, portNumber, 0);
            log.display("Send message to debugger: " + attach004.messageOK);
            pipe.println(attach004.messageOK);

            String message = pipe.readln();
            log.display("Received from debugger: " + message);
            if (!message.equals(attach004.messageQuit)) {
                throw new TestBug("Unexpected debugger message: " + message);
            }

            final long sleepTime = 10000;

            log.display("Sleep for " + sleepTime + "ms");
            try {
                // debugee VM should wait here, otherwise test script doesn't have time to obtain debuggee exit code
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.complain("Unexpected exception: " + e);
                e.printStackTrace(log.getOutStream());
                throw new Failure("Unexpected exception: " + e);
            }

            log.display("attach004t finished execution");
        } finally {
            if (pipe != null)
                pipe.close();
        }

        System.exit(Consts.JCK_STATUS_BASE + Consts.TEST_PASSED);
    }
}
