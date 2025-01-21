/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8183543
 * @summary C2 compilation often fails on aarch64 with "failed spill-split-recycle sanity check"
 *
 * @library /test/lib
 *
 * @build jdk.test.whitebox.WhiteBox
 *
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 *
 * @run main/othervm -Xbatch
 *                   -XX:-Inline
 *                   -XX:-TieredCompilation
 *                   -XX:+PreserveFramePointer
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:.
 *                   compiler.regalloc.TestC2IntPressure
 */

package compiler.regalloc;

import jdk.test.whitebox.WhiteBox;

public class TestC2IntPressure {

  static volatile int vol_f;

  static void not_inlined() {
    // Do nothing
  }

  static int test(TestC2IntPressure arg) {
    TestC2IntPressure a = arg;
    int res = 0;
    not_inlined();
    res = a.vol_f;
    return res;
  }

  public static void main(String args[]) {
    TestC2IntPressure arg = new TestC2IntPressure();
    for (int i = 0; i < 10000; i++) {
      test(arg);
    }
    try {
      var method = TestC2IntPressure.class.getDeclaredMethod("test", TestC2IntPressure.class);
      if (!WhiteBox.getWhiteBox().isMethodCompiled(method)) {
        throw new Error("test method didn't get compiled");
      }
    } catch (NoSuchMethodException e) {
      throw new Error("TESTBUG : " + e, e);
    }
  }
}

