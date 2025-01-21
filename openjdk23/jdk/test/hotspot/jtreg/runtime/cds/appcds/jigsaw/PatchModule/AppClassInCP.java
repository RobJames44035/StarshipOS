/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @requires vm.cds
 * @summary a test to demonstrate that an application class in the -cp
 *          will be archived although --patch-module is specified. The class in
 *          the -cp has no dependencies on the class in the --patch-module.
 * @library ../..
 * @library /test/hotspot/jtreg/testlibrary
 * @library /test/lib
 * @build PatchMain
 * @run driver AppClassInCP
 */

import java.io.File;
import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class AppClassInCP {
    private static String moduleJar;
    private static String appJar;

    public static void main(String args[]) throws Throwable {

        // Create a class file in the module java.naming. This class file
        // will be put in the javanaming.jar file.
        String source = "package javax.naming.spi; "                +
                        "public class NamingManager { "             +
                        "    static { "                             +
                        "        System.out.println(\"I pass!\"); " +
                        "    } "                                    +
                        "}";

        String classDir = System.getProperty("test.classes");

        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager",
             InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager", source, "--patch-module=java.naming"),
             classDir);

        // Build the jar file that will be used for the module "java.naming".
        JarBuilder.build("javanaming", "javax/naming/spi/NamingManager");
        moduleJar = TestCommon.getTestJar("javanaming.jar");

        String source2 = "package mypackage; "                +
                        "public class Hello { "             +
                        "    static { "                             +
                        "        System.out.println(\"Hello!\"); " +
                        "    } "                                    +
                        "}";
        ClassFileInstaller.writeClassToDisk("mypackage/Hello",
             InMemoryJavaCompiler.compile("mypackage.Hello", source2),
             classDir);

        JarBuilder.build("hello", "mypackage/Hello");
        appJar = TestCommon.getTestJar("hello.jar");

        System.out.println("Test dumping with --patch-module");
        OutputAnalyzer output =
            TestCommon.dump(appJar,
                TestCommon.list("javax/naming/spi/NamingManager", "mypackage/Hello"),
                "--patch-module=java.naming=" + moduleJar,
                "-Xlog:class+load",
                "PatchMain", "javax.naming.spi.NamingManager", "mypackage.Hello");
        output.shouldHaveExitValue(1)
              .shouldContain("Cannot use the following option when dumping the shared archive: --patch-module");

        String classPath = appJar + File.pathSeparator + classDir;
        System.out.println("classPath: " + classPath);
        TestCommon.run(
            "-XX:+UnlockDiagnosticVMOptions",
            "-cp", classPath,
            "--patch-module=java.naming=" + moduleJar,
            "-Xlog:class+load",
            "PatchMain", "javax.naming.spi.NamingManager", "mypackage.Hello")
            .assertSilentlyDisabledCDS(0, "I pass!", "Hello!");
    }
}
