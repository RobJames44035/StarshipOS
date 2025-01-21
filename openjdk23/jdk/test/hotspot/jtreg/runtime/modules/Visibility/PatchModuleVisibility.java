/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Ensure that a newly introduced java.base package placed within the --patch-module
 *          directory is considered part of the boot loader's visibility boundary
 * @requires !(os.family == "windows")
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver PatchModuleVisibility
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.helpers.ClassFileInstaller;

public class PatchModuleVisibility {

    public static void main(String[] args) throws Throwable {

      String Vis2_B_src =
              "package p2;" +
              "public class Vis2_B {" +
              "    public void m() {" +
              "        System.out.println(\"In B's m()\");" +
              "    }" +
              "}";

      String Vis2_A_src =
              "import p2.*;" +
              "public class Vis2_A {" +
              "    public static void main(String args[]) throws Exception {" +
                      // Try loading a class within a newly introduced java.base
                      // package.  Make sure the class can be found via --patch-module.
              "        try {" +
              "            p2.Vis2_B b = new p2.Vis2_B();" +
              "            if (b.getClass().getClassLoader() != null) {" +
              "                throw new RuntimeException(\"PatchModuleVisibility FAILED - class B " +
                                                           "should be loaded by boot class loader\\n\");" +
              "            }" +
              "            b.m();" +
              "        } catch (Throwable e) {" +
              "            throw new RuntimeException(\"PatchModuleVisibility FAILED - test " +
                                                       "should not throw an error or exception\\n\");" +
              "        }" +
              "        System.out.println(\"PatchModuleVisibility PASSED\\n\");" +
              "    }" +
              "}";

      ClassFileInstaller.writeClassToDisk("p2/Vis2_B",
          InMemoryJavaCompiler.compile("p2.Vis2_B", Vis2_B_src), System.getProperty("test.classes"));
      ClassFileInstaller.writeClassToDisk("p2/Vis2_B", "mods2/java.base");

      ClassFileInstaller.writeClassToDisk("Vis2_A",
          InMemoryJavaCompiler.compile("Vis2_A", Vis2_A_src), System.getProperty("test.classes"));

      // Make sure the classes are actually being loaded from mods2
      Files.delete(Paths.get(System.getProperty("test.classes") +  File.separator +
                                                           "p2" + File.separator + "Vis2_B.class"));

      new OutputAnalyzer(ProcessTools.createLimitedTestJavaProcessBuilder(
              "--patch-module=java.base=mods2/java.base",
              "--add-exports=java.base/p2=ALL-UNNAMED",
              "Vis2_A")
          .start()).shouldHaveExitValue(0);
    }
}
