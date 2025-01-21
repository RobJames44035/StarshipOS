/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.objectcount;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires (vm.gc == "G1" | vm.gc == null)
 *           & vm.opt.ExplicitGCInvokesConcurrent != false
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:+ExplicitGCInvokesConcurrent -XX:MarkSweepDeadRatio=0 -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+IgnoreUnrecognizedVMOptions jdk.jfr.event.gc.objectcount.TestObjectCountAfterGCEventWithG1ConcurrentMark
 */
public class TestObjectCountAfterGCEventWithG1ConcurrentMark {
    public static void main(String[] args) throws Exception {
        ObjectCountAfterGCEvent.test(GCHelper.gcG1Old);
    }
}
