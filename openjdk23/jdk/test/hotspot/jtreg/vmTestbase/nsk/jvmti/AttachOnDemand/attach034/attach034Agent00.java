/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach034;

import java.lang.instrument.Instrumentation;

public class attach034Agent00 extends AgentParent {

    public static void agentmain(String options, Instrumentation inst) {
        new attach034Agent00().runJarAgent(options, inst);
    }
}
