/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This is the redefined class for the redefineclasses001 JDI test.
 */

public class redefineclasses001b {

    static final String prefix = "**> debuggee: ";
    static Log log;

    redefineclasses001b(Log log) {
        this.log = log;
        log.display(prefix + "   This is the class to be redefined");
    }

    static int i1 = 0;

    static int bpline = 2;

    static void m2() {
        i1 = 1;
        i1 = 1;
    }

    static void m1() {
        log.display(prefix + "redefined method: before:   m2()");
        m2();
        log.display(prefix + "redefined method: after:    m2()");
        log.display(prefix + "redefined method: value of i1 == " + i1);
    }
}
