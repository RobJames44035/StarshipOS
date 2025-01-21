/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 * @test
 * @bug 4245011
 * @summary Test launcher command line construction
 * @author Gordon Hirsch
 *
 * @run build TestScaffold VMConnection
 * @run compile -g HelloWorld.java
 * @run build LaunchCommandLine
 *
 * @run driver LaunchCommandLine
 */
import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import java.util.List;

public class LaunchCommandLine extends TestScaffold {
    public static void main(String args[]) throws Exception {
        new LaunchCommandLine(args).startTests();
    }

    LaunchCommandLine(String args[]) {
        // args are set in code below
        super(args);
    }

    protected void runTests() throws Exception {
        String[] args = new String[2];
        args[0] = "-connect";
        args[1] = "com.sun.jdi.CommandLineLaunch:main=HelloWorld a b c \"a b c\"";
        testArgs(args);
        System.out.println("com.sun.jdi.CommandLineLaunch: passed");

        // Add test for RawCommandLineLauncher?
    }

    void testArgs(String[] args) throws Exception {
        connect(args);
        waitForVMStart();

        /*
         * Get to a point where the command line args are accessible.
         */
        BreakpointEvent bp = resumeTo("HelloWorld", "main", "([Ljava/lang/String;)V");

        StackFrame frame = bp.thread().frame(0);
        LocalVariable argsVariable = frame.visibleVariableByName("args");
        ArrayReference argsArray = (ArrayReference)frame.getValue(argsVariable);

        List argValues = argsArray.getValues();

        if (argValues.size() != 4) {
            throw new Exception("Wrong number of command line arguments: " + argValues.size());
        }

        String string = ((StringReference)argValues.get(0)).value();
        if (!string.equals("a")) {
            throw new Exception("Bad command line argument value: " + string);
        }
        string = ((StringReference)argValues.get(1)).value();
        if (!string.equals("b")) {
            throw new Exception("Bad command line argument value: " + string);
        }
        string = ((StringReference)argValues.get(2)).value();
        if (!string.equals("c")) {
            throw new Exception("Bad command line argument value: " + string);
        }
        string = ((StringReference)argValues.get(3)).value();
        if (!string.equals("a b c")) {
            throw new Exception("Bad command line argument value: " + string);
        }

        // Allow application to complete
        resumeToVMDisconnect();
    }

}
