/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */


/*
 * @test symbolsHsErr
 * @summary Test that function names are present in native frames of hs-err file as a proof that symbols are available.
 * @library /test/lib
 * @requires vm.flagless
 * @requires vm.debug
 * @requires os.family == "windows"
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestSymbolsInHsErrFile
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestSymbolsInHsErrFile {

  public static void main(String[] args) throws Exception {

    // Start a jvm and cause a SIGSEGV / ACCESS_VIOLATION
    ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
        "-XX:+UnlockDiagnosticVMOptions",
        "-Xmx100M",
        "-XX:-CreateCoredumpOnCrash",
        "-XX:ErrorHandlerTest=14",
        "-version");

    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    output.shouldNotHaveExitValue(0);

    // Verify that the hs_err problematic frame contains a function name that points to origin of the crash;
    // on Windows/MSVC, if symbols are present and loaded, we should see a ref to  either 'crash_with_segfault'
    // 'VMError::controlled_crash' depending on whether the compile optimizations (i.e. crash_with_segfault
    // was inlined or not):
    // # Problematic frame:
    // # V  [jvm.dll+0x.....]  crash_with_segfault+0x10
    // or
    // # V  [jvm.dll+0x.....]  VMError::controlled_crash+0x99
    //
    // If symbols could not be loaded, however, then the frame will contain not function name at all, i.e.
    // # Problematic frame:
    // # V  [jvm.dll+0x.....]
    // NB: this is not true for other OS/Compilers, where the functions names are present even with no symbols,
    // hence this test being restricted to Windows only.
    output.shouldMatch(("# V  \\[jvm.dll.*\\].*(crash_with_segfault|controlled_crash).*"));

  }

}


