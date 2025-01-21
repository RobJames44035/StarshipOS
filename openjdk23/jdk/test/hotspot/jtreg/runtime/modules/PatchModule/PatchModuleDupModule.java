/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Module system initialization exception results if a module is specificed twice to --patch-module.
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @run driver PatchModuleDupModule
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class PatchModuleDupModule {

  // The module system initialization should generate an ExceptionInInitializerError
  // if --patch-module is specified with the same module more than once.

  public static void main(String args[]) throws Exception {
    ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
      "--patch-module=module_one=module_one_dir",
      "--patch-module=module_one=module_one_dir",
      "-version");
    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    output.shouldContain("java.lang.ExceptionInInitializerError");
    output.shouldHaveExitValue(1);
  }
}
