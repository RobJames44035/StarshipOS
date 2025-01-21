/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.share.jdi;

import nsk.share.jpda.*;

public class AbstractJDIDebuggee extends AbstractDebuggeeTest {

    /*
     * Create argument handler handling options specific for JDI tests
     */
    protected DebugeeArgumentHandler createArgumentHandler(String args[]) {
        return new ArgumentHandler(args);
    }

    public static void main(String args[]) {
        AbstractJDIDebuggee debuggee = new AbstractJDIDebuggee();
        debuggee.init(args);
        debuggee.doTest();
    }

}
