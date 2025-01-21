/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4425852
 * @author Robert Field
 *
 * @summary EventQueueDisconnectTest checks to see that
 * VMDisconnectedException is never thrown before VMDisconnectEvent.
 *
 * Failure mode for this test is throwing VMDisconnectedException
 * on vm.eventQueue().remove();
 * Does not use a scaffold since we don't want that hiding the exception.
 *
 * @run build VMConnection
 * @run compile -g EventQueueDisconnectTest.java
 * @run driver EventQueueDisconnectTest
 */
import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;


    /********** target program **********/

class EventQueueDisconnectTarg {
    public static void main(String args[]) {
        for (int i=0; i < 10; ++i) {
            Say(i);
        }
    }
    static void Say(int what) {
        System.out.println("Say " + what);
    }
}

    /********** test program **********/

public class EventQueueDisconnectTest {

    public static void main(String args[]) throws Exception {
        VMConnection connection = new VMConnection(
                                       "com.sun.jdi.CommandLineLaunch:",
                                       VirtualMachine.TRACE_NONE);
        connection.setConnectorArg("main", "EventQueueDisconnectTarg");
        String debuggeeVMOptions = VMConnection.getDebuggeeVMOptions();
        if (!debuggeeVMOptions.equals("")) {
            if (connection.connectorArg("options").length() > 0) {
                throw new IllegalArgumentException("VM options in two places");
            }
            connection.setConnectorArg("options", debuggeeVMOptions);
        }
        VirtualMachine vm = connection.open();
        EventRequestManager requestManager = vm.eventRequestManager();
        MethodEntryRequest req = requestManager.createMethodEntryRequest();
        req.addClassFilter("EventQueueDisconnectTarg");
        req.setSuspendPolicy(EventRequest.SUSPEND_NONE);
        req.enable();

        // We need to have the BE stop when VMDeath comes
        VMDeathRequest ourVMDeathRequest = requestManager.createVMDeathRequest();
        ourVMDeathRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        ourVMDeathRequest.enable();

        vm.resume();
        while (true) {
            EventSet set = vm.eventQueue().remove();
            Event event = set.eventIterator().nextEvent();

            System.err.println("EventSet with: " + event.getClass());

            if (event instanceof VMDisconnectEvent) {
                System.err.println("Disconnecting successfully");
                break;
            }

            if (event instanceof VMDeathEvent) {
                System.err.println("Pausing after VM death");

                // sleep a few seconds
                try {
                    Thread.sleep(40 * 1000);
                } catch (InterruptedException exc) {
                    // ignore
                }
            }

            set.resume();
        }

        System.err.println("EventQueueDisconnectTest passed");
    }
}
