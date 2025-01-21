/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ArrayReference.getValues_ii;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class getvaluesii005a {

    static getvaluesii005aClassToCheck testedObj = new getvaluesii005aClassToCheck();


    public static void main (String argv[]) {

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instruction = pipe.readln();

        if ( instruction.equals("quit") ) {
            log.display("DEBUGEE> \"quit\" signal received.");
            log.display("DEBUGEE> completed succesfully.");
            System.exit(getvaluesii005.TEST_PASSED + getvaluesii005.JCK_STATUS_BASE);
        }
        log.complain("DEBUGEE FAILURE> unexpected signal "
                         + "(no \"quit\") - " + instruction);
        log.complain("DEBUGEE FAILURE> TEST FAILED");
        System.exit(getvaluesii005.TEST_FAILED + getvaluesii005.JCK_STATUS_BASE);
    }
}

class getvaluesii005aClassToCheck {
    static      int[] staticIntArr      = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    static      Object[] staticObjArr   = {null, null, null, null, null, null, null, null, null, null};

    static      int[][] staticIntArr2  = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}};

    static      int[][] staticIntArr0  = {{},{},{},{},{},{},{}};

    public      int[] publicIntArr0;
    protected   int[] protecIntArr0;
    private     int[] privatIntArr0;

    public      int[] publicIntArrC;
    protected   int[] protecIntArrC;
    private     int[] privatIntArrC;

    public      Object[] publicObjArr0;
    protected   Object[] protecObjArr0;
    private     Object[] privatObjArr0;

    public      Object[] publicObjArrC;
    protected   Object[] protecObjArrC;
    private     Object[] privatObjArrC;

    public getvaluesii005aClassToCheck() {
        publicIntArr0 = createIntArray(0);
        protecIntArr0 = createIntArray(0);
        privatIntArr0 = createIntArray(0);

        publicIntArrC = createIntArray(10);
        protecIntArrC = createIntArray(10);
        privatIntArrC = createIntArray(10);

        publicObjArr0 = createObjArray(0);
        protecObjArr0 = createObjArray(0);
        privatObjArr0 = createObjArray(0);

        publicObjArrC = createObjArray(10);
        protecObjArrC = createObjArray(10);
        privatObjArrC = createObjArray(10);
    }

    static private int[] createIntArray(int length) {
        int[] array = new int[length];
        for ( int i = 0; i < length; i++ ) array[i] = i;
        return array;
    }

    static private Object[] createObjArray(int length) {
        Object[] array = new Object[length];
        return array;
    }
}
