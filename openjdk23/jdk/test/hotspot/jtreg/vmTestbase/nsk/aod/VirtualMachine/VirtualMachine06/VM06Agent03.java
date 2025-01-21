/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package nsk.aod.VirtualMachine.VirtualMachine06;

import nsk.share.aod.TargetApplicationWaitingAgents;

import java.lang.instrument.Instrumentation;

public class VM06Agent03 {
    public static void agentmain(String options, Instrumentation inst) {
        TargetApplicationWaitingAgents.agentLoaded(VM06Agent03.class.getName());
        TargetApplicationWaitingAgents.agentFinished(VM06Agent03.class.getName(), true);

        throw new RuntimeException("Test exception");
    }
}
