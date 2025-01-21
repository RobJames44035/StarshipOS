/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8309978
 * @summary [x64] Fix useless padding
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @requires (os.simpleArch == "x64")
 * @run driver compiler.c2.irTests.TestPadding
 */

public class TestPadding {
    public static void main(String[] args) {
        TestFramework.runWithFlags("-XX:+IntelJccErratumMitigation");
    }

    @Run(test = "test")
    public static void test_runner() {
        test(42);
        tpf.b1++; // to take both branches in test()
    }

    @Test
    @IR(counts = { IRNode.NOP, "<=1" })
    static int test(int i) {
        TestPadding tp = tpf;
        if (tp.b1 > 42) { // Big 'cmpb' instruction at offset 0x30
          tp.i1 = i;
        }
        return i;
    }

    static TestPadding t1;
    static TestPadding t2;
    static TestPadding t3;
    static TestPadding t4;

    static TestPadding tpf = new TestPadding(); // Static field offset > 128

    int i1;

    long l1;
    long l2;
    long l3;
    long l4;
    long l5;
    long l6;
    long l7;
    long l8;
    long l9;
    long l10;
    long l11;
    long l12;
    long l13;
    long l14;
    long l15;
    long l16;

    byte b1 = 1; // Field offset > 128
}
