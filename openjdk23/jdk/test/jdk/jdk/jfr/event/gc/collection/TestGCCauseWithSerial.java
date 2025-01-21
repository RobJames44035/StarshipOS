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
 * @requires vm.gc == "Serial" | vm.gc == null
 * @library /test/lib /test/jdk
 *
 * @run driver jdk.jfr.event.gc.collection.TestGCCauseWithSerial
 */
public class TestGCCauseWithSerial {
    public static void main(String[] args) throws Exception {
        String testID = "Serial";
        String[] vmFlags = {"-XX:+UseSerialGC"};
        String[] gcNames = {GCHelper.gcDefNew, GCHelper.gcSerialOld};
        String[] gcCauses = {"Allocation Failure", "System.gc()", "GCLocker Initiated GC",
                             "CodeCache GC Threshold"};
        GCGarbageCollectionUtil.test(testID, vmFlags, gcNames, gcCauses);
    }
}
