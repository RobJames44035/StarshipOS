/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.VirtualMachine.Dispose;

import java.io.*;
import java.util.*;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

public class dispose001 {
    static final int JCK_STATUS_BASE = 95;
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final String PACKAGE_NAME = "nsk.jdwp.VirtualMachine.Dispose";
    static final String TEST_CLASS_NAME = PACKAGE_NAME + "." + "dispose001";
    static final String DEBUGEE_CLASS_NAME = TEST_CLASS_NAME + "a";

    static final String JDWP_COMMAND_NAME = "VirtualMachine.Dispose";
    static final int JDWP_COMMAND_ID = JDWP.Command.VirtualMachine.Dispose;

    public static void main (String argv[]) {
        int result = run(argv, System.out);
        if (result != 0) {
            throw new RuntimeException("Test failed");
        }
    }

    public static int run(String argv[], PrintStream out) {
    return new dispose001().runIt(argv, out);
    }

    public int runIt(String argv[], PrintStream out) {

        boolean success = true;

        try {
            final ArgumentHandler argumentHandler = new ArgumentHandler(argv);
            final Log log = new Log(out, argumentHandler);

            try {

                Binder binder = new Binder(argumentHandler, log);
                log.display("Start debugee VM");
                final Debugee debugee = binder.bindToDebugee(DEBUGEE_CLASS_NAME);
                Transport transport = debugee.getTransport();
                IOPipe pipe = debugee.createIOPipe();

                log.display("Waiting for VM_INIT event");
                debugee.waitForVMInit();

                log.display("Querying for IDSizes");
                debugee.queryForIDSizes();

                log.display("Resume debugee VM");
                debugee.resume();

                log.display("Waiting for command: " + "ready");
                String cmd = pipe.readln();
                log.display("Received command: " + cmd);

                int bytesToWrite = 1024;

                // begin test of JDWP command

                try {
                    log.display("Creating command packet for " + JDWP_COMMAND_NAME);
                    CommandPacket command = new CommandPacket(JDWP_COMMAND_ID);

                    log.display("Sending command packet:\n" + command);
                    transport.write(command);

                    log.display("Waiting for reply packet");
                    ReplyPacket reply = new ReplyPacket();
                    transport.read(reply);
                    log.display("Reply packet received:\n" + reply);

                    log.display("Checking reply packet header");
                    reply.checkHeader(command.getPacketID());

                    log.display("Parsing reply packet:");
                    reply.resetPosition();

                    if (! reply.isParsed()) {
                        log.complain("Extra bytes in reply packet at: " + reply.currentPosition());
                        success = false;
                    }

                    log.display("Reply packet parsed successfully");

                } catch (Exception e) {
                    log.complain("Exception catched: " + e);
                    success = false;
                }

/*
                // check if transport connection has been actually closed

                if (success) {

                    log.display("Trying to write VirtualMachine.Version command packet "
                                + "to the closed Transport connection");

                    try {
                        CommandPacket command = new CommandPacket(JDWP.Command.VirtualMachine.Version);
                        transport.write(command);

                        log.complain("Command packet successfully written to the closed transport channel\n"
                                          + "without any I/O exception thrown");
                        success = false;
                    } catch (IOException e) {
                        log.display("Caught expected IOException:\n\t" + e);
                    }

                }
*/

                // end test of JDWP command

                log.display("Sending command: " + "quit");
                pipe.println("quit");

                log.display("Waiting for debugee exits");
                int code = debugee.waitFor();
                if (code == JCK_STATUS_BASE + PASSED) {
                    log.display("Debugee PASSED: " + code);
                } else {
                    log.complain("Debugee FAILED: " + code);
                    success = false;
                }

            } catch (Exception e) {
                log.complain("Unexpected exception: " + e);
                e.printStackTrace(out);
                success = false;
            }

            if (!success) {
                log.complain("TEST FAILED");
                return FAILED;
            }

        } catch (Exception e) {
            out.println("Unexpected exception: " + e);
            e.printStackTrace(out);
            out.println("TEST FAILED");
            return FAILED;
        }

        out.println("TEST PASSED");
        return PASSED;

    }

}
