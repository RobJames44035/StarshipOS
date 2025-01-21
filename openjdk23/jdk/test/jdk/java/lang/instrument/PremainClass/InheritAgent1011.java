/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/**
 * @test
 * @bug 6289149 8165276
 * @summary test config (1,0,1,1): 2-arg in superclass, declared 2-arg and declared 1-arg in agent class
 * @author Daniel D. Daugherty, Sun Microsystems
 *
 * @library /test/lib
 * @build jdk.java.lang.instrument.PremainClass.InheritAgent1011
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             InheritAgent1011 InheritAgent1011.jar
 * @run main/othervm -javaagent:InheritAgent1011.jar DummyMain
 */

import java.lang.instrument.*;

public class InheritAgent1011 extends InheritAgent1011Super {

    //
    // This agent has a single argument premain() method which
    // is NOT the one that should be called.
    //
    public static void premain (String agentArgs) {
        System.out.println("Hello from Single-Arg InheritAgent1011!");
        throw new Error("ERROR: THIS AGENT SHOULD NOT HAVE BEEN CALLED.");
    }

    //
    // This agent has a double argument premain() method which
    // is the one that should be called.
    //
    public static void premain (String agentArgs, Instrumentation instArg) {
        System.out.println("Hello from Double-Arg InheritAgent1011!");
    }
}

class InheritAgent1011Super {

    // This agent does NOT have a single argument premain() method.

    //
    // This agent has a double argument premain() method which
    // is NOT the one that should be called.
    //
    public static void premain (String agentArgs, Instrumentation instArg) {
        System.out.println("Hello from Double-Arg InheritAgent1011Super!");
        throw new Error("ERROR: THIS AGENT SHOULD NOT HAVE BEEN CALLED.");
    }
}
