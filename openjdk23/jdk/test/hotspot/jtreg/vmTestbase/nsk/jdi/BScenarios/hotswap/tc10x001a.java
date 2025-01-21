/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.hotswap;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc10x001a</code> is deugee's part of the tc10x001.
 */
public class tc10x001a {

    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 55;
    public final static int checkLastLine = 55;
    public final static int INITIAL_INT_VALUE = 42;
    public final static boolean INITIAL_BOOL_VALUE = true;

    private static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);

        int i = INITIAL_INT_VALUE;
//      ^^^ will be changed
        tc10x001a obj = new tc10x001a();
        obj.method_A();

        System.err.println("i = " + i); // brkpLineNumber // checkLastLine
        pipe.println("@" + i);
        String instr = pipe.readln(); // Wait for debugger "quit"
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
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
