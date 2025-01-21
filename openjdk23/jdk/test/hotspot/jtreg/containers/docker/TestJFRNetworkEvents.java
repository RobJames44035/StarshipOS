/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */


/*
 * @test
 * @summary Test JFR network related events inside a container; make sure
 *          the reported host ip and host name are correctly reported within
 *          the container.
 * @requires container.support
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 *          jdk.jartool/sun.tools.jar
 * @build JfrNetwork
 * @run driver TestJFRNetworkEvents
 */
import jdk.test.lib.containers.docker.Common;
import jdk.test.lib.containers.docker.DockerRunOptions;
import jdk.test.lib.containers.docker.DockerTestUtils;
import jdk.test.lib.Utils;


public class TestJFRNetworkEvents {
    private static final String imageName = Common.imageName("jfr-network");
    private static final int availableCPUs = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception {
        System.out.println("Test Environment: detected availableCPUs = " + availableCPUs);
        if (!DockerTestUtils.canTestDocker()) {
            return;
        }

        DockerTestUtils.buildJdkContainerImage(imageName);

        try {
            runTest("jdk.SocketWrite");
        } finally {
            DockerTestUtils.removeDockerImage(imageName);
        }
    }

    private static void runTest(String event) throws Exception {
        DockerRunOptions opts = new DockerRunOptions(imageName, "/jdk/bin/java", "JfrNetwork")
        .addDockerOpts("--volume", Utils.TEST_CLASSES + ":/test-classes/")
        .addJavaOpts("-cp", "/test-classes/")
        .addDockerOpts("--hostname", JfrNetwork.HOST_NAME)
        .addClassOptions(event);
    DockerTestUtils.dockerRunJava(opts)
        .shouldHaveExitValue(0)
        .shouldContain(JfrNetwork.JFR_REPORTED_CONTAINER_HOSTNAME_TAG + JfrNetwork.HOST_NAME);
    }
}
