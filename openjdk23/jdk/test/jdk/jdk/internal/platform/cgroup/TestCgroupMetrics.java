/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @key cgroups
 * @requires os.family == "linux"
 * @modules java.base/jdk.internal.platform
 * @library /test/lib
 * @run main TestCgroupMetrics
 */

import jdk.test.lib.containers.cgroup.MetricsTester;
import jdk.internal.platform.Metrics;

public class TestCgroupMetrics {

    public static void main(String[] args) throws Exception {
        MetricsTester.main(args);
    }

}
