/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8130399
 * @summary Make sure --patch-module works for modules besides java.base.
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile PatchModuleMain.java
 * @run driver PatchModuleTest
 */

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.helpers.ClassFileInstaller;

public class PatchModuleTest {

    public static void main(String[] args) throws Exception {
        String source = "package javax.naming.spi; "                +
                        "public class NamingManager { "             +
                        "    static { "                             +
                        "        System.out.println(\"I pass!\"); " +
                        "    } "                                    +
                        "}";

        ClassFileInstaller.writeClassToDisk("javax/naming/spi/NamingManager",
             InMemoryJavaCompiler.compile("javax.naming.spi.NamingManager", source, "--patch-module=java.naming"),
             "mods/java.naming");

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("--patch-module=java.naming=mods/java.naming",
             "PatchModuleMain", "javax.naming.spi.NamingManager");

        new OutputAnalyzer(pb.start())
            .shouldContain("I pass!")
            .shouldHaveExitValue(0);
    }
}
