/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test TestOnSpinWaitEnableDisable
 * @summary Test to ensure basic functioning of java.lang.Thread.onSpinWait
 * @bug 8157683
 *
 * @run main compiler.onSpinWait.TestOnSpinWaitEnableDisable
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:DisableIntrinsic=_onSpinWait
 *                   compiler.onSpinWait.TestOnSpinWaitEnableDisable
 */

package compiler.onSpinWait;

public class TestOnSpinWaitEnableDisable {
    public static void main(String[] args) {
        for (int i = 0; i < 50_000; i++) {
            java.lang.Thread.onSpinWait();
        }
    }
}
