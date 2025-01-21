/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.monitoring.share.thread;

import java.lang.management.*;
import nsk.share.log.*;
import java.util.List;
import java.util.ArrayList;

/**
 * MultiScenario is stress scenario that creates many scenarios and runs all of them.
 */
public class MultiScenario implements ThreadMonitoringScenario {
        private ThreadMonitoringScenario[] scenarios;
        private int count;
        private ThreadMonitoringScenarioFactory scenarioFactory;

        public MultiScenario(ThreadMonitoringScenarioFactory scenarioFactory, int count) {
                this.scenarioFactory = scenarioFactory;
                this.count = count;
        }

        public void begin() {
                scenarios = scenarioFactory.createScenarios(count);
                for (ThreadMonitoringScenario scenario : scenarios)
                        scenario.begin();
        }

        public void waitState() {
                for (ThreadMonitoringScenario scenario : scenarios) {
                        System.out.println("Waiting: " + scenario);
                        scenario.waitState();
                }
        }

        public void check(ThreadMXBean threadMXBean) {
                for (ThreadMonitoringScenario scenario : scenarios) {
                        System.out.println("Checking: " + scenario);
                        scenario.check(threadMXBean);
                }
        }

        public void finish() {
                /* This is still called when ScenarioFactory.createScenarios() throws exception.. */
                if (scenarios == null)
                        return;
                for (ThreadMonitoringScenario scenario : scenarios)
                        scenario.finish();
        }

        public void end() {
                /* This is still called when ScenarioFactory.createScenarios() throws exception.. */
                if (scenarios == null)
                        return;
                for (ThreadMonitoringScenario scenario : scenarios)
                        scenario.end();
        }
}
