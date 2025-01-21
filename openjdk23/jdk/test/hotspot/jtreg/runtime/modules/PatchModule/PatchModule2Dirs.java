/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Make sure --patch-module works with multiple directories.
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile PatchModule2DirsMain.java
 * @run driver PatchModule2Dirs
 */

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.helpers.ClassFileInstaller;

public class PatchModule2Dirs {

    public static void main(String[] args) throws Exception {
        String source1 = "package javax.naming.spi; "               +
                        "public class NamingManager { "             +
                        "    static { "                             +
                        "        System.out.println(\"I pass one!\"); " +
                        "    } "                                    +
                        "}";
        String source2 = "package java.beans; "                     +
                        "public class Encoder { "                   +
                        "    static { "                             +
                        "        System.out.println(\"I pass two!\"); " +
                        "    } "                                    +
                        "}";

        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager",
             InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager", source1, "--patch-module=java.naming"),
             "mods/java.naming");

        ClassFileInstaller.writeClassToDisk("java/beans/Encoder",
             InMemoryJavaCompiler.compile("java.beans.Encoder", source2, "--patch-module=java.desktop"),
             "mods2/java.desktop");

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
             "--patch-module=java.naming=mods/java.naming",
             "--patch-module=java.desktop=mods2/java.desktop",
             "PatchModule2DirsMain", "javax.naming.spi.NamingManager", "java.beans.Encoder");

        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldContain("I pass one!");
        oa.shouldContain("I pass two!");
        oa.shouldHaveExitValue(0);
    }
}
