/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.c2.irTests;

import jdk.test.lib.Asserts;
import compiler.lib.ir_framework.*;

/*
 * @test
 * bug 8336860
 * @summary Verify codegen for CMoveL with constants 0 and 1
 * @library /test/lib /
 * @run driver compiler.c2.irTests.CMoveLConstants
 */
public class CMoveLConstants {
    public static void main(String[] args) {
        TestFramework.run();
    }

    @Test
    @IR(applyIfPlatform = {"x64", "true"}, counts = {IRNode.X86_CMOVEL_IMM01, "1"}, phase = CompilePhase.FINAL_CODE)
    public static long testSigned(int a, int b) {
        return a > b ? 1L : 0L;
    }

    @Test
    @IR(applyIfPlatform = {"x64", "true"}, counts = {IRNode.X86_CMOVEL_IMM01U, "1"}, phase = CompilePhase.FINAL_CODE)
    public static long testUnsigned(int a, int b) {
        return Integer.compareUnsigned(a, b) > 0 ? 1L : 0L;
    }

    @Test
    @IR(applyIfPlatform = {"x64", "true"}, counts = {IRNode.X86_CMOVEL_IMM01UCF, "1"}, phase = CompilePhase.FINAL_CODE)
    public static long testFloat(float a, float b) {
        return a > b ? 1L : 0L;
    }

    @DontCompile
    public void assertResults(int a, int b) {
        Asserts.assertEQ(a > b ? 1L : 0L, testSigned(a, b));
        Asserts.assertEQ(Integer.compareUnsigned(a, b) > 0 ? 1L : 0L, testUnsigned(a, b));
        Asserts.assertEQ((float) a > (float) b ? 1L : 0L, testFloat(a, b));
    }

    @Run(test = {"testSigned", "testUnsigned", "testFloat"})
    public void runMethod() {
        assertResults(10, 20);
        assertResults(20, 10);
    }
}
