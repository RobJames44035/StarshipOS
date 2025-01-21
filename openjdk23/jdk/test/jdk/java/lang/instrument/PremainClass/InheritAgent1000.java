/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6289149 8165276
 * @summary test config (1,0,0,0): 2-arg premain method in superclass of agent class must be rejected
 *
 * @library /test/lib
 * @library /test
 * @modules java.instrument
 * @build jdk.java.lang.instrument.PremainClass.InheritAgent1000
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             InheritAgent1000 InheritAgent1000.jar
 * @run main/othervm jdk.java.lang.instrument.NegativeAgentRunner InheritAgent1000 NoSuchMethodException
 */

import java.lang.instrument.Instrumentation;

public class InheritAgent1000 extends InheritAgent1000Super {

    // This agent does NOT have a single argument premain() method.

    // This agent does NOT have a double argument premain() method.
}

class InheritAgent1000Super {

    // This agent class does NOT have a single argument premain() method.

    // This agent class has a double argument premain() method which should NOT be called.
    public static void premain (String agentArgs, Instrumentation instArg) {
        System.out.println("Hello from Double-Arg InheritAgent1000Super!");
        throw new Error("ERROR: THIS AGENT SHOULD NOT HAVE BEEN CALLED.");
    }
}
