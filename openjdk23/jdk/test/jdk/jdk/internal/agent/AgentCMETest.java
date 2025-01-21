/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 7164191
 * @summary properties.putAll API may fail with ConcurrentModifcationException on multi-thread scenario
 * @modules jdk.management.agent/jdk.internal.agent
 * @author Deven You
 */

import java.util.Properties;
import jdk.internal.agent.Agent;

public class AgentCMETest {
    static Class<?> agentClass;

    /**
     * In jdk.internal.agent.Agent.loadManagementProperties(), call
     * properties.putAll API may fail with ConcurrentModifcationException if the
     * system properties are modified simultaneously by another thread
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Start...");

        final Properties properties = System.getProperties();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    properties.put(String.valueOf(i), "");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                }
            }
        });
        t1.start();

        for (int i = 0; i < 10000; i++) {
            Agent.loadManagementProperties();
        }

        System.out.println("Finished...");
    }
}
