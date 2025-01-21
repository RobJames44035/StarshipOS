/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdi/ReferenceType/instances/instances005.
 * VM Testbase keywords: [quick, jpda, jdi, feature_jdk6_jpda, vm6]
 * VM Testbase readme:
 * DESCRIPTION
 *     Test checks behavior of ReferenceType.instances() in case when there are many
 *     instances (several thousands) of corresponding ReferenceType in debuggee VM.
 *     Debugger forces debuggee VM create 100000 (this value can be changed through parameter -instanceCount) instances
 *     of 'nsk.share.jdi.TestClass1', obtains ReferenceType for 'nsk.share.jdi.TestClass1' and checks that ReferenceType.instances()
 *     returns collection with correct size and returned instances doesn't throw any exception when call following methods:
 *         referenceType()
 *         isCollected()
 *         uniqueID()
 *         hashCode()
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdi.ReferenceType.instances.instances005.instances005
 * @run main/othervm/native
 *      nsk.jdi.ReferenceType.instances.instances005.instances005
 *      -verbose
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package nsk.jdi.ReferenceType.instances.instances005;

import java.io.PrintStream;
import java.util.*;
import com.sun.jdi.*;

import nsk.share.Consts;
import nsk.share.ObjectInstancesManager;
import nsk.share.jdi.HeapwalkingDebuggee;
import nsk.share.jdi.HeapwalkingDebugger;
import nsk.share.jdi.TestClass1;

public class instances005 extends HeapwalkingDebugger {
    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    private int instanceCount = 100000;

    protected String[] doInit(String args[], PrintStream out) {
        args = super.doInit(args, out);

        ArrayList<String> standardArgs = new ArrayList<String>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-instanceCount") && (i < args.length - 1)) {
                instanceCount = Integer.parseInt(args[i + 1]);
                i++;
            } else
                standardArgs.add(args[i]);
        }

        return standardArgs.toArray(new String[] {});
    }

    public static int run(String argv[], PrintStream out) {
        return new instances005().runIt(argv, out);
    }

    protected String debuggeeClassName() {
        return HeapwalkingDebuggee.class.getName();
    }

    protected void doTest() {
        String className = TestClass1.class.getName();

        pipe.println(HeapwalkingDebuggee.COMMAND_CREATE_INSTANCES + ":" + className + ":" + instanceCount + ":" + 1
                + ":" + ObjectInstancesManager.STRONG_REFERENCE);

        checkDebugeeAnswer_instanceCounts(className, instanceCount);
        checkDebugeeAnswer_instances(className, instanceCount);

        ReferenceType referenceType = debuggee.classByName(className);
        List<ObjectReference> instances = referenceType.instances(0);

        vm.suspend();

        for (ObjectReference instance : instances) {
            try {
                if (!referenceType.equals(instance.referenceType())) {
                    setSuccess(false);
                    log.complain("Instance's ReferenceType " + instance.referenceType() + " doesn't equal "
                            + referenceType);
                }
                if (instance.isCollected()) {
                    setSuccess(false);
                    log.complain("isCollected() returns 'true' for " + instance);
                }
                instance.uniqueID();
                instance.hashCode();
            } catch (Throwable t) {
                setSuccess(false);
                t.printStackTrace(log.getOutStream());
                log.complain("Unexpected exception: " + t);
            }
        }

        vm.resume();
    }
}
