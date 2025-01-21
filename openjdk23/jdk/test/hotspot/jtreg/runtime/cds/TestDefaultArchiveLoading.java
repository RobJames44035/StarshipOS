/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test id=nocoops_nocoh
 * @summary Test Loading of default archives in all configurations
 * @requires vm.cds
 * @requires vm.bits == 64
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestDefaultArchiveLoading nocoops_nocoh
 */

/**
 * @test id=nocoops_coh
 * @summary Test Loading of default archives in all configurations (requires --enable-cds-archive-coh)
 * @requires vm.cds
 * @requires vm.bits == 64
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestDefaultArchiveLoading nocoops_coh
 */

/**
 * @test id=coops_nocoh
 * @summary Test Loading of default archives in all configurations
 * @requires vm.cds
 * @requires vm.bits == 64
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestDefaultArchiveLoading coops_nocoh
 */

/**
 * @test id=coops_coh
 * @summary Test Loading of default archives in all configurations (requires --enable-cds-archive-coh)
 * @requires vm.cds
 * @requires vm.bits == 64
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestDefaultArchiveLoading coops_coh
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.test.lib.Platform;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import jtreg.SkippedException;

public class TestDefaultArchiveLoading {

    private static String archiveName(String archiveSuffix) {
        return "classes" + archiveSuffix + ".jsa";
    }

    private static Path archivePath(String archiveSuffix) {
        return Paths.get(System.getProperty("java.home"), "lib",
                         "server", archiveName(archiveSuffix));
    }

    private static boolean isCOHArchiveAvailable(char coops, char coh,
                                                 String archiveSuffix) throws Exception {
        Path archive= archivePath(archiveSuffix);
        return Files.exists(archive);
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            throw new RuntimeException("Expected argument");
        }

        String archiveSuffix;
        char coh, coops;

        switch (args[0]) {
            case "nocoops_nocoh":
                coh = coops = '-';
                archiveSuffix = "_nocoops";
                break;
            case "nocoops_coh":
                coops = '-';
                coh = '+';
                archiveSuffix = "_nocoops_coh";
                if (!isCOHArchiveAvailable(coops, coh, archiveSuffix)) {
                    throw new SkippedException("Skipping test due to " +
                                               archivePath(archiveSuffix).toString() + " not available");
                }
                break;
            case "coops_nocoh":
                coops = '+';
                coh = '-';
                archiveSuffix = "";
                break;
            case "coops_coh":
                coh = coops = '+';
                archiveSuffix = "_coh";
                if (!isCOHArchiveAvailable(coops, coh, archiveSuffix)) {
                    throw new SkippedException("Skipping test due to " +
                                               archivePath(archiveSuffix).toString() + " not available");
                }
                break;
            default: throw new RuntimeException("Invalid argument " + args[0]);
        }

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-XX:+UnlockExperimentalVMOptions",
                "-XX:" + coh + "UseCompactObjectHeaders",
                "-XX:" + coops + "UseCompressedOops",
                "-Xlog:cds",
                "-Xshare:on", // fail if we cannot load archive
                "-version");

        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);

        output.shouldContain(archiveName(archiveSuffix));
    }
}
