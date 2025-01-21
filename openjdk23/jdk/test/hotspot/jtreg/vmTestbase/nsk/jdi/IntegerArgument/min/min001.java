/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.IntegerArgument.min;

import java.io.PrintStream;
import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Connector.IntegerArgument;


/**
 * The test for the implementation of an object of the type <BR>
 * Connector_IntegerArgument. <BR>
 * <BR>
 * The test checks up that results of the method                    <BR>
 * <code>com.sun.jdi.connect.Connector.IntegerArgument.min()</code> <BR>
 * complies with the following requirement:                         <BR>
 *      -2147483648 <= min() <= 2147483647                          <BR>
 * <BR>
 * In case of wrong values,                     <BR>
 * the test produces the return value 97 and    <BR>
 * a corresponding error message.               <BR>
 * Otherwise, the test is passed and produces   <BR>
 * the return value 95 and no message.          <BR>
 */


public class min001 {

    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {

        int exitCode  = 0;
        int exitCode0 = 0;
        int exitCode2 = 2;
//
        String sErr1 =  "WARNING\n" +
                        "Method tested: " +
                        "jdi.Connector.IntegerArgument.min\n" ;
//
        String sErr2 =  "ERROR\n" +
                        "Method tested: " +
                        "jdi.Connector.IntegerArgument.min\n" ;

        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();

        List connectorsList = vmm.allConnectors();
        Iterator connectorsListIterator = connectorsList.iterator();
//
        Connector.IntegerArgument argument = null;

        for ( ; ; ) {
            try {
                Connector connector =
                (Connector) connectorsListIterator.next();

                Map defaultArguments = connector.defaultArguments();
                Set keyset     = defaultArguments.keySet();
                int keysetSize = defaultArguments.size();
                Iterator  keysetIterator = keyset.iterator();

                for ( ; ; ) {
                    try {
                        String argName = (String) keysetIterator.next();

                        try {
//
                            argument = (Connector.IntegerArgument)
                                       defaultArguments.get(argName);
                            break ;
                        } catch ( ClassCastException e) {
                        }
                    } catch ( NoSuchElementException e) {
                        break ;
                    }
                }
                if (argument != null) {
                    break ;
                }
            } catch ( NoSuchElementException e) {
                out.println(sErr1 +
                    "no Connector with IntegerArgument found\n");
                return exitCode0;
            }
        }

        int iMin = argument.min();

        if (iMin < -2147483648) {
            exitCode = exitCode2;
            out.println(sErr2 +
                        "check: iMin < -2147483648\n");
        }

        if (iMin > 2147483647) {
            exitCode = exitCode2;
            out.println(sErr2 +
                        "check: iMin > 2147483647\n");
        }

        if (exitCode != exitCode0)
            out.println("TEST FAILED");

        return exitCode;
    }
}
