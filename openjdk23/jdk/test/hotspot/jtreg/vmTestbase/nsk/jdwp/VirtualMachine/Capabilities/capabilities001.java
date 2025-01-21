/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.VirtualMachine.Capabilities;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;
import java.util.*;

public class capabilities001 {
    static final int JCK_STATUS_BASE = 95;
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final String PACKAGE_NAME = "nsk.jdwp.VirtualMachine.Capabilities";
    static final String TEST_CLASS_NAME = PACKAGE_NAME + "." + "capabilities001";
    static final String DEBUGEE_CLASS_NAME = TEST_CLASS_NAME + "a";

    static final String JDWP_COMMAND_NAME = "VirtualMachine.Capabilities";
    static final int JDWP_COMMAND_ID = JDWP.Command.VirtualMachine.Capabilities;

    public static void main (String argv[]) {
        int result = run(argv, System.out);
        if (result != 0) {
            throw new RuntimeException("Test failed");
        }
    }

    public static int run(String argv[], PrintStream out) {
        return new capabilities001().runIt(argv, out);
    }

    public int runIt(String argv[], PrintStream out) {

        boolean success = true;

        try {
            ArgumentHandler argumentHandler = new ArgumentHandler(argv);
            Log log = new Log(out, argumentHandler);

            try {

                Binder binder = new Binder(argumentHandler, log);
                log.display("Start debugee VM");
                Debugee debugee = binder.bindToDebugee(DEBUGEE_CLASS_NAME);
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

                // begin test of JDWP command

                try {
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

                    byte canWatchFieldModification = reply.getByte();
                    log.display("  canWatchFieldModification:     " + canWatchFieldModification);

                    byte canWatchFieldAction = reply.getByte();
                    log.display("  canWatchFieldAction:           " + canWatchFieldAction);

                    byte canGetBytecodes = reply.getByte();
                    log.display("  canGetBytecodes:               " + canGetBytecodes);

                    byte canGetSyntheticAttribute = reply.getByte();
                    log.display("  canGetSyntheticAttribute:      " + canGetSyntheticAttribute);

                    byte canGetOwnedMonitorInfo = reply.getByte();
                    log.display("  canGetOwnedMonitorInfo:        " + canGetOwnedMonitorInfo);

                    byte canGetCurrentContendedMonitor = reply.getByte();
                    log.display("  canGetCurrentContendedMonitor: " + canGetCurrentContendedMonitor);

                    byte canGetMonitorInfo = reply.getByte();
                    log.display("  canGetMonitorInfo:             " + canGetMonitorInfo);

                    if (! reply.isParsed()) {
                        log.complain("Extra bytes in reply packet at: " + reply.currentPosition());
                        success = false;
                    }

                } catch (Exception e) {
                    log.complain("Exception catched: " + e);
                    success = false;
                }

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
