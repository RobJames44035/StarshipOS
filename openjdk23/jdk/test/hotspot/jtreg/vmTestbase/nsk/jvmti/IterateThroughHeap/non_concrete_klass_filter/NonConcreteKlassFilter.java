/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package nsk.jvmti.IterateThroughHeap.non_concrete_klass_filter;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class NonConcreteKlassFilter extends DebugeeClass {

    static {
        loadLibrary("NonConcreteKlassFilter");
    }

    public static void main(String args[]) {
        String[] argv = JVMTITest.commonInit(args);
        System.exit(new NonConcreteKlassFilter().runTest(argv,System.out) + Consts.JCK_STATUS_BASE);
    }

    protected Log log = null;
    protected ArgumentHandler argHandler = null;
    protected int status = Consts.TEST_PASSED;

    static Object objects[] = new Object[] { new InterfaceImplementation(),
                                             new AbstractClassImplementation() };

    public int runTest(String args[], PrintStream out) {
        argHandler = new ArgumentHandler(args);
        log = new Log(out, argHandler);
        log.display("Verifying JVMTI_ABORT.");
        status = checkStatus(status);
        return status;
    }

}

interface Interface {
    static long field1 = 0xF1E1D01L;
}

abstract class AbstractClass {
    static long field1 = 0xF1E1D02L;
    long field2 = 0xDEADF1E1D01L;
}

class InterfaceImplementation implements Interface {

}

class AbstractClassImplementation extends AbstractClass {

}
