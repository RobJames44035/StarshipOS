/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/**
 *  @test
 *  @bug 5013605
 *  @summary Localize log messages from the management agents
 *  @modules jdk.management.agent/jdk.internal.agent
 *
 *  @author Tim Bell
 */
import jdk.internal.agent.Agent;

public class AgentCheckTest {

    public static void main(String[] args){
        String [][] testStrings = {
            {"agent.err.error", "", ""},
            {"jmxremote.ConnectorBootstrap.starting", "", ""},
            {"jmxremote.ConnectorBootstrap.noAuthentication", "", ""},
            {"jmxremote.ConnectorBootstrap.ready", "Phony JMXServiceURL", ""},
            {"jmxremote.ConnectorBootstrap.password.readonly", "Phony passwordFileName", ""},
        };

        boolean pass = true;
        System.out.println("Start...");
        for (int ii = 0; ii < testStrings.length; ii++) {
            String key = testStrings[ii][0];
            String p1 = testStrings[ii][1];
            String p2 = testStrings[ii][2];
            String ss = Agent.getText(key, p1, p2);
            if (ss.startsWith("missing resource key")) {
                pass = false;
                System.out.println("    lookup failed for key = " + key);
            }
        }
        if (!pass) {
            throw new Error ("Resource lookup(s) failed; Test failed");
        }
        System.out.println("...Finished.");
    }
}
