/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.collection;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 *
 * @requires (vm.gc == "G1" | vm.gc == null)
 *           & vm.opt.ExplicitGCInvokesConcurrent != false
 * @library /test/lib /test/jdk
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -Xmx32m -Xmn8m -XX:+UseG1GC -XX:+ExplicitGCInvokesConcurrent jdk.jfr.event.gc.collection.TestGCEventMixedWithG1ConcurrentMark
 * good debug flags: -Xlog:gc+heap=trace,gc*=debug
 */
// TODO: Try to run without: -XX:+UnlockExperimentalVMOptions XX:-UseFastUnorderedTimeStamps
public class TestGCEventMixedWithG1ConcurrentMark {
    public static void main(String[] args) throws Throwable {
        GCEventAll.doTest();
    }
}
