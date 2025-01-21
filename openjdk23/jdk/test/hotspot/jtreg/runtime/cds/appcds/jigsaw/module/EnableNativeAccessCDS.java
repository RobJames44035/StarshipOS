/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8342089
 * @summary Test consistency of --enable-native-access option for CDS dump time and runtime
 * @requires vm.cds.write.archived.java.heap
 * @requires vm.flagless
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @run driver EnableNativeAccessCDS
 */

import jdk.test.lib.process.OutputAnalyzer;

public class EnableNativeAccessCDS {
    public static void main(String[] args) throws Exception {
        final String module0 = "java.base";
        final String module1 = "jdk.httpserver";
        final String disabledOptimizedModule = "Disabling optimized module handling";
        final String loggingOption = "-Xlog:cds=debug";

        String archiveName = TestCommon.getNewArchiveName("native-access");
        TestCommon.setCurrentArchiveName(archiveName);

        // dump a base archive with --enable-native-access=java.base
        OutputAnalyzer oa = TestCommon.dumpBaseArchive(
            archiveName,
            loggingOption,
            "--enable-native-access", module0,
            "-version");
        oa.shouldHaveExitValue(0);

        // same module specified during runtime
        oa = TestCommon.execCommon(
            loggingOption,
            "--enable-native-access", module0,
            "-version");
        oa.shouldHaveExitValue(0)
          .shouldContain("use_full_module_graph = true");

        // different module specified during runtime
        oa = TestCommon.execCommon(
            loggingOption,
            "--enable-native-access", module1,
            "-version");
        oa.shouldHaveExitValue(0)
          .shouldContain("Mismatched values for property jdk.module.enable.native.access: runtime jdk.httpserver dump time java.base")
          .shouldContain(disabledOptimizedModule);

        // no module specified during runtime
        oa = TestCommon.execCommon(
            loggingOption,
            "-version");
        oa.shouldHaveExitValue(0)
          .shouldContain("Mismatched values for property jdk.module.enable.native.access: java.base specified during dump time but not during runtime")
          .shouldContain(disabledOptimizedModule);

        // dump an archive without --enable-native-access option
        archiveName = TestCommon.getNewArchiveName("no-native-access-modules");
        TestCommon.setCurrentArchiveName(archiveName);
        oa = TestCommon.dumpBaseArchive(
            archiveName,
            loggingOption,
            "-version");
        oa.shouldHaveExitValue(0);

        // run with --enable-native-access
        oa = TestCommon.execCommon(
            loggingOption,
            "--enable-native-access", module0,
            "-version");
        oa.shouldHaveExitValue(0)
          .shouldContain("Mismatched values for property jdk.module.enable.native.access: java.base specified during runtime but not during dump time")
          .shouldContain(disabledOptimizedModule);

        // dump an archive with multiple modules with native access
        archiveName = TestCommon.getNewArchiveName("multiple-native-access-modules");
        TestCommon.setCurrentArchiveName(archiveName);
        oa = TestCommon.dumpBaseArchive(
            archiveName,
            loggingOption,
            "--enable-native-access", module0 + "," + module1,
            "-version");
        oa.shouldHaveExitValue(0);

        // same module specified during runtime but in a different order
        oa = TestCommon.execCommon(
            loggingOption,
            "--enable-native-access", module1 + "," + module0,
            "-version");
        oa.shouldHaveExitValue(0)
          .shouldContain("use_full_module_graph = true");

        // same module specified during runtime but specifying --enable-native-access twice
        oa = TestCommon.execCommon(
            loggingOption,
            "--enable-native-access", module0,
            "--enable-native-access", module1,
            "-version");
        oa.shouldHaveExitValue(0)
          .shouldContain("use_full_module_graph = true");

        // run with only one same module
        oa = TestCommon.execCommon(
            loggingOption,
            "--enable-native-access", module0,
            "-version");
        oa.shouldHaveExitValue(0)
          .shouldContain("Mismatched values for property jdk.module.enable.native.access: runtime java.base dump time java.base,jdk.httpserver")
          .shouldContain(disabledOptimizedModule);
    }
}
