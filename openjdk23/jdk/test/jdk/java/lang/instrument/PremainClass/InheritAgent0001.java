/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/**
 * @test
 * @bug 6289149 8165276
 * @summary test config (0,0,0,1): declared 1-arg in agent class
 * @author Daniel D. Daugherty, Sun Microsystems
 *
 * @library /test/lib
 * @build jdk.java.lang.instrument.PremainClass.InheritAgent0001
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             InheritAgent0001 InheritAgent0001.jar
 * @run main/othervm -javaagent:InheritAgent0001.jar DummyMain
 */

import java.lang.instrument.*;

public class InheritAgent0001 extends InheritAgent0001Super {

    //
    // This agent has a single argument premain() method which
    // is the one that should be called.
    //
    public static void premain (String agentArgs) {
        System.out.println("Hello from Single-Arg InheritAgent0001!");
    }

    // This agent does NOT have a double argument premain() method.
}

class InheritAgent0001Super {

    // This agent does NOT have a single argument premain() method.

    // This agent does NOT have a double argument premain() method.
}
