/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8074981
 * @summary Add C2 x86 Superword support for scalar sum reduction optimizations : float test
 * @library /test/lib /
 * @run driver compiler.loopopts.superword.SumRed_Float
 */

package compiler.loopopts.superword;

import compiler.lib.ir_framework.*;

public class SumRed_Float {
    public static void main(String[] args) throws Exception {
        TestFramework framework = new TestFramework();
        framework.addFlags("-XX:+IgnoreUnrecognizedVMOptions",
                           "-XX:LoopUnrollLimit=250",
                           "-XX:CompileThresholdScaling=0.1");
        int i = 0;
        Scenario[] scenarios = new Scenario[6];
        for (String reductionSign : new String[] {"+", "-"}) {
            for (int maxUnroll : new int[] {4, 8, 16}) {
                scenarios[i] = new Scenario(i, "-XX:" + reductionSign + "SuperWordReductions",
                                               "-XX:LoopMaxUnroll=" + maxUnroll);
                i++;
            }
        }
        framework.addScenarios(scenarios);
        framework.start();
    }

    @Run(test = {"sumReductionImplement"},
         mode = RunMode.STANDALONE)
    public static void runTests() throws Exception {
        float[] a = new float[256 * 1024];
        float[] b = new float[256 * 1024];
        float[] c = new float[256 * 1024];
        sumReductionInit(a, b, c);
        float total = 0;
        float valid = (float) 4.611686E18;
        for (int j = 0; j < 2000; j++) {
            total = sumReductionImplement(a, b, c, total);
        }
        if (total == valid) {
            System.out.println("Success");
        } else {
            System.out.println("Invalid sum of elements variable in total: " + total);
            System.out.println("Expected value = " + valid);
            throw new Exception("Failed");
        }
    }

    public static void sumReductionInit(
            float[] a,
            float[] b,
            float[] c) {
        for (int j = 0; j < 1; j++) {
            for (int i = 0; i < a.length; i++) {
                a[i] = i * 1 + j;
                b[i] = i * 1 - j;
                c[i] = i + j;
            }
        }
    }

    @Test
    @IR(applyIf = {"SuperWordReductions", "false"},
        failOn = {IRNode.ADD_REDUCTION_VF})
    @IR(applyIfCPUFeature = {"sse2", "true"},
        applyIfAnd = {"SuperWordReductions", "true", "LoopMaxUnroll", ">= 8"},
        counts = {IRNode.ADD_REDUCTION_VF, ">= 1"})
    public static float sumReductionImplement(
            float[] a,
            float[] b,
            float[] c,
            float total) {
        for (int i = 0; i < a.length; i++) {
            total += (a[i] * b[i]) + (a[i] * c[i]) + (b[i] * c[i]);
        }
        return total;
    }

}
