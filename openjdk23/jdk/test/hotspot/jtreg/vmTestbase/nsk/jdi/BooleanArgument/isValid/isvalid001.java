/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.BooleanArgument.isValid;

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
import com.sun.jdi.connect.LaunchingConnector;


/**
 * The test for the implementation of an object of the type <BR>
 * Connector.BooleanArgument. <BR>
 * <BR>
 * The test checks up that results of the method <BR>
 * <code>com.sun.jdi.connect.Connector.BooleanArgument.isValid()</code> <BR>
 * complies with its specification, that is, returns <BR>
 * true for the String values "true" and "false", and <BR>
 * false for another String value which, however, is not null, and <BR>
 * for the empty string. <BR>
 * <BR>
 * In case of a wrong boolean value returned, <BR>
 * the test produces the return value 97 and <BR>
 * a corresponding error message. <BR>
 * Otherwise, the test is passed and produces <BR>
 * the return value 95 and no message. <BR>
 */


public class isvalid001 {

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
                        "jdi.Connector.BooleanArgument.isValid\n" ;
//
        String sErr2 =  "ERROR\n" +
                        "Method tested: " +
                        "jdi.Connector.BooleanArgument.isValue()\n" ;

        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();

        List connectorsList = vmm.allConnectors();
        Iterator connectorsListIterator = connectorsList.iterator();
//
        Connector.BooleanArgument argument = null;

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
                            argument = (Connector.BooleanArgument)
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
                    "no Connecter with needed Argument found\n");
                return exitCode0;
            }
        }

        if (!argument.isValid("true")) {
            exitCode = exitCode2;
            out.println(sErr2 +
                      "check: isValid('true')\n" +
                      "error: returned value != true\n");
        }

        if (!argument.isValid("false")) {
            exitCode = exitCode2;
            out.println(sErr2 +
                      "check: isValid('false')\n" +
                      "error: returned value != true\n");
        }

        if (argument.isValid("fals")) {
            exitCode = exitCode2;
            out.println(sErr2 +
                      "check: isValid('fals')\n" +
                      "error: returned value == true\n");
        }

        if (argument.isValid("")) {
            exitCode = exitCode2;
            out.println(sErr2 +
                      "check: isValid(<empty_string>)\n" +
                      "error: returned value == true\n");
        }

        if (exitCode != exitCode0)
            out.println("TEST FAILED");

        return exitCode;
    }
}
