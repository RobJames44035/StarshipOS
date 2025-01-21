/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.refstat;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "G1" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -Xlog:gc+heap=trace,gc*=debug -Xmx50m -Xmn2m -XX:+UseG1GC jdk.jfr.event.gc.refstat.TestRefStatEventWithG1New
 */
public class TestRefStatEventWithG1New {
    public static void main(String[] args) throws Exception {
        RefStatEvent.test(GCHelper.gcG1New);
    }
}
