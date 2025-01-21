/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6289149
 * @summary test when the agent's class has a zero arg premain() function.
 * @library /test/lib
 * @library /test
 *
 * @modules java.instrument
 * @build jdk.java.lang.instrument.PremainClass.ZeroArgPremainAgent
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             ZeroArgPremainAgent ZeroArgPremainAgent.jar
 * @run main/othervm jdk.java.lang.instrument.NegativeAgentRunner ZeroArgPremainAgent NoSuchMethodException
 */

public class ZeroArgPremainAgent {

    // This agent has a zero arg premain() function.
    public static void premain () {
        System.out.println("Hello from ZeroArgInheritAgent!");
        throw new Error("ERROR: THIS AGENT SHOULD NOT HAVE BEEN CALLED.");
    }
}
