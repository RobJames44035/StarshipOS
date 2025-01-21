/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.Argument.label;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import java.io.*;
import javax.naming.directory.Attribute;
import java.util.*;

/**
 * Test for the control of
 *
 *      Interface:      com.sun.jdi.connect.Connector.Argument
 *      Method:         public java.lang.String label()
 *      Assertion:      "Returns a short human-readable label for
 *                       this argument."
 */

public class label001 {
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
            Set ks = cdfltArgmnts.keySet();

            if (ks.isEmpty()) {
                log.complain("FAILURE: empty default arguments set is found "
                           + "for " + c.name() + " connector!");
                return 2;
            }

            log.display("Looking over " + c.name() + " connector arguments: ");

            Iterator argi = ks.iterator();
            for (int j = 1; argi.hasNext(); j++) {
                String argkey = (String) argi.next();
                Connector.Argument argval =
                    (Connector.Argument)cdfltArgmnts.get((Object) argkey);
                String lbl = argval.label();

                if (lbl == null) {
                    log.complain("FAILURE: argument label is null "
                               + "for " + argkey + " argument of "
                               + c.name() + " connector!");
                    return 2;
                }

                if (lbl.length() < 1) {
                    log.complain("FAILURE: empty argument label is found "
                               + "for " + argkey + " argument of "
                               + c.name() + " connector!");
                    return 2;
                }

                log.display("Next (" + j + ") argument's label is: " + lbl);
            };
        };
        log.display("Test PASSED!");
        return 0;
    }
}
