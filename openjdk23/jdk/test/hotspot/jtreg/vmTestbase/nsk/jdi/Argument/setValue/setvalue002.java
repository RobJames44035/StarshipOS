/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.Argument.setValue;

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
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.connect.LaunchingConnector;


/**
 * The test for the implementation of an object of the type <BR>
 * Connector.Argument. <BR>
 *
 * The test checks up that results of the method                  <BR>
 * <code>com.sun.jdi.connect.Connector.Argument.setValue()</code> <BR>
 * complies with its specification, when a string value to be set <BR>
 * is the empty or non-empty string.                              <BR>
 * <BR>
 * In case of the method Arguemnt.value() returns the value,      <BR>
 * not equal to one has been set,             <BR>
 * the test produces the return value 97 and  <BR>
 * a corresponding error message.             <BR>
 * Otherwise, the test is passed and produces <BR>
 * the return value 95 and no message.        <BR>
 */


public class setvalue002 {

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
                        "jdi.Connector.IntegerArgument.max\n" ;
//
        String sErr2 =  "ERROR\n" +
                        "Method tested: " +
                        "jdi.Connector.Argument.setValue()\n" ;

        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();

        List connectorsList = vmm.allConnectors();
        Iterator connectorsListIterator = connectorsList.iterator();
//
        Connector.Argument argument = null;

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
                            argument = (Connector.Argument)
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
                    "no Connecter with Argument found\n");
                return exitCode0;
            }
        }

        argument.setValue("");
        if (argument.value() != "") {
            exitCode = exitCode2;
            out.println(sErr2 +
                        "check: setValue('');\n" +
                        "result: argument.value != ''\n");
        }

        argument.setValue("a");
        if (argument.value() != "a") {
            exitCode = exitCode2;
            out.println(sErr2 +
                        "check: argument.setValue('a');\n" +
                        "result: argument.value != 'a'\n");
        }

        if (exitCode != exitCode0)
            out.println("TEST FAILED");

        return exitCode;
    }
}
