/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.BooleanArgument.stringValueOf;

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
import com.sun.jdi.connect.Connector.BooleanArgument;


/**
 * The test for the implementation of an object of the type <BR>
 * Connector.BooleanArgument. <BR>
 * <BR>
 * The test checks up that results of the method <BR>
 * Connector.BooleanArgument.stringValueOf(boolean value)        <BR>
 * in the package com.sun.jdi.connect                            <BR>
 * complies with the following statement in its specification:   <BR>
 * "Return the string representation of the value parameter."    <BR>
 * <BR>
 * In case of the check results in a wrong value, <BR>
 * the test produces the return value 97 and      <BR>
 * a corresponding error message.                 <BR>
 * Otherwise, the test is passed and produces     <BR>
 * the return value 95 and no message.            <BR>
 */


public class stringvalueof001 {
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
                        "jdi.Connector.BooleanArgument.stringValueOf\n" ;
//
        String sErr2 =  "ERROR\n" +
                        "Method tested: " +
                        "jdi.Connector.BooleanArgument.stringValueof\n" ;

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
                    "no Connector with BooleanArgument found\n");
                return exitCode0;
            }
        }

        String sTrue  = argument.stringValueOf(true);
        String sFalse = argument.stringValueOf(false);

        if (sTrue.equalsIgnoreCase(sFalse)) {
            exitCode = 2;
            out.println(sErr2 +
                      "check: stringValueOf(true) != stringValueOf(false)\n" +
                      "error: strings are equal\n");
        }

        if (sTrue == null) {
            exitCode = 2;
            out.println(sErr2 +
                      "check: stringValueOf(true) = 'true'\n" +
                      "error: a returned value = null\n");
        }

        if (sFalse == null) {
            exitCode = 2;
            out.println(sErr2 +
                      "check: stringValueOf(false) = 'false'\n" +
                      "error: a returned value = null\n");
        }

        if (exitCode != exitCode0)
            out.println("TEST FAILED");

        return exitCode;
    }
}
