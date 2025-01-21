/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8290417
 * @summary CDS cannot archive lambda proxy with useImplMethodHandle
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/test-classes
 * @build pkg1.BaseWithProtectedMethod
 * @build pkg2.Child
 * @build LambdaWithUseImplMethodHandleApp
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar test.jar pkg1.BaseWithProtectedMethod pkg2.Child LambdaWithUseImplMethodHandleApp
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. DynamicLambdaWithUseImplMethodHandle
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class DynamicLambdaWithUseImplMethodHandle extends DynamicArchiveTestBase {

    // See pkg2/Child.jcod for details about the condition that triggers JDK-8290417
    public static void main(String[] args) throws Exception {
        runTest(DynamicLambdaWithUseImplMethodHandle::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName("top");

        String appJar = ClassFileInstaller.getJarPath("test.jar");
        String mainClass = "LambdaWithUseImplMethodHandleApp";
        String expectedMsg = "Called BaseWithProtectedMethod::protectedMethod";

        dump(topArchiveName,
             "-Xlog:cds",
             "-Xlog:cds+dynamic=debug",
             "-cp", appJar, mainClass)
            .assertNormalExit(output -> {
                    output.shouldContain(expectedMsg);
                });
        run(topArchiveName,
            "-Xlog:cds+dynamic=debug,cds=debug",
            "-cp", appJar, mainClass)
            .assertNormalExit(output -> {
                    output.shouldContain(expectedMsg)
                          .shouldHaveExitValue(0);
                });
    }
}
