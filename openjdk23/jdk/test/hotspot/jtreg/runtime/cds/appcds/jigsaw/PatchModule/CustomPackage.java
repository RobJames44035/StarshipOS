/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @requires vm.cds
 * @summary if a class is defined to a package which is not defined to any
 *          module in the jimage, the class will not be found during dump
 *          time but it will be used during run time.
 * @library ../..
 * @library /test/hotspot/jtreg/testlibrary
 * @library /test/lib
 * @build PatchMain
 * @run driver CustomPackage
 */

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class CustomPackage {
    private static String moduleJar;

    public static void main(String args[]) throws Throwable {

        // Create a class file in the module java.naming. This class file
        // will be put in the javanaming.jar file.
        String source = "package javax.naming.myspi; "                +
                        "public class NamingManager { "             +
                        "    static { "                             +
                        "        System.out.println(\"I pass!\"); " +
                        "    } "                                    +
                        "}";

        ClassFileInstaller.writeClassToDisk("javax/naming/myspi/NamingManager",
             InMemoryJavaCompiler.compile("javax.naming.myspi.NamingManager", source, "--patch-module=java.naming"),
             System.getProperty("test.classes"));

        // Build the jar file that will be used for the module "java.naming".
        JarBuilder.build("javanaming", "javax/naming/myspi/NamingManager");
        moduleJar = TestCommon.getTestJar("javanaming.jar");

        System.out.println("Test dumping with --patch-module");
        OutputAnalyzer output =
            TestCommon.dump(null,
                TestCommon.list("javax/naming/myspi/NamingManager"),
                "--patch-module=java.naming=" + moduleJar,
                "-Xlog:class+load",
                "-Xlog:class+path=info",
                "PatchMain", "javax.naming.myspi.NamingManager");
        output.shouldHaveExitValue(1)
              .shouldContain("Cannot use the following option when dumping the shared archive: --patch-module");

        TestCommon.run(
            "-XX:+UnlockDiagnosticVMOptions",
            "--patch-module=java.naming=" + moduleJar,
            "-Xlog:class+load",
            "-Xlog:class+path=info",
            "PatchMain", "javax.naming.myspi.NamingManager")
            .assertSilentlyDisabledCDS(0, "I pass!");
    }
}
