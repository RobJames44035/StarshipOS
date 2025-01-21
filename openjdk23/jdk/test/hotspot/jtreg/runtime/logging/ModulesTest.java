/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary -Xlog:module should emit logging output
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver ModulesTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class ModulesTest {
    // If modules in the system image have been archived in CDS, no Modules will
    // be dynamically created at runtime. Disable CDS so all of the expected messages
    // are printed.
    private static String XSHARE_OFF = "-Xshare:off";

    public static void main(String[] args) throws Exception {
        testModuleTrace("-Xlog:module=trace", XSHARE_OFF, "-version");
        testModuleLoad("-Xlog:module+load", XSHARE_OFF, "-version");
        testModuleUnload("-Xlog:module+unload", XSHARE_OFF, "-version");

        // same as -Xlog:module+load -Xlog:module+unload
        testModuleLoad("-verbose:module", XSHARE_OFF, "-version");
    }

    static void testModuleTrace(String... args) throws Exception {
        OutputAnalyzer output = run(args);
        output.shouldContain("define_javabase_module(): Definition of module:");
        output.shouldContain("define_javabase_module(): creation of package");
        output.shouldContain("define_module(): creation of module");
        output.shouldContain("define_module(): creation of package");
        output.shouldContain("set_bootloader_unnamed_module(): recording unnamed");
        output.shouldContain("add_module_exports(): package");
        output.shouldContain("add_reads_module(): Adding read from module");
        output.shouldContain("Setting package: class:");
        output.shouldHaveExitValue(0);
    }

    static void testModuleLoad(String... args) throws Exception {
        OutputAnalyzer output = run(args);
        output.shouldContain("java.base location:");
        output.shouldContain("java.management location:");
        output.shouldHaveExitValue(0);
    }

    static void testModuleUnload(String... args) throws Exception {
        OutputAnalyzer output = run(args);
        output.shouldHaveExitValue(0);
    }

    static OutputAnalyzer run(String... args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(args);
        return new OutputAnalyzer(pb.start());
    }
}

