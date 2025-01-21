/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.Connector.description;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import java.io.PrintStream;
import java.util.*;

/**
 * Test for the control of
 *
 *      Interface:      com.sun.jdi.connect.Connector
 *      Method:         public java.lang.String description()
 *      Assertion:      "Returns a human-readable description of this
 *                       connector of this connector and its purpose."
 */

public class description001 {
    private static Log log;

    public static void main( String argv[] ) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();

        List acl = vmm.allConnectors();
        if (acl.size() > 0) {
            log.display("Number of all known JDI connectors: " + acl.size());
        } else {
            log.complain("FAILURE: no JDI connectors found!");
            return 2;
        }

        Iterator aci = acl.iterator();
        for (int i = 1; aci.hasNext(); i++) {
            Connector c = (Connector) aci.next();
            String cdsc = c.description();

            if (cdsc == null) {
                log.complain("FAILURE: connector description is null.");
                log.complain("         Name: " + c.name());
                log.complain("         Transport: " + c.transport().name());
                return 2;
            }

            if (cdsc.length() == 0) {
                log.complain("FAILURE: connector with empty-description "
                           + "is found.");
                log.complain("         Name: " + c.name());
                log.complain("         Transport: " + c.transport().name());
                return 2;
            }
            log.display("Next (" + i + ") connector's description is: "
                        + cdsc);
        };
        log.display("Test PASSED!");
        return 0;
    }
}
