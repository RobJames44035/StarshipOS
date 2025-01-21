/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @key cgroups
 * @summary Test JDK Metrics class when running inside docker container
 * @requires container.support
 * @library /test/lib
 * @modules java.base/jdk.internal.platform
 * @run main TestSystemMetrics
 */

import jdk.test.lib.Utils;
import jdk.test.lib.containers.docker.Common;
import jdk.test.lib.containers.docker.DockerRunOptions;
import jdk.test.lib.containers.docker.DockerTestUtils;
import jdk.test.lib.containers.cgroup.MetricsTester;

public class TestSystemMetrics {
    private static final String imageName = Common.imageName("metrics");

    public static void main(String[] args) throws Exception {
        if (!DockerTestUtils.canTestDocker()) {
            return;
        }

        DockerTestUtils.buildJdkContainerImage(imageName);

        try {
            Common.logNewTestCase("Test SystemMetrics");
            DockerRunOptions opts =
                    new DockerRunOptions(imageName, "/jdk/bin/java", "jdk.test.lib.containers.cgroup.MetricsTester");
            opts.addDockerOpts("--volume", Utils.TEST_CLASSES + ":/test-classes/");
            opts.addDockerOpts("--memory=256m");
            opts.addJavaOpts("-cp", "/test-classes/");
            opts.addJavaOpts("--add-exports", "java.base/jdk.internal.platform=ALL-UNNAMED");
            opts.addClassOptions("-incontainer");
            DockerTestUtils.dockerRunJava(opts).shouldHaveExitValue(0).shouldContain("TEST PASSED!!!");
        } finally {
            DockerTestUtils.removeDockerImage(imageName);
        }
    }
}
