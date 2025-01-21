/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/aod/VirtualMachine/VirtualMachine05.
 * VM Testbase keywords: [feature_282, jdk]
 * VM Testbase readme:
 * Description :
 *     Test checks work of Attach API (com.sun.tools.attach).
 *     Test is based on the nsk.share.aod framework.
 *     This test checks that method VirtualMachine.list() returns
 *     VirtualMachineDescriptors for 2 VMs started by this test.
 *
 * @library /vmTestbase /test/hotspot/jtreg/vmTestbase
 *          /test/lib
 * @build nsk.share.aod.DummyTargetApplication
 * @run main/othervm
 *      -XX:+UsePerfData
 *      nsk.aod.VirtualMachine.VirtualMachine05.VirtualMachine05
 *      -jdk ${test.jdk}
 *      -javaOpts="-XX:+UsePerfData ${test.vm.opts} ${test.java.opts}"
 *      -target nsk.share.aod.DummyTargetApplication
 */

package nsk.aod.VirtualMachine.VirtualMachine05;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import nsk.share.aod.AODTestRunner;
import nsk.share.test.TestUtils;

import java.util.List;

/*
 * Test checks method VirtualMachine.list()
 * (test checks that collection returned by VirtualMachine.list() contains current VM
 * and another VM started by this test)
 */
public class VirtualMachine05 extends AODTestRunner {

    public VirtualMachine05(String[] args) {
        super(args);
    }

    public void doTestActions(String targetVMId) {
        String currentVMId = getCurrentVMId();

        log.display("Checking VirtualMachine.list()");
        checkList(currentVMId, targetVMId);
    }

    private void checkList(String currentVMId, String targetVMId) {
        VirtualMachineDescriptor currentVM = null;
        VirtualMachineDescriptor targetVM = null;

        for (VirtualMachineDescriptor vmDescriptor : VirtualMachine.list()) {
            log.display("VirtualMachineDescriptor: " + vmDescriptor);

            if (vmDescriptor.id().equals(currentVMId)) {
                currentVM = vmDescriptor;
            } else if (vmDescriptor.id().equals(targetVMId)) {
                targetVM = vmDescriptor;
            }
        }

        TestUtils.assertNotNull(currentVM, "VirtualMachine.list() didn't return descriptor for the current VM");

        TestUtils.assertNotNull(targetVM, "VirtualMachine.list() didn't return descriptor for VM with id '" +
                targetVMId + "'");
    }

    public static void main(String[] args) {
        new VirtualMachine05(args).runTest();
    }
}
