/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.collection;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 *
 * @requires vm.gc == "G1" | vm.gc == null
 * @requires vm.opt.ExplicitGCInvokesConcurrent != true
 * @library /test/lib /test/jdk
 *
 * @run driver jdk.jfr.event.gc.collection.TestGCCauseWithG1FullCollection
 */
public class TestGCCauseWithG1FullCollection {
    public static void main(String[] args) throws Exception {
        String testID = "G1FullCollection";
        String[] vmFlags = {"-XX:+UseG1GC"};
        String[] gcNames = {GCHelper.gcG1New, GCHelper.gcG1Old, GCHelper.gcG1Full};
        String[] gcCauses = {"Metadata GC Threshold", "G1 Evacuation Pause", "G1 Preventive Collection",
                             "G1 Compaction Pause", "CodeCache GC Threshold", "System.gc()"};
        GCGarbageCollectionUtil.test(testID, vmFlags, gcNames, gcCauses);
    }
}
