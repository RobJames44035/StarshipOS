/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jdi.ReferenceType.instances.instances002;

import nsk.share.jdi.*;

public class instances002a extends HeapwalkingDebuggee {
    public final static String MAIN_THREAD_NAME = "instances002a_MainThread";

    protected String[] doInit(String args[]) {
        Thread.currentThread().setName(MAIN_THREAD_NAME);

        return args;
    }

    public static void main(String args[]) {
        new instances002a().doTest(args);
    }
}
