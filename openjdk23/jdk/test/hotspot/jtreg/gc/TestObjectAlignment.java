/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc;

/**
 * @test TestObjectAlignment
 * @bug 8021823
 * @requires vm.bits == "64"
 * @summary G1: Concurrent marking crashes with -XX:ObjectAlignmentInBytes>=32 in 64bit VMs
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:+ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=8
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:+ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=16
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:+ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=32
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:+ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=64
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:+ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=128
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:+ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=256
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:-ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=8
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:-ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=16
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:-ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=32
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:-ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=64
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:-ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=128
 * @run main/othervm gc.TestObjectAlignment -Xmx20M -XX:-ExplicitGCInvokesConcurrent -XX:ObjectAlignmentInBytes=256
 */

public class TestObjectAlignment {

  public static byte[] garbage;

  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 10; i++) {
      garbage = new byte[1000];
      System.gc();
    }
  }
}
