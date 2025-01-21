/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.lang.instrument.Instrumentation;

public class Main {
    public static void main(String[] args) throws Exception {
        Instrumentation inst = Agent.inst;
        if (inst == null)
            throw new RuntimeException("Agent not loaded");

        // check boot class path has been extended
        Class<?> helper = Class.forName("AgentHelper");
        if (helper.getClassLoader() != null)
            throw new RuntimeException("AgentHelper not loaded by boot loader");

        // check Instrumentation object can be used
        Class<?>[] classes = inst.getAllLoadedClasses();
        System.out.println(classes.length + " classes loaded");
    }
}
