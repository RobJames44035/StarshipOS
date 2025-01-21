/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Test parallel loading of lambda proxy classes.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build ParallelLambdaLoad jdk.test.whitebox.WhiteBox LambdaVerification
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar parallel_lambda.jar ParallelLambdaLoad ThreadUtil DoSomething LambdaVerification
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar WhiteBox.jar jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. ParallelLambdaLoadTest
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class ParallelLambdaLoadTest extends DynamicArchiveTestBase {
    public static void main(String[] args) throws Exception {
        runTest(ParallelLambdaLoadTest::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = ClassFileInstaller.getJarPath("parallel_lambda.jar");
        String mainClass = "ParallelLambdaLoad";
        String wbJar = ClassFileInstaller.getJarPath("WhiteBox.jar");
        String use_whitebox_jar = "-Xbootclasspath/a:" + wbJar;

        dump(topArchiveName,
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+WhiteBoxAPI", "-Xlog:cds+dynamic=info",
            use_whitebox_jar,
            "-cp", appJar, mainClass)
            .assertNormalExit(output -> {
                output.shouldContain("Archiving hidden ParallelLambdaLoad$$Lambda")
                      .shouldHaveExitValue(0);
            });

        run(topArchiveName,
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+WhiteBoxAPI",
            use_whitebox_jar,
            "-cp", appJar, mainClass, "run")
            .assertNormalExit(output -> {
                output.shouldHaveExitValue(0);
            });
    }
}
