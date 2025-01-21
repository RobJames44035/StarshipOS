/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 8241232
 * @requires vm.flagless
 * @requires vm.jvmci
 * @library /test/lib
 * @run driver TestInvalidTieredStopAtLevel
 */

import jdk.test.lib.process.ProcessTools;

public class TestInvalidTieredStopAtLevel {
    public static void main(String... args) throws Exception {
        ProcessTools.executeTestJava("-XX:+UnlockExperimentalVMOptions",
                                     "-XX:+UseJVMCICompiler",
                                     "-XX:+BootstrapJVMCI",
                                     "-XX:TieredStopAtLevel=1",
                                     "-Xcomp")
                .outputTo(System.out)
                .errorTo(System.out)
                .stdoutShouldNotContain("hs_err");
    }
}
