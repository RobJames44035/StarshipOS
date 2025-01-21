/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 6583051
 * @summary Give error if java.lang.Object has been incompatibly overridden on the bootpath
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver BootstrapRedefine
 */

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class BootstrapRedefine {

    public static void main(String[] args) throws Exception {
        String source = "package java.lang;" +
                        "public class Object {" +
                        "    void dummy1() { return; }" +
                        "    void dummy2() { return; }" +
                        "    void dummy3() { return; }" +
                        "}";

        ClassFileInstaller.writeClassToDisk("java/lang/Object",
                                        InMemoryJavaCompiler.compile("java.lang.Object", source,
                                        "--patch-module=java.base"),
                                        "mods/java.base");

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("--patch-module=java.base=mods/java.base", "-version");
        new OutputAnalyzer(pb.start())
            .shouldContain("Incompatible definition of java.lang.Object")
            .shouldHaveExitValue(1);
    }
}
