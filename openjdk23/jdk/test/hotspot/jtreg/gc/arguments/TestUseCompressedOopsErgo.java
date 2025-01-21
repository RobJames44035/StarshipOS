/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestUseCompressedOopsErgoSerial
 * @bug 8010722
 * @summary Tests ergonomics for UseCompressedOops.
 * @requires vm.gc.Serial
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management/sun.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.arguments.TestUseCompressedOopsErgo -XX:+UseSerialGC
 */

/*
 * @test TestUseCompressedOopsErgoParallel
 * @bug 8010722
 * @summary Tests ergonomics for UseCompressedOops.
 * @requires vm.gc.Parallel
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management/sun.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.arguments.TestUseCompressedOopsErgo -XX:+UseParallelGC
 */

/*
 * @test TestUseCompressedOopsErgoG1
 * @bug 8010722
 * @summary Tests ergonomics for UseCompressedOops.
 * @requires vm.gc.G1
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management/sun.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.arguments.TestUseCompressedOopsErgo -XX:+UseG1GC
 */

/*
 * @test TestUseCompressedOopsErgoShenandoah
 * @bug 8010722
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management/sun.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.arguments.TestUseCompressedOopsErgo -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
 */

public class TestUseCompressedOopsErgo {

  public static void main(String args[]) throws Exception {
    if (!TestUseCompressedOopsErgoTools.is64bitVM()) {
      // this test is relevant for 64 bit VMs only
      return;
    }
    final String[] gcFlags = args;
    TestUseCompressedOopsErgoTools.checkCompressedOopsErgo(gcFlags);
  }
}
