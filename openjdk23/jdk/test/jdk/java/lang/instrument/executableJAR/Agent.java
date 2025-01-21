/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.lang.instrument.Instrumentation;

public class Agent {

    public static Instrumentation inst;

    public static void agentmain(String agentArgs, Instrumentation inst) {
        Agent.inst = inst;
    }
}
