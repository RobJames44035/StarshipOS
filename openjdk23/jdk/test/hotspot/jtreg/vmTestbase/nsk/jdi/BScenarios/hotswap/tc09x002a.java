/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.hotswap;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc09x002a</code> is deugee's part of the tc09x002.
 */
public class tc09x002a {

    public final static String brkpMethodName = "method_C";
    public final static int brkpLineNumber1 = 67;
    public final static int brkpLineNumber2 = 70;
    public final static int checkLastLine = 67;
    public final static String fieldToCheckName = "fieldToCheck";
    public final static int INITIAL_VALUE = 0;
    public final static int CHANGED_VALUE = 1;

    private static Log log;
    private static int fieldToCheck = INITIAL_VALUE;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);

        tc09x002a obj = new tc09x002a();
        obj.method_A();

        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    public void method_A() {
        method_B();
    }

    public void method_B() {
        method_C();
    }

    public void method_C() {
        System.err.println("method_C:: line 1"); // brkpLineNumber1 // checkLastLine
        System.err.println("method_C:: line 2"); // this line will be removed
        System.err.println("method_C:: line 3");
        System.err.println("method_C:: line 4"); // brkpLineNumber2
    }
}
