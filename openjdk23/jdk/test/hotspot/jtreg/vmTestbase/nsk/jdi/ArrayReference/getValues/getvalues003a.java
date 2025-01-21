/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ArrayReference.getValues;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class getvalues003a {

    static getvalues003aClassToCheck testedObj = new getvalues003aClassToCheck();

    public static void main (String argv[]) {

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);

        pipe.println("ready");

        String instruction = pipe.readln();

        if ( instruction.equals("quit") ) {
            log.display("DEBUGEE> \"quit\" signal recieved.");
            log.display("DEBUGEE> completed succesfully.");
            System.exit(getvalues003.TEST_FAILED + getvalues003.JCK_STATUS_BASE);
        }
        log.complain("DEBUGEE FAILURE> unexpected signal "
                         + "(no \"quit\") - " + instruction);
        log.complain("DEBUGEE FAILURE> TEST FAILED");
        System.exit(getvalues003.TEST_FAILED + getvalues003.JCK_STATUS_BASE);
    }
}

class getvalues003aClassToCheck {
    static      int[] staticIntArr      = {};
    static      Object[] staticObjArr   = {};

    public      int[] publicIntArrC;
    protected   int[] protecIntArrC;
    private     int[] privatIntArrC;

    public      Object[] publicObjArrC;
    protected   Object[] protecObjArrC;
    private     Object[] privatObjArrC;

    public getvalues003aClassToCheck() {
        publicIntArrC = createIntArray();
        protecIntArrC = createIntArray();
        privatIntArrC = createIntArray();

        publicObjArrC = createObjArray();
        protecObjArrC = createObjArray();
        privatObjArrC = createObjArray();
    }

    static private int[] createIntArray() {
        return new int[0];
    }

    static private Object[] createObjArray() {
        return new Object[0];
    }

}
