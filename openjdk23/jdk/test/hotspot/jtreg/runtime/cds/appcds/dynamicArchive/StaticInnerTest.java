/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Outer class is directly referenced during dump time but not during
 *          runtime. This test makes sure the nest host of a lambda proxy class
 *          could be loaded from the archive during runtime though it isn't being
 *          referenced directly.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build StaticInnerApp jdk.test.whitebox.WhiteBox LambdaVerification
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar static_inner_app.jar StaticInnerApp HelloStaticInner HelloStaticInner$InnerHello
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. StaticInnerTest
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class StaticInnerTest extends DynamicArchiveTestBase {
    public static void main(String[] args) throws Exception {
        runTest(StaticInnerTest::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = ClassFileInstaller.getJarPath("static_inner_app.jar");
        String mainClass = "StaticInnerApp";

        dump(topArchiveName,
            "-Xlog:class+load=info,class+nestmates=trace,cds+dynamic=info",
            "-cp", appJar, mainClass, "dump")
            .assertNormalExit(output -> {
                output.shouldContain("Archiving hidden HelloStaticInner$InnerHello$$Lambda")
                      .shouldHaveExitValue(0);
            });

        run(topArchiveName,
            "-Xlog:class+load=info",
            "-cp", appJar, mainClass, "run")
            .assertNormalExit(output -> {
                output.shouldHaveExitValue(0)
                      .shouldContain("HelloStaticInner source: shared objects file (top)")
                      .shouldMatch(".class.load. HelloStaticInner[$]InnerHello[$][$]Lambda.*/0x.*source:.*shared.*objects.*file.*(top)");
            });
    }
}
