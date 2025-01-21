/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/**
 * @test
 * @bug 6289149 8165276
 * @summary test config (0,1,0,1): 1-arg in superclass and declared 1-arg in agent class
 * @author Daniel D. Daugherty, Sun Microsystems
 *
 * @key intermittent
 * @library /test/lib
 * @build jdk.java.lang.instrument.PremainClass.InheritAgent0101
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             InheritAgent0101 InheritAgent0101.jar
 * @run main/othervm -javaagent:InheritAgent0101.jar DummyMain
 */

import java.lang.instrument.*;

public class InheritAgent0101 extends InheritAgent0101Super {

    //
    // This agent has a single argument premain() method which
    // is the one that should be called.
    //
    public static void premain (String agentArgs) {
        System.out.println("Hello from Single-Arg InheritAgent0101!");
    }

    // This agent does NOT have a double argument premain() method.
}

class InheritAgent0101Super {

    //
    // This agent has a single argument premain() method which
    // is NOT the one that should be called.
    //
    public static void premain (String agentArgs) {
        System.out.println("Hello from Single-Arg InheritAgent0101Super!");
        throw new Error("ERROR: THIS AGENT SHOULD NOT HAVE BEEN CALLED.");
    }

    // This agent does NOT have a double argument premain() method.
}
