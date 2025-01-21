/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/aod/VirtualMachine/VirtualMachine09.
 * VM Testbase keywords: [feature_282, jdk]
 * VM Testbase readme:
 * Description :
 *     Test checks work of Attach API (com.sun.tools.attach).
 *     Test is based on the nsk.share.aod framework.
 *     This test checks method VirtualMachine.loadAgentLibrary(String agent).
 *     Test checks following spec clause: "Agent_OnAttach function is invoked even if the agent library was loaded
 *     prior to invoking this method". In this test the same agent library first loaded via VM command line
 *     option 'agentlib:', then it is loaded using method 'System.loadLibrary' and than dynamically attached.
 *
 * @library /vmTestbase /test/hotspot/jtreg/vmTestbase
 *          /test/lib
 * @build nsk.aod.VirtualMachine.VirtualMachine09.VM09Target
 * @run main/othervm/native
 *      -XX:+UsePerfData
 *      nsk.aod.VirtualMachine.VirtualMachine09.VirtualMachine09
 *      -jdk ${test.jdk}
 *      -javaOpts="-agentlib:VirtualMachine09agent00 -XX:+UsePerfData ${test.vm.opts} ${test.java.opts}"
 *      -target nsk.aod.VirtualMachine.VirtualMachine09.VM09Target
 *      -na VirtualMachine09agent00
 *      -testedMethod loadAgentLibrary
 */

package nsk.aod.VirtualMachine.VirtualMachine09;

import com.sun.tools.attach.VirtualMachine;
import nsk.aod.VirtualMachine.VirtualMachine07.VirtualMachine07;
import nsk.share.TestBug;
import nsk.share.aod.AgentInformation;

import java.util.List;

/*
 * Test checks methods VirtualMachine.loadAgentLib and VirtualMachineloadAgentPath.
 *
 * Test checks following spec clause: "Agent_OnAttach function is invoked even if the agent library was loaded
 * prior to invoking this method"
 */
public class VirtualMachine09 extends VirtualMachine07 {

    public VirtualMachine09(String[] args) {
        super(args);
    }

    public void doTestActions(String targetVMId) throws Throwable {
        // check that all required parameters were passed to the test
        List<AgentInformation> agents = argParser.getAgents();
        if (agents.size() != 1) {
            throw new TestBug("Test requires 1 agent, actually " + agents.size() + " were specified");
        }
        for (AgentInformation agent : agents) {
            if (agent.jarAgent) {
                throw new TestBug("Non native agent was specified");
            }
        }

        VirtualMachine vm = VirtualMachine.attach(targetVMId);

        try {
            AgentInformation agent;
            agent = agents.get(0);
            loadAgent(vm, agent.pathToAgent, agent.agentOptions);
        } finally {
            vm.detach();
        }
    }

    public static void main(String[] args) {
        new VirtualMachine09(args).runTest();
    }
}
