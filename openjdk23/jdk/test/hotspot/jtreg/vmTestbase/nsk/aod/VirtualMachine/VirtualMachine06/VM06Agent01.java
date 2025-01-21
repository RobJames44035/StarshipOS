/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package nsk.aod.VirtualMachine.VirtualMachine06;

import nsk.share.aod.TargetApplicationWaitingAgents;

import java.lang.instrument.Instrumentation;

public class VM06Agent01 {
    public static void agentmain(String options, Instrumentation inst) {
        boolean success = true;

        TargetApplicationWaitingAgents.agentLoaded(VM06Agent01.class.getName());
        try {
            System.out.println("Agent options: " + options);
            if (options != null) {
                success = false;
                System.out.println("ERROR: unexpected non-null options");
            }
        } catch (Throwable t) {
            success = false;
            System.out.println("Unexpected exception: " + t);
        } finally {
            TargetApplicationWaitingAgents.agentFinished(VM06Agent01.class.getName(), success);
        }
    }
}
