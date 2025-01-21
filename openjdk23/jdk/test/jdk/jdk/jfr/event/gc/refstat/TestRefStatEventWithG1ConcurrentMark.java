/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.refstat;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires (vm.gc == "G1" | vm.gc == null)
 *           & vm.opt.ExplicitGCInvokesConcurrent != false
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -Xlog:gc+heap=trace,gc*=debug -XX:+UseG1GC -XX:+ExplicitGCInvokesConcurrent jdk.jfr.event.gc.refstat.TestRefStatEventWithG1ConcurrentMark
 */
public class TestRefStatEventWithG1ConcurrentMark {
    public static void main(String[] args) throws Exception {
        RefStatEvent.test(GCHelper.gcG1Old);
    }
}
