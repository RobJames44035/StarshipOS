/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Make sure --patch-module works when a jar file and a directory is specified for a module
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          jdk.jartool/sun.tools.jar
 * @compile PatchModule2DirsMain.java
 * @run driver PatchModuleTestJarDir
 */

import java.io.File;

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.helpers.ClassFileInstaller;

public class PatchModuleTestJarDir {
    private static String moduleJar;

    public static void main(String[] args) throws Exception {

        // Create a class file in the module java.naming. This class file
        // will be put in the javanaming.jar file.
        String source = "package javax.naming.spi; "                    +
                        "public class NamingManager1 { "                +
                        "    static { "                                 +
                        "        System.out.println(\"I pass one!\"); " +
                        "    } "                                        +
                        "}";

        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager1",
             InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager1", source, "--patch-module=java.naming"),
             System.getProperty("test.classes"));

        // Build the jar file that will be used for the module "java.naming".
        BasicJarBuilder.build("javanaming", "javax/naming/spi/NamingManager1");
        moduleJar = BasicJarBuilder.getTestJar("javanaming.jar");

        // Just to make sure we are not fooled by the class file being on the
        // class path where all the test classes are stored, write the NamingManager.class
        // file out again with output that does not contain what OutputAnalyzer
        // expects. This will provide confidence that the contents of the class
        // is truly coming from the jar file and not the class file.
        source = "package javax.naming.spi; "                +
                 "public class NamingManager1 { "            +
                 "    static { "                             +
                 "        System.out.println(\"Fail!\"); "   +
                 "    } "                                    +
                 "}";

        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager1",
             InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager1", source, "--patch-module=java.naming"),
             System.getProperty("test.classes"));

        // Create a second class file in the module java.naming. This class file
        // will be put in the mods/java.naming directory.
        source = "package javax.naming.spi; "                    +
                 "public class NamingManager2 { "                +
                 "    static { "                                 +
                 "        System.out.println(\"I pass two!\"); " +
                 "    } "                                        +
                 "}";

        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager2",
             InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager2", source, "--patch-module=java.naming"),
             (System.getProperty("test.classes") + "/mods/java.naming"));


        // Supply --patch-module with the name of the jar file for the module java.naming.
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("--patch-module=java.naming=" +
                                                                                 moduleJar +
                                                                                 File.pathSeparator +
                                                                                 System.getProperty("test.classes") + "/mods/java.naming",
                                                                             "PatchModule2DirsMain",
                                                                             "javax.naming.spi.NamingManager1",
                                                                             "javax.naming.spi.NamingManager2");

        new OutputAnalyzer(pb.start())
            .shouldContain("I pass one!")
            .shouldContain("I pass two!")
            .shouldHaveExitValue(0);
    }
}
