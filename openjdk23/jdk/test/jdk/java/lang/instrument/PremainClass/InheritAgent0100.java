/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 6289149 8165276
 * @summary test config (0,1,0,0): 1-arg premain method in superclass of agent class must be rejected
 * @library /test/lib
 * @library /test
 * @modules java.instrument
 * @build jdk.java.lang.instrument.PremainClass.InheritAgent0100
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             InheritAgent0100 InheritAgent0100.jar
 * @run main/othervm jdk.java.lang.instrument.NegativeAgentRunner InheritAgent0100 NoSuchMethodException
 */

public class InheritAgent0100 extends InheritAgent0100Super {

     // This agent does NOT have a single argument premain() method.
     // This agent does NOT have a double argument premain() method.

}

class InheritAgent0100Super {
    // This agent class has a single argument premain() method which should NOT be called.
    public static void premain (String agentArgs) {
        System.out.println("Hello from Single-Arg InheritAgent0100Super!");
        throw new Error("ERROR: THIS AGENT SHOULD NOT HAVE BEEN CALLED.");
    }

    // This agent class does NOT have a double argument premain() method.
}
