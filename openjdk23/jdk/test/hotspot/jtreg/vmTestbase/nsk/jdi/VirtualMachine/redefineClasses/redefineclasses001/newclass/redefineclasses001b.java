/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This is the redefining class for the redefineclasses001 JDI test.
 */

public class redefineclasses001b {

    static final String prefix = "**> debuggee: ";
    static Log log;

    redefineclasses001b(Log log) {
        this.log = log;
        log.display(prefix + "   This is the class to be redefine");
    }

    static int i1 = 0;

    static int bpline = 2;

    static void m2() {
        i1 = 2;
        i1 = 2;
    }

    static void m1() {
        log.display(prefix + "redefining method: before:   m2()");
        m2();
        log.display(prefix + "redefining method: after:    m2()");
        log.display(prefix + "redefining method: value of i1 == " + i1);
    }

}
