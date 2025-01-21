/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.SelectedArgument.isValid;

import java.io.PrintStream;
import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.connect.Connector;


/**
 * The test for the implementation of an object of the type <BR>
 * Connector.SelectedArgument. <BR>
 *
 * The test checks up that results of the method <BR>
 * <code>com.sun.jdi.connect.Connector.SelectedArgument.isValid()</code> <BR>
 * complies with its specification in the following case:  <BR>
 * - for each String in List of choices, isValid returns true; <BR>
 *  <BR>
 * In case of any check returns a wrong value - false, <BR>
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
                        "jdi.Connector.SelectedArgument.isValid\n" ;
//
        String sErr2 =  "ERROR\n" +
                        "Method tested: " +
                        "jdi.Connector.SelectedArgument.isValid\n" ;

        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();

        List connectorsList = vmm.allConnectors();
        Iterator connectorsListIterator = connectorsList.iterator();
//
        Connector.SelectedArgument argument = null;

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
                            argument = (Connector.SelectedArgument)
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
                    "no Connector with SelectedArgument found\n");
                return exitCode0;
            }
        }

        List listofChoices = argument.choices();

        if (listofChoices.isEmpty()) {
            exitCode = exitCode2;
            out.println(sErr2 +
                      "error: returned List of String is empty\n");
        } else {

            Iterator listIterator = listofChoices.iterator();

            for ( ; ; ) {
                 try {
                      String choiceString = (String) listIterator.next();

                      if (!argument.isValid(choiceString)) {
                          exitCode = exitCode2;
                          out.println(sErr2 +
                                    "check: isValid(arg)\n" +
                                    "error: List contains invalid String\n");
                                break ;
                      }

                  } catch ( ClassCastException e1 ) {
                      exitCode = exitCode2;
                      out.println(sErr2 +
                                "check: isValid(arg)\n" +
                                "error: List contains non-String\n");
                      break ;
                  } catch ( NoSuchElementException e2) {
                      break ;
                  }
            }
        }

        if (exitCode != exitCode0)
            out.println("TEST FAILED");

        return exitCode;
    }
}
