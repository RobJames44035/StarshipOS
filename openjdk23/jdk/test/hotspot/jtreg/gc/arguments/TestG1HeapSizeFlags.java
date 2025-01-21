/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestG1HeapSizeFlags
 * @bug 8006088
 * @summary Tests argument processing for initial and maximum heap size for the G1 collector
 * @key flag-sensitive
 * @requires vm.gc.G1 & vm.opt.MinHeapSize == null & vm.opt.MaxHeapSize == null & vm.opt.InitialHeapSize == null
 * @requires vm.compMode != "Xcomp"
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver gc.arguments.TestG1HeapSizeFlags
 * @author thomas.schatzl@oracle.com
 */

public class TestG1HeapSizeFlags {

  public static void main(String args[]) throws Exception {
    final String gcName = "-XX:+UseG1GC";

    TestMaxHeapSizeTools.checkMinInitialMaxHeapFlags(gcName);

    TestMaxHeapSizeTools.checkGenMaxHeapErgo(gcName);
  }
}
