/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.hotswap;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc10x002a</code> is deugee's part of the tc10x002.
 */
public class tc10x002a {

    public final static String brkpMethodName = "runIt";
    public final static int brkpLineNumber1 = 61;
    public final static int brkpLineNumber2 = 58;
    public final static int checkLastLine1 = 61;
    public final static int checkLastLine2 = 58;
    public final static int INITIAL_INT_VALUE = 42;
    public final static boolean INITIAL_BOOL_VALUE = true;

    private static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);

        runIt();
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    public static void runIt() {
        int i = INITIAL_INT_VALUE;
//      ^^^     ^^^^^^^^^^^^^^^^^ will be redefined
        tc10x002a obj = new tc10x002a(); // brkpLineNumber2 // checkLastLine2
        obj.method_A();

        System.err.println("i = " + i); // brkpLineNumber1 // checkLastLine1
    }

    public void method_A() {
        method_B();
    }

    public void method_B() {
        method_C();
    }

    public void method_C() {
        System.err.println("method_C:: line 1");
        System.err.println("method_C:: line 2");
        System.err.println("method_C:: line 3");
        System.err.println("method_C:: line 4");
    }
}
