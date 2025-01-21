/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */


package jdk.test.lib.containers.cgroup;

import java.util.Objects;

import jdk.internal.platform.Metrics;

/**
 * Cgroup version agnostic metrics tester
 *
 */
public class MetricsTester {

    private static final String CGROUP_V1 = "cgroupv1";
    private static final String CGROUP_V2 = "cgroupv2";

    private static CgroupMetricsTester createInstance(Metrics m) {
        Objects.requireNonNull(m);
        if (CGROUP_V1.equals(m.getProvider())) {
            MetricsTesterCgroupV1 t = new MetricsTesterCgroupV1();
            t.setup();
            return t;
        } else if (CGROUP_V2.equals(m.getProvider())) {
            return new MetricsTesterCgroupV2();
        } else {
            System.err.println("WARNING: Metrics provider, '" + m.getProvider()
                                                              + "' is unknown!");
            return null;
        }
    }

    private void testAll(Metrics m, boolean inContainer) throws Exception {
        CgroupMetricsTester tester =  createInstance(m);
        tester.testCpuAccounting();
        tester.testCpuConsumption();
        tester.testCpuSchedulingMetrics();
        tester.testCpuSets();
        if (!inContainer) {
          // If not running in a container, these test cases query the memory usage.
          // of all processes in the entire system (or those belonging to the current
          // Linux user). They cannot produce predictable results due to interference
          // from unrelated processes.
          System.out.println("testMemorySubsystem and testMemoryUsage skipped");
        } else {
          tester.testMemorySubsystem();
          tester.testMemoryUsage();
        }
        tester.testMisc();
        testContainerized(m, inContainer);
    }

    private void testContainerized(Metrics m, boolean inContainer) {
        if (m.isContainerized() != inContainer) {
            throw new RuntimeException("containerized test failed. " +
                                       "Expected isContainerized()==" + inContainer +
                                       " but got '" + m.isContainerized() + "'");
        }
        System.out.println("testContainerized() PASSED!");
    }

    public static void main(String[] args) throws Exception {
        Metrics m = Metrics.systemMetrics();
        // If cgroups is not configured, report success
        if (m == null) {
            System.out.println("TEST PASSED!!!");
            return;
        }

        boolean inContainer = false;
        if (args.length > 0 && "-incontainer".equals(args[0])) {
          inContainer = true;
        }
        System.out.println("inContainer = " + inContainer);

        MetricsTester metricsTester = new MetricsTester();
        metricsTester.testAll(m, inContainer);
        System.out.println("TEST PASSED!!!");
    }
}
