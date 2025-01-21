/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.refstat;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "Parallel" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -Xlog:gc+heap=trace,gc*=debug -XX:+UseParallelGC jdk.jfr.event.gc.refstat.TestRefStatEventWithParallelOld
 */
public class TestRefStatEventWithParallelOld {
    public static void main(String[] args) throws Exception {
        RefStatEvent.test(GCHelper.gcParallelOld);
    }
}
