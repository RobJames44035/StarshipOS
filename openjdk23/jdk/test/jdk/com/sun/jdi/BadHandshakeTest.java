/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.net.ConnectException;
import java.net.Socket;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector.Argument;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import lib.jdb.Debuggee;

/* @test
 * @bug 6306165 6432567
 * @summary Check that a bad handshake doesn't cause a debuggee to abort
 * @library /test/lib
 *
 * @modules java.management
 *          jdk.jdi
 * @build BadHandshakeTest Exit0
 * @run driver BadHandshakeTest
 */
public class BadHandshakeTest {

    /*
     * Find a connector by name
     */
    private static Connector findConnector(String name) {
        List<Connector> connectors = Bootstrap.virtualMachineManager().allConnectors();
        Iterator<Connector> iter = connectors.iterator();
        while (iter.hasNext()) {
            Connector connector = iter.next();
            if (connector.name().equals(name)) {
                return connector;
            }
        }
        return null;
    }

    private static void log(Object s) {
        System.out.println(String.valueOf(s));
    }

    public static void main(String args[]) throws Exception {
        // Launch the server debugee
        log("Starting debuggee...");
        try (Debuggee debuggee = Debuggee.launcher("Exit0").launch()) {
            log("Debuggee started.");
            int port = Integer.parseInt(debuggee.getAddress());
            log("Debuggee port: " + port);

            log("testcase 1...");
            // Connect to the debuggee and handshake with garbage
            Socket s = new Socket("localhost", port);
            s.getOutputStream().write("Here's a poke in the eye".getBytes("UTF-8"));
            s.close();

            log("testcase 2...");
            // Re-connect and do a partial handshake - don't disconnect
            // Re-connect just after disconnect may cause "connection refused" error (see JDK-8192057)
            Exception error = null;
            long retryDelay = 20;
            for (int retry = 0; retry < 5; retry++) {
                if (error != null) {
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ex) {
                        // ignore
                    }
                    retryDelay *= 2;
                    error = null;
                }
                try {
                    log("retry: " + retry);
                    s = new Socket("localhost", port);
                    s.getOutputStream().write("JDWP-".getBytes("UTF-8"));
                    break;
                } catch (ConnectException ex) {
                    log("got exception: " + ex.toString());
                    error = ex;
                }
            }
            if (error != null) {
                throw error;
            }

            log("final attach...");
            // Attach to server debuggee to ensure it's still available to attach and resume it so it can exit
            AttachingConnector conn = (AttachingConnector)findConnector("com.sun.jdi.SocketAttach");
            retryDelay = 20;
            for (int retry = 0; retry < 5; retry++) {
                if (error != null) {
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ex) {
                        // ignore
                    }
                    retryDelay *= 2;
                    error = null;
                }
                try {
                    log("retry: " + retry);
                    Map<String, Argument> conn_args = conn.defaultArguments();
                    Connector.IntegerArgument port_arg =
                            (Connector.IntegerArgument)conn_args.get("port");
                    port_arg.setValue(port);
                    VirtualMachine vm = conn.attach(conn_args);

                    // The first event is always a VMStartEvent, and it is always in
                    // an EventSet by itself.  Wait for it.
                    EventSet evtSet = vm.eventQueue().remove();
                    for (Event event : evtSet) {
                        if (event instanceof VMStartEvent) {
                            break;
                        }
                        throw new RuntimeException("Test failed - debuggee did not start properly");
                    }

                    vm.eventRequestManager().deleteAllBreakpoints();
                    vm.resume();
                    break;
                } catch (ConnectException ex) {
                    log("got exception: " + ex.toString());
                    error = ex;
                }
            }
            if (error != null) {
                throw error;
            }

            // give the debuggee some time to exit before forcibly terminating it
            debuggee.waitFor(10, TimeUnit.SECONDS);
        }
    }
}
