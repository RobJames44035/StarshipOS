/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ArrayReference.getValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class getvalue003a {

    public final static String[] NON_INIT_FIELDS = {"staticIntArr2C",
                                                      "staticIntArrC",
                                                      "staticObjArrC"};

    static getvalue003aClassToCheck testedObj = new getvalue003aClassToCheck();


    public static void main (String argv[]) {

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instruction = pipe.readln();

        if ( instruction.equals("quit") ) {
            log.display("DEBUGEE> \"quit\" signal recieved.");
            log.display("DEBUGEE> completed succesfully.");
            System.exit(getvalue003.TEST_PASSED + getvalue003.JCK_STATUS_BASE);
        }
        log.complain("DEBUGEE FAILURE> unexpected signal "
                         + "(no \"quit\") - " + instruction);
        log.complain("DEBUGEE FAILURE> TEST FAILED");
        System.exit(getvalue003.TEST_FAILED + getvalue003.JCK_STATUS_BASE);
    }
}

class getvalue003aClassToCheck {
    static      int[] staticIntArr      = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    static      Object[] staticObjArr   = {null, null, null, null, null, null, null, null, null, null};

    static      int[][] staticIntArr2  = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}};

    static      int[][] staticIntArr2C;  // not initialized

    static      int[] staticIntArrC; // not initialized
    public      int[] publicIntArrC;
    protected   int[] protecIntArrC;
    private     int[] privatIntArrC;

    static      Object[] staticObjArrC; // not initialized
    public      Object[] publicObjArrC;
    protected   Object[] protecObjArrC;
    private     Object[] privatObjArrC;

    public getvalue003aClassToCheck() {
        publicIntArrC = createIntArray();
        protecIntArrC = createIntArray();
        privatIntArrC = createIntArray();

        publicObjArrC = createObjArray();
        protecObjArrC = createObjArray();
        privatObjArrC = createObjArray();
    }

    static private int[] createIntArray() {
        int[] array = new int[10];
        for ( int i = 0; i < 10; i++ ) array[i] = i;
        return array;
    }

    static private Object[] createObjArray() {
        Object[] array = new Object[10];
        return array;
    }

}
