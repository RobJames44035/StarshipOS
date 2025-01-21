/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach034;

import nsk.share.aod.AbstractJarAgent;
import java.lang.instrument.Instrumentation;

public class AgentParent extends AbstractJarAgent {

    protected void agentActions() {
        display("Agent is running (agent class: '" + this.getClass().getSimpleName() + "')");
    }

    public static void agentmain(String options, Instrumentation inst) {
        new AgentParent().runJarAgent(options, inst);
    }
}
