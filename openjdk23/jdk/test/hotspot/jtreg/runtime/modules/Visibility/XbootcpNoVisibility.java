/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Ensure that a class defined within a java.base package can not
 *          be located via -Xbootclasspath/a
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver XbootcpNoVisibility
 */

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class XbootcpNoVisibility {
    public static void main(String args[]) throws Exception {

        String Vis3_B_src =
                "package sun.util;" +
                "public class Vis3_B { public void m() { System.out.println(\"In B's m()\"); } }";

        ClassFileInstaller.writeClassToDisk("sun/util/Vis3_B",
            InMemoryJavaCompiler.compile("sun.util.Vis3_B", Vis3_B_src), System.getProperty("test.classes"));

        String Vis3_A_src =
                "import sun.util.*;" +
                "public class Vis3_A {" +
                "    public static void main(String args[]) throws Exception {" +
                        // Try loading a class within a named package in a module which has been defined
                        // to the boot loader. In this situation, the class should only be attempted
                        // to be loaded from the boot loader's module path which consists of:
                        //   [--patch-module]; exploded build | "modules" jimage
                        //
                        // Since the class is located on the boot loader's append path via
                        // -Xbootclasspath/a specification, it should not be found.
                "       try {" +
                "               sun.util.Vis3_B b = new sun.util.Vis3_B();" +
                "       } catch (NoClassDefFoundError e) {" +
                "               System.out.println(\"XbootcpNoVisibility PASSED - " +
                                                "test should throw exception\\n\");" +
                "               return;" +
                "       }" +
                "       throw new RuntimeException(\"XbootcpNoVisibility FAILED - " +
                                                    "test should have thrown exception\");" +
                "    }" +
                "}";

        ClassFileInstaller.writeClassToDisk("Vis3_A",
                InMemoryJavaCompiler.compile("Vis3_A", Vis3_A_src), System.getProperty("test.classes"));

        new OutputAnalyzer(ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Xbootclasspath/a:.",
                "Vis3_A")
            .start())
            .shouldHaveExitValue(0)
            .shouldContain("XbootcpNoVisibility PASSED");
    }
}
