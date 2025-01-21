/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 *
 * @test
 * @requires !vm.graal.enabled
 * @summary Tests that the -javaagent option adds the java.instrument into
 * the module graph
 *
 * @run shell MakeJAR3.sh SimpleAgent
 * @run main/othervm -javaagent:SimpleAgent.jar --limit-modules java.base TestAgentWithLimitMods
 *
 */
public class TestAgentWithLimitMods {

    public static void main(String[] args) {
        System.out.println("Test passed");
    }

}
