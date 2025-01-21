/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.test.lib.containers.docker;

/*
 * Methods and definitions common to docker tests container in this directory
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;

import static jdk.test.lib.Asserts.assertNotNull;


public class Common {
    // Create a unique name for docker image.
    public static String imageName() {
        // jtreg guarantees that test.name is unique among all concurrently executing
        // tests. For example, if you have two test roots:
        //
        //     $ find test -type f
        //     test/foo/TEST.ROOT
        //     test/foo/my/TestCase.java
        //     test/bar/TEST.ROOT
        //     test/bar/my/TestCase.java
        //     $ jtreg -concur:2 test/foo test/bar
        //
        // jtreg will first run all the tests under test/foo. When they are all finished, then
        // jtreg will run all the tests under test/bar. So you will never have two concurrent
        // test cases whose test.name is "my/TestCase.java"
        String testname = System.getProperty("test.name");
        assertNotNull(testname, "must be set by jtreg");
        testname = testname.replace(".java", "");
        testname = testname.replace('/', '-');
        testname = testname.replace('\\', '-');

        // Example: "jdk-internal:test-containers-docker-TestMemoryAwareness"
        return "jdk-internal:test-" + testname;
    }

    public static String imageName(String suffix) {
        // Example: "jdk-internal:test-containers-docker-TestMemoryAwareness-memory"
        return imageName() + '-' + suffix;
    }

    public static void prepareWhiteBox() throws Exception {
        Files.copy(Paths.get(new File("whitebox.jar").getAbsolutePath()),
                   Paths.get(Utils.TEST_CLASSES, "whitebox.jar"), StandardCopyOption.REPLACE_EXISTING);
    }


    // create simple commonly used options
    public static DockerRunOptions newOpts(String imageName) {
        return new DockerRunOptions(imageName, "/jdk/bin/java", "-version")
            .addJavaOpts("-Xlog:os+container=trace");
    }

    public static DockerRunOptions newOptsShowSettings(String imageName) {
        return new DockerRunOptions(imageName, "/jdk/bin/java", "-version", "-XshowSettings:system");
    }


    // create commonly used options with class to be launched inside container
    public static DockerRunOptions newOpts(String imageName, String testClass) {
        DockerRunOptions opts =
            new DockerRunOptions(imageName, "/jdk/bin/java", testClass);
        opts.addDockerOpts("--volume", Utils.TEST_CLASSES + ":/test-classes/");
        opts.addJavaOpts("-Xlog:os+container=trace", "-cp", "/test-classes/");
        return opts;
    }


    public static DockerRunOptions addWhiteBoxOpts(DockerRunOptions opts) {
        opts.addJavaOpts("-Xbootclasspath/a:/test-classes/whitebox.jar",
                         "-XX:+UnlockDiagnosticVMOptions", "-XX:+WhiteBoxAPI");
        return opts;
    }


    // most common type of run and checks
    public static OutputAnalyzer run(DockerRunOptions opts) throws Exception {
        return DockerTestUtils.dockerRunJava(opts)
            .shouldHaveExitValue(0).shouldContain("Initializing Container Support");
    }


    // log beginning of a test case
    public static void logNewTestCase(String msg) {
        System.out.println("========== NEW TEST CASE:      " + msg);
    }

}
