/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @key cgroups
 * @bug 8242480
 * @requires container.support
 * @library /test/lib
 * @build GetFreeSwapSpaceSize
 * @run driver TestGetFreeSwapSpaceSize
 */
import jdk.test.lib.containers.docker.Common;
import jdk.test.lib.containers.docker.DockerRunOptions;
import jdk.test.lib.containers.docker.DockerTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

public class TestGetFreeSwapSpaceSize {
    private static final String imageName = Common.imageName("osbeanSwapSpace");

    public static void main(String[] args) throws Exception {
        if (!DockerTestUtils.canTestDocker()) {
            return;
        }

        DockerTestUtils.buildJdkContainerImage(imageName);

        try {
            testGetFreeSwapSpaceSize(
                "150M", Integer.toString(((int) Math.pow(2, 20)) * 150),
                "150M", Integer.toString(0)
            );
        } finally {
            if (!DockerTestUtils.RETAIN_IMAGE_AFTER_TEST) {
                DockerTestUtils.removeDockerImage(imageName);
            }
        }
    }

    private static void testGetFreeSwapSpaceSize(String memoryAllocation, String expectedMemory,
            String memorySwapAllocation, String expectedSwap) throws Exception {
        Common.logNewTestCase("TestGetFreeSwapSpaceSize");

        DockerRunOptions opts = Common.newOpts(imageName, "GetFreeSwapSpaceSize")
            .addClassOptions(memoryAllocation, expectedMemory, memorySwapAllocation, expectedSwap)
            .addDockerOpts(
                "--memory", memoryAllocation,
                "--memory-swap", memorySwapAllocation
            );

        OutputAnalyzer out = DockerTestUtils.dockerRunJava(opts);
        out.shouldHaveExitValue(0)
           .shouldContain("TestGetFreeSwapSpaceSize PASSED.");
    }
}
