/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.share.aod;

/*
 * Class contains information about dynamically attached agent
 */
public class AgentInformation {

    // counters used for unique agent names generation

    private static int jarAgentsCounter;

    private static int nativeAgentsCounter;

    public boolean jarAgent;

    public String pathToAgent;

    public String agentOptions;

    public AgentInformation(boolean jarAgent, String pathToAgent, String options, boolean addAgentNameOption) {
        this.jarAgent = jarAgent;
        this.pathToAgent = pathToAgent;
        this.agentOptions = options;

        // add to agent options additional parameter - agent name (it used by nsk.share.aod framework)

        String name;

        if (jarAgent)
            name = "JarAgent-" + jarAgentsCounter++;
        else
            name = "NativeAgent-" + nativeAgentsCounter++;

        if (addAgentNameOption) {
            if (this.agentOptions == null) {
                this.agentOptions = "-agentName=" + name;
            } else {
                this.agentOptions += " -agentName=" + name;
            }
        }
    }

    public AgentInformation(boolean jarAgent, String pathToAgent, String options) {
        this(jarAgent, pathToAgent, options, true);
    }
}
