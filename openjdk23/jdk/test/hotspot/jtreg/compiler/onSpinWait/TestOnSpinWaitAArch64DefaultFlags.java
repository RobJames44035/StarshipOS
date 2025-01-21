/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test TestOnSpinWaitAArch64DefaultFlags
 * @summary Check default values of '-XX:OnSpinWaitInst' and '-XX:OnSpinWaitInstCount' for AArch64 implementations.
 * @bug 8277137
 * @library /test/lib /
 *
 * @requires os.arch=="aarch64"
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.onSpinWait.TestOnSpinWaitAArch64DefaultFlags
 */

package compiler.onSpinWait;

import java.util.Iterator;
import java.util.List;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.whitebox.cpuinfo.CPUInfo;

public class TestOnSpinWaitAArch64DefaultFlags {
    private static boolean isCPUModelNeoverseN1(String cpuModel) {
        return cpuModel.contains("0xd0c");
    }

    private static void checkFinalFlagsEqualTo(ProcessBuilder pb, String expectedOnSpinWaitInstValue, String expectedOnSpinWaitInstCountValue) throws Exception {
        OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());
        analyzer.shouldHaveExitValue(0);

        Iterator<String> iter = analyzer.asLines().listIterator();
        String line = null;
        boolean hasExpectedOnSpinWaitInstValue = false;
        boolean hasExpectedOnSpinWaitInstCountValue = false;
        while (iter.hasNext()) {
            line = iter.next();
            if (!hasExpectedOnSpinWaitInstValue && line.contains("ccstr OnSpinWaitInst")) {
                hasExpectedOnSpinWaitInstValue = line.contains("= " + expectedOnSpinWaitInstValue);
            }

            if (!hasExpectedOnSpinWaitInstCountValue && line.contains("uint OnSpinWaitInstCount")) {
                hasExpectedOnSpinWaitInstCountValue = line.contains("= " + expectedOnSpinWaitInstCountValue);
            }
        }
        if (!hasExpectedOnSpinWaitInstValue) {
            System.out.println(analyzer.getOutput());
            throw new RuntimeException("OnSpinWaitInst with the expected value '" + expectedOnSpinWaitInstValue + "' not found.");
        }
        if (!hasExpectedOnSpinWaitInstCountValue) {
            System.out.println(analyzer.getOutput());
            throw new RuntimeException("OnSpinWaitInstCount with the expected value '" + expectedOnSpinWaitInstCountValue + "' not found.");
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> cpuFeatures = CPUInfo.getFeatures();
        if (cpuFeatures.isEmpty()) {
            System.out.println("Skip because no CPU features are available.");
            return;
        }

        final String cpuModel = cpuFeatures.get(0);

        if (isCPUModelNeoverseN1(cpuModel)) {
            checkFinalFlagsEqualTo(ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintFlagsFinal", "-version"),
                "isb", "1");
            checkFinalFlagsEqualTo(ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+UnlockDiagnosticVMOptions", "-XX:OnSpinWaitInstCount=2", "-XX:+PrintFlagsFinal", "-version"),
                "isb", "2");
        } else {
            System.out.println("Skip because no defaults for CPU model: " + cpuModel);
        }
    }
}
