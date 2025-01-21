/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.hotswap;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc04x002a</code> is deugee's part of the tc04x002.
 */
public class tc04x002a {

    public final static String brkpMethodName = "method_B";
    public final static int brkpLineNumber = 63;
    public final static int checkLastLine = 63;
    public final static String fieldToCheckName = "fieldToCheck";
    public final static int INITIAL_VALUE = 0;
    public final static int CHANGED_VALUE = 1;

    private static Log log;
    private static int fieldToCheck = INITIAL_VALUE;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);

        tc04x002a obj = new tc04x002a();
        obj.method_A();

        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    public void method_A() {
//        fieldToCheck = CHANGED_VALUE;
        method_B();
    }

    public void method_B() {
        method_C(); // brkpLineNumber // checkLastLine
    }

    public void method_C() {
        System.err.println("method_C:: line 1");
        System.err.println("method_C:: line 2");
        System.err.println("method_C:: line 3");
        System.err.println("method_C:: line 4");
    }
}
