/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @requires vm.cds
 * @summary a simple test to ensure that class is loaded from jar file in --patch-module at runtime
 * @library ../..
 * @library /test/hotspot/jtreg/testlibrary
 * @library /test/lib
 * @build PatchMain
 * @run driver Simple
 */

import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class Simple {
    private static String moduleJar;

    public static void main(String args[]) throws Throwable {

        // Create a class file in the module java.naming. This class file
        // will be put in the javanaming.jar file.
        String source = "package javax.naming.spi; "                +
                        "public class NamingManager { "             +
                        "    static { "                             +
                        "        System.out.println(\"I pass!\"); " +
                        "    } "                                    +
                        "}";

        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager",
             InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager", source, "--patch-module=java.naming"),
             System.getProperty("test.classes"));

        // Build the jar file that will be used for the module "java.naming".
        JarBuilder.build("javanaming", "javax/naming/spi/NamingManager");
        moduleJar = TestCommon.getTestJar("javanaming.jar");

        System.out.println("Test dumping with --patch-module");
        OutputAnalyzer output =
            TestCommon.dump(null,
                TestCommon.list("javax/naming/spi/NamingManager"),
                "--patch-module=java.naming=" + moduleJar,
                "-Xlog:class+load",
                "-Xlog:class+path=info",
                "PatchMain", "javax.naming.spi.NamingManager");
        output.shouldHaveExitValue(1)
              .shouldContain("Cannot use the following option when dumping the shared archive: --patch-module");

        TestCommon.run(
            "-XX:+UnlockDiagnosticVMOptions",
            "--patch-module=java.naming=" + moduleJar,
            "-Xlog:class+load",
            "-Xlog:class+path=info",
            "PatchMain", "javax.naming.spi.NamingManager")
            .assertSilentlyDisabledCDS(0, "I pass!");

        // ========================================
        if (!CDSTestUtils.DYNAMIC_DUMP) {
            System.out.println("Dump again without --patch-module");
            output =
                TestCommon.dump(null,
                    TestCommon.list("javax/naming/spi/NamingManager"));
            output.shouldHaveExitValue(0);

            TestCommon.run(
                "-XX:+UnlockDiagnosticVMOptions",
                "--patch-module=java.naming=" + moduleJar,
                "-Xlog:class+load",
                "-Xlog:class+path=info",
                "PatchMain", "javax.naming.spi.NamingManager")
                .assertSilentlyDisabledCDS(0, "I pass!");
        }
    }
}
