/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package nsk.jvmti.IterateThroughHeap.concrete_klass_filter;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class ConcreteKlassFilter extends DebugeeClass {

    static {
        loadLibrary("ConcreteKlassFilter");
    }

    public static void main(String args[]) {
        String[] argv = JVMTITest.commonInit(args);
        System.exit(new ConcreteKlassFilter().runTest(argv,System.out) + Consts.JCK_STATUS_BASE);
    }

    protected Log log = null;
    protected ArgumentHandler argHandler = null;
    protected int status = Consts.TEST_PASSED;

    static protected Object testObject;

    public int runTest(String args[], PrintStream out) {
        argHandler = new ArgumentHandler(args);
        log = new Log(out, argHandler);
        testObject = new TestClass();
        log.display("Verifying reachable objects.");
        status = checkStatus(status);
        testObject = null;
        log.display("Verifying unreachable objects.");
        status = checkStatus(status);
        return status;
    }

}

interface Interface {
    long interfaceLongField = 0xDEADF1E1D01L;
}

abstract class AbstractClass {
    long acLongField = 0xDEADF1E1D02L;
    static long acStaticLongField = 0xDEADF1E1D03L;
}

class TestClass /*extends AbstractClass implements Interface*/ {
    long classField = 0xC1A55F1E1DL;
    static long classStaticField = 0xDEADF1E1D04L;
}

class SubClass extends TestClass {
    long subClassField = 0xDEADF1E1D05L;
    static long subClassStaticField = 0xDEADF1E1D06L;
}
