/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */


/*
 * @test
 * @summary Basic (sanity) test for JDK-under-test inside a docker image.
 * @requires container.support
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 *          jdk.jartool/sun.tools.jar
 * @build HelloDocker
 * @run driver DockerBasicTest
 */
import jdk.test.lib.containers.docker.Common;
import jdk.test.lib.containers.docker.DockerRunOptions;
import jdk.test.lib.containers.docker.DockerTestUtils;
import jdk.test.lib.Platform;
import jdk.test.lib.Utils;


public class DockerBasicTest {
    private static final String imageNameAndTag = Common.imageName("basic");

    public static void main(String[] args) throws Exception {
        if (!DockerTestUtils.canTestDocker()) {
            return;
        }

        DockerTestUtils.buildJdkContainerImage(imageNameAndTag);

        try {
            testJavaVersion();
            testHelloDocker();
            testJavaVersionWithCgMounts();
        } finally {
            if (!DockerTestUtils.RETAIN_IMAGE_AFTER_TEST) {
                DockerTestUtils.removeDockerImage(imageNameAndTag);
            }
        }
    }


    private static void testJavaVersion() throws Exception {
        DockerRunOptions opts =
            new DockerRunOptions(imageNameAndTag, "/jdk/bin/java", "-version");

        DockerTestUtils.dockerRunJava(opts)
            .shouldHaveExitValue(0)
            .shouldContain(Platform.vmName);
    }


    private static void testHelloDocker() throws Exception {
        DockerRunOptions opts =
            new DockerRunOptions(imageNameAndTag, "/jdk/bin/java", "HelloDocker")
            .addJavaOpts("-cp", "/test-classes/")
            .addDockerOpts("--volume", Utils.TEST_CLASSES + ":/test-classes/");

        DockerTestUtils.dockerRunJava(opts)
            .shouldHaveExitValue(0)
            .shouldContain("Hello Docker");
    }


    private static void testJavaVersionWithCgMounts() throws Exception {
        DockerRunOptions opts =
            new DockerRunOptions(imageNameAndTag, "/jdk/bin/java", "-version")
            .addDockerOpts("-v", "/sys/fs/cgroup:/cgroups-in:ro");

        // Duplicated cgroup mounts should be handled by the container detection
        // code and should not cause any error/warning output.
        DockerTestUtils.dockerRunJava(opts)
            .shouldHaveExitValue(0)
            .shouldNotMatch("\\[os,container *\\]");
    }
}
