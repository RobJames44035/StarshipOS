/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.Method.isObsolete;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE
/**
 * This is the redefined class for the isobsolete001 JDI test.
 */
public class isobsolete001b {

    static final String prefix = "**> debuggee: ";
    static Log log;

    isobsolete001b(Log log) {
        this.log = log;
        log.display(prefix + "   This is the class to be redefined");
    }

    static int i1 = 0;
    static int i2 = 0;

    static void m2() {
        i1 = 1;
        i2 = 1; // isobsolete001.brkpLineNumber
    }

    static void m1() {
        log.display(prefix + "redefined method: before:   m2()");
        m2();
        log.display(prefix + "redefined method: after:    m2()");
        log.display(prefix + "redefined method: value of i1 == " + i1);
        log.display(prefix + "redefined method: value of i2 == " + i2);
    }
}
