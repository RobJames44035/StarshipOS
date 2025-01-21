/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Hidden class not of type lambda proxy class will not be archived.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 *          /test/hotspot/jtreg/runtime/cds/appcds/dynamicArchive/test-classes
 * @compile ../../../HiddenClasses/InstantiateHiddenClass.java
 *          ../../../../../../lib/jdk/test/lib/compiler/InMemoryJavaCompiler.java
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar hidden.jar InstantiateHiddenClass
 *                 jdk/test/lib/compiler/InMemoryJavaCompiler
 *                 jdk/test/lib/compiler/InMemoryJavaCompiler$FileManagerWrapper$1
 *                 jdk/test/lib/compiler/InMemoryJavaCompiler$FileManagerWrapper
 *                 jdk/test/lib/compiler/InMemoryJavaCompiler$SourceFile
 *                 jdk/test/lib/compiler/InMemoryJavaCompiler$ClassFile
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:. RegularHiddenClass
 */

import jdk.test.lib.helpers.ClassFileInstaller;

public class RegularHiddenClass extends DynamicArchiveTestBase {
    public static void main(String[] args) throws Exception {
        runTest(RegularHiddenClass::test);
    }

    static void test() throws Exception {
        String topArchiveName = getNewArchiveName();
        String appJar = ClassFileInstaller.getJarPath("hidden.jar");
        String mainClass = "InstantiateHiddenClass";

        dump(topArchiveName,
            "-Xlog:class+load=debug,cds+dynamic,cds=debug",
            "-cp", appJar, mainClass, "keep-alive")
            .assertNormalExit(output -> {
                output.shouldMatch("cds.*Skipping.TestClass.0x.*Hidden.class")
                      .shouldNotMatch("cds.dynamic.*Archiving.hidden.TestClass.*")
                      .shouldHaveExitValue(0);
            });

        run(topArchiveName,
            "-Xlog:class+load=debug,cds+dynamic,cds",
            "-cp", appJar, mainClass)
            .assertNormalExit(output -> {
                output.shouldMatch("class.load.*TestClass.*source.*InstantiateHiddenClass")
                      .shouldHaveExitValue(0);
            });
    }
}
