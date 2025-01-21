/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4287596
 * @summary Unit test for "Pluggable Connectors and Transports" feature.
 *
 * When a transport service is deployed the virtual machine
 * is required to create an AttachingConnector and ListeningConnector
 * to encapsulate the transport. This tests that the connectors are
 * created and that they have an "address" argument.
 *
 * @build GeneratedConnectors NullTransportService SimpleLaunchingConnector
 * @run main/othervm GeneratedConnectors
 */

import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import java.util.*;

public class GeneratedConnectors {

    static Connector find(List l, String name) {
        Iterator i = l.iterator();
        while (i.hasNext()) {
            Connector c = (Connector)i.next();
            if (c.name().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public static void main(String args[]) throws Exception {
        /*
         * In development builds the JDI classes are on the boot class
         * path so defining class loader for the JDI classes will
         * not find classes on the system class path.
         */
        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
        if (vmm.getClass().getClassLoader() == null) {
            System.out.println("JDI on bootclasspath - test skipped");
            return;
        }

        List connectors = vmm.allConnectors();

        // test that the connectors are ceated and are of the
        // correct type
        //
        AttachingConnector attacher =
            (AttachingConnector)find(connectors, "NullAttach");
        ListeningConnector listener =
            (ListeningConnector)find(connectors, "NullListen");

        if (attacher == null || listener == null) {
            throw new RuntimeException("One, or both, generated connectors are missing");
        }

        // check that the connectors have the required "address" argument
        //
        Connector.StringArgument arg;
        arg = (Connector.StringArgument)attacher.defaultArguments().get("address");
        arg = (Connector.StringArgument)listener.defaultArguments().get("address");
    }

}
