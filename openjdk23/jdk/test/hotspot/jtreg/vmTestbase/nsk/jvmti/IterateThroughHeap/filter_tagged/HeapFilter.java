/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package nsk.jvmti.IterateThroughHeap.filter_tagged;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class HeapFilter extends DebugeeClass {

    static {
        loadLibrary("HeapFilter");
    }

    public static void main(String args[]) {
        String[] argv = JVMTITest.commonInit(args);
        System.exit(new HeapFilter().runTest(argv,System.out) + Consts.JCK_STATUS_BASE);
    }

    protected Log log = null;
    protected ArgumentHandler argHandler = null;
    protected int status = Consts.TEST_PASSED;

    static protected Object testObjects[];

    public int runTest(String args[], PrintStream out) {
        argHandler = new ArgumentHandler(args);
        log = new Log(out, argHandler);
        testObjects = new Object[]{new TaggedClass(),
                                   new UntaggedClass()};
        log.display("Verifying reachable objects.");
        status = checkStatus(status);
        testObjects = null;
        log.display("Verifying unreachable objects.");
        status = checkStatus(status);
        return status;
    }

}

class TaggedClass {
    static int field1 = 0xC0DE01;
    int field2 = 0xC0DE02;
    String stringField = "I'm a tagged string";
    int arrayField[] = new int[]{field1,field1+1};
}

class UntaggedClass {
    static int field1 = 0xC0DE03;
    int field2 = 0xC0DE04;
    String stringField = "I'm an untagged string";
    int arrayField[] = new int[]{field1,field1+1};
}
