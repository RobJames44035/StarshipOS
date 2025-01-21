/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8276184
 * @summary If the caller class is redefined during dump time, the caller class
 *          and its lambda proxy class should not be archived.
 * @requires vm.cds
 * @requires vm.jvmti
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/test-classes
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @build jdk.test.whitebox.WhiteBox OldProvider
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver RedefineClassHelper
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. RedefineCallerClassTest
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class RedefineCallerClassTest extends DynamicArchiveTestBase {
    static String mainClass = RedefineCallerClass.class.getName();

    static String providerClass = OldProvider.class.getName();

    static String sharedClasses[] = {
        mainClass,
        "SimpleLambda", // caller class will be redefined in RedefineCallerClass
        providerClass,  // inteface with class file major version < 50
        "jdk/test/lib/compiler/InMemoryJavaCompiler",
        "jdk/test/lib/compiler/InMemoryJavaCompiler$FileManagerWrapper",
        "jdk/test/lib/compiler/InMemoryJavaCompiler$FileManagerWrapper$1",
        "jdk/test/lib/compiler/InMemoryJavaCompiler$SourceFile",
        "jdk/test/lib/compiler/InMemoryJavaCompiler$ClassFile"
    };

    public static void main(String[] args) throws Exception {
        runTest(RedefineCallerClassTest::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = ClassFileInstaller.writeJar("redefine_caller_class.jar", sharedClasses);

        String[] mainArgs = {
            "redefineCaller", // redefine caller class only
            "useOldInf",      // use old interface only
            "both"            // both of the above
        };

        for (String mainArg : mainArgs) {
            String[] options = {
                "-Xlog:class+load,cds",
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+AllowArchivingWithJavaAgent",
                "-javaagent:redefineagent.jar",
                "-cp", appJar, mainClass, mainArg
            };

            dump(topArchiveName, options)
                .assertNormalExit(output -> {
                    output.shouldHaveExitValue(0);
                    if (mainArg.equals("both") || mainArg.equals("useOldInf")) {
                        output.shouldContain("Skipping OldProvider: Old class has been linked")
                              .shouldMatch("Skipping.SimpleLambda[$][$]Lambda.*0x.*:.*Old.class.has.been.linked");
                    }
                    if (mainArg.equals("both") || mainArg.equals("redefineCaller")) {
                        output.shouldContain("Skipping SimpleLambda: Has been redefined");
                    }
                });

            run(topArchiveName, options)
                .assertNormalExit(output -> {
                    output.shouldHaveExitValue(0)
                          .shouldContain("RedefineCallerClass source: shared objects file (top)")
                          .shouldMatch(".class.load. SimpleLambda[$][$]Lambda.*/0x.*source:.*SimpleLambda");
                    if (mainArg.equals("both") || mainArg.equals("useOldInf")) {
                        output.shouldMatch(".class.load. OldProvider.source:.*redefine_caller_class.jar");
                    }
                    if (mainArg.equals("both") || mainArg.equals("redefineCaller")) {
                        output.shouldMatch(".class.load. SimpleLambda.source:.*redefine_caller_class.jar");
                    }
                });
        }
    }
}
