/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/**
 * @test
 * @bug 6289149 8165276
 * @summary test config (0,1,1,0): 1-arg in superclass and declared 2-arg in agent class
 * @author Daniel D. Daugherty, Sun Microsystems
 *
 * @library /test/lib
 * @build jdk.java.lang.instrument.PremainClass.InheritAgent0110
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             InheritAgent0110 InheritAgent0110.jar
 * @run main/othervm -javaagent:InheritAgent0110.jar DummyMain
 */

import java.lang.instrument.*;

public class InheritAgent0110 extends InheritAgent0110Super {

    // This agent does NOT have a one argument premain() method.

    //
    // This agent has a double argument premain() method which
    // is the one that should be called.
    //
    public static void premain (String agentArgs, Instrumentation instArg) {
        System.out.println("Hello from Double-Arg InheritAgent0110!");
    }
}

class InheritAgent0110Super {

    //
    // This agent has a single argument premain() method which
    // is NOT the one that should be called.
    //
    public static void premain (String agentArgs) {
        System.out.println("Hello from Single-Arg InheritAgent0110Super!");
        throw new Error("ERROR: THIS AGENT SHOULD NOT HAVE BEEN CALLED.");
    }

    // This agent does NOT have a double argument premain() method.
}
