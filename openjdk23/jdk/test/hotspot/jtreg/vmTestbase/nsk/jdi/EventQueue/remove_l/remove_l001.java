/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventQueue.remove_l;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.event.*;

import java.io.*;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * The test checks that a VMDisconnectedException thrown by
 * the JDI method <b>com.sun.jdi.request.EventQueue.remove(long)</b>
 * will always be preceded by a <code>VMDisconnectEvent</code>
 * when a debuggee part of the test normally exits.
 */
public class remove_l001 {
    public static final int PASSED = 0;
    public static final int FAILED = 2;
    public static final int JCK_STATUS_BASE = 95;
    static final String DEBUGGEE_CLASS =
        "nsk.jdi.EventQueue.remove_l.remove_l001t";
    static final String COMMAND_READY = "ready";
    static final String COMMAND_QUIT = "quit";

    private ArgumentHandler argHandler;
    private Log log;
    private Debugee debuggee;
    private int tot_res = FAILED;

    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {
        return new remove_l001().runIt(argv, out);
    }

    private int runIt(String args[], PrintStream out) {
        argHandler = new ArgumentHandler(args);
        log = new Log(out, argHandler);
        Binder binder = new Binder(argHandler, log);

        debuggee = binder.bindToDebugee(DEBUGGEE_CLASS);
        debuggee.redirectStderr(log, "remove_l001t.err> ");
// dummy IOPipe: just to avoid:
// "Pipe server socket listening error: java.net.SocketException"
        IOPipe pipe = debuggee.createIOPipe();

// Getting JDI events
        checkEvents(debuggee.VM().eventQueue());

        debuggee.waitFor();
        int debStat = debuggee.getStatus();
        if (debStat != (JCK_STATUS_BASE + PASSED)) {
            log.complain("TEST FAILED: debuggee's process finished with status: "
                + debStat);
            tot_res = FAILED;
        } else
            log.display("Debuggee's process finished with status: "
                + debStat);

        return tot_res;
    }

    private void checkEvents(EventQueue eventQ) {
        boolean gotVMDisconnect = false; // VMDisconnectEvent is received
        boolean gotVMDeath = false; // VMDeathEvent is received
        EventSet eventSet = null;

        debuggee.resume();
        while (true) {
            try {
                eventSet = eventQ.remove(argHandler.getWaitTime()*60000);
                if (eventSet == null) {
                    log.display("Specified time for the next available event has elapsed");
                    continue;
                }
                EventIterator eventIter = eventSet.eventIterator();
                while (eventIter.hasNext()) {
                    Event event = eventIter.nextEvent();
                    if (event instanceof VMDisconnectEvent) {
                        gotVMDisconnect = true;
                        log.display("Got expected VMDisconnectEvent");
                        break;
                    } else if (event instanceof VMStartEvent) {
                        log.display("Got VMStartEvent");
                    } else if (event instanceof VMDeathEvent) {
                        gotVMDeath = true;
                        log.display("Got VMDeathEvent");
                    }
                    if (!gotVMDisconnect && !gotVMDeath &&
                            eventSet.suspendPolicy() !=
                                EventRequest.SUSPEND_NONE) {
                        log.display("Calling EventSet.resume() ...");
                        eventSet.resume();
                    }
                }
            } catch(InterruptedException e) {
                log.complain("TEST INCOMPLETE: caught " + e);
                tot_res = FAILED;
            } catch(VMDisconnectedException e) {
                if (gotVMDisconnect) {
                    log.display("\nCHECK PASSED: caught VMDisconnectedException preceded by a VMDisconnectEvent\n");
                    tot_res = PASSED;
                } else {
                    log.complain("\nTEST FAILED: caught VMDisconnectedException without preceding VMDisconnectEvent\n");
                    e.printStackTrace();
                    tot_res = FAILED;
                }
                break;
            }
        }
        log.display("Stopped JDI events processing");
    }
}
