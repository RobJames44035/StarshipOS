/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.Connector.defaultArguments;

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
 *      Method:         public java.util.Map defaultArguments()
 *      Assertion:      "The keys of the returned map are string
 *                       argument names."
 */

public class defaultArguments002 {

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
            Map cdfltArgmnts = c.defaultArguments();
            if (cdfltArgmnts.size() < 1) {
                log.complain("FAILURE: connector with empty list of "
                           + "default arguments is found!:");
                log.complain("         Name: " + c.name());
                return 2;
            }

            Set ks = cdfltArgmnts.keySet();
            if (ks.isEmpty()) {
                log.complain("FAILURE: empty argument name set is found "
                           + "for " + c.name() + " connector!");
                return 2;
            }

            log.display(c.name() + "connector arguments: ");

            Iterator argi = ks.iterator();
            for (int j = 1; argi.hasNext(); j++) {
                Object ob = argi.next();
                if (!(ob instanceof String)) {
                    log.complain("FAILURE: " + j + "-argument key must be "
                               + "of String type!");
                    return 2;
                }
                String argkey = (String) ob;

                if (argkey == null) {
                    log.complain("FAILURE: argument name is null "
                               + "for " + c.name() + " connector.");
                    return 2;
                }

                if (argkey.length() == 0) {
                    log.complain("FAILURE: empty argument name is found "
                               + "for " + c.name() + " connector!");
                    return 2;
                }

                log.display("Next (" + j + ") argument's name is: " + argkey);
            };
        };
        log.display("Test PASSED!");
        return 0;
    }
}
