/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8069469
 * @summary Make sure -Xlog:class+load=info works properly with "modules" jimage,
            --patch-module, and with -Xbootclasspath/a
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile PatchModuleMain.java
 * @run driver PatchModuleTraceCL
 */

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;
import jdk.test.lib.process.ProcessTools;

public class PatchModuleTraceCL {

    public static void main(String[] args) throws Exception {
        String source = "package javax.naming.spi; "                +
                        "public class NamingManager { "             +
                        "    static { "                             +
                        "        System.out.println(\"I pass!\"); " +
                        "    } "                                    +
                        "}";

        // Test -Xlog:class+load=info output for --patch-module
        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager",
             InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager", source, "--patch-module=java.naming"),
             "mods/java.naming");

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("--patch-module=java.naming=mods/java.naming",
             "-Xlog:class+load=info", "PatchModuleMain", "javax.naming.spi.NamingManager");

        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
        // "modules" jimage case.
        output.shouldContain("[class,load] java.lang.Thread source: jrt:/java.base");
        // --patch-module case.
        output.shouldContain("[class,load] javax.naming.spi.NamingManager source: mods/java.naming");
        // -cp case.
        output.shouldContain("[class,load] PatchModuleMain source: file");

        // Test -Xlog:class+load=info output for -Xbootclasspath/a
        source = "package PatchModuleTraceCL_pkg; "                 +
                 "public class ItIsI { "                          +
                 "    static { "                                  +
                 "        System.out.println(\"I also pass!\"); " +
                 "    } "                                         +
                 "}";

        ClassFileInstaller.writeClassToDisk("PatchModuleTraceCL_pkg/ItIsI",
             InMemoryJavaCompiler.compile("PatchModuleTraceCL_pkg.ItIsI", source),
             "xbcp");

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xbootclasspath/a:xbcp",
             "-Xlog:class+load=info", "PatchModuleMain", "PatchModuleTraceCL_pkg.ItIsI");
        output = new OutputAnalyzer(pb.start());
        // -Xbootclasspath/a case.
        output.shouldContain("[class,load] PatchModuleTraceCL_pkg.ItIsI source: xbcp");
        output.shouldHaveExitValue(0);
    }
}
